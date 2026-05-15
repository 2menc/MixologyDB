package mix_db.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.util.Set;

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
    private static void setup () {
        connection = DatabaseConnection.localConnection("localHost", "root", "Password");
    }

    @Test
    public void ingredients() {
        final var actual = Ingredient.DAO.allMaterials(connection);
        final var expected = Set.of(new Ingredient("gin", 1));

        assertThat(actual).hasSameElementsAs(expected);
    }
}
