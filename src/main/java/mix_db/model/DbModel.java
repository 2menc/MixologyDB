package mix_db.model;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import mix_db.data.dao.*;

public class DbModel implements Model{

    private final Connection connection;

    /**
     * construcytor
     * @param connection .
     */
    public DbModel(Connection connection) {
        Objects.requireNonNull(connection, "Null connection");
        this.connection = connection;
    }

    @Override
    public Optional<User> registerUser(User user) {
        if(! User.DAO.insertNewUser(connection, user)) {
            return Optional.empty();
        }
        final var u = User.DAO.getUser(connection, user.getEmail(), user.getPassword());
        return u;
    }

    @Override
    public Optional<User> login(String email, String password) {
        return User.DAO.getUser(connection, email, password);
    }

    @Override
    public User getAnonymUser() {
        return User.DAO.getUser(connection, "anonimo", "0").get();
    }

    @Override
    public boolean userExists(String email) {
        return User.DAO.userExists(connection, email);        
    }

    @Override
    public Optional<User> getUserFromEmail(String email) {
        return User.DAO.getUserFromEmail(connection, email);
    }

    @Override
    public Optional<Bar> createBar(Bar bar) {
        Bar.DAO.createBar(connection, bar);
        return Bar.DAO.searchBarByParams(connection, bar.getBarName(), bar.getCity(), bar.getAddress());
    }

    @Override
    public Optional<Drink> createDrink(Drink drink, int userID, Optional<Integer> barID, List<Composition> composition, List<String> keywords) {
        Drink.DAO.createDrink(connection, drink, barID, userID, composition, keywords);
        return Drink.DAO.searchByName(connection, drink.getName());
    }

    @Override
    public boolean addUserToBar(int barID, int userID) {
        Bar.DAO.addUserToBar(connection, userID, barID);
        return ! Bar.DAO.searchBar(connection, barID).isEmpty();
    }

    @Override
    public boolean isUserInABar(int userID) {
        return Bar.DAO.isUserInABar(connection, userID);
    }

    @Override
    public Optional<Bar> checkIfEmployed(int userID) {
        if(Bar.DAO.isUserInABar(connection, userID)) {
            return Bar.DAO.getBarEmployed(connection, userID);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Drink> getDrink(int drinkID) {
        return Drink.DAO.getDrink(connection, drinkID);
    }

    @Override
    public Optional<User> getDrinkCreator(int drinkID) {
        return Drink.DAO.getCreator(connection, drinkID);
    }

    @Override
    public List<Drink> getRandomDrinkList(int numberOfResults) {
        return Drink.DAO.getRandomDrinkList(connection,numberOfResults);
    }

    @Override
    public List<Ingredient> getIngredients(int drinkID) {
        return Ingredient.DAO.ofDrink(connection, drinkID);
    }

    @Override
    public List<Composition> getComposition(int drinkID) {
        return Composition.DAO.getComposition(connection, drinkID);
    }

    @Override
    public List<String> getKeywords(int drinkID) {
        return Tag.DAO.ofDrink(connection, drinkID);
    }

    @Override
    public boolean saveAsFavourite(int drinkID, int userID) {
        return User.DAO.setFavourite(connection, drinkID, userID);
    }

    @Override
    public List<Drink> getFavourites(int userID) {
        return User.DAO.getFavourites(connection, userID);
    }

    @Override
    public boolean removeFromFavourites(int drinkID, int userID) {
        return User.DAO.deleteFavourite(connection, drinkID, userID);
    }

    @Override
    public boolean addReview(int drinkID, int userID, String description, int score) {
        return Review.DAO.addReview(connection, drinkID, userID, description, score);
    }

    @Override
    public List<Review> getDrinkReviews(int drinkID) {
        return Review.DAO.searchDrinkReviews(connection, drinkID);
    }

    @Override
    public List<Drink> searchByKeywords(String keyword) {
        return Drink.DAO.searchByKeyword(connection, keyword);
    }

    @Override
    public List<String> getAllCategories() {
        return Category.DAO.getAllCategories(connection);
    }

    @Override
    public Map<Drink, Integer> calculateDrinkBestReviewsLeaderboard(int daysAgo, int numberOfResults) {
        return Leaderboards.DAO.MorePositiveDrinkReviews(connection, daysAgo, numberOfResults);
    }

    @Override
    public List<Ingredient> getMostUsedIngredients(int numberOfResults) {
        return Leaderboards.DAO.MostUsedIngredients(connection, numberOfResults);
    }

    @Override
    public List<User> calculateUsersWithMostPositiveReviewsLeaderboard(int numberOfResults) {
        return Leaderboards.DAO.usersWithMorePositiveReviews(connection, numberOfResults);
    }

    @Override
    public List<Tag> getTrendingKeywords(int daysAgo, int numberOfResults) {
        return Leaderboards.DAO.getTrendingKeywords(connection, daysAgo, numberOfResults);
    }

    @Override
    public List<Drink> getSuggestions(int userID, int numberOfResults) {
        return User.DAO.getSuggestedDrinks(connection, userID, numberOfResults);
    }

    @Override
    public Map<User, List<Review>> getUsersAnalitics() {
        return AdminUtils.DAO.getUsersAnalitics(connection);
    }

    @Override
    public Optional<User> getFullUserFromID(int userID) {
        return User.DAO.getUserFromID(connection, userID);
    }

    @Override
    public boolean deleteReview(int userID, int drinkID) {
        return AdminUtils.DAO.removeReview(connection, drinkID, userID);
    }

    @Override
    public boolean deleteDrink(int drinkID) {
        return AdminUtils.DAO.deleteDrink(connection, drinkID);
    }

    @Override
    public boolean banUser(int userID) {
        return AdminUtils.DAO.deleteUser(connection, userID);
    }


}
