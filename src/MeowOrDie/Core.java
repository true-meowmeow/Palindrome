package MeowOrDie;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Core {


    String s = "";
    Base base = new Base();

    Scanner scanner = new Scanner(System.in);
    Core(Connection connection, Statement statement) throws SQLException {
        while (true) {
            System.out.println("\nваш input ..");
            s = new String(new StringBuilder(scanner.nextLine()));

            switch (s) {
                case "exit":
                    return;
                case "leaderboard":
                    base.leaderboard(connection, statement);
                    continue;
                default:
                    break;
            }
            if (isPalindrome(s))
                base.update(statement);
            else
                System.out.println("это не палиндром");

        }
    }


    public boolean isPalindrome(String s) { //проверка на палиндром
        base.word = s;
        s = s.replaceAll("[^\\p{L}\\p{Nd}]+", ""); //соответсвует всем Unicode
        s = permissible(s).toLowerCase();
        base.wordCode = s;

        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public String permissible(String s) { //допущения палиндромов в русском языке
        s = s.replace('ё', 'е');
        s = s.replace('й', 'и');
        s = s.replace('щ', 'ш');
        s = s.replace('ъ', 'ь');
        return s;
    }
}
