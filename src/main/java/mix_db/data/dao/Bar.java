package mix_db.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * Represents a bar entity with its unique identifier, name, city, and address.
 * This class provides methods to access the bar's properties and includes a static
 * inner class {@code DAO} for database operations related to Bar objects.
 */
public class Bar {

    private final int barID;
    private final String barName;
    private final String city;
    private final String address;

    /**
     * constructs a new Bar instance.
     * @param barID the unique identifier of the bar
     * @param barName the name of the bar
     * @param city the city where the bar is located
     * @param address the address of the bar
     */
    public Bar(int barID, String barName, String city, String address) {
        this.barID = barID;
        this.barName = barName;
        this.city = city;
        this.address = address;
    }

    /**
     * returns a string representation of the Bar object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Bar [barID=" + barID + ", barName=" + barName + ", city=" + city + 
            ", address=" + address + "]";
    }

    /**
     * computes a hash code for this Bar object.
     *
     * @return a hash code value for this object
     */
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

    /**
     * compares this Bar object to the specified object.
     * The result is {@code true} if and only if the argument is not {@code null}
     * and is a {@code Bar} object that has the same barID, barName, city, and address
     * as this object.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
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
         * creates a new bar in the database with the provided details.
         * @param connection the database connection
         * @param bar the Bar object containing the details to create
         * @throws DAOException if a database access error occurs
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
         * adds a user to a bar, establishing an employment relationship.
         * @param connection the database connection
         * @param userID the ID of the user to add
         * @param barID the ID of the bar to add the user to
         * @return {@code true} if the user can be added to the bar, {@code false} otherwise
         * @throws DAOException if a database access error occurs
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
         * searches a bar by its name, city, and address.
         * @param connection the database connection
         * @param name the name of the bar to search for
         * @param city the city of the bar to search for
         * @param address the address of the bar to search for
         * @return Optional of bar if exists, empty Optional otherwise
         * @throws DAOException if a database access error occurs
         */
        public static Optional<Bar> searchBarByParams(Connection connection, String name, String city, String address) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.SEARCH_BAR_BY_PARAMETERS, name, city, address);
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
         * searches a bar by its unique identifier.
         * @param connection the database connection
         * @param barID the unique identifier of the bar to search for
         * @return Optional of bar if exists, empty Optional otherwise
         * @throws DAOException if a database access error occurs
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
         * retrieves a list of all users employed in a specific bar.
         *
         * @param connection the database connection
         * @param barID the unique identifier of the bar
         * @return a list of User objects employed in the specified bar
         * @throws DAOException if a database access error occurs
         */
        public static List<User> getUsersInBarList(Connection connection, int barID) {
            final List<User> users = new LinkedList<>();

            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.USERS_IN_BAR, barID);
                final ResultSet rs = statement.executeQuery();
            ) {
                while(rs.next()) {
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
                    users.add(u);
                }
                return new LinkedList<>(users);
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * checks if the user is employed in any bar.
         * @param connection the database connection
         * @param userID the unique identifier of the user
         * @return true if the user is employed in a bar, false otherwise
         * @throws DAOException if a database access error occurs
         */
        public static boolean isUserInABar(Connection connection, int userID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.IS_USER_IN_BAR, userID);
                final ResultSet rs = statement.executeQuery();
            ) {
                return rs.next();
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * gets the bar in which the user is employed.
         * @param connection the database connection
         * @param userID the unique identifier of the user
         * @return an Optional of Bar if the user is employed, empty Optional otherwise
         * @throws DAOException if a database access error occurs
         */
        public static Optional<Bar> getBarEmployed(Connection connection, int userID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.SEARCH_BAR_BY_EMPLOYEE, userID);
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
         * gets the bar in which a specific drink was created.
         * @param connection the database connection
         * @param drinkID the unique identifier of the drink
         * @return an Optional of Bar representing the creation bar, empty Optional otherwise
         * @throws DAOException if a database access error occurs
         */
        public static Optional<Bar> getCreationBar(Connection connection, int drinkID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.GET_DRINK_CREATOR_BAR, drinkID);
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
         * deletes a bar from the database.
         * @param connection the database connection
         * @param barID the unique identifier of the bar to delete
         * @return {@code true} if the bar was successfully deleted, {@code false} otherwise
         * @throws DAOException if a database access error occurs
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

    /**
     * gets the unique identifier of the bar.
     * @return the bar's ID
     */
    public int getBarID() {
        return barID;
    }

    /**
     * gets the name of the bar.
     * @return the bar's name
     */
    public String getBarName() {
        return barName;
    }

    /**
     * gets the city where the bar is located.
     * @return the bar's city
     */
    public String getCity() {
        return city;
    }

    /**
     * gets the address of the bar.
     * @return the bar's address
     */
    public String getAddress() {
        return address;
    }
}