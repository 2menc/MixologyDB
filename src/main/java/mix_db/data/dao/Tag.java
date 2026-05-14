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

    /**
     * DAO obj for Tag
     */
    public static final class DAO {

    }
}
