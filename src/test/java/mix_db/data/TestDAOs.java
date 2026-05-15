package mix_db.data;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.*;

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
    void ListOfIngredients() {
        final var actual = Ingredient.DAO.allMaterials(connection);
        final var expected = new Ingredient("Gin", 0);

        assertThat(actual).contains(expected);
    }

    @Test 
    void ingredientsOf() {
        final var actual = Ingredient.DAO.ofDrink(connection, "5");
        final var expected = new Ingredient("Gin", 0);

        assertThat(actual.contains(expected));
    }
}
