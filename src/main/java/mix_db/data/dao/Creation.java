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

    /**
     * DAO object for Creation
     */
    public static final class DAO {

    }
}
