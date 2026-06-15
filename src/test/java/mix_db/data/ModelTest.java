package mix_db.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.Optional;

import mix_db.data.dao.User;
import mix_db.data.dao.Bar;
import mix_db.data.dbConnection.DatabaseConnection;
import mix_db.model.DbModel;
import mix_db.model.Model;

public class ModelTest {

    private Model model;
    private static Connection connection;

    @BeforeAll
    static void initConnection() {
        connection = DatabaseConnection.localConnection("MixologyDB", "root", "Password");
    }

    @BeforeEach
    void setup() {
        this.model = new DbModel(connection);
    }

    @AfterAll
    static void closeConnection() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    void testUserLifecycleAndAuthentication() {
        // 1) mockup data
        final User mockUser = new User(-1, "student@uni.edu", "pass", "Mario", "Rossi", new Date(100, 0, 1), Role.USER, null, 0, 0, 0);

        // 2) model check
        Optional<User> registeredOpt = model.registerUser(mockUser);
        assertTrue(registeredOpt.isPresent());
        
        User user = registeredOpt.get();

        // 3) sign in and login methods test
        assertTrue(model.userExists("student@uni.edu"));
        
        Optional<User> authenticatedUser = model.login("student@uni.edu", "pass");
        assertTrue(authenticatedUser.isPresent());
        assertEquals("Rossi", authenticatedUser.get().getSurname(), "surname not corresponding.");

        // 4) delete mockup
        boolean isDeleted = model.banUser(user.getUserID());
        assertTrue(isDeleted);
        assertFalse(model.userExists("student@uni.edu"));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testUserEmployedInBarLogic() {
        // mockup setup
        Optional<User> uOpt = model.registerUser(new User(-1, "bartender@uni.edu", "pass123", "Luigi", "Bianchi", new Date(95, 1, 1), Role.USER, null, 0, 0, 0));
        Optional<Bar> bOpt = model.createBar(new Bar(-1, "Mock Lounge", "Milano", "Centro"));
        
        assertTrue(uOpt.isPresent() && bOpt.isPresent(), "primary mockups registered.");
        
        User bartender = uOpt.get();
        Bar testBar = bOpt.get();

        boolean isAssociated = model.addUserToBar(testBar.getBarID(), bartender.getUserID());
        assertTrue(isAssociated, "Model should return true.");

        // Verifica Integrità Logica
        assertTrue(model.isUserInABar(bartender.getUserID()), "The userID must change.");
        Optional<Bar> targetBar = model.checkIfEmployed(bartender.getUserID());
        assertTrue(targetBar.isPresent());
        assertEquals("Mock Lounge", targetBar.get().getBarName(), "fail: user assigned to the wrong bar.");

        // Teardown
        assertTrue(model.banUser(bartender.getUserID()));
        
        // bar deletion
        assertTrue(Bar.DAO.deleteBar(connection, testBar.getBarID()));
    }
}