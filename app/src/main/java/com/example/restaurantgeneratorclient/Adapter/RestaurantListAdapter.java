package com.example.restaurantgeneratorclient.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantgeneratorclient.Object.Restaurant;
import com.example.restaurantgeneratorclient.R;

import java.util.ArrayList;
import java.util.List;


// Adapter for the Restaurant List Recycler.
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.CustomViewHolder> {

    List<Restaurant> mItemList = new ArrayList<Restaurant>();
    private Context mContext;

    // Populate the item list of this adapter.
    public RestaurantListAdapter(Context context, List<Restaurant> dataSet) {
        mContext = context;
        mItemList = dataSet;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    // This is a crucial part, where we connect the item class with the ViewHolder!
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Restaurant item = mItemList.get(position);
        Log.d("Adapter", "onBindViewHolder");

        // Fill each view in the Custom ViewHolder with corresponding attributes.
        //Log.d("Adapter", item.getPhotoURL());
        if (item.getPhotoUrl() == null) {
            Log.d("Adapter", "null photo position is " + String.valueOf(position));
        } else {
            String photoUrl = item.getPhotoUrl();
            Glide.with(mContext)
                    .load(photoUrl)
                    .into(holder.icon);
            Log.d("Adapter", "custom image loaded to item.");
        }
        holder.restaurantName.setText(item.getRestaurantName());
        holder.foodType.setText(item.getFoodType());
        float float_rating = (float) item.getAvgRating() / 10;
        holder.avgRating.setText(String.valueOf(float_rating));

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    // Constructor for the customized item view.
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView restaurantName;
        private TextView foodType;
        private TextView avgRating;

        // Map each view attribute with concrete xml component that has been
        // implemented.
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.restaurant_item_icon);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            foodType = itemView.findViewById(R.id.food_type);
            avgRating = itemView.findViewById(R.id.avg_rating);
        }
    }



}

