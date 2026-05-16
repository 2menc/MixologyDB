package mix_db.data.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Establishes a connection to a MySQL local server on port 3306
 */
public class DatabaseConnection {

    /**
     * establishes the connection
     * @param databaseName the database name
     * @param userName username
     * @param password database password
     * @return a connection to the database
     */
    public static Connection localConnection(final String databaseName, final String userName, final String password) {
        try {
            final String host = "localHost";
            final String port = "3306";
            final String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
            
            return DriverManager.getConnection(connectionString, userName, password);
        } catch (final Exception e) {
            throw new DAOException(e);
        }
    }
    
    /**
     * prepares the statement
     * @param connection the connection to the MySQL local server
     * @param query the SQL query to prepare
     * @param values the values to put in the ?'s
     * @return the statement
     * @throws SQLException if cannot prepare such statement
     */
    public static PreparedStatement prepare(final Connection connection, final String query, final Object... values) throws SQLException {
        PreparedStatement statement = null;
        
        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            return statement;
        } catch (Exception e) {
            if (statement != null) {
                statement.close();
            }
            throw e;
        }
    }

    /**
     * gets auto_increment keys
     * @param connection .
     * @param query .
     * @param params .
     * @return the previewsly createe keys
     */
    public static PreparedStatement prepareWithKeys(Connection connection, String query, Object... params) {
        try {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setParameters(statement, params);

            return statement;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * inseets the parameters into the statement
     * @param statement .
     * @param params .
     * @throws SQLException .
     */
    private static void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
        }
    }


}
