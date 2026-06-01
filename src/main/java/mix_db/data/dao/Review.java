package mix_db.data.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * User - Review - Drink
 */
public class Review {

    private final int drinkID;
    private final int userID;
    private final String description;
    private final Date reviewDate;
    private final int score;
    
    /**
     * constructor
     * @param drinkID drinkID
     * @param userID userID
     * @param description description
     * @param reviewDate reviewDate
     * @param score score
     */
    public Review(int drinkID, int userID, String description, Date reviewDate, int score) {
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

        /**
         * adds a new review
         * @param connection .
         * @param drinkID .
         * @param userID .
         * @param description .
         * @param score .
         */
        public static boolean addReview(Connection connection, int drinkID, int userID, String description, int score) {
            try{
                // !transaction start
                connection.setAutoCommit(false);

                // *1) add review
                try(
                    final var statement = DatabaseConnection.prepare(connection, 
                        Queries.CREATE_REVIEW, 
                    drinkID, userID, description, score);
                ) {
                    statement.executeUpdate();
                } 

                // *2) updates user review couynter
                try(
                    final var statement = DatabaseConnection.prepare(connection, 
                        Queries.UPDATE_USER_REVIEW_NUMBER_COUNTER, 
                    userID);
                ) {
                    statement.executeUpdate();
                } 

                // *3) updates user positive review counter
                // ?verifies if score > 2 (positive) 
                if(score > 2) {
                    try(
                        final var statement = DatabaseConnection.prepare(connection, 
                            Queries.UPDATE_USER_POSITIVE_REVIEW_COUNTER, 
                        userID);
                    ) {
                        statement.executeUpdate();
                    } 
                }

                // !commit
                connection.commit();
                return true;

            } catch (Exception e) {
                // ! transaction ends with rollback
                try {
                    connection.rollback();
                } catch(final SQLException rollBackException) {}
                throw new DAOException("error during addReview(): " + e.getMessage(), e);
            } finally {
                try{
                    connection.setAutoCommit(true);
                } catch(SQLException autoCommitException) { }
            }
        }

        /**
         * searches all reviews linked to a drink
         * @param connection .
         * @param drinkID .
         * @return a list of the reviews
         */
        public static List<Review> searchDrinkReviews(Connection connection, int drinkID) {
            final var reviews = new LinkedList<Review>();

            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.SEARCH_DRINK_REVIEWS, 
                drinkID);
                final var rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    final var r = new Review(
                        drinkID, 
                        rs.getInt("userID"),
                        rs.getString("descrizione"),
                        rs.getDate("dataRecensione"), 
                        rs.getInt("voto")
                    );
                    reviews.add(r);
                }

                return reviews;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * updates user's attribute that counts how much reviews he has made
         * @param connection .
         * @param userID .
         */
        public static void updateReviewsCounter(Connection connection, int userID) {
            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.UPDATE_USER_REVIEW_NUMBER_COUNTER,
                    userID);
            ) {
                statement.executeUpdate();
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * updates user's attribute that counts how much positive reviews he has
         * @param connection .
         * @param userID .
         */
        public static void updatePositiveReviewsCounter(Connection connection, int userID) {
            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.UPDATE_USER_POSITIVE_REVIEW_COUNTER,
                    userID);
            ) {
                statement.executeUpdate();
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }

    public int getDrinkID() {
        return drinkID;
    }

    public int getUserID() {
        return userID;
    }

    public String getDescription() {
        return description;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public int getScore() {
        return score;
    }    
}
