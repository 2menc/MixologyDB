package mix_db.model;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import mix_db.data.dao.*;

/**
 * database implementation of the model interface.
 */
public class DbModel implements Model{

    private final Connection connection;

    /**
     * construcytor
     * @param connection the database connection
     */
    public DbModel(Connection connection) {
        Objects.requireNonNull(connection, "Null connection");
        this.connection = connection;
    }

    /**
     * registers a new user in the database.
     * @param user the user to register
     * @return an optional containing the registered user, or empty if registration failed
     */
    @Override
    public Optional<User> registerUser(User user) {
        if(! User.DAO.insertNewUser(connection, user)) {
            return Optional.empty();
        }
        final var u = User.DAO.getUser(connection, user.getEmail(), user.getPassword());
        return u;
    }

    /**
     * performs user login with email and password.
     * @param email the user's email
     * @param password the user's password
     * @return an optional containing the logged-in user, or empty if credentials are invalid
     */
    @Override
    public Optional<User> login(String email, String password) {
        return User.DAO.getUser(connection, email, password);
    }

    /**
     * gets the anonymous user.
     * @return the anonymous user
     */
    @Override
    public User getAnonymUser() {
        return User.DAO.getUser(connection, "anonimo", "0").get();
    }

    /**
     * checks if a user with the given email exists.
     * @param email the email to check
     * @return true if the user exists, false otherwise
     */
    @Override
    public boolean userExists(String email) {
        return User.DAO.userExists(connection, email);        
    }

    /**
     * retrieves a user by their email.
     * @param email the email of the user
     * @return an optional containing the user, or empty if not found
     */
    @Override
    public Optional<User> getUserFromEmail(String email) {
        return User.DAO.getUserFromEmail(connection, email);
    }

    /**
     * creates a new bar in the database.
     * @param bar the bar to create
     * @return an optional containing the created bar, or empty if creation failed
     */
    @Override
    public Optional<Bar> createBar(Bar bar) {
        Bar.DAO.createBar(connection, bar);
        return Bar.DAO.searchBarByParams(connection, bar.getBarName(), bar.getCity(), bar.getAddress());
    }

    /**
     * creates a new drink with its composition and keywords.
     * @param drink the drink to create
     * @param userID the ID of the user creating the drink
     * @param barID the optional ID of the bar where the drink is served
     * @param composition the list of ingredients and quantities composing the drink
     * @param keywords the list of keywords associated with the drink
     * @return an optional containing the created drink, or empty if creation failed
     */
    @Override
    public Optional<Drink> createDrink(Drink drink, int userID, Optional<Integer> barID, List<Composition> composition, List<String> keywords) {
        Drink.DAO.createDrink(connection, drink, barID, userID, composition, keywords);
        return Drink.DAO.searchByName(connection, drink.getName());
    }

    /**
     * gets the bar associated with a specific drink.
     * @param drinkID the ID of the drink
     * @return an optional containing the bar, or empty if not associated with any bar
     */
    @Override
    public Optional<Bar> getDrinkBar(int drinkID) {
        final var b = Bar.DAO.getCreationBar(connection, drinkID);

        if(b.isEmpty()) return Optional.empty();
        return Bar.DAO.searchBarByParams(connection, b.get().getBarName(), b.get().getCity(), b.get().getAddress());
    }

    /**
     * adds a user as an employee of a bar.
     * @param barID the ID of the bar
     * @param userID the ID of the user
     * @return true if the user was successfully added, false otherwise
     */
    @Override
    public boolean addUserToBar(int barID, int userID) {
        Bar.DAO.addUserToBar(connection, userID, barID);
        return ! Bar.DAO.searchBar(connection, barID).isEmpty();
    }

    /**
     * checks if a user is employed in any bar.
     * @param userID the ID of the user
     * @return true if the user is employed in a bar, false otherwise
     */
    @Override
    public boolean isUserInABar(int userID) {
        return Bar.DAO.isUserInABar(connection, userID);
    }

    /**
     * retrieves the bar where the user is employed, if any.
     * @param userID the ID of the user
     * @return an optional containing the bar, or empty if the user is not employed in any bar
     */
    @Override
    public Optional<Bar> checkIfEmployed(int userID) {
        if(Bar.DAO.isUserInABar(connection, userID)) {
            return Bar.DAO.getBarEmployed(connection, userID);
        }
        return Optional.empty();
    }

    /**
     * retrieves a drink by its ID.
     * @param drinkID the ID of the drink
     * @return an optional containing the drink, or empty if not found
     */
    @Override
    public Optional<Drink> getDrink(int drinkID) {
        return Drink.DAO.getDrink(connection, drinkID);
    }

    /**
     * retrieves the creator of a specific drink.
     * @param drinkID the ID of the drink
     * @return an optional containing the creator user, or empty if not found
     */
    @Override
    public Optional<User> getDrinkCreator(int drinkID) {
        return Drink.DAO.getCreator(connection, drinkID);
    }

    /**
     * retrieves a list of random drinks.
     * @param numberOfResults the maximum number of drinks to return
     * @return a list of random drinks
     */
    @Override
    public List<Drink> getRandomDrinkList(int numberOfResults) {
        return Drink.DAO.getRandomDrinkList(connection,numberOfResults);
    }

    /**
     * retrieves the ingredients of a specific drink.
     * @param drinkID the ID of the drink
     * @return a list of ingredients
     */
    @Override
    public List<Ingredient> getIngredients(int drinkID) {
        return Ingredient.DAO.ofDrink(connection, drinkID);
    }

    /**
     * retrieves the composition details of a specific drink.
     * @param drinkID the ID of the drink
     * @return a list of compositions
     */
    @Override
    public List<Composition> getComposition(int drinkID) {
        return Composition.DAO.getComposition(connection, drinkID);
    }

    /**
     * retrieves the keywords associated with a specific drink.
     * @param drinkID the ID of the drink
     * @return a list of keywords
     */
    @Override
    public List<String> getKeywords(int drinkID) {
        return Tag.DAO.ofDrink(connection, drinkID);
    }

    /**
     * saves a drink as a favourite for a user.
     * @param drinkID the ID of the drink
     * @param userID the ID of the user
     * @return true if successfully saved, false otherwise
     */
    @Override
    public boolean saveAsFavourite(int drinkID, int userID) {
        return User.DAO.setFavourite(connection, drinkID, userID);
    }

    /**
     * retrieves the favourite drinks of a user.
     * @param userID the ID of the user
     * @return a list of favourite drinks
     */
    @Override
    public List<Drink> getFavourites(int userID) {
        return User.DAO.getFavourites(connection, userID);
    }

    /**
     * removes a drink from a user's favourites.
     * @param drinkID the ID of the drink
     * @param userID the ID of the user
     * @return true if successfully removed, false otherwise
     */
    @Override
    public boolean removeFromFavourites(int drinkID, int userID) {
        return User.DAO.deleteFavourite(connection, drinkID, userID);
    }

    /**
     * adds a review for a drink by a user.
     * @param drinkID the ID of the drink
     * @param userID the ID of the user
     * @param description the review text
     * @param score the review score
     * @return true if the review was successfully added, false otherwise
     */
    @Override
    public boolean addReview(int drinkID, int userID, String description, int score) {
        return Review.DAO.addReview(connection, drinkID, userID, description, score);
    }

    /**
     * retrieves all reviews for a specific drink.
     * @param drinkID the ID of the drink
     * @return a list of reviews
     */
    @Override
    public List<Review> getDrinkReviews(int drinkID) {
        return Review.DAO.searchDrinkReviews(connection, drinkID);
    }

    /**
     * searches for drinks by a keyword.
     * @param keyword the keyword to search for
     * @return a list of matching drinks
     */
    @Override
    public List<Drink> searchByKeywords(String keyword) {
        return Drink.DAO.searchByKeyword(connection, keyword);
    }

    /**
     * retrieves all available drink categories.
     * @return a list of category names
     */
    @Override
    public List<String> getAllCategories() {
        return Category.DAO.getAllCategories(connection);
    }

    /**
     * calculates the leaderboard of drinks with the best reviews.
     * @param daysAgo the number of days to look back
     * @param numberOfResults the maximum number of results to return
     * @return a map of drinks and their positive review counts
     */
    @Override
    public Map<Drink, Integer> calculateDrinkBestReviewsLeaderboard(int daysAgo, int numberOfResults) {
        return Leaderboards.DAO.MorePositiveDrinkReviews(connection, daysAgo, numberOfResults);
    }

    /**
     * retrieves the most used ingredients across all drinks.
     * @param numberOfResults the maximum number of results to return
     * @return a list of most used ingredients
     */
    @Override
    public List<Ingredient> getMostUsedIngredients(int numberOfResults) {
        return Leaderboards.DAO.MostUsedIngredients(connection, numberOfResults);
    }

    /**
     * calculates the leaderboard of users with the most positive reviews.
     * @param numberOfResults the maximum number of results to return
     * @return a list of top users
     */
    @Override
    public List<User> calculateUsersWithMostPositiveReviewsLeaderboard(int numberOfResults) {
        return Leaderboards.DAO.usersWithMorePositiveReviews(connection, numberOfResults);
    }

    /**
     * retrieves the trending keywords within a specific timeframe.
     * @param daysAgo the number of days to look back
     * @param numberOfResults the maximum number of results to return
     * @return a list of trending tags
     */
    @Override
    public List<Tag> getTrendingKeywords(int daysAgo, int numberOfResults) {
        return Leaderboards.DAO.getTrendingKeywords(connection, daysAgo, numberOfResults);
    }

    /**
     * retrieves suggested drinks for a user.
     * @param userID the ID of the user
     * @param numberOfResults the maximum number of results to return
     * @return a list of suggested drinks
     */
    @Override
    public List<Drink> getSuggestions(int userID, int numberOfResults) {
        return User.DAO.getSuggestedDrinks(connection, userID, numberOfResults);
    }

    /**
     * retrieves analytics data for all users and their reviews.
     * @return a map of users and their reviews
     */
    @Override
    public Map<User, List<Review>> getUsersAnalitics() {
        return AdminUtils.DAO.getUsersAnalitics(connection);
    }

    /**
     * retrieves full user details by user ID.
     * @param userID the ID of the user
     * @return an optional containing the user, or empty if not found
     */
    @Override
    public Optional<User> getFullUserFromID(int userID) {
        return User.DAO.getUserFromID(connection, userID);
    }

    /**
     * deletes a review written by a user for a specific drink.
     * @param userID the ID of the user
     * @param drinkID the ID of the drink
     * @return true if successfully deleted, false otherwise
     */
    @Override
    public boolean deleteReview(int userID, int drinkID) {
        return AdminUtils.DAO.removeReview(connection, drinkID, userID);
    }

    /**
     * deletes a drink from the database.
     * @param drinkID the ID of the drink to delete
     * @return true if successfully deleted, false otherwise
     */
    @Override
    public boolean deleteDrink(int drinkID) {
        return AdminUtils.DAO.deleteDrink(connection, drinkID);
    }

    /**
     * bans a user by deleting their account.
     * @param userID the ID of the user to ban
     * @return true if successfully banned, false otherwise
     */
    @Override
    public boolean banUser(int userID) {
        return AdminUtils.DAO.deleteUser(connection, userID);
    }


}