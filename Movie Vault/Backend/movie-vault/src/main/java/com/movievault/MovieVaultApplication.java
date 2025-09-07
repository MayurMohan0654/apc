package com.movievault;
import java.sql.*;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// public class MovieVaultApplication {
//     public static void main(String[] args) {
//         SpringApplication.run(MovieVaultApplication.class, args);
//     }
// }
@SpringBootApplication
public class MovieVaultApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter MySQL username: ");
        String username = scanner.nextLine();

        System.out.print("Enter MySQL password: ");
        String password = scanner.nextLine();

        System.out.println("Enter database name (must exist): ");
        String db = scanner.nextLine();

        String url = "jdbc:mysql://localhost:3306/" + db;

        try (Connection conn = DriverManager.getConnection(url, username, password)){ 
            System.out.println("Database connection successful!");
            System.setProperty("spring.datasource.username", username);
            System.setProperty("spring.datasource.password", password);
            System.setProperty("spring.datasource.url", url);

            SpringApplication.run(MovieVaultApplication.class, args);

        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
            System.err.println("create database if not exists.");
        }finally{
            scanner.close();
        }

    }
}