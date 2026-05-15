package mix_db.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;
import java.util.TreeSet;

import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;
import mix_db.data.dbConnection.Queries;

/**
 * Ingredient
 */
public class Ingredient {

    private final String ingredientName;
    private final int numUsed;

    /**
     * constructor
     * @param ingredientName .
     * @param numUsed .
     */
    public Ingredient(String ingredientName, int numUsed) {
        this.ingredientName = ingredientName;
        this.numUsed = numUsed;
    }

    @Override
    public String toString() {
        return "Ingredient [ingredientName=" + ingredientName + ", numUsed=" + numUsed + "]";
    }

    /**
     * DAO obj for Ingredient
     */
    public static final class DAO {

        public static Set<Ingredient> allMaterials(Connection connection) {
            final Set<Ingredient> allIngredients = new TreeSet<>();

            // try with resources: controlla prima che si instanzino correttamente statement e resultSet
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, Queries.GET_ALL_INGREDIENTS);
                final ResultSet rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    final String ingredientName = rs.getString("nomeIngrediente");
                    final int numUsed = rs.getInt("volteUtilizzato");
                    
                    final Ingredient ingredient = new Ingredient(ingredientName, numUsed);
                    allIngredients.add(ingredient);
                }
            } catch (final Exception e) {
                throw new DAOException(e);
            }

            return allIngredients;
        }
    }
}
