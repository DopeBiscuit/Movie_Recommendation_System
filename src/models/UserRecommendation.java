package models;

import java.util.List;

public class UserRecommendation {
    private String userName;
    private String userId;
    private List<String> recommendedMovieTitles;

    public UserRecommendation(String userName, String userId, List<String> recommendedMovieTitles) {
        this.userName = userName;
        this.userId = userId;
        this.recommendedMovieTitles = recommendedMovieTitles;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getRecommendedMovieTitles() {
        return recommendedMovieTitles;
    }
}