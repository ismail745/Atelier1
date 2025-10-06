package ma.fstt.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static String URL = "jdbc:mysql://localhost:3306/mvc2jee?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";
    private static String USER = "root";
    private static String PASSWORD = "";

    static {
        // load driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // load properties from classpath
        try (InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                Properties p = new Properties();
                p.load(in);
                URL = p.getProperty("jdbc.url", URL);
                USER = p.getProperty("jdbc.user", USER);
                PASSWORD = p.getProperty("jdbc.password", PASSWORD);
            }
        } catch (IOException e) {
            // fallback to defaults already set
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
