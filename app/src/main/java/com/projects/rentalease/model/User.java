package com.projects.rentalease.model;
public class User {
    public String name;
    public String email;
    public String image_uri;

    public String uId;

    public User() {
        // empty constructor required
    }
    public User(String name, String email, String image_uri, String uId) {
        this.name = name;
        this.email = email;
        this.image_uri = image_uri;
        this.uId = uId;
    }
}