package com.example.restaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;


import androidx.fragment.app.DialogFragment;
public class EditReviewDialog extends DialogFragment {
    private TextView tvRestaurantName;
    private EditText etReviewText;
    private RatingBar ratingBar;
    private int id;
    private String restaurant;
    private String reviewText;
    private float rating;
    private OnReviewUpdatedListener listener;

    public interface OnReviewUpdatedListener {
        void onReviewUpdated(Review review);
    }

    // Static method to create a new instance of the dialog
    public static EditReviewDialog newInstance(int id, String restaurant, String reviewText, float rating) {
        EditReviewDialog dialog = new EditReviewDialog();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("restaurant", restaurant);
        args.putString("reviewText", reviewText);
        args.putFloat("rating", rating);
        dialog.setArguments(args);
        return dialog;
    }

    public void setListener(OnReviewUpdatedListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_review, container, false);

        // Initialize views
        tvRestaurantName = view.findViewById(R.id.tvRestaurantName);
        etReviewText = view.findViewById(R.id.etReviewText);
        ratingBar = view.findViewById(R.id.rating_bar);

        // Get the review data from arguments
        if (getArguments() != null) {
            id = getArguments().getInt("id");
            restaurant = getArguments().getString("restaurant");
            reviewText = getArguments().getString("reviewText");
            rating = getArguments().getFloat("rating");

            // Pre-populate the dialog fields with the review data
            tvRestaurantName.setText(restaurant);
            etReviewText.setText(reviewText);
            ratingBar.setRating(rating);
        }

        // Handle the Save button click
        view.findViewById(R.id.btnSave).setOnClickListener(v -> saveReview());

        return view;
    }

    private void saveReview() {
        // Get the updated review text and rating
        String updatedReviewText = etReviewText.getText().toString();
        float updatedRating = ratingBar.getRating();

        // Create a new Review object with the updated data
        Review updatedReview = new Review(id, restaurant, updatedReviewText, updatedRating);

        // Notify the listener that the review has been updated
        if (listener != null) {
            listener.onReviewUpdated(updatedReview);
        }

        // Dismiss the dialog
        dismiss();
    }
}