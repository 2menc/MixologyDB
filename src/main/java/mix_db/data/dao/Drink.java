package mix_db.data.dao;

/**
 * Drink
 */
public class Drink {

    private final int drinkID;
    private final String name;
    private final String description;
    private final String imagePath;
    private final String categoryName;
    private final boolean isIBA;

    /**
     * constructor
     * @param drinkID .
     * @param name .
     * @param description .
     * @param imagePath .
     * @param categoryName .
     * @param isIBA .
     */
    public Drink(int drinkID, String name, String description, String imagePath, String categoryName, boolean isIBA) {
        this.drinkID = drinkID;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.categoryName = categoryName;
        this.isIBA = isIBA;
    }

    @Override
    public String toString() {
        return "Drink [drinkID=" + drinkID + ", name=" + name + ", description=" + description + ", imagePath="
                + imagePath + ", categoryName=" + categoryName + ", isIBA=" + isIBA + "]";
    }

    /**
     * DAO for Drink
     */
    public static final class DAO {

    }
}
