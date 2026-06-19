package mix_db.core;


/**
 * General settings
 */
public final class GeneralSettings {

    /**
     * 0 args private constructor
     */
    private GeneralSettings() {} 
    
    /** standard path for drink photos */
    public static final String fotoPath = "images/";
    
    /** standard path for pdf fonts */
    public static final String fontPath = "src/main/resources/fonts/";

    /** standard path for icons */
    public static final String iconsPath = "src/main/resources/icons/";

    /** the IP address for the database server */
    public static final String databseIP = "127.0.0.1";

    /** the name of the dfatabase */
    public static final String databaseName = "MixologyDB";

    /** the userName */
    public static final String databaseUser = "root";

    /** the database password */
    public static final String databasePassword = "Password";

}