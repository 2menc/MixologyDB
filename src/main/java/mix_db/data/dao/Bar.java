package mix_db.data.dao;

/**
 * Bar
 */
public class Bar {

    private final int barID;
    private final String barName;
    private final String city;
    private final String address;

    /**
     * constructor
     * @param barID barID
     * @param barName barName
     * @param city city
     * @param address address
     */
    public Bar(int barID, String barName, String city, String address) {
        this.barID = barID;
        this.barName = barName;
        this.city = city;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Bar [barID=" + barID + ", barName=" + barName + ", city=" + city + ", address=" + address + "]";
    }

    /**
     * DAO object for Bar
     */
    public static final class DAO {

    }
}
