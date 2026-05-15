package mix_db.data.dbConnection;

public class Queries {

    public static final String GET_ALL_INGREDIENTS = 
    """
    SELECT nomeIngrediente, volteUtilizzato
    FROM ingredienti
    """;

}
