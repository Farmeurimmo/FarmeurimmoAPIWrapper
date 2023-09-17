package fr.farmeurimmo.users;

import java.util.UUID;

public class User {

    private final UUID uuid;
    private final String name;
    private final long firstSeen;
    private String discordId;
    private String displayName;
    private double fame;
    private long lastSeen;
    private long playTime;

    public User(UUID uuid, String name, String discordId, String displayName, double fame, long lastSeen, long firstSeen, long playTime) {
        this.uuid = uuid;
        this.name = name;
        this.discordId = discordId;
        this.displayName = displayName;
        this.fame = fame;
        this.lastSeen = lastSeen;
        this.firstSeen = firstSeen;
        this.playTime = playTime;
    }

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.displayName = name;
        this.fame = 0;
        this.lastSeen = System.currentTimeMillis();
        this.firstSeen = System.currentTimeMillis();
        this.playTime = 0;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public double getFame() {
        return fame;
    }

    public void setFame(double fame) {
        this.fame = fame;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public long getFirstSeen() {
        return firstSeen;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }


}
