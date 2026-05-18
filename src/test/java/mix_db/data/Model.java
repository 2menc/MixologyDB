package mix_db.data;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;
import java.sql.Date;

import mix_db.data.dao.Bar;
import mix_db.data.dao.User;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;
import mix_db.model.DbModel;

/**
 * tests for model features
 */
public class Model {

    private mix_db.model.Model model;

    private static Connection connection;

    @SuppressWarnings("deprecation")
    @Test
    void setup() {
        connection = DatabaseConnection.localConnection("MixologyDB", "root", "Password");
        this.model = new DbModel(connection);
        
        final var user = new User(
            -1, 
            "try@email", 
            "password", 
            "testName", 
            "testSurname", 
            new Date(2000, 02, 2), 
            Role.USER, 
            null, 
            0, 0, 0);

        final var actualUser  = model.registerUser(user);
        assertFalse(actualUser.isEmpty());

        //Bar
        final var bar = new Bar(
            -1, 
            "Test Bar", 
            "Test City", 
            "Test Address");

        final var actualBar = model.createBar(bar);
        assertFalse(actualBar.isEmpty());

        // Link User to Bar
        assertTrue(model.addUserToBar(actualUser.get().getUserID(), actualBar.get().getBarID()));
    }

    @Test
    void teardown() {

        final String[] tablesToDelete = {
            "salvataggioPreferiti", 
            "identificazioni", 
            "composizioni", 
            "recensioni", 
            "creazioni", 
            "creazioniBar", 
            "occupazioni", 
            "Drink", 
            "User", 
            "Bar", 
            "Tag", 
            "Ingredient", 
            "Categoria"
        };

        for (var table : tablesToDelete) {
            try (
                final var statement = DatabaseConnection.prepare(connection, "DELETE FROM " + table);
            ) {
                statement.executeUpdate();
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
        
        try {
            connection.close();
        } catch (final Exception e) {
            throw new DAOException(e);
        }
    }
}
