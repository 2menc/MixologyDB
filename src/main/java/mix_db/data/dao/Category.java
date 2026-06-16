package mix_db.data.dao;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * Category
 */
public class Category {

    private final String categoryName;
    private final String description;

    /**
     * constructor
     * @param categoryName the name of the category
     * @param description a brief description of the category
     */
    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    /**
     * calculates the hash code for this Category object.
     * @return the hash code for this object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    /**
     * compares this Category object to the specified object.
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        if (categoryName == null) {
            if (other.categoryName != null)
                return false;
        } else if (!categoryName.equals(other.categoryName))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }

    /**
     * DAO obj for Category
     */
    public static final class DAO {

        /**
         * creates a new category
         * @param connection the database connection
         * @param name the name of the category to create
         * @param description a description for the new category
         * @return true if the category is created successfully, false otherwise
         * @throws DAOException if a database access error occurs
         */
        public static boolean createCategory(Connection connection, String name, String description) {
            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.CREATE_CATEGORY,
                    name, description);
            ) {
                return (statement.executeUpdate() == 1);
            } catch(final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * deletes a category from the database.
         * @param connection the database connection
         * @param categoryName the name of the category to delete
         * @return true if the category was deleted successfully, false otherwise
         * @throws DAOException if a database access error occurs
         */
        public static boolean deleteCategory(Connection connection, String categoryName) {
            try (
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.DELETE_CATEGORY, 
                categoryName);
            ) {
                return (statement.executeUpdate() == 1);
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * gets all categories
         * @param connection the database connection
         * @return a list of category names
         * @throws DAOException if a database access error occurs
         */
        public static List<String> getAllCategories(Connection connection) {
            final var list = new LinkedList<String>();

            try (
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.GET_ALL_CATEGORIES);
                final var rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    list.add(rs.getString("nomeCategoria"));
                }
                return list;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }

    /**
     * gets the name of this category.
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * gets the description of this category.
     * @return the category description
     */
    public String getDescription() {
        return description;
    }

}