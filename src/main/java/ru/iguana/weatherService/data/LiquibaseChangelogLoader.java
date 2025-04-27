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

    private final ConnectionData connectionData;
    public LiquibaseChangelogLoader(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    public void load() {
        String url = connectionData.getUrl();
        String username = connectionData.getUser();
        String password = connectionData.getPassword();

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