package com.example.restaurant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
    private List<Review> reviews;
    private OnReviewClickListener listener;

    public interface OnReviewClickListener {
        void onEditClick(Review review);
        void onDeleteClick(Review review);
    }

    public ReviewsAdapter(List<Review> reviews, OnReviewClickListener listener) {
        this.reviews = reviews;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.restaurantName.setText(review.getRestaurant());
        holder.reviewText.setText(review.getReview());
        holder.rating.setText(String.valueOf(review.getRating()));

        // Set click listeners for edit and delete buttons
        holder.editButton.setOnClickListener(v -> listener.onEditClick(review));
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(review));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName, reviewText, rating;
        Button editButton, deleteButton;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            reviewText = itemView.findViewById(R.id.review_text);
            rating = itemView.findViewById(R.id.rating);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}