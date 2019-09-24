package MeowOrDie;

import java.sql.*;
import java.util.Scanner;

public class Base {



    public static String user;
    String word = "";
    String wordCode = "";
    public void update(Statement statement) {
        try {
            statement.executeUpdate("insert " + user + "(word, wordCode, score) " +
                    "value ('" + word + "', '" + wordCode + "', char_length(wordCode));");

            ResultSet resultSet = statement.executeQuery("select sum(score) as total from " + user);
            if (resultSet.next())
                System.out.println("ваш счет: " + resultSet.getString("total"));

        } catch (Exception e) {
            System.out.println("слово уже есть в базе");
        }
    }


    public void createUser(Statement statement, String name) throws SQLException {
        statement.executeUpdate("create table if not exists " + name +
                "(id int AUTO_INCREMENT primary key, " +
                "word varchar(50) unique, " +
                "wordCode varchar(50) unique check(wordCode != ''), " +
                "score int);");
    }


    public void showUsers(Connection connection, Statement statement) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, "%", null);

        System.out.println("Список пользователей: ");
        int i = 1;
        while (resultSet.next()) {
        System.out.println(i + ": " + resultSet.getString(3));
        i++;
        }
        System.out.println("Выберите пользователя, введя его nick, или же создайте новый, введя не существующий");

        user = scanner.next();
        while (resultSet.next()) {
            if (user == resultSet.getString(3))
                return;
        }
        createUser(statement, user);
    }


    int[] score;
    String[] users;
    public void leaderboard(Connection connection, Statement statement) throws SQLException {

        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, "%", null);
        int x = 0;
        while (resultSet.next())
            x ++;

        score = new int[x];
        users = new String[x];
        x = 0;
        resultSet = databaseMetaData.getTables(null, null, "%", null);
        while (resultSet.next()) {
            users[x] = resultSet.getString(3);
            x++;
        }

        while (x != 0) {
            x--;
            resultSet = statement.executeQuery("select sum(score) as total from " + users[x]);
            if (resultSet.next()) {
                if (resultSet.getString("total") == null) {
                    score[x] = 0;
                    continue;
                }
                score[x] = Integer.parseInt(resultSet.getString("total"));
            }
        }

        bubbleSorter(score, users);

        while (x != 5) {
            if (users[x] == null) break;
            System.out.println(x + 1 + ": " + users[x] + " - " + score[x]);
            x++;
        }
    }

    public void bubbleSorter(int[] score, String[] users) {
        for (int out = score.length - 1; out >= 1; out--){
            for (int in = 0; in < out; in++){
                if(score[in] < score[in + 1])
                    toSwap(score, users, in, in + 1);
            }
        }
    }

    private void toSwap(int[] score, String[] users, int first, int second) {
        int i = score[first];
        String s = users[first];
        score[first] = score[second];
        users[first] = users[second];
        score[second] = i;
        users[second] = s;
    }
}
