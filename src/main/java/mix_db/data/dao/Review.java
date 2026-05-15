package mix_db.data.dao;

import java.sql.Date;

/**
 * User - Review - Drink
 */
public class Review {

    private final int drinkID;
    private final int userID;
    private final String description;
    private final Date reviewDate;
    private final float score;
    
    /**
     * constructor
     * @param drinkID drinkID
     * @param userID userID
     * @param description description
     * @param reviewDate reviewDate
     * @param score score
     */
    public Review(int drinkID, int userID, String description, Date reviewDate, float score) {
        this.drinkID = drinkID;
        this.userID = userID;
        this.description = description;
        this.reviewDate = reviewDate;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Review [drinkID=" + drinkID + ", userID=" + userID + ", description=" + description + ", reviewDate="
                + reviewDate + ", score=" + score + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + drinkID;
        result = prime * result + userID;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((reviewDate == null) ? 0 : reviewDate.hashCode());
        result = prime * result + Float.floatToIntBits(score);
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
        Review other = (Review) obj;
        if (drinkID != other.drinkID)
            return false;
        if (userID != other.userID)
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (reviewDate == null) {
            if (other.reviewDate != null)
                return false;
        } else if (!reviewDate.equals(other.reviewDate))
            return false;
        if (Float.floatToIntBits(score) != Float.floatToIntBits(other.score))
            return false;
        return true;
    }

    /**
     * DAO object for Review
     */
    public static final class DAO {

    }    
}
