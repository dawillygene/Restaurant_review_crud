package com.example.restaurant;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RestaurantAdapter.OnRestaurantClickListener {
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<Restaurant> restaurantList;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());

        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant("The Rock Restaurant", "Zanzibar"));
        restaurantList.add(new Restaurant("Slipway Restaurant", "Msasani Peninsula, Dar es Salaam"));
        restaurantList.add(new Restaurant("Serengeti Coffee House", "Arusha"));
        restaurantList.add(new Restaurant("Karafuu Restaurant", "Pemba Island"));
        restaurantList.add(new Restaurant("Mamboz Corner BBQ", "Dar es Salaam"));
        restaurantList.add(new Restaurant("Samaki Samaki", "Masaki, Dar es Salaam"));
        restaurantList.add(new Restaurant("Zenji Cafe", "Stone Town, Zanzibar"));
        restaurantList.add(new Restaurant("The Waterfront Sunset Restaurant", "Dar es Salaam"));
        restaurantList.add(new Restaurant("Maembe Cafe", "Arusha"));
        restaurantList.add(new Restaurant("Cholo Cafe", "Moshi"));

        adapter = new RestaurantAdapter(getContext(), restaurantList, this);
        recyclerView.setAdapter(adapter);

        return view;
    }



    @Override
    public void onReviewClick(Restaurant restaurant) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_submit_review, null);
        builder.setView(dialogView);

        TextView tvRestaurantName = dialogView.findViewById(R.id.tvRestaurantName);
        EditText etReviewText = dialogView.findViewById(R.id.etReviewText);
        RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
        Button btnSubmitReview = dialogView.findViewById(R.id.btnSubmitReview);

        tvRestaurantName.setText(restaurant.getName());

        AlertDialog dialog = builder.create();
        btnSubmitReview.setOnClickListener(v -> {
            String reviewText = etReviewText.getText().toString();
            float rating = ratingBar.getRating();
            Review newReview = new Review(restaurant.getName(), reviewText, rating);

            dbHelper.addReview(newReview);

            Toast.makeText(getContext(), "Review submitted!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onFavoriteClick(Restaurant restaurant) {
        Favorite favorite = new Favorite(restaurant.getName(), restaurant.getLocation());
        dbHelper.addFavorite(favorite);
        Toast.makeText(getContext(), restaurant.getName() + " added to favorites!", Toast.LENGTH_SHORT).show();
    }
}