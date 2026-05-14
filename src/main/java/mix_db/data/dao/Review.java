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

    /**
     * DAO object for Review
     */
    public static final class DAO {

    }    
}
