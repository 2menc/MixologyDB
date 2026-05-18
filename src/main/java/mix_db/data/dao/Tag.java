package mix_db.data.dao;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * Tag
 */
public class Tag {

    private final String keyword;

    /**
     * constructor
     * @param keyword .
     */
    public Tag(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "Tag [keyword=" + keyword + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        Tag other = (Tag) obj;
        if (keyword == null) {
            if (other.keyword != null)
                return false;
        } else if (!keyword.equals(other.keyword))
            return false;
        return true;
    }

    /**
     * DAO obj for Tag
     */
    public static final class DAO {

        /**
         * gets a dirnk's tags
         * @param connection .
         * @param drinkID .
         * @return a list of tags
         */
        public static List<Tag> ofDrink(Connection connection, int drinkID) {
            final List<Tag> tags = new LinkedList<>();

            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.GET_DRINK_TAGS, 
                    drinkID);
                final var rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    final var t = new Tag(
                        rs.getString("keyword")
                    );

                    tags.add(t);
                }
                return tags;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

    }

    public String getKeyword() {
        return keyword;
    }
}
