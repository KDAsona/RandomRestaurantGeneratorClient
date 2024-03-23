package com.example.restaurantgeneratorclient.Interface;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

// This class serves as an interface for all the RESTful API requests we send
// to the web application.
public interface StringListAPI {
    @GET("restaurant/allTypes")
    Call<List<String>> getAllFoodTypes();
}
