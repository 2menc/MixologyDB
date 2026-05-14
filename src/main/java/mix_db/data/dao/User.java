package mix_db.data.dao;

import java.sql.Date;

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


        /**
         * DAO object for User
         */
    public static final class DAO {

    }
}
