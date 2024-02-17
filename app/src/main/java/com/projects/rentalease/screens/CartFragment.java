package com.projects.rentalease.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.projects.rentalease.R;
import com.projects.rentalease.adapters.LikedProductsAdapter;
import com.projects.rentalease.model.Product;

import java.util.Objects;


public class CartFragment extends Fragment {


    private LikedProductsAdapter likedProductsAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Query productsRef = db.collection("products");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String uId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        FirestoreRecyclerOptions<Product> likedProductsRecyclerViewOptions =
                new FirestoreRecyclerOptions.Builder<Product>()
                        .setQuery(productsRef.whereArrayContains("likedBy", uId), Product.class)
                        .build();


        likedProductsAdapter = new LikedProductsAdapter(likedProductsRecyclerViewOptions);


        recyclerView.setAdapter(likedProductsAdapter);

    }

    @Override
    public void onStart() {
        likedProductsAdapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        likedProductsAdapter.stopListening();
        super.onStop();
    }
}