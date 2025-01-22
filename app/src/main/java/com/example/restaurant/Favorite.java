package com.example.restaurant;

public class Favorite {
    private int id;
    private String restaurant;
    private String location;

    public Favorite(String restaurant, String location) {
        this.id = -1;
        this.restaurant = restaurant;
        this.location = location;
    }

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