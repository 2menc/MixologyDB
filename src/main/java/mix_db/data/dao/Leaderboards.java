package mix_db.data.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * leaderboards
 */
public class Leaderboards {

    /**
     * DAO for leaderboards
     */
    public static final class DAO {
        
        /**
         * gets a leaderboard of the drinks with the more positive reviews
         * @param connection .
         * @param daysToFilter .
         * @param numberOfResults number of drinks to show
         * @return a difensive copy Map of (drinks, numReviews)
         */
        public static Map<Drink, Integer> MorePositiveDrinkReviews(Connection connection, int daysToFilter, int numberOfResults) {
            final var drinks = new HashMap<Drink, Integer>();
            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.CREATE_DRINK, 
                    daysToFilter, numberOfResults);
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

                    drinks.put(d, rs.getInt("numero"));
                }

                return new HashMap<>(drinks);
            } catch(Exception e) {
                throw new DAOException(e);
            }
        }
   
        /**
         * gets the list of the most used ingredients
         * @param connection .
         * @param numberOfResults .
         * @return a List of infvredients
         */
        public static List<Ingredient> MostUsedIngredients(Connection connection, int numberOfResults) {
            final var ingredients = new LinkedList<Ingredient>();
            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.MOST_USED_INGREDIENTS, 
                    numberOfResults);
                final var rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    final var i = new Ingredient(
                        rs.getString("nomeIngrediente"), 
                        rs.getInt("volteUtilizzato")
                    );

                    ingredients.add(i);
                }
                return new LinkedList<>(ingredients);
            } catch(Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * gets the list of users with the more positive reviews
         * @param connection .
         * @param numberOfResults .
         * @return a List of Users
         */
        public static List<User> usersWithMorePositiveReviews(Connection connection, int numberOfResults) {
            final var users = new LinkedList<User>();

            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.USERS_WITH_MORE_POSITIVE_REVIEWS, 
                    numberOfResults);
                final var rs = statement.executeQuery();
            ) {
                while(rs.next()) {
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
                        
                    users.add(u);
                }
                    return new LinkedList<>(users);
            } catch(Exception e) {
                throw new DAOException(e);
            }

        }
        
        /**
         * gets the trending tastes
         * @param connection .
         * @param daysToFilter .
         * @param numberOfResults .
         * @return a List of tags containing String keywords
         */
        public static List<Tag> getTrendingKeywords(Connection connection, int daysToFilter, int numberOfResults) {
            final var tags = new LinkedList<Tag>();

            try(
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.TRENDING_KEYWORDS, 
                    daysToFilter, numberOfResults);
                final var rs = statement.executeQuery();
            ) {
                while(rs.next()) {
                    final Tag t = new Tag(
                        rs.getString("keyword")
                    );

                    tags.add(t);
                }
                return new LinkedList<>(tags);
            } catch(Exception e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Leaderboards []";
    }
}
