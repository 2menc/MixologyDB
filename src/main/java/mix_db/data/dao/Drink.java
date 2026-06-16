package mix_db.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * Represents a drink entity with its properties such as ID, name, description, image path, category, and IBA status.
 * This class is immutable once created.
 */
public class Drink {

    private final int drinkID;
    private final String name;
    private final String description;
    private final String imagePath;
    private final String categoryName;
    private final boolean isIBA;

    /**
     * constructs a new Drink instance.
     * @param drinkID the unique identifier for the drink.
     * @param name the name of the drink.
     * @param description a brief description of the drink.
     * @param imagePath the file path to the drink's image.
     * @param categoryName the name of the category the drink belongs to.
     * @param isIBA a boolean indicating if the drink is an IBA (International Bartenders Association) official cocktail.
     */
    public Drink(int drinkID, String name, String description, String imagePath, String categoryName, boolean isIBA) {
        this.drinkID = drinkID;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.categoryName = categoryName;
        this.isIBA = isIBA;
    }

    @Override
    public String toString() {
        return "Drink [drinkID=" + drinkID + ", name=" + name + ", description=" + description + ", imagePath="
                + imagePath + ", categoryName=" + categoryName + ", isIBA=" + isIBA + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + drinkID;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
        result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
        result = prime * result + (isIBA ? 1231 : 1237);
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
        Drink other = (Drink) obj;
        if (drinkID != other.drinkID)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (imagePath == null) {
            if (other.imagePath != null)
                return false;
        } else if (!imagePath.equals(other.imagePath))
            return false;
        if (categoryName == null) {
            if (other.categoryName != null)
                return false;
        } else if (!categoryName.equals(other.categoryName))
            return false;
        if (isIBA != other.isIBA)
            return false;
        return true;
    }

    /**
     * DAO for Drink
     */
    public static final class DAO {

        /**
         * inserts a new drink as barCreation using transactions.
         * @param connection the database connection to use for the transaction.
         * @param d the drink to insert.
         * @param barID an optional ID of the bar associated with the drink, if any.
         * @param userID the ID of the user creating the drink.
         * @param composition a list of ingredients and their quantities for the drink.
         * @param keywords the keywords to identify the drink.
         * @throws DAOException if a database access error occurs during the transaction.
         */
        public static void createDrink(Connection connection, Drink d, Optional<Integer> barID, int userID, List<Composition> composition, List<String> keywords) {
            
            try {
                // !transaction start
                connection.setAutoCommit(false);
                int correctDrinkId;

                final String finalPathName = d.getImagePath();
                
                // *creates the drink
                try(
                    final var statement = DatabaseConnection.prepareWithKeys(connection, 
                        Queries.CREATE_DRINK, 
                    d.getName(), d.getDescription(), finalPathName, d.getCategoryName());
                ) {
                    statement.executeUpdate();

                    // ? the drinkID is currently -1: before linking the program should get the correct id
                    try(final var rs = statement.getGeneratedKeys()) {
                        if(rs.next()) {
                            correctDrinkId = rs.getInt(1);
                        } else {
                            throw new SQLException("Insert failed: no drinkID specified");
                        }
                    }

                } catch(Exception e) {
                    throw new DAOException(e);
                }

                // *links drink to user

                //? no barID
                if(barID.isEmpty()) {
                    try (
                        final var statement = DatabaseConnection.prepare(connection, 
                                Queries.LINK_DRINK_WITHOUT_BAR,
                                correctDrinkId, userID);
                        ){                    
                            statement.executeUpdate();
                    } catch(final Exception e) {
                        throw new DAOException(e);
                    }
                } else {
                // ? with barID
                    try (
                        final var statement = DatabaseConnection.prepare(connection, 
                            Queries.LINK_DRINK_WITH_BAR,
                            correctDrinkId, barID.get(), userID); 
                    ) {
                        statement.executeUpdate();
                    } catch(final Exception e) {
                        throw new DAOException(e);
                    }
                }

                // *inserts ingredients
                for(var c: composition) {
                    final String correctIngredientName = Ingredient.DAO.getOrCreateIngredient(connection, c.getIngredientName());

                    try (
                        final var statement = DatabaseConnection.prepare(connection, 
                            Queries.INSERT_DRINK_INGREDIENTS, 
                            correctIngredientName, correctDrinkId, c.getQuantity(), c.getMeasureUnit());
                        ) {
                            statement.executeUpdate();
                        } catch(final Exception e) {
                            throw new DAOException(e);
                        }
                }

                // *inserts keywords
                for(String k : keywords) {
                    final String correctKeyword = Tag.DAO.getOrCreateTag(connection, k);

                    try (final var statement = DatabaseConnection.prepare(connection, 
                            Queries.INSERT_DRINK_KEYWORD, 
                            correctDrinkId, correctKeyword);
                    ) {
                        statement.executeUpdate();
                    }  
                }

                // *increments ingredients timeUsed counter
                for(var c: composition) {
                    try (
                        final var statement = DatabaseConnection.prepare(connection, 
                            Queries.UPDATE_INGREDIENT_TIMEUSED_COUNTER, 
                        c.getIngredientName())
                    ) {
                        statement.executeUpdate();
                    } catch(final Exception e) {
                        throw new DAOException(e);
                    }
                }
                
                // ! COMMIT
                connection.commit();
                
            } catch (final Exception e) {
                // ?if this exception was thrown, then the transaction has to be interrupted:
                // ! transaction ends with rollback
                try {
                    connection.rollback();
                } catch(final SQLException rollBackException) {
                    throw new DAOException(rollBackException);
                }

                // and the DAOexception has to be thrown with the previews trace
                throw new DAOException(e);
            } finally {
                // if no exceptions were thrown, seta the autocommit true and commits the transacrion
                // ! transaction ends with commit
                try{
                    connection.setAutoCommit(true);
                } catch(SQLException autoCommitException) {
                    //ignores the exception
                }
            }
        }

        /**
         * searches a drink by name.
         * @param connection the database connection.
         * @param name the name of the drink to search for.
         * @return an {@code Optional} containing the {@link Drink} if found, or an empty {@code Optional} otherwise.
         * @throws DAOException if a database access error occurs.
         */
        public static Optional<Drink> searchByName(Connection connection, String name) {
            try (
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.SEARCH_DRINK_BY_NAME,
                    name);
                final var rs = statement.executeQuery()
            ) {
                if(rs.next()) {
                    final var d = new Drink(
                        rs.getInt("drinkID"), 
                        rs.getString("nome"),
                        rs.getString("descrizione"), 
                        rs.getString("foto"), 
                        rs.getString("nomeCategoria"),
                        rs.getBoolean("IBA")
                        );
                    return Optional.of(d);
                } 
            } catch(Exception e) {
                throw new DAOException(e);
            }
            return Optional.empty();
        }

        /**
         * gets a list of random drinks.
         * @param connection the database connection.
         * @param numberOfResults the maximum number of random drinks to retrieve.
         * @return a {@code List} of {@link Drink} objects, shuffled randomly.
         * @throws DAOException if a database access error occurs.
         */
        public static List<Drink> getRandomDrinkList(Connection connection, int numberOfResults) {
            final List<Drink> drinkList = new LinkedList<>();

            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.GET_RANDOM_DRINKS, numberOfResults);
                final var rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    final var d = new Drink(
                        rs.getInt("drinkID"), 
                        rs.getString("nome"),
                        rs.getString("descrizione"), 
                        rs.getString("foto"), 
                        rs.getString("nomeCategoria"),
                        rs.getBoolean("IBA")
                        );
                    drinkList.add(d);
                }
                Collections.shuffle(drinkList);
                return drinkList;
            } catch(Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * searches a drink by its ID.
         * @param connection the database connection.
         * @param drinkID the unique identifier of the drink to search for.
         * @return an {@code Optional} containing the {@link Drink} if found, or an empty {@code Optional} otherwise.
         * @throws DAOException if a database access error occurs.
         */
        public static Optional<Drink> getDrink(Connection connection, int drinkID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.SEARCH_DRINK, drinkID);
                final var rs = statement.executeQuery();
            ) {
                if(rs.next()) {
                    final var d = new Drink(
                        rs.getInt("drinkID"), 
                        rs.getString("nome"),
                        rs.getString("descrizione"), 
                        rs.getString("foto"), 
                        rs.getString("nomeCategoria"),
                        rs.getBoolean("IBA")
                        );

                    return Optional.of(d);
                }
                return Optional.empty();
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * saves a drink as favourite for a specific user.
         * @param connection the database connection.
         * @param drinkID the ID of the drink to save as favourite.
         * @param userID the ID of the user for whom to save the favourite drink.
         * @return true if the drink was successfully saved as a favourite (i.e., it was not already a favourite), false otherwise.
         * @throws DAOException if a database access error occurs.
         */
        @Deprecated
        public static boolean saveAsFavourite(Connection connection, int drinkID, int userID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.SAVE_FAVOURITE, drinkID, userID);
            ) {
                return (statement.executeUpdate() == 1);
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * ! FOR TESTS
         * deletes drinks by category name. This method is intended for testing purposes only.
         * @param connection the database connection.
         * @param categoryName the name of the category whose drinks are to be deleted.
         * @throws DAOException if a database access error occurs.
         */
        @Deprecated
        public static void deleteByCategoryName(Connection connection, String categoryName) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection, 
                    Queries.DELETE_DRINK_BY_CATEGORYNAME, 
                    categoryName);
            ) {
                statement.executeUpdate();
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * searches a drink by keywords.
         * @param connection the database connection.
         * @param keyword the keyword to search for within drink names, descriptions, or categories.
         * @return a {@code List} of {@link Drink} objects matching the keyword.
         * @throws DAOException if a database access error occurs.
         */
        public static List<Drink> searchByKeyword(Connection connection, String keyword) {
            final List<Drink> drinks = new LinkedList<>();

            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.SEARCH_BY_KEYWORD, 
                    keyword,
                    keyword,
                    keyword,
                    keyword,
                    keyword);
                final var rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    final var d = new Drink(
                        rs.getInt("drinkID"), 
                        rs.getString("nome"),
                        rs.getString("descrizione"), 
                        rs.getString("foto"), 
                        rs.getString("nomeCategoria"),
                        rs.getBoolean("IBA")
                        );

                        drinks.add(d);
                    }

                    return drinks;
                } catch(final Exception e) {
                    throw new DAOException(e);
                }
            }

            /**
             * gets the creator of the drink.
             * @param connection the database connection.
             * @param drinkID the ID of the drink whose creator is to be retrieved.
             * @return an {@code Optional} containing the {@link User} who created the drink, or an empty {@code Optional} if not found.
             * @throws DAOException if a database access error occurs.
             */
            public static Optional<User> getCreator(Connection connection, int drinkID) {
                try(
                    final var statement = DatabaseConnection.prepare(connection, 
                        Queries.GET_DRINK_CREATOR, 
                        drinkID);
                    final var rs = statement.executeQuery();
                ) {
                    if(rs.next()) {
                        final User u = new User(
                            rs.getInt("userID"),
                            rs.getString("email"),
                            rs.getString("password"), 
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getDate("dataNascita"),
                            rs.getString("ruoloUtente"),
                            rs.getDate("dataIscrizione"),
                            rs.getInt("numeroRicetteCreate"),
                            rs.getInt("numeroRecensioniPositive"),
                            rs.getInt("numeroRecensioniEffettuate")
                        );
                        return Optional.of(u);
                    }
                    return Optional.empty();
                } catch(Exception e) {
                    throw new DAOException(e);
                }
            }
        }        

    /**
     * gets the unique identifier of the drink.
     * @return the drink's ID.
     */
    public int getDrinkID() {
        return drinkID;
    }

    /**
     * gets the name of the drink.
     * @return the drink's name.
     */
    public String getName() {
        return name;
    }

    /**
     * gets the description of the drink.
     * @return the drink's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * gets the image path of the drink.
     * @return the path to the drink's image.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * gets the category name of the drink.
     * @return the name of the drink's category.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * checks if the drink is an IBA (International Bartenders Association) official cocktail.
     * @return true if the drink is an IBA cocktail, false otherwise.
     */
    public boolean isIBA() {
        return isIBA;
    }
}