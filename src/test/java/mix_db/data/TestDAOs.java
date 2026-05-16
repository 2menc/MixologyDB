package mix_db.data;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

    @BeforeAll
    static void setup () {
        connection = DatabaseConnection.localConnection("MixologyDB", "root", "Password");
    }

    @SuppressWarnings("deprecation")
    @BeforeEach
    void insertUser() {
        final User u = new User(-1, "tryemail", "pass",
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
        final User u = User.DAO.getUser(connection, "tryemail", "pass").get();
        final int userID = u.getUserID();

        final boolean result = User.DAO.deleteUser(connection, userID);
        assertTrue(result);
    }

    /**
     * insertUser() required
     */
    @Test
    void login() {
        final String email = "tryemail";
        final String password = "pass";

        final Optional<User> result = User.DAO.getUser(connection, email, password);
        assertFalse(result.isEmpty());        
    }

    @Test 
    void barWithEmployee() {
        final User u = User.DAO.getUser(connection, "tryemail", "pass").get();
        
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
    @SuppressWarnings("deprecation")
    void createDrink() {
        Category.DAO.createCategory(connection, "cat", "category description");

        final Drink d = new Drink(-1, "drink1", "description1", "path", "cat", false);
        final User u = User.DAO.getUser(connection, "tryemail", "pass").get();

        // ingredients population
        Ingredient.DAO.createIngredient(connection, "ingredient1");

        // compositions
        final List<Composition> composition = List.of(
            new Composition("ingredient1", -1, 20, "ml")
        );

        // drink
        Drink.DAO.createDrink(connection, d, u.getUserID(), composition);

        assertDoesNotThrow(() -> {
            Drink.DAO.createDrink(connection, d, u.getUserID(), composition);
        }, "createDrink() should not throw DAOExceptions");
   
        // deletions after test
        Drink.DAO.deleteByCategoryName(connection, "cat");
        Category.DAO.deleteCategory(connection, "cat");
        Ingredient.DAO.deleteIngredient(connection, "Ingredient1");
    }

    @Test
    void favourites() {                     //TODO 
        // *save    
        final User u = User.DAO.getUser(connection, "tryemail", "pass").get();


        // *get

        // *remove
    }


}
