package mix_db.data;

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

    /**
     * registers a new user
     */
    public static final String REGISTER_USER = 
    """
    INSERT INTO Utenti (email, password, nome, cognome, dataNascita, ruoloUtente, dataIscrizione, 
        numeroRicetteCreate, numeroRecensioniPositive, numeroRecensioniEffettuate)
    VALUES (?, ?, ?, ?, ?, ?, DATE(NOW()), 0, 0, 0);
    """;

    public static final String CREATE_INGREDIENT =
    """
    INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato)
    VALUES (?, 0);        
    """;

    /**
     * logs in with an existing user
     */
    public static final String LOGIN =
    """
    SELECT *
    FROM Utenti
    WHERE email = ?
    AND password = ?;
    """;

    /**
     * creates a new bar
     */
    public static final String CREATE_BAR =
    """
    INSERT INTO Bar (nomeBar, città, indirizzo)
    VALUES (?, ?, ?);
    """;

    /**
     * adds an employee to the specified bar
     */
    public static final String ADD_EMPLOYEE =
    """
    INSERT INTO occupazioni (userID, barID)
    VALUES (?, ?);
    """;

    /**
     * searches a bar
     */
    public static final String SEARCH_BAR =
    """
    SELECT *
    FROM Bar
    WHERE nomeBar = ? 
    AND città = ?
    AND indirizzo = ?;
    """;

    /**
     * deletes a bar
     */
    public static final String DELETE_BAR =
    """
    DELETE FROM Bar
    WHERE barID = ?;     
    """;

    /**
     * creates a new category
     */
    public static final String CREATE_CATEGORY =
    """
    INSERT INTO Categorie (nomeCategoria, descrizione)
    VALUES (?,?);        
    """;

    /**
     * deletes a category
     * ! ---
     * {@link DELETE_DRINK_BY_CATEGORYNAME}
     */
    public static final String DELETE_CATEGORY =
    """
    DELETE FROM Categorie 
    WHERE nomeCategoria = ?;
    """;
    
    /**
     * deletes drinks having categoryname = ?
     */
    public static final String DELETE_DRINK_BY_CATEGORYNAME =
    """
    DELETE FROM drink 
    WHERE nomeCategoria = ?;    
    """;

    /*
    * DRINK CREATION START
    ! TRANSACTION START
    */

    /**
     * creates, categorizes, identifies and links to the user a new drink
     */
    public static final String CREATE_DRINK =
    """
    INSERT INTO Drink (nome, descrizione, foto, nomeCategoria, IBA)
    VALUES (?, ?, ?, ?, false);
    """;

    /**
     * links the drink with the creator
     */
    public static final String LINK_DRINK_WITH_USER =
    """
    INSERT INTO creazioni (drinkID, dataCreazione, userID)
    VALUES (?, DATE(NOW()), ?);
    """;

    /**
     * inserts  drink's ingredients
     */
    public static final String INSERT_DRINK_INGREDIENTS =
    """
    INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura)
    VALUES (?, ?, ?, ?)        
    """;

    /**
     * increments the timeUsed ingredient counter
     */
    public static final String UPDATE_INGREDIENT_TIMEUSED_COUNTER =
    """
    UPDATE Ingredienti
    SET volteUtilizzato = volteUtilizzato+1
    WHERE nomeIngrediente = ?
    """;

    /**
     * updates the user creation counter
     */
    public static final String UPDATE_USER_CREATIONS_COUNTER =                                                  
    """
    UPDATE Utenti
    SET numeroRicetteCreate = numeroRicetteCreate+1
    WHERE userID = ?;
    """;
    
    /*
    * DRINK CREATION STOP
    ! TRANSACTION STOP
    */


    /**
     * saves a drink in the favourites section
     */
    public static final String SAVE_FAVOURITE =
    """
    INSERT INTO salvataggioPreferiti (drinkID, userID, dataSalvataggio)
    VALUES (?, ?, DATE(NOW()));        
    """;

    /**
     * gets the list of user's favourite drinks
     */
    public static final String GET_FAVOURITES =
    """
    SELECT D.*
    FROM Utenti U, salvataggioPreferiti SP, Drink D
    WHERE U.userID = SP.userID
    AND SP.drinkID = D.drinkID
    AND U.userID = ?;    
    """;

    public static final String REMOVE_FAVOURITE =
    """
    DELETE FROM salvataggioPreferiti
    WHERE userID = ?
    AND drinkID = ?;
    """;

    /**
     * adds a review to an existing drink
     */
    public static final String CREATE_REVIEW =
    """
    NSERT INTO recensioni (drinkID, userID, descrizione, dataRecensione, voto)
    VALUES (?, ?, ?, DATE(NOW()), ?);      
    """;

    /**
     * updates the {@code numeroRecensioniEffettuate} counter in User
     */
    public static final String UPDATE_USER_REVIEW_NUMBER_COUNTER =
    """
    UPDATE utenti
    SET numeroRecensioniEffettuate = numeroRecensioniEffettuate+1
    WHERE userID = ?;
    """;

    /**
     * updates the {@code numeroRecensioniPositive} counter in User
     */
    public static final String UPDATE_USER_POSITIVE_REVIEW_COUNTER =
    """
    UPDATE utenti
    SET numeroRecensioniPositive = numeroRecensioniPositive+1
    WHERE userID = ?;
    """;

    /**
     * searches by keyword, ingredients, description, drink name
     */
    public static final String SEARCH_BY_KEYWORD =
    """
    SELECT DISTINCT D.*
    FROM Drink D
    LEFT JOIN identificazioni I
    ON D.drinkID = I.drinkID
    LEFT JOIN composizioni C
    ON D.drinkID = C.drinkID
    WHERE D.nome = ?
    OR I.keyword = ?
    OR C.nomeIngrediente = ?
    OR D.nomeCategoria = ?
    OR D.descrizione LIKE CONCAT('%', ?, '%');
    """;

    /**
     * calculates the drinks with the most positive reviews in a time period leaderbord
     */
    public static final String MORE_POSITIVE_REVIEWS_DRINK_LEADERBOARD =
    """
    SELECT D.*, COUNT(*) AS numero
    FROM Drink D, recensioni R
    WHERE D.drinkID = R.drinkID
    AND voto > 2
    AND DATEDIFF(NOW(), R.dataRecensione) <= ?
    GROUP by D.drinkId, D.nome, D.foto
    ORDER BY COUNT(R.voto) DESC
    LIMIT ?;        
    """;

    /**
     * gets the list of the most used ingredients
     */
    public static final String MOST_USED_INGREDIENTS =
    """
    SELECT *
    FROM ingredienti
    ORDER BY volteUtilizzato DESC
    LIMIT ?;
    """;

    public static final String USERS_WITH_MORE_POSITIVE_REVIEWS =
    """
    SELECT *
    FROM utenti
    ORDER BY numeroRecensioniPositive DESC
    LIMIT ?;        
    """;

    /**
     * gets a list of the trending keywords
     */
    public static final String TRENDING_KEYWORDS =
    """
    SELECT keyword, COUNT(*) AS tendenza
    FROM identificazioni I, recensioni R
    WHERE I.drinkID = R.drinkID
    AND DATEDIFF(DATE(NOW()), R.dataRecensione) <= ?
    GROUP BY keyword
    ORDER BY tendenza DESC LIMIT ?;        
    """;

    /**
     * gets the dring suggested for the user
     */
    public static final String SUGGEST_DRINKS =
    """
    SELECT DISTINCT D.*
    FROM Drink D, identificazioni I
    WHERE D.drinkID = I.drinkID
    AND I.keyword IN
    (SELECT I2.keyword
    FROM salvataggioPreferiti SP, identificazioni I2
    WHERE I2.drinkID = SP.drinkID
    AND SP.userID = ?)
    AND D.drinkID NOT IN
    (SELECT drinkID
    FROM salvataggioPreferiti SP2
    WHERE SP2.userID = ?)
    LIMIT ?;       
    """;

    /**
     * gets user analitics
     */
    public static final String GET_USER_ANALITICS =
    """
    SELECT *
    FROM utenti;       
    """;

    /**
     * gets user's reviews
     */
    public static final String GET_USER_REVIEWS =
    """
    SELECT descrizione, dataRecensione, voto
    FROM utenti U, recensioni R
    WHERE U.userID = R.userID
    AND U.userID = ?;       
    """;

    /**
     * deletes a review. automatically deletes relations
     */
    public static final String DELETE_REVIEW =
    """
    DELETE FROM recensioni
    WHERE drinkId = ?
    AND userID = ?;        
    """;

    /**
     * deletes a drink. automatically deletes relations
     */
    public static final String DELETE_DRINK =
    """
    DELETE FROM drink
    WHERE drinkID = ?;        
    """;

    /**
     * sets all user's drink as "anonymous" 
     */
    public static final String PREPARE_TO_BAN_USER =
    """
    UPDATE creazioni
    SET userID = 0
    WHERE userID = ?;
    """;

    public static final String BAN_USER =
    """
    DELETE FROM utenti
    WHERE userID = ?;        
    """;
}
