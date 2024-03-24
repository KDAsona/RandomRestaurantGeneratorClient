package com.example.restaurantgeneratorclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import com.example.restaurantgeneratorclient.Adapter.RestaurantListAdapter;
import com.example.restaurantgeneratorclient.Interface.RestaurantListAPI;
import com.example.restaurantgeneratorclient.Object.Restaurant;
import com.example.restaurantgeneratorclient.Repository.RestaurantListRepo;
import com.example.restaurantgeneratorclient.Repository.StringListRepo;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantgeneratorclient.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private StringListRepo mStringListRepo = new StringListRepo(); // Repo for getting List<String> type results.
    private RestaurantListRepo mRestaurantListRepo = new RestaurantListRepo();
    private List<String> options = new ArrayList<>();
    private Spinner mSpinner;
    List<Restaurant> mRestaurantList = new ArrayList<Restaurant>();
    private RecyclerView mRestaurantListRecyclerView;
    private RestaurantListAdapter mRestaurantListAdapter;
    private Button mSortButton;
    private Button mRestoreAllButton;
    private Button mRandButton;
    private Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Basic initialization of the recycler (but Restaurant list has yet to be populated).
        mRestaurantListRecyclerView = (RecyclerView) findViewById(R.id.restaurant_list_recycler_view);
        mRestaurantListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRestaurantListAdapter = new RestaurantListAdapter(this, mRestaurantList);
        mRestaurantListRecyclerView.setAdapter(mRestaurantListAdapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        showAllRestaurants();

        // Populate the list
        mStringListRepo.getStringList(new StringListRepo.DataCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                options.addAll(data);
                updateSpinner(); // Update the spinner when we finish populating the list.
                Log.d("MainActivity","List populated to a size of : " + String.valueOf(options.size()));
            }

            @Override
            public void onError(Exception e) {

            }
        });;


        // Set up the spinner to show all food types.
        mSpinner = findViewById(R.id.spinner);
        options.add("Select a food type");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, options);
        adapter.setDropDownViewResource(R.layout.custom_spinner_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    showAllRestaurants(); // Restore all the Restaurant items, regardless of its type.
                } else {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    Log.d("MainActivity", "Item selected from food type list");
                    populateRecycler(selectedItem);
                    Toast.makeText(MainActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set up the buttons:
        // Randomly pick a Restaurant for the user, from all the Restaurants that
        // are being displayed.
        mRandButton = findViewById(R.id.random_pick_button);
        mRandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Rand button clicked");
                int index = mRandom.nextInt(mRestaurantList.size());
                Restaurant pickedRestaurant = mRestaurantList.get(index);
                mRestaurantList.clear();
                mRestaurantList.add(pickedRestaurant);
                mRestaurantListAdapter.notifyDataSetChanged();
            }
        });

        // Fetch all the Restaurants from server again.
        mRestoreAllButton = findViewById(R.id.restore_all_button);
        mRestoreAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllRestaurants();
            }
        });

    }

    private void showAllRestaurants() {
        mRestaurantListRepo.getAllRestaurants(new RestaurantListRepo.DataCallback<List<Restaurant>>() {
            @Override
            public void onSuccess(List<Restaurant> data) {
                mRestaurantList.clear();
                mRestaurantList.addAll(data);
                mRestaurantListAdapter.notifyDataSetChanged();
                Log.d("MainActivity", "GET all restaurant success.");
            }

            @Override
            public void onError(Exception e) {
                Log.d("MainActivity", "GET all restaurant error.");
            }
        });
    }

    private void populateRecycler(String selectedItem) {


        mRestaurantListRepo.getRestaurantList(selectedItem, new RestaurantListRepo.DataCallback<List<Restaurant>>() {
            @Override
            public void onSuccess(List<Restaurant> data) {
                mRestaurantList.clear(); // Empty the leftover data from last selection.
                mRestaurantList.addAll(data);
                mRestaurantListAdapter.notifyDataSetChanged();
                Log.d("MainActivity","Restaurant list populated to a size of : " + String.valueOf(mRestaurantList.size()));
            }

            @Override
            public void onError(Exception e) {
            }
        });;
    }

    private void updateSpinner() {
        // Assuming spinner is your Spinner instance and it's already initialized
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }
}