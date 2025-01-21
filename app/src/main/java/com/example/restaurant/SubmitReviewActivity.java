package com.example.restaurant;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SubmitReviewActivity extends AppCompatActivity {
    private EditText reviewText;
    private RatingBar ratingBar;
    private DatabaseHelper dbHelper;
    private String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_submit_review);

        reviewText = findViewById(R.id.etReviewText);
        ratingBar = findViewById(R.id.rating_bar);
        dbHelper = new DatabaseHelper(this);

        // Get the restaurant name from the intent
        restaurantName = getIntent().getStringExtra("restaurant_name");

        findViewById(R.id.btnSubmitReview).setOnClickListener(v -> submitReview());
    }

    private void submitReview() {
        String review = reviewText.getText().toString();
        float rating = ratingBar.getRating();

        // Save the review to the database
        Review newReview = new Review(restaurantName, review, rating);
        dbHelper.addReview(newReview);

        Toast.makeText(this, "Review submitted!", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity
    }
}