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
         * searches a bar by: 
         * @param connection .
         * @param barID
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
         * checks if the user is employed
         * @param connection .
         * @param userID .
         * @return true if it is
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
         * gets the bar in whick the user is employed
         * @param connection .
         * @param userID .
         * @return an Opytional of bar
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
