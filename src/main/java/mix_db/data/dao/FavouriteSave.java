package mix_db.data.dao;

import java.sql.Date;

/**
 * User - favouriteSave - Drink
 */
public class FavouriteSave {

    private final int drinkID;
    private final int userID;
    private final Date saveDate;

    /**
     * constructor
     * @param drinkID drinkID
     * @param userID userID
     * @param saveDate saveDate
     */
    public FavouriteSave(int drinkID, int userID, Date saveDate) {
        this.drinkID = drinkID;
        this.userID = userID;
        this.saveDate = saveDate;
    }

    @Override
    public String toString() {
        return "FavouriteSave [drinkID=" + drinkID + ", userID=" + userID + ", saveDate=" + saveDate + "]";
    }

    /**
     * DAO object for FavouriteSave
     */
    public static final class DAO {

    }
}
