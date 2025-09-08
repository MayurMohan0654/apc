
import java.util.HashMap;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.*;

public class Test {
    public static void main(String[] args) {
        HashMap<String, Object> currentUser = new HashMap<>();
        System.out.println("\n---------------------------- User Login ----------------------------");
        System.out.print("Email: ");
        String email = "mayur@gamail.com";
        System.out.print("Password: ");
        String password = "hello";
        
        try {
            HttpResponse<JsonNode> response = Unirest.post("/users/login")
                .header("Content-Type", "application/json")
                .body(new JSONObject()
                    .put("email", email)
                    .put("password", password))
                .asJson();
            
            JSONObject jsonResponse = response.getBody().getObject();
            
            if (jsonResponse.getBoolean("success")) {
                JSONObject userJson = jsonResponse.getJSONObject("user");
                currentUser.put("id", userJson.getLong("id"));
                currentUser.put("name", userJson.getString("name"));
                currentUser.put("email", userJson.getString("email"));

                System.out.println(userJson);
                
                System.out.println("Login successful! Welcome, " + currentUser.get("name"));
            } else {
                System.out.println("Login failed: " + jsonResponse.getString("message"));
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }
    
}

// https://kong.github.io/unirest-java/requests/
// https://kong.github.io/unirest-java/responses/