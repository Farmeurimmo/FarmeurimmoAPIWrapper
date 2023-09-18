package fr.farmeurimmo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Requester {

    private static final String URL = "https://api.farmeurimmo.fr/mc/";

    public static CompletableFuture<JsonObject> getAsync(String endpoint) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(URL + endpoint))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(JsonParser::parseString)
                .thenApply(JsonObject.class::cast)
                .exceptionally(e -> null);
    }

    public static void postAsync(String endpoint, JsonObject json) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(URL + endpoint))
                .header("X-API-Key", System.getenv("AUTH_KEY"))
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(JsonParser::parseString)
                .thenApply(JsonObject.class::cast)
                .exceptionally(e -> null);
    }
}
