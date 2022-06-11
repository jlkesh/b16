package uz.jl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author "Elmurodov Javohir"
 * @since 11/06/22/11:44 (Saturday)
 * hikari/IntelliJ IDEA
 */
public class App {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlite:src/main/resources/db.sqlite";
        Connection connection = DriverManager.getConnection(url);
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        String sql = """
                    create table book(
                        id integer primary key autoincrement,
                        name varchar
                    );
                """;
        statement.execute(sql);
        connection.commit();
    }
}
