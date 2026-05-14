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

    /**
     * DAO obj for Composition
     */
    public static final class DAO {

    }
}
