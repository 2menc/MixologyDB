package mix_db.data.dao;

/**
 * User - Employment - bar
 */
public class Employment {

    private final int userID;
    private final int barID;

    /**
     * constructor
     * @param userID userID
     * @param barID barID
     */
    public Employment(int userID, int barID) {
        this.userID = userID;
        this.barID = barID;
    }

    @Override
    public String toString() {
        return "Employment [userID=" + userID + ", barID=" + barID + "]";
    }

    /**
     * DAO object for Employment
     */
    public static final class DAO {

    }
}
