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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + barID;
        result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
        result = prime * result + drinkID;
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
        BarCreation other = (BarCreation) obj;
        if (barID != other.barID)
            return false;
        if (creationDate == null) {
            if (other.creationDate != null)
                return false;
        } else if (!creationDate.equals(other.creationDate))
            return false;
        if (drinkID != other.drinkID)
            return false;
        return true;
    }


    /**
     * DAO for BarCreations
     */
    public static final class DAO {

    }
}
