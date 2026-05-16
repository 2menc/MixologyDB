package mix_db.data.dao;

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
