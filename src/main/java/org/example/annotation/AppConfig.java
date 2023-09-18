package org.example.annotation;

public class AppConfig {
    private String apiKey;
    private int maxUsersPerPage;

    public AppConfig(String apiKey, int maxUsersPerPage) {
        this.apiKey = apiKey;
        this.maxUsersPerPage = maxUsersPerPage;
    }

    public String getApiKey() {
        return apiKey;
    }

    public int getMaxUsersPerPage() {
        return maxUsersPerPage;
    }
}
