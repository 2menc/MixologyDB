package mix_db.data;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mix_db.data.dao.*;
import mix_db.data.dbConnection.*;

/**
 * Test class for taking data from the database.
 */
public class TestDAOs {

    private static Connection connection;

    final String email = "tryemail";
    final String password = "pass";

    final String drinkName = "drink1";

    @BeforeAll
    static void setup () {
        connection = DatabaseConnection.localConnection("MixologyDB", "root", "Password");
    }

    @SuppressWarnings("deprecation")
    @BeforeEach
    void insertUser() {
        final User u = new User(-1, email, password,
        "name", "aurn", new Date(2005, 05, 05),
        Role.USER, null, 0, 0, 0);

        final boolean result = User.DAO.insertNewUser(connection, u);

        assertTrue(result);
    }

    /**
     * insertUser() required
     */
    @AfterEach
    void deleteUser() {
        final User u = User.DAO.getUser(connection, email, password).get();
        final int userID = u.getUserID();

        final boolean result = AdminUtils.DAO.deleteUser(connection, userID);
        assertTrue(result);
    }

    @Test
    void login() {
        final Optional<User> result = User.DAO.getUser(connection, email, password);
        assertFalse(result.isEmpty());   
    }

    @Test 
    void barWithEmployee() {
        final User u = User.DAO.getUser(connection, email, password).get();
        
        final String barName = "name bar";
        final String city = "city 1";
        final String address = "addr1";

        final Bar b = new Bar(-1, barName, city,address);
        Bar.DAO.createBar(connection, b);

        Optional<Bar> barNew = Bar.DAO.searchBar(connection, barName, city, address);
        assertFalse(barNew.isEmpty());


        assertTrue(Bar.DAO.addUserToBar(connection, u.getUserID(), barNew.get().getBarID()));

        assertTrue(Bar.DAO.deleteBar(connection, barNew.get().getBarID()));
    }

    @Test
    void createDrink() {
        Category.DAO.createCategory(connection, "cat", "category description");

        final Drink d = new Drink(-1, drinkName, "description1", "path", "cat", false);
        final User u = User.DAO.getUser(connection, email, password).get();

        // ingredients population
        Ingredient.DAO.createIngredient(connection, "ingredient1");

        // compositions
        final List<Composition> composition = List.of(
            new Composition("ingredient1", -1, 20, "ml")
        );

        // drink
        assertDoesNotThrow(() -> {
            Drink.DAO.createDrink(connection, d, u.getUserID(), composition);
        }, "createDrink() should not throw DAOExceptions");
    }

    @SuppressWarnings("deprecation")
    @Test
    void deleteDrink() {
        Drink.DAO.deleteByCategoryName(connection, "cat");
        Category.DAO.deleteCategory(connection, "cat");
        Ingredient.DAO.deleteIngredient(connection, "Ingredient1");
    }

    /**
     * createDrink() needed
     */
    @Test
    void favourites() {                     
        // *save    
        final var u = User.DAO.getUser(connection, email, password).get();
        final var d = Drink.DAO.getDrink(connection, drinkName).get();

        final boolean resultSave =User.DAO.setFavourite(connection, d.getDrinkID(), u.getUserID());
        assertTrue(resultSave);

        // *get
        final List<Drink> favs = User.DAO.getFavourites(connection, u.getUserID());
        assertThat(favs).hasSameElementsAs(List.of(d));

        // *remove
        final boolean resultDelete = User.DAO.deleteFavourite(connection, d.getDrinkID(), u.getUserID());
        assertTrue(resultDelete);
    }

    /**
     * createDrink() needed
     */
    @Test 
    void review() {
    this.createDrink();
        int testDrinkID = Drink.DAO.getDrink(connection, drinkName).get().getDrinkID();
        int testUserID = User.DAO.getUser(connection, email, password).get().getUserID();
        String testDescription = "description review";
        int testScore = 3;

        assertDoesNotThrow(() -> {
            Review.DAO.addReview(connection, testDrinkID, testUserID, testDescription, testScore);
        }, "addReview() should not throw DAOExceptions");

        // *checks if exists
        
        final List<Review> r = Review.DAO.searchDrinkReviews(connection, testDrinkID);
        assertEquals(r.get(0).getDescription(), testDescription);

        // *deletes thereview
        Review.DAO.removeReview(connection, testDrinkID, testUserID);
        final List<Review> rNew = Review.DAO.searchDrinkReviews(connection, testDrinkID);
        assertThat(rNew).isEmpty(); 

        this.deleteDrink();
    }
}
