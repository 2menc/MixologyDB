package mix_db.data.dao;

import java.sql.Date;

/**
 * User - Creation - Drink
 */
public class Creation {

    private final int drinkID;
    private final int userID;
    private final Date creationDate;

    /**
     * constructor
     * @param drinkID .
     * @param userID .
     * @param creationDate .
     */
    public Creation(int drinkID, int userID, Date creationDate) {
        this.drinkID = drinkID;
        this.userID = userID;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "creation [drinkID=" + drinkID + ", userID=" + userID + ", creationDate=" + creationDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + drinkID;
        result = prime * result + userID;
        result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
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
        Creation other = (Creation) obj;
        if (drinkID != other.drinkID)
            return false;
        if (userID != other.userID)
            return false;
        if (creationDate == null) {
            if (other.creationDate != null)
                return false;
        } else if (!creationDate.equals(other.creationDate))
            return false;
        return true;
    }

    /**
     * DAO object for Creation
     */
    public static final class DAO {

    }
}
