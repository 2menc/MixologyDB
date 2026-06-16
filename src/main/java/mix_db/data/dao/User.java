package mix_db.data.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * User
 */
public class User {

    private final int userID;
    private final String email;
    private final String password;
    private final String name;
    private final String surname;
    private final Date birthDate;
    private final String userRole;
    private final Date subsctriptionDate;
    private final int numCreatedRecipes;
    private final int numPositiveReviews;
    private final int numCreatedReviews;

    /**constructor
    * @param userID userID
    * @param email email
    * @param password password
    * @param name name
    * @param surname surname
    * @param birthDate birthDate
    * @param userRole userRole
    * @param subsctriptionDate subsctriptionDate
    * @param numCreatedRecipes numCreatedRecipes
    * @param numPositiveReviews numPositiveReviews
    * @param numCreatedReviews numCreatedReviews
    */
    public User(int userID, String email, String password, String name, String surname, Date birthDate,
            String userRole, Date subsctriptionDate, int numCreatedRecipes, int numPositiveReviews,
            int numCreatedReviews) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.userRole = userRole;
        this.subsctriptionDate = subsctriptionDate;
        this.numCreatedRecipes = numCreatedRecipes;
        this.numPositiveReviews = numPositiveReviews;
        this.numCreatedReviews = numCreatedReviews;
    }

    @Override
    public String toString() {
        return "User [userID=" + userID + ", email=" + email + ", password=" + password + ", name=" + name
                + ", surname=" + surname + ", birthDate=" + birthDate + ", userRole=" + userRole
                + ", subsctriptionDate=" + subsctriptionDate + ", numCreatedRecipes=" + numCreatedRecipes
                + ", numPositiveReviews=" + numPositiveReviews + ", numCreatedReviews=" + numCreatedReviews + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + userID;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
        result = prime * result + ((userRole == null) ? 0 : userRole.hashCode());
        result = prime * result + ((subsctriptionDate == null) ? 0 : subsctriptionDate.hashCode());
        result = prime * result + numCreatedRecipes;
        result = prime * result + numPositiveReviews;
        result = prime * result + numCreatedReviews;
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
        User other = (User) obj;
        if (userID != other.userID)
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        if (birthDate == null) {
            if (other.birthDate != null)
                return false;
        } else if (!birthDate.equals(other.birthDate))
            return false;
        if (userRole == null) {
            if (other.userRole != null)
                return false;
        } else if (!userRole.equals(other.userRole))
            return false;
        if (subsctriptionDate == null) {
            if (other.subsctriptionDate != null)
                return false;
        } else if (!subsctriptionDate.equals(other.subsctriptionDate))
            return false;
        if (numCreatedRecipes != other.numCreatedRecipes)
            return false;
        if (numPositiveReviews != other.numPositiveReviews)
            return false;
        if (numCreatedReviews != other.numCreatedReviews)
            return false;
        return true;
    }

    /**
    * DAO object for User
    */
    public static final class DAO {

        /**
         * inserts a new user
         * @param connection db connection
         * @param u the user to insert
         * @return {@code true} if can add the user, {@code false} otherwise
         */
        public static boolean insertNewUser(Connection connection, User u) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.REGISTER_USER, 
                    u.email, u.password, u.name, u.surname, u.birthDate, u.userRole
                );
            ) {
                return (statement.executeUpdate() == 1);
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * logs in with email and password
         * @param connection the database query connection
         * @param email the user email address
         * @param password the hashing database password
         * @return an empty Optional if there is no such user in the db, 
         * an Optional of the searched User otherwise
         */
        public static Optional<User> getUser(Connection connection, String email, String password) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.LOGIN, email, password);
                final ResultSet rs = statement.executeQuery();
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
                
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * gets a full user knowing his id
         * @param connection the database connection
         * @param userID the integer user identifier
         * @return an empty Optional if there is no such user in the db, 
         * an Optional of the searched User otherwise
         */
        public static Optional<User> getUserFromID(Connection connection, int userID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.GET_USER_FROM_ID, userID);
                final ResultSet rs = statement.executeQuery();
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
                
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * gets a full user knowing his email
         * @param connection the database connection
         * @param email the user email address
         * @return an empty Optional if there is no such user in the db, 
         * an Optional of the searched User otherwise
         */
        public static Optional<User> getUserFromEmail(Connection connection, String email) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.GET_USER_FROM_EMAIL, email);
                final ResultSet rs = statement.executeQuery();
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
                
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * checks if a user exists
         * @param connection the database connection
         * @param email the user email address
         * @return false if there is no such user in the db, true otherwise
         */
        public static boolean userExists(Connection connection, String email) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.GET_USER_FROM_EMAIL, email);
                final ResultSet rs = statement.executeQuery();
            ) {
                return rs.next();                
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * gets a list of user's favourites
         * @param connection the database connection
         * @param userID the integer user identifier
         * @return the list
         */
        public static List<Drink> getFavourites(Connection connection, int userID) {
            final List<Drink> favs = new LinkedList<>();

            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.GET_FAVOURITES, userID);
                final ResultSet rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    final Drink d = new Drink( 
                        rs.getInt("drinkID"), 
                        rs.getString("nome"), 
                        rs.getString("descrizione"), 
                        rs.getString("foto"), 
                        rs.getString("nomeCategoria"),
                        rs.getBoolean("IBA")
                    );
                    favs.add(d);
                }
                return new LinkedList<>(favs);
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * sets a drink as favourite
         * @param connection the database connection
         * @param drinkID the integer drink identifier
         * @param userID the integer user identifier
         * @return true if success, false otherwise
         */
        public static boolean setFavourite(Connection connection, int drinkID, int userID) {
            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.SAVE_FAVOURITE,
                    drinkID, userID);
            ) {
                return (statement.executeUpdate() == 1);
            } catch(final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * removees a drink from favourites
         * @param connection the database connection
         * @param drinkID the integer drink identifier
         * @param userID the integer user identifier
         * @return true if success, false otherwise
         */
        public static boolean deleteFavourite(Connection connection, int drinkID, int userID) {
            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.REMOVE_FAVOURITE,
                    userID, drinkID);
            ) {
                return (statement.executeUpdate() == 1);
            } catch(final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * gets suggested drinks
         * @param connection the database connection
         * @param userID the integer user identifier
         * @param numberOfResults maximum results amount
         * @return a list of Drinks
         */
        public static List<Drink> getSuggestedDrinks(Connection connection, int userID, int numberOfResults) {
            final var drinks = new LinkedList<Drink>();

            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.SUGGEST_DRINKS, 
                    userID, userID, numberOfResults);
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
                return new LinkedList<>(drinks);
            } catch(Exception e) {
                throw new DAOException(e);
            }

        }
    }

    /**
     * gets the userID of this user
     * @return the userID integer
     */
    public int getUserID() {
        return userID;
    }

    /**
     * gets the email of this user
     * @return the email string
     */
    public String getEmail() {
        return email;
    }

    /**
     * gets the password of this user
     * @return the password string
     */
    public String getPassword() {
        return password;
    }

    /**
     * gets the name of this user
     * @return the name string
     */
    public String getName() {
        return name;
    }

    /**
     * gets the surname of this user
     * @return the surname string
     */
    public String getSurname() {
        return surname;
    }

    /**
     * gets the birthDate of this user
     * @return the birthDate sql Date
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * gets the userRole of this user
     * @return the userRole string
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * gets the subsctriptionDate of this user
     * @return the subsctriptionDate sql Date
     */
    public Date getSubsctriptionDate() {
        return subsctriptionDate;
    }

    /**
     * gets the numCreatedRecipes of this user
     * @return the numCreatedRecipes integer
     */
    public int getNumCreatedRecipes() {
        return numCreatedRecipes;
    }

    /**
     * gets the numPositiveReviews of this user
     * @return the numPositiveReviews integer
     */
    public int getNumPositiveReviews() {
        return numPositiveReviews;
    }

    /**
     * gets the numCreatedReviews of this user
     * @return the numCreatedReviews integer
     */
    public int getNumCreatedReviews() {
        return numCreatedReviews;
    }
}
