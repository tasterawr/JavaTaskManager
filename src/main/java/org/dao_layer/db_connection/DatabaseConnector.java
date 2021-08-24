package org.dao_layer.db_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String url = "jdbc:postgresql://localhost:5432/task_management";
    private static final String user = "postgres";
    private static final String password = "1234";

    public static Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, user,password);
        return connection;
    }
}
