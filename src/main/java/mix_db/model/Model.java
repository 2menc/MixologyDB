package mix_db.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import mix_db.data.dao.*;

/**
 * interface that defines methods to communicate with the DAOs
 */
public interface Model {

    /**
     * registers a new {@link User}. 
     * Checks if this user is already in the db
     * @param user the user
     * @return an empty Optional if the user is already in the db, the User otherwise 
     */
    Optional<User> registerUser(User user);

    /**
     * logs in with user's credentials
     * @param user the user
     * @return an empty Optional if the user is not in the db, the User otherwise 
     */
    Optional<User> login(String email, String password);

    /**
     * creates a new bar
     * @param bar the bar to create
     * @return anOptional of Bar
     */
    public Optional<Bar> createBar(Bar bar);

    /**
     * Creates a new Drink, also saving its composition (ingredients) and identification (keywords). 
     * Automatically links the drink to the user and saves the creationDate
     * @param drink .
     * @param barID NULL if is a single user creation
     * @param userID .
     * @param composition a {@link Set} of {@link Composition}
     * @param keywords a {@link Map} of {@link (drinkID, keyword)}
     * @returnan empty Optional if the drink is not in the db, the drink otherwise 
     */
    Optional<Drink> createDrink(Drink drink, int userID, Integer barID, List<Composition> composition, List<String> keywords);

    /**
     * links a user to a bar
     * @param barID the bar
     * @param userID the user
     */
    boolean addUserToBar(int barID, int userID);

    /**
     * gets a drink
     * @param drinkID .
     * @return empty Optional if the drink is not in the db, the drink otherwise 
     */
    Optional<Drink> getDrink(int drinkID);

    /**
     * gets a drink's ingredients
     * @param drinkID the id of the drink to get the ingredients from
     * @return empty Optional if the drink is not in the db, a list with all Ingredients otherwise
     */
    List<Ingredient> getIngredients(int drinkID);

    /**
     * gets a drink's keywords
     * @param drinkID the id of the drink to get the keywords from
     * @return empty Optional if the drink is not in the db, a list with all Tags otherwise
     */
    List<Tag> getKeywords(int drinkID);

    /**
     * saves a drink in favourites
     * @param drinkID the id of the drink to save
     * @return {@code true} if the drink can be saved (its not already in the list), {@code false} otherwise
     */
    boolean saveAsFavourite(int drinkID, int userID);

    /**
     * gets the list of drink saved as fav.
     * @return the list (an empty list if there are not any favourites)
     */
    List<Drink> getFavourites(int userID);

    /**
     * removes a drink from favourites
     * @param drinkID the drink
     * @param userID the user
     * @return {@code true} if the drink was in the favourite, {@code false} otherwise
     */
    boolean removeFromFavourites(int drinkID, int userID);

    /**
     * adds a review linked to the drink
     * @param drinkID the drink id
     * @return {@code true} if can add the review, {@code false} otherwise (f.e. the user has already created a review for that drink)
     */
    boolean addReview(int drinkID, int userID, String description, int score);

    /**
     * Searches a drink using keywords, drink name, drink description, category name, ingredients
     * @param keyword the word/phrase to search for
     * @return a List of all drinks found
     */
    List<Drink> searchByKeywords(String keyword);

    /**
     * Calculates a leaderbord of the {@code numberOfResults} drinks with most positive reviews in the specified time intervall
     * @param daysAgo time interval filter
     * @param numberOfResults number of Drinks to show
     * @return a {@code Map (Drink, number of positive reviews)} 
     */
    Map<Drink, Integer> calculateDrinkBestReviewsLeaderboard(int daysAgo, int numberOfResults);

    /**
     * gets the most used ingredients
     * @param numberOfResults number of ingredients to show
     * @return a list of ingredients
     */
    List<Ingredient> getMostUsedIngredients(int numberOfResults);

    /**
     * calculates a leaderboard of the {@code numberOfResults} users with most positive reviews
     * @param numberOfResults number of users to show
     * @return the list of users, an empty list if there are not users with any positive review
     */
    List<User> calculateUsersWithMostPositiveReviewsLeaderboard(int numberOfResults);

    /**
     * gets a list of trending identifiers
     * @param daysAgo time interval filter
     * @param numberOfResults number of Drinks to show
     * @return a List of the trending keywords
     */
    List<Tag> getTrendingKeywords(int daysAgo, int numberOfResults);

    /**
     * gets a list of suggested drinks
     * @param numberOfResults number of results to show
     * @param userID the user
     * @return the list of drinks
     */
    List<Drink> getSuggestions(int userID, int numberOfResults);

    /**
     * ONLY FOR ADMINS
     * Gets a list of Users with analitics data such as the reviews he made
     * @return a {@code Map (User, List of the reviews he has made)}
     */
    Map<User, List<Review>> getUsersAnalitics();

    /**
     * ONLY FOR ADMINS
     * deletes the specified review
     * @param userID .
     * @param drinkID .
     * @return {@code true} if the review has been successfully deleted, {@code false} otherwise
     */
    boolean deleteReview(int userID, int drinkID);

    /**
     * ONLY FOR ADMINS
     * deletes the specified drink
     * @param drinkID .
     * @return {@code true} if the drink has been successfully deleted, {@code false} otherwise
     */
    boolean deleteDrink(int drinkID);

    /**
     * ONLY FOR ADMINS
     * deletes a user, maintains recipes as anonymous {@code(special User with userID=0)}
     * @param userID
     * @return
     */
    boolean banUser(int userID);
}
