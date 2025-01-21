package com.example.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

public class Review {
    private int id;
    private String restaurant;
    private String review;
    private float rating;


    public Review(int id, String restaurant, String review, float rating) {
        this.id = id;
        this.restaurant = restaurant;
        this.review = review;
        this.rating = rating;
    }


    public Review(String restaurant, String review, float rating) {
        this.id = -1;
        this.restaurant = restaurant;
        this.review = review;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getReview() {
        return review;
    }

    public float getRating() {
        return rating;
    }
}