package com.projects.rentalease.data;

public class Category {
    public String name;
    public String imageUrl;

    public Category() {
        // empty constructor needed
    }

    public Category(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
