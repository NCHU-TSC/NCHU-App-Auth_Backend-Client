package app.nchu.auth;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Authenticator {

    private final String auth_server = "https://api.auth.nchu.app/client/gen_auth_url.pye";

    private String client_id;
    private String client_token;

    public Authenticator(String id, String token) {
        this.client_id = id;
        this.client_token = token;
    }

    public String getClientId() {
        return this.client_id;
    }

    public String getClientToken() {
        return this.client_token;
    }

    public String getLoginURL(String callback_url, String state) {

        try {
            // Create the data map
            Map<String, String> data = new HashMap<>();
            data.put("id", this.client_id);
            data.put("token", this.client_token);
            data.put("redirect_url", callback_url);
            data.put("state", state);

            // Convert map to JSON string
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(data);

            // Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.auth_server))
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

            // Return the auth URL
            return jsonObject.getAsJsonObject("data").get("auth_url").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
