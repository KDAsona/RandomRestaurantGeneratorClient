package com.example.restaurantgeneratorclient.Repository;

import android.util.Log;

import com.example.restaurantgeneratorclient.Interface.StringListAPI;
import com.example.restaurantgeneratorclient.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StringListRepo {
    private StringListAPI mStringListAPI;

    public StringListRepo() {
        mStringListAPI = RetrofitClient.getClient().create(StringListAPI.class);
    }

    public void getStringList(final DataCallback<List<String>> callback) {
        Call<List<String>> call = mStringListAPI.getAllFoodTypes();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Log.d("Repo: ", "line 26");
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    Log.d("Repo: ", "request failed.");
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                callback.onError(new Exception("Network error"));
            }
        });
    }

    public interface DataCallback<T> {
        void onSuccess(T data);
        void onError(Exception e);
    }
}

