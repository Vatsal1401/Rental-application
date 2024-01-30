package com.projects.rentalease.daos;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projects.rentalease.data.User;

import java.util.concurrent.CompletableFuture;

public class UserDao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("users");
    public void addUser(User user){
        if(user != null){
            CompletableFuture.runAsync(()->{
                users.document(user.email).set(user);
            });
        }
    }

}
