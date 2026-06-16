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
 * represents a user's review for a specific drink, including details like the description, score, and date.
 */
public class Review {

    private final int drinkID;
    private final int userID;
    private final String description;
    private final Date reviewDate;
    private final int score;
    
    /**
     * constructs a new Review instance with the specified details.
     * @param drinkID the unique identifier of the drink being reviewed.
     * @param userID the unique identifier of the user who made the review.
     * @param description the textual content of the review.
     * @param reviewDate the date when the review was made.
     * @param score the numerical score given to the drink by the user.
     */
    public Review(int drinkID, int userID, String description, Date reviewDate, int score) {
        this.drinkID = drinkID;
        this.userID = userID;
        this.description = description;
        this.reviewDate = reviewDate;
        this.score = score;
    }

    /**
     * returns a string representation of the Review object.
     * @return a string containing the drink ID, user ID, description, review date, and score.
     */
    @Override
    public String toString() {
        return "Review [drinkID=" + drinkID + ", userID=" + userID + ", description=" + description + ", reviewDate="
                + reviewDate + ", score=" + score + "]";
    }

    /**
     * returns a hash code value for the object.
     * @return a hash code value for this object.
     */
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

    /**
     * indicates whether some other object is "equal to" this one.
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
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
         * adds a new review to the database, along with updating the user's review counters.
         * This operation is performed within a transaction.
         * @param connection the database connection.
         * @param drinkID the unique identifier of the drink being reviewed.
         * @param userID the unique identifier of the user making the review.
         * @param description the textual content of the review.
         * @param score the numerical score given to the drink (1-5).
         * @return {@code true} if the review was successfully added and counters updated, {@code false} otherwise.
         * @throws DAOException if a database access error occurs during the operation.
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
         * searches all reviews linked to a specific drink.
         * @param connection the database connection.
         * @param drinkID the unique identifier of the drink.
         * @return a list of the reviews associated with the given drink ID.
         * @throws DAOException if a database access error occurs.
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
         * updates the user's attribute that counts how many reviews they have made.
         * @param connection the database connection.
         * @param userID the unique identifier of the user.
         * @throws DAOException if a database access error occurs.
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
         * updates the user's attribute that counts how many positive reviews they have made.
         * A positive review is typically considered a score greater than 2.
         * @param connection the database connection.
         * @param userID the unique identifier of the user.
         * @throws DAOException if a database access error occurs.
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

    /**
     * gets the unique identifier of the drink associated with this review.
     * @return the drink ID.
     */
    public int getDrinkID() {
        return drinkID;
    }

    /**
     * gets the unique identifier of the user who made this review.
     * @return the user ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * gets the textual description of the review.
     * @return the review description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * gets the date when this review was made.
     * @return the review date.
     */
    public Date getReviewDate() {
        return reviewDate;
    }

    /**
     * gets the score given in this review.
     * @return the review score.
     */
    public int getScore() {
        return score;
    }    
}