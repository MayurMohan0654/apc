package com.example;

// import kong.unirest.HttpResponse;
// import kong.unirest.JsonNode;
// import kong.unirest.Unirest;
// import kong.unirest.json.JSONObject;

// import java.util.*;



import java.util.*;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.*;

// public class App {
//     private static final String BASE_URL = "http://localhost:8080/api";
//     private static final Scanner scanner = new Scanner(System.in);
//     private static Map<String, Object> currentUser = null; // Will store user/theater info after login
//     private static boolean isTheater = false; // Flag to track if logged in as theater

//     public static void main(String[] args) {
//         System.out.println("\n---------------------------- User Login ----------------------------");
//         System.out.print("Email: ");
//         String email = scanner.nextLine();
//         System.out.print("Password: ");
//         String password = scanner.nextLine();
        
//         try {
//             HttpResponse<JsonNode> response = Unirest.post(BASE_URL + "/users/login")
//                 .header("Content-Type", "application/json")
//                 .body(new JSONObject()
//                     .put("email", email)
//                     .put("password", password))
//                 .asJson();
//             System.out.println(response.getBody());
            
//             JSONObject jsonResponse = response.getBody().getObject();
            
//             if (jsonResponse.getBoolean("success")) {
//                 JSONObject userJson = jsonResponse.getJSONObject("user");
//                 currentUser = new HashMap<>();
//                 currentUser.put("id", userJson.getLong("id"));
//                 currentUser.put("name", userJson.getString("name"));
//                 currentUser.put("email", userJson.getString("email"));
//                 isTheater = false;
                
//                 System.out.println("Login successful! Welcome, " + currentUser.get("name"));
//             } else {
//                 System.out.println("Login failed: " + jsonResponse.getString("message"));
//             }
//         } catch (Exception e) {
//             System.out.println("Error during login: " + e.getMessage());
//         }
//     }
// }


public class App {
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final Scanner scanner = new Scanner(System.in);
    private static Map<String, Object> currentUser = null; // Will store user/theater info after login
    private static boolean isTheater = false; // Flag to track if logged in as theater
    public static void main(String args[]) {
            try {
                HttpResponse<JsonNode> response = Unirest.get("/showtimes/theater/{theaterId}")
                    .routeParam("theaterId", String.valueOf(currentUser.get("id")))
                    .asJson();
                
                JSONArray showtimes = response.getBody().getArray();
                
                System.out.println("\n---------------------------- My Theater's Showtimes ----------------------------");
                if (showtimes.length() == 0) {
                    System.out.println("No showtimes available for your theater.");
                } else {
                    for (int i = 0; i < showtimes.length(); i++) {
                        JSONObject showtime = showtimes.getJSONObject(i);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error retrieving showtimes: " + e.getMessage());
            }
        }
    
}

