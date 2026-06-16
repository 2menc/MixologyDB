package mix_db.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * represents an ingredient used in a drink, along with the number of times it has been utilized across all drinks.
 * This class provides a data structure for ingredient information and includes a static nested class for Data Access Object (DAO) operations.
 */
public class Ingredient {

    private final String ingredientName;
    private final int numUsed;

    /**
     * constructs a new Ingredient instance.
     * @param ingredientName the name of the ingredient.
     * @param numUsed the number of times this ingredient has been used in drinks.
     */
    public Ingredient(String ingredientName, int numUsed) {
        this.ingredientName = ingredientName;
        this.numUsed = numUsed;
    }

    /**
     * returns a string representation of the Ingredient object.
     * @return a string containing the ingredient's name and its usage count.
     */
    @Override
    public String toString() {
        return "Ingredient [ingredientName=" + ingredientName + ", numUsed=" + numUsed + "]";
    }

    /**
     * returns a hash code value for the object.
     * This method is supported for the benefit of hash tables such as those provided by {@link java.util.HashMap}.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ingredientName == null) ? 0 : ingredientName.hashCode());
        result = prime * result + numUsed;
        return result;
    }

    /**
     * indicates whether some other object is "equal to" this one.
     * The comparison is based on the ingredient name and the number of times it has been used.
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
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
     * provides Data Access Object (DAO) operations for {@link Ingredient} entities.
     * This static nested class encapsulates all database interactions related to ingredients.
     */
    public static final class DAO {

        /**
         * gets ALL materials (useful for tests)
         * @param connection the connection
         * @return a set of Ingredient
         */
        public static List<Ingredient> allIngredients(Connection connection) {
            final List<Ingredient> allIngredients = new LinkedList<>();

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

        /**
         * gets the ingredients of a drink.
         * @param connection the database connection.
         * @param drinkID the unique identifier of the drink.
         * @return a Set of ingredients associated with the specified drink.
         * @throws DAOException if a database access error occurs.
         */
        public static List<Ingredient> ofDrink(Connection connection, int drinkID) {
            final List<Ingredient> ingredients = new LinkedList<>();

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

        /**
         * creates a new ingredient in the database.
         * @param connection the database connection.
         * @param ingredientName the name of the ingredient to create.
         * @return true if the ingredient was successfully created (one row affected), false otherwise.
         * @throws DAOException if a database access error occurs.
         */
        public static boolean createIngredient(Connection connection, String ingredientName) {
            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.CREATE_INGREDIENT, 
                    ingredientName);
            ) {
                return (statement.executeUpdate() == 1);
            } catch(final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * searches for an ingredient by name and returns its name if found.
         * If the ingredient does not exist, it creates a new ingredient with the given name and then returns its name.
         * This ensures that an ingredient with the specified name always exists in the database after this method call.
         * @param connection the database connection.
         * @param name the name of the ingredient to search for or create.
         * @return the name of the existing or newly created ingredient.
         * @throws DAOException if a database access error occurs during search or creation.
         */
        public static String getOrCreateIngredient(Connection connection, String name) {

            try (var statement = DatabaseConnection.prepare(connection, 
                Queries.SEARCH_INGREDIENT, 
                name);
                var rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nomeIngrediente"); 
                }
            } catch (SQLException e) { throw new DAOException(e); }

            try (var statement = DatabaseConnection.prepare(connection,
                Queries.CREATE_INGREDIENT,
                name);
            ) {
                statement.executeUpdate();
                return name;
            } catch (SQLException e) {
                throw new DAOException(e); 
            }
        }


        /**
         * deletes an ingredient from the database.
         * @param connection the database connection.
         * @param ingredientName the name of the ingredient to delete.
         * @return true if the ingredient was successfully deleted (one row affected), false otherwise.
         * @throws DAOException if a database access error occurs.
         */
        public static boolean deleteIngredient(Connection connection, String ingredientName) {
            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.DELETE_INGREDIENT, 
                    ingredientName);
            ) {
                return (statement.executeUpdate() == 1);
            } catch(final Exception e) {
                throw new DAOException(e);
            }
        }

    }

    /**
     * gets the name of this ingredient.
     * @return the ingredient's name.
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * gets the number of times this ingredient has been used.
     * @return the usage count of the ingredient.
     */
    public int getNumUsed() {
        return numUsed;
    }
}