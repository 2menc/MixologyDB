package mix_db.data.dao;

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

    }
}
