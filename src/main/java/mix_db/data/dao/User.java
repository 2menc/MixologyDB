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

    }
}
