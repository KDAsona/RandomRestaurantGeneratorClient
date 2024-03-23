package com.example.restaurantgeneratorclient.Repository;

import android.util.Log;

import com.example.restaurantgeneratorclient.Interface.RestaurantListAPI;
//import com.example.restaurantgeneratorclient.Interface.StringListAPI;
import com.example.restaurantgeneratorclient.Object.Restaurant;
import com.example.restaurantgeneratorclient.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantListRepo {
    private RestaurantListAPI mRestaurantListAPI;

    public RestaurantListRepo() {
        mRestaurantListAPI = RetrofitClient.getClient().create(RestaurantListAPI.class);
    }

    public void getAllRestaurants(final RestaurantListRepo.DataCallback<List<Restaurant>> callback) {
        Call<List<Restaurant>> call = mRestaurantListAPI.getAllRestaurants();
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }
            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                callback.onError(new Exception("Network error"));
            }
        });
    }

    public void getRestaurantList(String foodType, final RestaurantListRepo.DataCallback<List<Restaurant>> callback) {
        Call<List<Restaurant>> call = mRestaurantListAPI.getRestaurantsByType(foodType);
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                Log.d("RestaurantListRepo: ", "onResponse");
                if (response.isSuccessful()) {
                    Log.d("RestaurantListRepo: ", "request success.");
                    callback.onSuccess(response.body());
                } else {
                    Log.d("RestaurantListRepo: ", "request failed.");
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                callback.onError(new Exception("Network error"));
            }
        });
    }

    public interface DataCallback<T> {
        void onSuccess(T data);
        void onError(Exception e);
    }
}
