package ma.fstt.startup;

import ma.fstt.dao.JdbcUtils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.Properties;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // execute schema and data SQL from classpath resources if available
        ensureDatabaseExists();
        executeSqlResource("db/schema.sql");
        if (isDatabaseEmpty()) {
            executeSqlResource("db/data.sql");
        }
    }

    private void executeSqlResource(String resourcePath) {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) return;
            try (BufferedReader r = new BufferedReader(new InputStreamReader(in)); Connection conn = JdbcUtils.getConnection(); Statement st = conn.createStatement()) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("--")) continue;
                    sb.append(line).append(' ');
                    if (line.endsWith(";")) {
                        String sql = sb.toString();
                        // remove trailing semicolon
                        sql = sql.substring(0, sql.lastIndexOf(';'));
                        st.execute(sql);
                        sb.setLength(0);
                    }
                }
            }
        } catch (Exception e) {
            // log to console; avoid failing startup
            System.err.println("DatabaseInitializer: could not execute resource " + resourcePath + " -> " + e.getMessage());
        }
    }

    private boolean isDatabaseEmpty() {
        try (Connection conn = JdbcUtils.getConnection(); Statement st = conn.createStatement()) {
            // Sum counts from all application tables. If total is 0, we consider the DB empty.
            String countSql = "SELECT " +
                    "(SELECT COUNT(*) FROM clients) + " +
                    "(SELECT COUNT(*) FROM produits) + " +
                    "(SELECT COUNT(*) FROM commandes) + " +
                    "(SELECT COUNT(*) FROM lignes_de_commande) AS total";
            try (ResultSet rs = st.executeQuery(countSql)) {
                if (rs.next()) {
                    long total = rs.getLong("total");
                    return total == 0L;
                }
            }
        } catch (Exception e) {
            // If we cannot determine, be safe and do not seed to avoid duplicates
            System.err.println("DatabaseInitializer: could not check if database is empty -> " + e.getMessage());
        }
        return false;
    }

    private void ensureDatabaseExists() {
        try {
            DbConfig cfg = loadDbConfig();
            if (cfg.databaseName == null || cfg.databaseName.isEmpty()) {
                return; // nothing to do
            }
            try (Connection conn = DriverManager.getConnection(cfg.serverUrl, cfg.user, cfg.password);
                 Statement st = conn.createStatement()) {
                String sql = "CREATE DATABASE IF NOT EXISTS `" + cfg.databaseName + "` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
                st.execute(sql);
            }
        } catch (Exception e) {
            System.err.println("DatabaseInitializer: could not ensure database exists -> " + e.getMessage());
        }
    }

    private DbConfig loadDbConfig() {
        // defaults must mirror JdbcUtils
        String url = "jdbc:mysql://localhost:3306/mvc2jee?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "";
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                Properties p = new Properties();
                p.load(in);
                url = p.getProperty("jdbc.url", url);
                user = p.getProperty("jdbc.user", user);
                password = p.getProperty("jdbc.password", password);
            }
        } catch (Exception ignored) {
        }

        // derive serverUrl and databaseName from JDBC URL (mysql)
        String databaseName = null;
        String serverUrl = url;
        try {
            // example: jdbc:mysql://host:3306/dbname?params
            int schemeIdx = url.indexOf("jdbc:mysql://");
            int pathStart = url.indexOf('/', schemeIdx + "jdbc:mysql://".length());
            if (pathStart > 0) {
                int nextSlash = url.indexOf('/', pathStart + 1);
                int question = url.indexOf('?', pathStart + 1);
                int endIdx = question >= 0 ? question : (nextSlash >= 0 ? nextSlash : url.length());
                if (endIdx > pathStart + 1) {
                    databaseName = url.substring(pathStart + 1, endIdx);
                }
                // server URL should not include database name, keep params if present but after '?'
                serverUrl = url.substring(0, pathStart + 1);
                int qIdx = url.indexOf('?', endIdx);
                if (qIdx >= 0) {
                    serverUrl = serverUrl + "?" + url.substring(qIdx + 1);
                }
            }
        } catch (Exception ignored) {
        }
        return new DbConfig(url, user, password, serverUrl, databaseName);
    }

    private static class DbConfig {
        final String url;
        final String user;
        final String password;
        final String serverUrl;
        final String databaseName;
        DbConfig(String url, String user, String password, String serverUrl, String databaseName) {
            this.url = url;
            this.user = user;
            this.password = password;
            this.serverUrl = serverUrl;
            this.databaseName = databaseName;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // nothing
    }
}
