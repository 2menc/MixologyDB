package mix_db.core;

import mix_db.data.dao.User;

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
     */
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
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
    public boolean idLoggedIn() {
        return loggedUser != null;
    }

}
