package mix_db.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * Drink
 */
public class Drink {

    private final int drinkID;
    private final String name;
    private final String description;
    private final String imagePath;
    private final String categoryName;
    private final boolean isIBA;

    /**
     * constructor
     * @param drinkID .
     * @param name .
     * @param description .
     * @param imagePath .
     * @param categoryName .
     * @param isIBA .
     */
    public Drink(int drinkID, String name, String description, String imagePath, String categoryName, boolean isIBA) {
        this.drinkID = drinkID;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.categoryName = categoryName;
        this.isIBA = isIBA;
    }

    @Override
    public String toString() {
        return "Drink [drinkID=" + drinkID + ", name=" + name + ", description=" + description + ", imagePath="
                + imagePath + ", categoryName=" + categoryName + ", isIBA=" + isIBA + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + drinkID;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
        result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
        result = prime * result + (isIBA ? 1231 : 1237);
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
        Drink other = (Drink) obj;
        if (drinkID != other.drinkID)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (imagePath == null) {
            if (other.imagePath != null)
                return false;
        } else if (!imagePath.equals(other.imagePath))
            return false;
        if (categoryName == null) {
            if (other.categoryName != null)
                return false;
        } else if (!categoryName.equals(other.categoryName))
            return false;
        if (isIBA != other.isIBA)
            return false;
        return true;
    }

    /**
     * DAO for Drink
     */
    public static final class DAO {

        /**
         * inserts a new drink using transactions. 
         * @param connection .
         * @param d the drink to insert
         * @param keywords the keywords to identify the drink
         * @return {@code true} if can insert the drink, {@code false} otherwise
         */
        public static void createDrink(Connection connection, Drink d, int userID, List<Composition> composition, List<String> keywords) {
            
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
                        Queries.LINK_DRINK_WITH_USER,
                    correctDrinkId, userID); 
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

                // *increments user's creation counter
                try (
                    final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                        Queries.UPDATE_USER_CREATIONS_COUNTER, userID);
                ) {
                    statement.executeUpdate();
                } catch(final Exception e) {
                    throw new DAOException(e);
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
         * searches a drink by his name
         * @param connection .
         * @param drinkName .
         * @return an optional of drink
         */
        public static Optional<Drink> getDrink(Connection connection, int drinkID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.SEARCH_DRINK, drinkID);
                final var rs = statement.executeQuery();
            ) {
                if(rs.next()) {
                    final var d = new Drink(
                        rs.getInt("drinkID"), 
                        rs.getString("nome"),
                        rs.getString("descrizione"), 
                        rs.getString("foto"), 
                        rs.getString("nomeCategoria"),
                        rs.getBoolean("IBA")
                        );

                    return Optional.of(d);
                }
                return Optional.empty();
            } catch (final Exception e) {
                throw new DAOException(e);
            }

        }

        /**
         * saves a drink as favourite
         * @param connection
         * @param drinkID .
         * @param userID .
         * @return true if the drink is not already in the favs, false otherwise
         */
        @Deprecated
        public static boolean saveAsFavourite(Connection connection, int drinkID, int userID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.SAVE_FAVOURITE, drinkID, userID);
            ) {
                return (statement.executeUpdate() == 1);
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * ! FOR TESTS
         */
        @Deprecated
        public static void deleteByCategoryName(Connection connection, String categoryName) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.DELETE_DRINK_BY_CATEGORYNAME, 
                    categoryName);
            ) {
                statement.executeUpdate();
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * searches a drink by keywords
         * @param connection .
         * @param keyword .
         * @return a List of drinks
         */
        public static List<Drink> searchByKeyword(Connection connection, String keyword) {
            final List<Drink> drinks = new LinkedList<>();

            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.SEARCH_BY_KEYWORD, 
                    keyword);
                final var rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    final var d = new Drink(
                        rs.getInt("drinkID"), 
                        rs.getString("nome"),
                        rs.getString("descrizione"), 
                        rs.getString("foto"), 
                        rs.getString("nomeCategoria"),
                        rs.getBoolean("IBA")
                        );

                        drinks.add(d);
                    }

                    return drinks;
                } catch(final Exception e) {
                    throw new DAOException(e);
                }
            }

            /**
             * gets the creator of the drink
             * @param connection .
             * @param userID .
             * @return an optional of user
             */
            public static Optional<User> getCreator(Connection connection, int drinkID) {
                try(
                    final var statement = DatabaseConnection.prepare(connection, 
                        Queries.CREATE_DRINK, 
                        drinkID);
                    final var rs = statement.executeQuery();
                ) {
                    if(rs.next()) {
                        final User u = new User(
                            rs.getInt("userID"),
                            rs.getString("email"),
                            rs.getString("password"), 
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getDate("dataNascita"),
                            rs.getString("ruoloUtente"),
                            rs.getDate("dataIscrizione"),
                            rs.getInt("numeroRicetteCreate"),
                            rs.getInt("numeroRecensioniPositive"),
                            rs.getInt("numeroRecensioniEffettuate")
                        );
                        return Optional.of(u);
                    }
                    return Optional.empty();
                } catch(Exception e) {
                    throw new DAOException(e);
                }
            }
        }        

    public int getDrinkID() {
        return drinkID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public boolean isIBA() {
        return isIBA;
    }
}
