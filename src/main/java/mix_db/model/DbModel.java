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
        User.DAO.insertNewUser(connection, user);
        final var u = User.DAO.getUser(connection, user.getEmail(), user.getPassword());
        return u;
    }

    @Override
    public Optional<User> login(String email, String password) {
        return User.DAO.getUser(connection, email, password);
    }

    @Override
    public Optional<Drink> createDrink(Drink drink, int userID, List<Composition> composition, List<String> keywords) {
        Drink.DAO.createDrink(connection, drink, userID, composition, keywords);
        return Drink.DAO.getDrink(connection, drink.getDrinkID());
    }

    @Override
    public Optional<Drink> getDrink(int drinkID) {
        return Drink.DAO.getDrink(connection, drinkID);
    }

    @Override
    public List<Ingredient> getIngredients(int drinkID) {
        return Ingredient.DAO.ofDrink(connection, drinkID);
    }

    @Override
    public List<Tag> getKeywords(int drinkID) {
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
    public boolean addReview(int drinkID, int userID, String description, int score) {
        return Review.DAO.addReview(connection, drinkID, userID, description, score);
    }

    @Override
    public List<Drink> searchByKeywords(String keyword) {
        return Drink.DAO.searchByKeyword(connection, keyword);
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
