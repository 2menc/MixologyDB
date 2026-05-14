package mix_db.data.dao;

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

    }
}
