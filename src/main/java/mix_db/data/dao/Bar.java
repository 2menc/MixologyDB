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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + barID;
        result = prime * result + ((barName == null) ? 0 : barName.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
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
        Bar other = (Bar) obj;
        if (barID != other.barID)
            return false;
        if (barName == null) {
            if (other.barName != null)
                return false;
        } else if (!barName.equals(other.barName))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        return true;
    }

    /**
     * DAO object for Bar
     */
    public static final class DAO {

    }
}
