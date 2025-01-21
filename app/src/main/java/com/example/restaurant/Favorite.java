package com.example.restaurant;

public class Favorite {
    private int id;
    private String restaurant;
    private String location;

    // Constructor for new favorites (without id)
    public Favorite(String restaurant, String location) {
        this.id = -1; // Default value for new favorites
        this.restaurant = restaurant;
        this.location = location;
    }

    // Constructor for existing favorites (with id)
    public Favorite(int id, String restaurant, String location) {
        this.id = id;
        this.restaurant = restaurant;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getLocation() {
        return location;
    }
}