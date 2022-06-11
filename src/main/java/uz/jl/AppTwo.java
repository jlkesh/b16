package uz.jl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author "Elmurodov Javohir"
 * @since 11/06/22/11:58 (Saturday)
 * hikari/IntelliJ IDEA
 */

@Slf4j
public class AppTwo {
    public static void main(String[] args) throws SQLException {
        Connection connection = DataSource.getConnection();
        Statement statement = connection.createStatement();
        log.info("Select all books form db");
        String select = "select * from book;";

        ResultSet resultSet = statement.executeQuery(select);
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            books.add(new Book(resultSet.getInt("id"), resultSet.getString("name")));
        }
        System.out.println("books = " + books);
    }
}

@Data
@AllArgsConstructor
class Book {
    private int id;
    private String name;
}

class DataSource {

    private static final HikariConfig config;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        config = new HikariConfig();
    }

    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:sqlite:src/main/resources/db.sqlite");
////        config.setUsername( "database_username" );
////        config.setPassword( "database_password" );
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
