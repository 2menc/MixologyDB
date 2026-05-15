package mix_db.data.dbConnection;

public class Queries {

    /**
     * gets all ingredients
     */
    public static final String ALL_INGREDIENTS = 
    """
    SELECT nomeIngrediente, volteUtilizzato
    FROM ingredienti
    """;

    /**
     * gets the ingredients related to this drinkID
     */
    public static final String INGREDIENTS_OF_DRINK =
    """
    SELECT I.nomeIngrediente, volteUtilizzato
    FROM composizioni C, ingredienti I
    WHERE C.nomeIngrediente = C.nomeIngrediente
    AND C.drinkID = ?;
    """;

}
