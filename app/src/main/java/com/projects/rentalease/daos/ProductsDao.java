package com.projects.rentalease.daos;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.projects.rentalease.data.Product;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ProductsDao {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference products = db.collection("products");

    private StorageReference imagesReference = FirebaseStorage.getInstance().getReference().child("images").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));


    public void addProduct(Product product, List<Uri> images){
        product.images = addImagesAndGetImageId(images);
        CompletableFuture.runAsync(() -> {
            products.document().set(product);
        });
    }

    private String addImagesAndGetImageId(List<Uri> images){
        String imageId = String.valueOf(new Date().getTime());

        StorageReference folder = imagesReference.child(imageId);

        images.forEach(image -> {
            String fileName = UUID.randomUUID().toString();
            folder.child(fileName).putFile(image);
        });

        return imageId;
    }

}
