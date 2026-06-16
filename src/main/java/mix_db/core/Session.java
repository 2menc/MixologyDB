package mix_db.core;

import java.sql.Connection;
import java.util.Optional;

import mix_db.data.Role;
import mix_db.data.dao.User;
import mix_db.data.dbConnection.DatabaseConnection;
import mix_db.model.DbModel;
import mix_db.model.Model;

/**
 * class that models the current session, providing login, logout, sign in features
 */
public class Session {

    private static Session currentInstance;

    private User loggedUser;

    /** 
     * 0-args private constructor
     */
    private Session() {}

    /**
     * gets the current instance
     * @return current session
     */
    public static Session getInstance() {
        if(currentInstance == null) {
            currentInstance = new Session();
        }
        return currentInstance;
    }

    /**
     * gets the user currently logged in
     * @return user
     */
    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * sets a new user
     * @param loggedUser the user to set as logged in
     */
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * tells if the current user is an admin
     * @return {@code true} if the logged-in user has the ADMIN role, {@code false} otherwise
     */
    public boolean isAdmin() {
        return this.loggedUser.getUserRole().equals(Role.ADMIN);
    }

    /**
     * logs in
     * @param email the email of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return {@code true} if can log in, {@code false} otherwise
     */
    public boolean login(String email, String password) {

        try (final Connection connection = DatabaseConnection.localConnection("MixologyDB", "root", "Password")) {

            if(email.equals("anonimo") && password.equals("0")) {
                throw new IllegalArgumentException("cannot login as anonymous user");
            }

            final Model model = new DbModel(connection);
            final Optional<User> userOpt = model.login(email, password);


            if(userOpt.isPresent()) {
                this.loggedUser = userOpt.get();
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }

    /**
     * logs out
     */
    public void logout() {
        this.loggedUser = null;
    }

    /**
     * tells if the user is currently logged in
     * @return true if it is
     */
    public boolean isLoggedIn() {
        return loggedUser != null;
    }

}