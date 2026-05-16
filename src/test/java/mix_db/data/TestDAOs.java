package mix_db.data;

import java.sql.Connection;
import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
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

    @Test
    void insertUser() {
        final User u = new User(-1, "tryemail", "pass",
        "name", "aurn", new Date(2005, 05, 05),
        Role.USER, null, 0, 0, 0);

        final boolean result = User.DAO.insertNewUser(connection, u);

        assertTrue(result);
    }

    @Test
    void login() {
        final String email = "tryemail";
        final String password = "pass";

        final Optional<User> result = User.DAO.getUser(connection, email, password);
        assertFalse(result.isEmpty());        
    }
}
