package com.projects.rentalease.model;

import java.util.ArrayList;

public class Product {

    public String userId;
    public String category;

    public String name;
    public String images;
    public String description;
    public String price;

    public String contactDetails;

    ArrayList<String> likedBy;

    public Product(String userId, String category, String name, String description, String price, String contactDetails) {
        this.userId = userId;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.contactDetails = contactDetails;
        likedBy = new ArrayList<>();
    }

    public Product() {
        // empty constructor required by firebase
        likedBy = new ArrayList<>();
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

    public String getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getContactDetails() {
        return contactDetails;
    }



    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public ArrayList<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(ArrayList<String> likedBy) {
        this.likedBy = likedBy;
    }
}
