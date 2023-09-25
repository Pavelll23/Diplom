package data;

import lombok.SneakyThrows;
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

    @SneakyThrows
    public static Connection getConnection() {
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    @SneakyThrows
    public static void deleteDataBase() {
        var runner = new QueryRunner();
        getConnection();

        runner.update(connection, "DELETE FROM payment_entity");
        runner.update(connection, "DELETE FROM order_entity");
        runner.update(connection, "DELETE FROM credit_request_entity");
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        String codeSql = "SELECT status FROM payment_entity";
        var runner = new QueryRunner();
        getConnection();
        var cardStatus = runner.query(connection, codeSql, new ScalarHandler<String>());
        return cardStatus;
    }

    @SneakyThrows
    public static String getCreditPaymentStatus() {
        String codeSql = "SELECT status FROM credit_request_entity";
        var runner = new QueryRunner();
        getConnection();
        var cardStatus = runner.query(connection, codeSql, new ScalarHandler<String>());
        return cardStatus;

    }
}




