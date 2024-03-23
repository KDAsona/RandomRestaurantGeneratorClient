package com.example.restaurantgeneratorclient.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// 1 Retrofit Client Class needs to be defined per baseURL.
public class RetrofitClient {
    private static Retrofit mRetrofit = null;

    /*
        1. When using Android Emulator instead of real device, the address for
        local host should be http://10.0.2.2:8080 (assuming on port 8080).
        2. A network_security_config.xml file needs to be added, as clear text
        traffic is disabled on default.
     */
    public static Retrofit getClient() {
        mRetrofit = new Retrofit.Builder()
                //.baseUrl("http://10.0.2.2:8080/restaurant/") // Change the addr/port accordingly
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return mRetrofit;
    }
}
