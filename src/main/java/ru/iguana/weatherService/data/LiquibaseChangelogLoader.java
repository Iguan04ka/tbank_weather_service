package ru.iguana.weatherService.data;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class LiquibaseChangelogLoader {
    public static void load() {
        String url = "jdbc:postgresql://localhost:5433/Weather";
        String username = "iguana";
        String password = "postgres";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(
                    "db/changelog/changelog-master.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            );

            liquibase.update(new Contexts(), new LabelExpression());

            System.out.println("Миграции успешно выполнены");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка при выполнении миграций: " + e.getMessage());
        }
    }
}