package mix_db.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * Bar
 */
public class Bar {

    private final int barID;
    private final String barName;
    private final String city;
    private final String address;

    /**
     * constructor
     * @param barID barID
     * @param barName barName
     * @param city city
     * @param address address
     */
    public Bar(int barID, String barName, String city, String address) {
        this.barID = barID;
        this.barName = barName;
        this.city = city;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Bar [barID=" + barID + ", barName=" + barName + ", city=" + city + 
            ", address=" + address + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + barID;
        result = prime * result + ((barName == null) ? 0 : barName.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bar other = (Bar) obj;
        if (barID != other.barID)
            return false;
        if (barName == null) {
            if (other.barName != null)
                return false;
        } else if (!barName.equals(other.barName))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        return true;
    }

    /**
     * DAO object for Bar
     */
    public static final class DAO {

        /**
         * creates a new empty bar
         * @param connection .
         * @param bar .
         */
        public static void createBar(Connection connection, Bar bar) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection,
                    Queries.CREATE_BAR, bar.barName, bar.city, bar.address);
            ) {
                statement.executeUpdate();
            } catch(final Exception e) {
                throw new DAOException(e);
            }
        } 

        /**
         * inserts a new drink as barCreation using transactions. 
         * @param connection .
         * @param d the drink to insert
         * @param keywords the keywords to identify the drink
         * @return {@code true} if can insert the drink, {@code false} otherwise
         */
        public static void createDrink(Connection connection, Drink d, int barID, List<Composition> composition, List<String> keywords) {
            
            try {
                // !transaction start
                connection.setAutoCommit(false);
                int correctDrinkId;
                
                // *creates the drink
                try(
                    final var statement = DatabaseConnection.prepareWithKeys(connection, 
                        Queries.CREATE_DRINK, 
                    d.getName(), d.getDescription(), d.getImagePath(), d.getCategoryName());
                ) {
                    statement.executeUpdate();

                    // ? the drinkID is currently -1: before linking the program should get the correct id
                    try(final var rs = statement.getGeneratedKeys()) {
                        if(rs.next()) {
                            correctDrinkId = rs.getInt(1);
                        } else {
                            throw new SQLException("Insert failed: no drinkID specified");
                        }
                    }

                } catch(Exception e) {
                    throw new DAOException(e);
                }

                // *links drink to user
                try (
                    final var statement = DatabaseConnection.prepare(connection, 
                        Queries.LINK_DRINK_WITH_BAR,
                    correctDrinkId, barID); 
                ) {
                    statement.executeUpdate();
                } catch(final Exception e) {
                    throw new DAOException(e);
                }

                // *inserts ingredients
                for(var c: composition) {
                    final int correctIngredientName = Ingredient.DAO.getOrCreateId(connection, c.getIngredientName());

                    try (
                        final var statement = DatabaseConnection.prepare(connection, 
                            Queries.INSERT_DRINK_INGREDIENTS, 
                            correctIngredientName, correctDrinkId, c.getQuantity(), c.getMeasureUnit());
                        ) {
                            statement.executeUpdate();
                        } catch(final Exception e) {
                            throw new DAOException(e);
                        }
                }

                // *inserts keywords
                for(String k : keywords) {
                    final int correctKeyword = Tag.DAO.getOrCreateId(connection, k);

                    try (final var statement = DatabaseConnection.prepare(connection, 
                            Queries.INSERT_DRINK_KEYWORD, 
                            correctDrinkId, correctKeyword);
                    ) {
                        statement.executeUpdate();
                    }  
                }

                // *increments ingredients timeUsed counter
                for(var c: composition) {
                    try (
                        final var statement = DatabaseConnection.prepare(connection, 
                            Queries.UPDATE_INGREDIENT_TIMEUSED_COUNTER, 
                        c.getIngredientName())
                    ) {
                        statement.executeUpdate();
                    } catch(final Exception e) {
                        throw new DAOException(e);
                    }
                }
                
                // ! COMMIT
                connection.commit();
                
            } catch (final Exception e) {
                // ?if this exception was thrown, then the transaction has to be interrupted:
                // ! transaction ends with rollback
                try {
                    connection.rollback();
                } catch(final SQLException rollBackException) {
                    throw new DAOException(rollBackException);
                }

                // and the DAOexception has to be thrown with the previews trace
                throw new DAOException(e);
            } finally {
                // if no exceptions were thrown, seta the autocommit true and commits the transacrion
                // ! transaction ends with commit
                try{
                    connection.setAutoCommit(true);
                } catch(SQLException autoCommitException) {
                    //ignores the exception
                }
            }
        }

        /**
         * adds a user to a bar
         * @param connection .
         * @param userID .
         * @param barID .
         * @return {@code true} if the user can be added to the bar, {@code false} otherwise
         */
        public static boolean addUserToBar(Connection connection, int userID, int barID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.ADD_EMPLOYEE, userID, barID);
            ) {
                return (statement.executeUpdate() == 1);
            } catch(final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * searches a bar by: 
         * @param connection .
         * @param name .
         * @param city .
         * @param address .
         * @return Optional of bar if exists, empty Optional otherwise
         */
        public static Optional<Bar> searchBar(Connection connection, String name, String city, String address) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.SEARCH_BAR, name, city, address);
                final ResultSet rs = statement.executeQuery();
            ) {
                if(rs.next()) {
                    final Bar b = new Bar(
                        rs.getInt("barID"), 
                        rs.getString("nomeBar"), 
                        rs.getString("città"), 
                        rs.getString("indirizzo")
                    );
                    return Optional.of(b);
                }
                return Optional.empty();
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * searches a bar by: 
         * @param connection .
         * @param name .
         * @param city .
         * @param address .
         * @return Optional of bar if exists, empty Optional otherwise
         */
        public static Optional<Bar> searchBar(Connection connection, int barID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.SEARCH_BAR, barID);
                final ResultSet rs = statement.executeQuery();
            ) {
                if(rs.next()) {
                    final Bar b = new Bar(
                        rs.getInt("barID"), 
                        rs.getString("nomeBar"), 
                        rs.getString("città"), 
                        rs.getString("indirizzo")
                    );
                    return Optional.of(b);
                }
                return Optional.empty();
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }


        /**
         * deletes a bar
         * @param connection .
         * @param barID .
         */
        public static boolean deleteBar(Connection connection, int barID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.DELETE_BAR, barID);
            ) {
                return (statement.executeUpdate() == 1);
            } catch(final Exception e) {
                throw new DAOException(e);
            }
        }
    }

    public int getBarID() {
        return barID;
    }

    public String getBarName() {
        return barName;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }
}
