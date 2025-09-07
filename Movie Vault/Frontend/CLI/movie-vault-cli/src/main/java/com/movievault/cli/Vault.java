package com.movievault.cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Vault {
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final Scanner scanner = new Scanner(System.in);
    private static Map<String, Object> currentUser = null; // Will store user/theater info after login
    private static boolean isTheater = false; // Flag to track if logged in as theater
    
    public static void main(String[] args) {
        System.out.println("---------------------------- Welcome to Movie Vault ----------------------------");
        
        while (true) {
            if (currentUser == null) {
                showMainMenu();
            } else if (isTheater) {
                showTheaterMenu();
            } else {
                showUserMenu();
            }
        }
    }
    
    private static void showMainMenu() {
        System.out.println("\n---------------------------- Main Menu ----------------------------");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                showLoginTypeMenu();
                break;
            case 2:
                showRegisterTypeMenu();
                break;
            case 0:
                System.out.println("Thank you for using Movie Vault. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void showLoginTypeMenu() {
        System.out.println("\n---------------------------- Login As ----------------------------");
        System.out.println("1. User");
        System.out.println("2. Theater");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                loginUser();
                break;
            case 2:
                loginTheater();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void showRegisterTypeMenu() {
        System.out.println("\n---------------------------- Register As ----------------------------");
        System.out.println("1. User");
        System.out.println("2. Theater");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                registerUser();
                break;
            case 2:
                registerTheater();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    // USER MENU
    private static void showUserMenu() {
        System.out.println("\n---------------------------- User Menu ----------------------------");
        System.out.println("1. View All Showtimes");
        System.out.println("2. Find Showtimes by Movie ID");
        System.out.println("3. Find Showtimes by Theater ID");
        System.out.println("4. Book a Ticket");
        System.out.println("5. View My Bookings");
        System.out.println("6. Cancel Booking");
        System.out.println("0. Logout");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                viewAllShowtimes();
                break;
            case 2:
                findShowtimesByMovie();
                break;
            case 3:
                findShowtimesByTheater();
                break;
            case 4:
                bookTicket();
                break;
            case 5:
                viewMyBookings();
                break;
            case 6:
                cancelBooking();
                break;
            case 0:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    // THEATER MENU
    private static void showTheaterMenu() {
        System.out.println("\n---------------------------- Theater Menu ----------------------------");
        System.out.println("1. Add Showtime");
        System.out.println("2. View My Showtimes");
        System.out.println("3. View Bookings for Showtime");
        System.out.println("0. Logout");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                addShowtime();
                break;
            case 2:
                viewTheaterShowtimes();
                break;
            case 3:
                viewBookingsForShowtime();
                break;
            case 0:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    // USER AUTHENTICATION
    private static void loginUser() {
        System.out.println("\n---------------------------- User Login ----------------------------");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        try {
            URL url = new URL(BASE_URL + "/users/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("email", email);
            jsonInput.put("password", password);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode == HttpURLConnection.HTTP_OK ? 
                    conn.getInputStream() : conn.getErrorStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONObject jsonResponse = new JSONObject(response.toString());
            
            if (jsonResponse.getBoolean("success")) {
                JSONObject userJson = jsonResponse.getJSONObject("user");
                currentUser = new HashMap<>();
                currentUser.put("id", userJson.getLong("id"));
                currentUser.put("name", userJson.getString("name"));
                currentUser.put("email", userJson.getString("email"));
                isTheater = false;
                
                System.out.println("Login successful! Welcome, " + currentUser.get("name"));
            } else {
                System.out.println("Login failed: " + jsonResponse.getString("message"));
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }
    
    private static void registerUser() {
        System.out.println("\n---------------------------- User Registration ----------------------------");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        
        try {
            URL url = new URL(BASE_URL + "/users/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("name", name);
            jsonInput.put("email", email);
            jsonInput.put("password", password);
            jsonInput.put("phone", phone);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode == HttpURLConnection.HTTP_CREATED ? 
                    conn.getInputStream() : conn.getErrorStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONObject jsonResponse = new JSONObject(response.toString());
            
            if (jsonResponse.getBoolean("success")) {
                System.out.println("Registration successful! Please login.");
            } else {
                System.out.println("Registration failed: " + jsonResponse.getString("message"));
            }
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }
    
    // THEATER AUTHENTICATION
    private static void loginTheater() {
        System.out.println("\n---------------------------- Theater Login ----------------------------");
        System.out.print("Theater Name: ");
        String name = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        try {
            URL url = new URL(BASE_URL + "/theaters/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("name", name);
            jsonInput.put("password", password);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode == HttpURLConnection.HTTP_OK ? 
                    conn.getInputStream() : conn.getErrorStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONObject jsonResponse = new JSONObject(response.toString());
            
            if (jsonResponse.getBoolean("success")) {
                JSONObject theaterJson = jsonResponse.getJSONObject("theater");
                currentUser = new HashMap<>();
                currentUser.put("id", theaterJson.getLong("id"));
                currentUser.put("name", theaterJson.getString("name"));
                currentUser.put("city", theaterJson.optString("city", ""));
                isTheater = true;
                
                System.out.println("Login successful! Welcome, " + currentUser.get("name"));
            } else {
                System.out.println("Login failed: " + jsonResponse.getString("message"));
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }
    
    private static void registerTheater() {
        System.out.println("\n---------------------------- Theater Registration ----------------------------");
        System.out.print("Theater Name: ");
        String name = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        try {
            URL url = new URL(BASE_URL + "/theaters/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("name", name);
            jsonInput.put("city", city);
            jsonInput.put("password", password);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode == HttpURLConnection.HTTP_CREATED ? 
                    conn.getInputStream() : conn.getErrorStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONObject jsonResponse = new JSONObject(response.toString());
            
            if (jsonResponse.getBoolean("success")) {
                System.out.println("Registration successful! Please login.");
            } else {
                System.out.println("Registration failed: " + jsonResponse.getString("message"));
            }
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }
    
    // USER FUNCTIONALITY
    private static void viewAllShowtimes() {
        try {
            URL url = new URL(BASE_URL + "/showtimes");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONArray showtimes = new JSONArray(response.toString());
            
            System.out.println("\n---------------------------- All Showtimes ----------------------------");
            if (showtimes.length() == 0) {
                System.out.println("No showtimes available.");
            } else {
                for (int i = 0; i < showtimes.length(); i++) {
                    JSONObject showtime = showtimes.getJSONObject(i);
                    displayShowtime(showtime);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving showtimes: " + e.getMessage());
        }
    }
    
    private static void findShowtimesByMovie() {
        System.out.print("Enter Movie ID: ");
        long movieId = getIntInput();
        
        try {
            URL url = new URL(BASE_URL + "/showtimes/movie/" + movieId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONArray showtimes = new JSONArray(response.toString());
            
            System.out.println("\n---------------------------- Showtimes for Movie ID " + movieId + " ----------------------------");
            if (showtimes.length() == 0) {
                System.out.println("No showtimes available for this movie.");
            } else {
                for (int i = 0; i < showtimes.length(); i++) {
                    JSONObject showtime = showtimes.getJSONObject(i);
                    displayShowtime(showtime);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving showtimes: " + e.getMessage());
        }
    }
    
    private static void findShowtimesByTheater() {
        System.out.print("Enter Theater ID: ");
        long theaterId = getIntInput();
        
        try {
            URL url = new URL(BASE_URL + "/showtimes/theater/" + theaterId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONArray showtimes = new JSONArray(response.toString());
            
            System.out.println("\n---------------------------- Showtimes for Theater ID " + theaterId + " ----------------------------");
            if (showtimes.length() == 0) {
                System.out.println("No showtimes available for this theater.");
            } else {
                for (int i = 0; i < showtimes.length(); i++) {
                    JSONObject showtime = showtimes.getJSONObject(i);
                    displayShowtime(showtime);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving showtimes: " + e.getMessage());
        }
    }
    
    private static void bookTicket() {
        System.out.print("Enter Showtime ID: ");
        long showtimeId = getIntInput();
        
        try {
            URL url = new URL(BASE_URL + "/bookings");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("userId", currentUser.get("id"));
            jsonInput.put("showTimeId", showtimeId);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode == HttpURLConnection.HTTP_CREATED ? 
                    conn.getInputStream() : conn.getErrorStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONObject jsonResponse = new JSONObject(response.toString());
            
            if (jsonResponse.getBoolean("success")) {
                System.out.println("Booking successful! Your booking ID is: " + 
                                   jsonResponse.getJSONObject("booking").getLong("id"));
            } else {
                System.out.println("Booking failed: " + jsonResponse.getString("message"));
            }
        } catch (Exception e) {
            System.out.println("Error during booking: " + e.getMessage());
        }
    }
    
    private static void viewMyBookings() {
        try {
            URL url = new URL(BASE_URL + "/bookings/user/" + currentUser.get("id"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONArray bookings = new JSONArray(response.toString());
            
            System.out.println("\n---------------------------- My Bookings ----------------------------");
            if (bookings.length() == 0) {
                System.out.println("You have no bookings.");
            } else {
                for (int i = 0; i < bookings.length(); i++) {
                    JSONObject booking = bookings.getJSONObject(i);
                    System.out.println("Booking ID: " + booking.getLong("id"));
                    System.out.println("Showtime ID: " + booking.getJSONObject("showTime").getLong("id"));
                    System.out.println("Movie: " + booking.getJSONObject("showTime").getJSONObject("movie").getString("title"));
                    System.out.println("Theater: " + booking.getJSONObject("showTime").getJSONObject("theater").getString("name"));
                    System.out.println("Date: " + booking.getJSONObject("showTime").getString("startTime"));
                    System.out.println("Booking Date: " + booking.getString("bookingDate"));
                    System.out.println("-----------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving bookings: " + e.getMessage());
        }
    }
    
    private static void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        long bookingId = getIntInput();
        
        try {
            URL url = new URL(BASE_URL + "/bookings/" + bookingId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode == HttpURLConnection.HTTP_OK ? 
                    conn.getInputStream() : conn.getErrorStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONObject jsonResponse = new JSONObject(response.toString());
            
            if (jsonResponse.getBoolean("success")) {
                System.out.println("Booking cancelled successfully!");
            } else {
                System.out.println("Cancellation failed: " + jsonResponse.getString("message"));
            }
        } catch (Exception e) {
            System.out.println("Error during cancellation: " + e.getMessage());
        }
    }
    
    // THEATER FUNCTIONALITY
    private static void addShowtime() {
        System.out.print("Enter Movie ID: ");
        long movieId = getIntInput();
        System.out.print("Enter Start Time (format: 'Year-Month-DateT24hours'): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter End Time (format: 'Year-Month-DateT24hours'): ");
        String endTime = scanner.nextLine();
        
        try {
            URL url = new URL(BASE_URL + "/showtimes");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("theaterId", currentUser.get("id"));
            jsonInput.put("movieId", movieId);
            jsonInput.put("startTime", startTime);
            jsonInput.put("endTime", endTime);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode == HttpURLConnection.HTTP_CREATED ? 
                    conn.getInputStream() : conn.getErrorStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONObject jsonResponse = new JSONObject(response.toString());
            
            if (jsonResponse.getBoolean("success")) {
                System.out.println("Showtime added successfully!");
            } else {
                System.out.println("Failed to add showtime: " + jsonResponse.getString("message"));
            }
        } catch (Exception e) {
            System.out.println("Error adding showtime: " + e.getMessage());
        }
    }
    
    private static void viewTheaterShowtimes() {
        try {
            URL url = new URL(BASE_URL + "/showtimes/theater/" + currentUser.get("id"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONArray showtimes = new JSONArray(response.toString());
            
            System.out.println("\n---------------------------- My Theater's Showtimes ----------------------------");
            if (showtimes.length() == 0) {
                System.out.println("No showtimes available for your theater.");
            } else {
                for (int i = 0; i < showtimes.length(); i++) {
                    JSONObject showtime = showtimes.getJSONObject(i);
                    displayShowtime(showtime);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving showtimes: " + e.getMessage());
        }
    }
    
    private static void viewBookingsForShowtime() {
        System.out.print("Enter Showtime ID: ");
        long showtimeId = getIntInput();
        
        try {
            URL url = new URL(BASE_URL + "/bookings/showtime/" + showtimeId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
            JSONArray bookings = new JSONArray(response.toString());
            
            System.out.println("\n---------------------------- Bookings for Showtime ID " + showtimeId + " ----------------------------");
            if (bookings.length() == 0) {
                System.out.println("No bookings for this showtime.");
            } else {
                System.out.println("Total bookings: " + bookings.length());
                for (int i = 0; i < bookings.length(); i++) {
                    JSONObject booking = bookings.getJSONObject(i);
                    System.out.println("Booking ID: " + booking.getLong("id"));
                    System.out.println("User: " + booking.getJSONObject("user").getString("name"));
                    System.out.println("Email: " + booking.getJSONObject("user").getString("email"));
                    System.out.println("Booking Date: " + booking.getString("bookingDate"));
                    System.out.println("-----------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving bookings: " + e.getMessage());
        }
    }
    
    // HELPER METHODS
    private static void logout() {
        currentUser = null;
        isTheater = false;
        System.out.println("Logged out successfully.");
    }
    
    private static void displayShowtime(JSONObject showtime) {
        System.out.println("ID: " + showtime.getLong("id"));
        System.out.println("Movie: " + showtime.getJSONObject("movie").getString("title"));
        System.out.println("Theater: " + showtime.getJSONObject("theater").getString("name"));
        System.out.println("City: " + showtime.getJSONObject("theater").getString("city"));
        System.out.println("Start Time: " + showtime.getString("startTime"));
        System.out.println("End Time: " + showtime.getString("endTime"));
        System.out.println("-----------------------------");
    }
    
    private static int getIntInput() {
        try {
            int input = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            return input;
        } catch (Exception e) {
            scanner.nextLine(); // Clear buffer
            return -1;
        }
    }
}
