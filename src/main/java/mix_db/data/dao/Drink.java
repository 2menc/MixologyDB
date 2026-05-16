package mix_db.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;
import mix_db.data.dbConnection.Queries;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + drinkID;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
        result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
        result = prime * result + (isIBA ? 1231 : 1237);
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
        Drink other = (Drink) obj;
        if (drinkID != other.drinkID)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (imagePath == null) {
            if (other.imagePath != null)
                return false;
        } else if (!imagePath.equals(other.imagePath))
            return false;
        if (categoryName == null) {
            if (other.categoryName != null)
                return false;
        } else if (!categoryName.equals(other.categoryName))
            return false;
        if (isIBA != other.isIBA)
            return false;
        return true;
    }

    /**
     * DAO for Drink
     */
    public static final class DAO {

        /**
         * inserts a new drink
         * @param connection .
         * @param d the drink to insert
         * @return {@code true} if can insert the drink, {@code false} otherwise
         */
        public static boolean createDrink(Connection connection, Drink d) {
            try (
               final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                Queries.CREATE_DRINK, d); 
            ) {
                return (statement.executeUpdate() == 1);
            } catch(final Exception e) {
                throw new DAOException(e);
            }
        }

    }

    public int getDrinkID() {
        return drinkID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public boolean isIBA() {
        return isIBA;
    }
}
