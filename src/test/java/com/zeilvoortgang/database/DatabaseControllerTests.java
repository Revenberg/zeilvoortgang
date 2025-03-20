package com.zeilvoortgang.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class DatabaseControllerTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseControllerTests.class);

    @Test
    void testDatabase() {
        LOGGER.info("Running testDatabase...");
/*
        try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
            SqlFileExecutor.executeSqlFile(connection, "src/main/resources/sql/Database.sql");
            SqlFileExecutor.executeSqlFile(connection, "src/main/resources/sql/Permissions.sql");
            SqlFileExecutor.executeSqlFile(connection, "src/main/resources/sql/Education.sql");
            SqlFileExecutor.executeSqlFile(connection, "src/main/resources/sql/Import.sql");
        } catch (IOException | SQLException e) {
            LOGGER.error("An error occurred while executing the SQL file.", e);
        }
            */
    }
}