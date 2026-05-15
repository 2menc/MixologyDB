package mix_db.data;

/**
 * Test queries 
 */
public final class Queries {

    public static final String GET_ALL_INGREDIENTS = 
    """
    SELECT nomeIngrediente, volteUtilizzato
    FROM ingredienti
    """;

    public static final String GET_INGREDIENT =
    """
    SELECT nomeIngrediente, volteUtilizzato
    FROM ingredienti
    WHERE nomeIngrediente = ?
    """;
}
