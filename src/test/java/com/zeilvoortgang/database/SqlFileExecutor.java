package com.zeilvoortgang.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlFileExecutor {

    private static final Logger LOGGER = Logger.getLogger(SqlFileExecutor.class.getName());

    public static void executeSqlFile(Connection connection, String filePath) throws IOException, SQLException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                Statement statement = connection.createStatement()) {
            String line;
            StringBuilder sql = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sql.append(line);
                if (line.trim().endsWith(";")) {
                    try {
                        statement.execute(sql.toString());
                        LOGGER.log(Level.INFO, "Executed SQL: {0}", sql.toString());
                    } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, "Error executing SQL: {0}", sql.toString());
                        throw e;
                    }
                    sql.setLength(0);
                }
            }
        } catch (IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error reading or executing SQL file: {0}", filePath);
            throw e;
        }
    }
}