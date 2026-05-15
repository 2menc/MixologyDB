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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + drinkID;
        result = prime * result + userID;
        result = prime * result + ((saveDate == null) ? 0 : saveDate.hashCode());
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
        FavouriteSave other = (FavouriteSave) obj;
        if (drinkID != other.drinkID)
            return false;
        if (userID != other.userID)
            return false;
        if (saveDate == null) {
            if (other.saveDate != null)
                return false;
        } else if (!saveDate.equals(other.saveDate))
            return false;
        return true;
    }

    /**
     * DAO object for FavouriteSave
     */
    public static final class DAO {

    }
}
