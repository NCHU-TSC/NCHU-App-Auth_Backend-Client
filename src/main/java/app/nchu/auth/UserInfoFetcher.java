package app.nchu.auth;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class UserInfoFetcher {

    private final String info_server = "https://api.auth.nchu.app/client/get_user_info.pye";

    private Authenticator authenticator;

    public UserInfoFetcher(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public UserInfo fetch(String user_id, String res_token) {
            
            try {
                // Create the data map
                Map<String, String> data = new HashMap<>();
                data.put("id", this.authenticator.getClientId());
                data.put("token", this.authenticator.getClientToken());
                data.put("user_id", user_id);
                data.put("res_token", res_token);
    
                // Convert map to JSON string
                Gson gson = new Gson();
                String jsonInputString = gson.toJson(data);
    
                // Create HttpClient
                HttpClient client = HttpClient.newHttpClient();
    
                // Create HttpRequest
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(this.info_server))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                        .build();
    
                // Send the request and get the response
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                // Parse the JSON response
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

                String status = jsonObject.get("status").getAsString();
    
                if (!status.equals("success")) {
                    JsonObject reason = jsonObject.getAsJsonObject("reason");
                    throw new StatusException(reason.get("code").getAsString(), reason.get("description").getAsString(),
                            reason.get("detail").getAsString());
                }

                JsonObject responseData = jsonObject.getAsJsonObject("data");

                UserInfo userInfo = new UserInfo();
                userInfo.setId(user_id);
                userInfo.setToken(res_token);
                userInfo.setLoginId(responseData.get("Login_ID") == JsonNull.INSTANCE ? null : responseData.get("Login_ID").getAsString());
                userInfo.setName(responseData.get("Name") == JsonNull.INSTANCE ? null : responseData.get("Name").getAsString());
                userInfo.setEmail(responseData.get("Email") == JsonNull.INSTANCE ? null : responseData.get("Email").getAsString());
                userInfo.setEmailVerified(responseData.get("Valid_Email").getAsBoolean());
                userInfo.setGender(responseData.get("Gender") == JsonNull.INSTANCE ? null : responseData.get("Gender").getAsString());
                userInfo.setDepartment(responseData.get("Department") == JsonNull.INSTANCE ? null : responseData.get("Department").getAsString());
                userInfo.setGrade(responseData.get("Grade") == JsonNull.INSTANCE ? null : responseData.get("Grade").getAsString());
                userInfo.setPhoto(responseData.get("Photo") == JsonNull.INSTANCE ? null : responseData.get("Photo").getAsString());
    
                // Return the UserInfo object
                return userInfo;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }
    
}
