package mix_db.data.dao;

import java.sql.Date;

/**
 * Bar -BarCreation - Drink
 */
public class BarCreation {

    private final int barID;
    private final Date creationDate;
    private final int drinkID;


    /**
     * constructor 
     * @param barID barID
     * @param creationDate creationDate
     * @param drinkID drinkID
     */
    public BarCreation(int barID, Date creationDate, int drinkID) {
        this.barID = barID;
        this.creationDate = creationDate;
        this.drinkID = drinkID;
    }

     @Override
    public String toString() {
        return "BarCreation [barID=" + barID + ", creationDate=" + creationDate + ", drinkID=" + drinkID + "]";
    }

    /**
     * DAO for BarCreations
     */
    public static final class DAO {

    }
}
