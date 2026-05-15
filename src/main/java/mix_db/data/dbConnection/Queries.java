package mix_db.data.dbConnection;

public class Queries {

    public static final String ALL_INGREDIENTS = 
    """
    SELECT nomeIngrediente, volteUtilizzato
    FROM ingredienti
    """;

    public static final String INGREDIENTS_OF_DRINK =
    """
    SELECT I.nomeIngrediente, volteUtilizzato
    FROM composizioni C, ingredienti I
    WHERE C.nomeIngrediente = C.nomeIngrediente
    AND C.drinkID = ?;
    """;

}
