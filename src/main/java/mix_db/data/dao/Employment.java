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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + userID;
        result = prime * result + barID;
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
        Employment other = (Employment) obj;
        if (userID != other.userID)
            return false;
        if (barID != other.barID)
            return false;
        return true;
    }

    /**
     * DAO object for Employment
     */
    public static final class DAO {

    }

}
