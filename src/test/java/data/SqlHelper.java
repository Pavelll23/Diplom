package data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SqlHelper {

    private static QueryRunner runner = new QueryRunner();
    private static String url = System.getProperty("db.url");
    private static String user = System.getProperty("db.user");
    private static String password = System.getProperty("db.password");
    private static Connection connection;


    public static Connection getConnection() {

        try {connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    public static void deleteDataBase() {
        val runner = new QueryRunner();
        val deletePayment = "DELETE FROM payment_entity";
        val deleteOrder = "DELETE FROM order_entity";
        val deleteCreditRequest = "DELETE FROM credit_request_entity";

        try (val connection = getConnection()) {
            runner.update(connection, deletePayment);
            runner.update(connection, deleteOrder);
            runner.update(connection, deleteCreditRequest);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


        public static String getPaymentStatus() {
            String codeSql = "SELECT status FROM payment_entity";
            val runner = new QueryRunner();
            try (val connection = getConnection()) {
                val cardStatus = runner.query(connection, codeSql, new ScalarHandler<String>());
                return cardStatus;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            return null;
        }

        public static String getCreditPaymentStatus() {
            String codeSql = "SELECT status FROM credit_request_entity";
            val runner = new QueryRunner();
            try (val connection = getConnection()) {
                val cardStatus = runner.query(connection, codeSql, new ScalarHandler<String>());
                return cardStatus;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            return null;

        }
    }




