package data;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {
    private static QueryRunner runner = new QueryRunner();

    private SqlHelper(){
    }
    private static Connection getConnMysql() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }
}
