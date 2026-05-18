package mix_db.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mix_db.data.Queries;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;

/**
 * admin commands
 */
public class AdminUtils {

    /**
     * DAO for admin operations
     */
    public static final class DAO {

        /**
         * gets all users with additional analitics data
         * @param connection .
         * @param userId .
         * @return a Map of (User - List of reviews he made)
         */
        public static Map<User, List<Review>> getUsersAnalitics(Connection connection) {
            final var analitics = new HashMap<User, List<Review>>();

            // *selects all users
            try (
                final var statement = DatabaseConnection.prepare(connection, 
                    Queries.GET_USER_ANALITICS);
                final var rs = statement.executeQuery();
            ) {
                User u;
                while(rs.next()) {
                    u = new User(
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
                    
                    // *selects user's reviews
                    try (
                        final var revStatement = DatabaseConnection.prepare(connection, 
                            Queries.GET_USER_REVIEWS, 
                            u.getUserID());
                        final var rrs = revStatement.executeQuery();
                    ) {
                        final var revs = new LinkedList<Review>();
                        while(rrs.next()) {
                            final Review r = new Review(
                                rrs.getInt("drinkID"),
                                rrs.getInt("userID"),
                                rrs.getString("descrizione"), 
                                rrs.getDate("dataRecensione"),
                                rrs.getInt("voto")
                            );
                            revs.add(r);
                        }
                        analitics.put(u, revs);
                    }
                }
                
                return new HashMap<>(analitics);
            } catch(Exception e) {
                throw new DAOException(e);
            }

        }  

        /**
         * needed to ban a user
         * @param connection .
         * @param userID the user to ban
         */
        private static boolean removeUserDrinks(Connection connection, int userID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection,
                    Queries.PREPARE_TO_BAN_USER, 
                    userID);
            ) {
                return (statement.executeUpdate() == 1);
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * deletes a user
         * @param userID
         * @return {@code true} if the user exists and can be deleted,
         * {@code false} otherwise
         */
        public static boolean deleteUser(Connection connection, int userID) {
            try (
                final PreparedStatement statement = DatabaseConnection.prepare(connection,
                    Queries.BAN_USER, userID);
            ) {
                removeUserDrinks(connection, userID);
                return (statement.executeUpdate() == 1);
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
      
    }
}
