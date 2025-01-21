package com.example.restaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyReviewsFragment extends Fragment implements ReviewsAdapter.OnReviewClickListener {
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private ReviewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_reviews, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(getActivity());
        List<Review> reviews = databaseHelper.getAllReviews();

        adapter = new ReviewsAdapter(reviews, this);
        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onEditClick(Review review) {
        EditReviewDialog dialog = EditReviewDialog.newInstance(
                review.getId(),
                review.getRestaurant(),
                review.getReview(),
                review.getRating()
        );
        dialog.setListener(updatedReview -> {
            databaseHelper.updateReview(updatedReview.getId(), updatedReview.getRestaurant(), updatedReview.getReview(), updatedReview.getRating());
            refreshReviews();
        });
        dialog.show(getParentFragmentManager(), "EditReviewDialog");
    }

    @Override
    public void onDeleteClick(Review review) {
        // Delete the review from the database
        databaseHelper.deleteReview(review.getId());
        refreshReviews();
    }

    private void refreshReviews() {
        List<Review> reviews = databaseHelper.getAllReviews();
        adapter = new ReviewsAdapter(reviews, this);
        recyclerView.setAdapter(adapter);
    }
}