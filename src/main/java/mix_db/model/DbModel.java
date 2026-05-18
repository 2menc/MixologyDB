package mix_db.model;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
    public Optional<Drink> createDrink(Drink drink, String userID, Set<Composition> composition,
            Map<Integer, String> identification) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createDrink'");
    }

    @Override
    public Optional<Drink> getDrink(String drinkID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDrink'");
    }

    @Override
    public Optional<List<Ingredient>> getIngredients(String drinkID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIngredients'");
    }

    @Override
    public Optional<List<Tag>> getKeywords(String drinkID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getKeywords'");
    }

    @Override
    public boolean saveAsFavourite(String drinkID, String userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAsFavourite'");
    }

    @Override
    public List<Drink> getFavourites(String userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFavourites'");
    }

    @Override
    public boolean addReview(String drinkID, String userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addReview'");
    }

    @Override
    public Optional<Drink> searchForKeywords(String keyword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchForKeywords'");
    }

    @Override
    public Map<Drink, Integer> calculateDrinkBestReviewsLeaderboard(int daysAgo, int numberOfResults) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateDrinkBestReviewsLeaderboard'");
    }

    @Override
    public List<Ingredient> getMostUsedIngredients(int numberOfResults) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMostUsedIngredients'");
    }

    @Override
    public List<User> calculateUsersWithMostPositiveReviewsLeaderboard(int numberOfResults) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateUsersWithMostPositiveReviewsLeaderboard'");
    }

    @Override
    public Map<String, Integer> getTrendingKeywords(int daysAgo, int numberOfResults) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTrendingKeywords'");
    }

    @Override
    public List<Drink> getSuggestions(int numberOfResults) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSuggestions'");
    }

    @Override
    public Map<User, List<Review>> getUserAnalitics() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserAnalitics'");
    }

    @Override
    public boolean deleteReview(String userID, String drinkID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteReview'");
    }

    @Override
    public boolean deleteDrink(String drinkID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteDrink'");
    }

    @Override
    public boolean banUser(String userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'banUser'");
    }

}
