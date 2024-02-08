package com.projects.rentalease.data;

import java.util.List;

public class Product {

    public String userId;
    public String category;

    public String name;
    public List<String> images;
    public String description;
    public String price;

    public Product(String userId, String category, String name, List<String> images, String description, String price) {
        this.userId = userId;
        this.category = category;
        this.name = name;
        this.images = images;
        this.description = description;
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public List<String> getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
