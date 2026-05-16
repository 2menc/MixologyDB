package mix_db.data;

import java.sql.Connection;
import java.sql.Date;
import java.util.Optional;

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
    @Test
    void login() {
        final String email = "tryemail";
        final String password = "pass";

        final Optional<User> result = User.DAO.getUser(connection, email, password);
        assertFalse(result.isEmpty());        
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
    }

    @Test
    void createDrink() {
        
    }
}
