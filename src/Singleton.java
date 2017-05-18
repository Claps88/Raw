import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class Singleton {

    private static Connection conn = null;

    private Singleton() {
    }

    public static Connection getConn() {

        if (conn == null) {
            String url = "jdbc:mysql://127.0.0.1:3306/battleship";
            String user = "root";
            String passw = "1234";
            String driver = "com.mysql.jdbc.Driver";

            try {
                Class.forName(driver);

                conn = DriverManager.getConnection(url, user, passw);

            } catch (SQLException ex) {

                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {

                ex.printStackTrace();
            }

        }
        return conn;
    }

}