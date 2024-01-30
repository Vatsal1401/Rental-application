package com.projects.rentalease.data;

public class Category {
    public String name;
    public String image_uri;

    public Category(String name, String image_uri){
        this.name = name;
        this.image_uri = image_uri;
    }

    public String getName(){
        return name;
    }

}
