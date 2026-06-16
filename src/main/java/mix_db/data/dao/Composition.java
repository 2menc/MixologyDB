package mix_db.data.dao;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * Represents the composition of a drink, detailing an ingredient, its quantity, and unit of measure for a specific drink.
 * This class links drinks and ingredients, specifying how much of each ingredient is needed for a particular drink.
 */
public class Composition {

    private final String ingredientName;
    private final int drinkID;
    private final float quantity;
    private final String measureUnit;

    /**
     * constructs a new Composition instance.
     * @param ingredientName the name of the ingredient.
     * @param drinkID the unique identifier of the drink.
     * @param quantity the amount of the ingredient used in the drink.
     * @param measureUnit the unit of measure for the ingredient quantity (e.g., "ml", "g", "dash").
     */
    public Composition(String ingredientName, int drinkID, float quantity, String measureUnit) {
        this.ingredientName = ingredientName;
        this.drinkID = drinkID;
        this.quantity = quantity;
        this.measureUnit = measureUnit;
    }

    /**
     * returns a string representation of this Composition object.
     * @return a string containing the ingredient name, drink ID, quantity, and measure unit.
     */
    @Override
    public String toString() {
        return "Composition [ingredientName=" + ingredientName + ", drinkID=" + drinkID + ", quantity=" + quantity
                + ", measureUnit=" + measureUnit + "]";
    }

    /**
     * returns a hash code value for this Composition object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ingredientName == null) ? 0 : ingredientName.hashCode());
        result = prime * result + drinkID;
        result = prime * result + Float.floatToIntBits(quantity);
        result = prime * result + ((measureUnit == null) ? 0 : measureUnit.hashCode());
        return result;
    }

    /**
     * indicates whether some other object is "equal to" this one.
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
        Composition other = (Composition) obj;
        if (ingredientName == null) {
            if (other.ingredientName != null)
                return false;
        } else if (!ingredientName.equals(other.ingredientName))
            return false;
        if (drinkID != other.drinkID)
            return false;
        if (Float.floatToIntBits(quantity) != Float.floatToIntBits(other.quantity))
            return false;
        if (measureUnit == null) {
            if (other.measureUnit != null)
                return false;
        } else if (!measureUnit.equals(other.measureUnit))
            return false;
        return true;
    }
    
    /**
     * DAO obj for Composition
     */
    public static final class DAO {

        /**
         * retrieves the composition details for a specific drink from the database.
         * @param connection the database connection to use.
         * @param drinkID the unique identifier of the drink whose composition is to be retrieved.
         * @return a list of Composition objects, each representing an ingredient, its quantity, and unit of measure for the specified drink.
         * @throws DAOException if a database access error occurs.
         */
        public static List<Composition> getComposition(Connection connection, int drinkID) {
            final var l = new LinkedList<Composition>();

            try (
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.GET_DRINK_COMPOSITION, 
                drinkID);
                final var rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    final var c = new Composition(
                        rs.getString("nomeIngrediente"), 
                        rs.getInt("drinkID"), 
                        rs.getFloat("quantita"), 
                        rs.getString("unitaDiMisura")
                    );
                    l.add(c);
                }
                return l;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }

    /**
     * gets the name of the ingredient.
     * @return the ingredient name.
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * gets the unique identifier of the drink.
     * @return the drink ID.
     */
    public int getDrinkID() {
        return drinkID;
    }

    /**
     * gets the quantity of the ingredient.
     * @return the quantity.
     */
    public float getQuantity() {
        return quantity;
    }

    /**
     * gets the unit of measure for the ingredient quantity.
     * @return the measure unit.
     */
    public String getMeasureUnit() {
        return measureUnit;
    }

}