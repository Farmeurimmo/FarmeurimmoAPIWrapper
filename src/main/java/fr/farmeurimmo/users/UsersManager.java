package fr.farmeurimmo.users;

import com.google.gson.JsonObject;
import fr.farmeurimmo.Requester;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UsersManager {

    private static final HashMap<User, Long> users = new HashMap<>();

    public static User getCachedUser(UUID uuid) {
        for (User user : users.keySet()) {
            if (user.getUUID().equals(uuid)) {
                return user;
            }
        }
        return null;
    }

    public static long getLastGotUser(User user) {
        return users.get(user);
    }

    public static CompletableFuture<User> getUser(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            CompletableFuture<JsonObject> json = Requester.getAsync("user/" + uuid.toString());
            User user = parse(json.join());
            if (user != null) users.put(user, System.currentTimeMillis());
            return user;
        });
    }

    public static CompletableFuture<User> getUserOrCreate(UUID uuid, String name) {
        return CompletableFuture.supplyAsync(() -> {
            JsonObject json = Requester.getAsync("user/" + uuid.toString()).join();
            User user = parse(json);
            if (user == null) {
                user = new User(uuid, name);
                updateUser(user);
            }
            users.put(user, System.currentTimeMillis());
            return user;
        });
    }

    private static User parse(JsonObject json) {
        if (json == null) return null;

        UUID id = json.has("id") ? UUID.fromString(json.get("id").getAsString()) : null;
        String name = json.has("name") ? json.get("name").getAsString() : null;
        String discordId = json.has("discordId") ? (json.get("discordId").isJsonNull() ? null :
                json.get("discordId").getAsString()) : null;
        String displayName = json.has("displayName") ? json.get("displayName").getAsString() : null;
        double fame = json.has("fame") ? json.get("fame").getAsDouble() : 0;
        long lastSeen = json.has("lastSeen") ? json.get("lastSeen").getAsLong() : 0;
        long firstSeen = json.has("firstSeen") ? json.get("firstSeen").getAsLong() : 0;
        long playTime = json.has("playTime") ? json.get("playTime").getAsLong() : 0;

        User user = new User(id, name, discordId, displayName, fame, lastSeen, firstSeen, playTime);
        users.put(user, System.currentTimeMillis());
        return user;
    }

    public static void updateUser(User user) {
        JsonObject json = new JsonObject();

        json.addProperty("id", user.getUUID().toString());
        json.addProperty("name", user.getName());
        json.addProperty("discordId", user.getDiscordId());
        json.addProperty("displayName", user.getDisplayName());
        json.addProperty("fame", user.getFame());
        json.addProperty("lastSeen", user.getLastSeen());
        json.addProperty("firstSeen", user.getFirstSeen());
        json.addProperty("playTime", user.getPlayTime());

        Requester.postAsync("user/" + user.getUUID().toString(), json);
    }
}
