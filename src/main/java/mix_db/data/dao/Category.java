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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + drinkID;
        result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
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
        Category other = (Category) obj;
        if (drinkID != other.drinkID)
            return false;
        if (keyword == null) {
            if (other.keyword != null)
                return false;
        } else if (!keyword.equals(other.keyword))
            return false;
        return true;
    }

    /**
     * DAO obj for Category
     */
    public static final class DAO {

    }

}
