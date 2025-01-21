package com.example.restaurant;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "restaurant_reviews.db";
    private static final int DATABASE_VERSION = 1;

    // Tables and Columns
    private static final String TABLE_REVIEWS = "reviews";
    private static final String KEY_ID = "id";
    private static final String KEY_RESTAURANT = "restaurant";
    private static final String KEY_REVIEW = "review";
    private static final String KEY_RATING = "rating";
    private static final String KEY_DATE = "date";

    private static final String TABLE_FAVORITES = "favorites";
    private static final String KEY_FAV_ID = "id";
    private static final String KEY_FAV_RESTAURANT = "restaurant";
    private static final String KEY_LOCATION = "location";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the reviews table
        String CREATE_REVIEWS_TABLE = "CREATE TABLE " + TABLE_REVIEWS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_RESTAURANT + " TEXT,"
                + KEY_REVIEW + " TEXT,"
                + KEY_RATING + " REAL,"
                + KEY_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_REVIEWS_TABLE);

        // Create the favorites table
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + KEY_FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FAV_RESTAURANT + " TEXT,"
                + KEY_LOCATION + " TEXT)";
        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    // Add a new review
    public long addReview(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RESTAURANT, review.getRestaurant());
        values.put(KEY_REVIEW, review.getReview());
        values.put(KEY_RATING, review.getRating());
        long id = db.insert(TABLE_REVIEWS, null, values);
        db.close();
        return id;
    }

    // Add a new favorite
    public long addFavorite(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FAV_RESTAURANT, favorite.getRestaurant());
        values.put(KEY_LOCATION, favorite.getLocation());
        long id = db.insert(TABLE_FAVORITES, null, values);
        db.close();
        return id;
    }

    public List<Favorite> getAllFavorites() {
        List<Favorite> favorites = new ArrayList<>();

        // Query to select all favorites
        String query = "SELECT * FROM " + TABLE_FAVORITES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Loop through the cursor and add favorites to the list
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(KEY_FAV_ID));
                @SuppressLint("Range") String restaurant = cursor.getString(cursor.getColumnIndex(KEY_FAV_RESTAURANT));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(KEY_LOCATION));

                // Use the correct constructor (with id)
                Favorite favorite = new Favorite(id, restaurant, location);
                favorites.add(favorite);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return favorites;
    }

    public void deleteFavorite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, KEY_FAV_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }




    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_REVIEWS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Loop through the cursor and add reviews to the list
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                @SuppressLint("Range") String restaurant = cursor.getString(cursor.getColumnIndex(KEY_RESTAURANT));
                @SuppressLint("Range") String reviewText = cursor.getString(cursor.getColumnIndex(KEY_REVIEW));
                @SuppressLint("Range") float rating = cursor.getFloat(cursor.getColumnIndex(KEY_RATING));

                Review review = new Review(id, restaurant, reviewText, rating);
                reviews.add(review);
            } while (cursor.moveToNext());
        }else {
            Log.d("ReviewData", "No reviews found in the database.");
        }
        cursor.close();
        db.close();
        return reviews;
    }

    public void deleteReview(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REVIEWS, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    public void updateReview(int id, String restaurant, String review, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RESTAURANT, restaurant);
        values.put(KEY_REVIEW, review);
        values.put(KEY_RATING, rating);

        db.update(TABLE_REVIEWS, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}