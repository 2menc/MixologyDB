package mix_db.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.Optional;

import mix_db.data.dao.Bar;
import mix_db.data.dbConnection.DatabaseConnection;

public class DaoTest {

    private static Connection connection;

    @BeforeAll
    static void initConnection() {
        connection = DatabaseConnection.localConnection("MixologyDB", "root", "Password");
    }

    @AfterAll
    static void closeConnection() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    void testBarLifecycle() {
        // 1) population with mockup
        Bar mockBar = new Bar(-1, "DAO Test Bar Mock", "Roma", "Piazza Navona 1");
        
        // 2) insert success check
        assertDoesNotThrow(() -> Bar.DAO.createBar(connection, mockBar), "ERROR: The insert should not fail.");

        // 3) integrity check
        Optional<Bar> barOpt = Bar.DAO.searchBarByParams(connection, "DAO Test Bar Mock", "Roma", "Piazza Navona 1");
        assertTrue(barOpt.isPresent(), "The inserted bar should be in the database.");
        
        Bar bar = barOpt.get();
        assertEquals("DAO Test Bar Mock", bar.getBarName(), "Name not matching.");
        assertTrue(bar.getBarID() > 0, "id not valid (should be > 0).");

        // 4) delete 
        boolean isDeleted = Bar.DAO.deleteBar(connection, bar.getBarID());
        assertTrue(isDeleted, "The delete should return true.");
        
        // general check
        Optional<Bar> checkDeleted = Bar.DAO.searchBar(connection, bar.getBarID());
        assertFalse(checkDeleted.isPresent(), "Mockup bar not found in the database.");
    }
}