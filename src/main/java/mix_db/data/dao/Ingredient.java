package mix_db.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ingredientName == null) ? 0 : ingredientName.hashCode());
        result = prime * result + numUsed;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ingredient other = (Ingredient) obj;
        if (ingredientName == null) {
            if (other.ingredientName != null)
                return false;
        } else if (!ingredientName.equals(other.ingredientName))
            return false;
        if (numUsed != other.numUsed)
            return false;
        return true;
    }

    /**
     * DAO obj for Ingredient
     */
    public static final class DAO {

        /**
         * gets ALL materials (useful for tests)
         * @param connection the connection
         * @return a set of Ingredient
         */
        public static Set<Ingredient> allMaterials(Connection connection) {
            final Set<Ingredient> allIngredients = new HashSet<>();

            // try with resources: controlla prima che si instanzino correttamente statement e resultSet
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, Queries.ALL_INGREDIENTS);
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

        public static Set<Ingredient> ofDrink(Connection connection, String drinkID) {
            final Set<Ingredient> ingredients = new HashSet<>();

            try (
                final var statement = DatabaseConnection.prepare(connection, Queries.INGREDIENTS_OF_DRINK, drinkID);
                final var rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    final String ingredientName = rs.getString("nomeIngrediente");
                    final int numUsed = rs.getInt("volteUtilizzato");
                
                    ingredients.add(new Ingredient(ingredientName, numUsed));
                }
            } catch (final Exception e) {
                throw new DAOException(e);
            }

            return ingredients;
        }
    }
}
