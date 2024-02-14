package com.projects.rentalease.data;

import java.util.List;

public class Product {

    private String postId;
    public String userId;
    public String category;

    public String name;
    public String images;
    public String description;
    public String price;

    public String contactDetails;

    public Product(String userId, String category, String name, String description, String price, String contactDetails) {
        this.userId = userId;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.contactDetails = contactDetails;
    }

    public Product() {
        // empty constructor required by firebase
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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
}
