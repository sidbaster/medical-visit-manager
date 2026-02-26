package pl.medical.visit.manager.dao;

import pl.medical.visit.manager.util.FirebirdConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URL = "db.url";
    private static final String NAME = "db.user";
    private static final String PASSWORD = "db.password";

    public static Connection open() {
        try {
            return DriverManager.getConnection(FirebirdConfig.get(URL),
                    FirebirdConfig.get(NAME),
                    FirebirdConfig.get(PASSWORD));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {
    }
}
