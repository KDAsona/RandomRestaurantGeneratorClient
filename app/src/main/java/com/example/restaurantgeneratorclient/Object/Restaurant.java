package com.example.restaurantgeneratorclient.Object;

public class Restaurant {

    private int restaurantId;
    private String restaurantName;
    private String foodType;
    private int avgRating;
    private String photoUrl;
    private String googleMapUrl;

    // In theory, we shouldn't call this method at any given time.
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public String getPhotoUrl() { return photoUrl;}

    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getGoogleMapUrl() { return googleMapUrl; }

    public void setGoogleMapUrl(String googleMapUrl) { this.googleMapUrl = googleMapUrl; }

}
