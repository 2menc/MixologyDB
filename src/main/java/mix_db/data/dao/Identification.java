package mix_db.data.dao;

/**
 * Drink - Identification - Tag
 */
public class Identification {

    private final int drinkID;
    private final String keyword;

    /**
     * constructor
     * @param drinkID .
     * @param keyword .
     */
    public Identification(int drinkID, String keyword) {
        this.drinkID = drinkID;
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "Identification [drinkID=" + drinkID + ", keyword=" + keyword + "]";
    }

    /**
     * DAO obj for Identification
     */
    public static final class DAO {

    }
}
