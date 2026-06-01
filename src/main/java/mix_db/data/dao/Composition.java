package mix_db.data.dao;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * Drink - Composition - Ingredient
 */
public class Composition {

    private final String ingredientName;
    private final int drinkID;
    private final float quantity;
    private final String measureUnit;

    /**
     * constructor
     * @param ingredientName .
     * @param drinkID .
     * @param quantity .
     * @param measureUnit .
     */
    public Composition(String ingredientName, int drinkID, float quantity, String measureUnit) {
        this.ingredientName = ingredientName;
        this.drinkID = drinkID;
        this.quantity = quantity;
        this.measureUnit = measureUnit;
    }

    @Override
    public String toString() {
        return "Composition [ingredientName=" + ingredientName + ", drinkID=" + drinkID + ", quantity=" + quantity
                + ", measureUnit=" + measureUnit + "]";
    }

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
         * gets the composition of the drink
         * @param drinkID .
         * @return a list of campositions ingredientName, quantity, unit of measure
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

    public String getIngredientName() {
        return ingredientName;
    }

    public int getDrinkID() {
        return drinkID;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

}
