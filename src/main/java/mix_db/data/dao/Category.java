package mix_db.data.dao;

/**
 * Category
 */
public class Category {

    private final int drinkID;
    private final String keyword;

    public Category(int drinkID, String keyword) {
        this.drinkID = drinkID;
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "category [drinkID=" + drinkID + ", keyword=" + keyword + "]";
    }

    /**
     * DAO obj for Category
     */
    public static final class DAO {

    }
}
