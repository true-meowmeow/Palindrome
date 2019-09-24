package MeowOrDie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String loginPass = "root";
        String connectionURL = "jdbc:mysql://localhost:3306/base?autoReconnect=true&useSSL=false";
        Class.forName("com.mysql.jdbc.Driver");

        Base base = new Base();


        try (Connection connection = DriverManager.getConnection(connectionURL, loginPass, loginPass)) {
            Statement statement = connection.createStatement();

            base.showUsers(connection, statement);
            Core core = new Core(connection, statement);
        }
    }

    /*

    для игры нужно подключить                   mysql
    для выхода используйте                      exit
    для вывода таблицы лидеров используйте      leaderboard

    *дальнейшие инструкции при игре*

    */


}

