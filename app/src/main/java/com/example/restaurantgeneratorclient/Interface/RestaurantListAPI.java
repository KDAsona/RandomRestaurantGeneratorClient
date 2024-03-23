package com.example.restaurantgeneratorclient.Interface;

import com.example.restaurantgeneratorclient.Object.Restaurant;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantListAPI {

    @GET("restaurant")
    Call<List<Restaurant>> getAllRestaurants();

    @GET("restaurant/type")
    Call<List<Restaurant>> getRestaurantsByType(@Query("foodType") String foodType);
}
