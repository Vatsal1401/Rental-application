package com.projects.rentalease.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.projects.rentalease.adapters.ProductListAdapter;
import com.projects.rentalease.data.Product;
import com.projects.rentalease.databinding.FragmentProductsBinding;
import com.projects.rentalease.models.MainViewModel;

import java.util.Objects;

public class ProductsFragment extends Fragment implements ProductListAdapter.CategoryListListeners {

    private MainViewModel mainViewModel;
    private FragmentProductsBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Query productsRef = db.collection("products");



    ProductListAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        FirestoreRecyclerOptions<Product> productFirestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Product>()
                        .setQuery(productsRef.whereEqualTo("category", Objects.requireNonNull(mainViewModel.activeCategory.getValue()).name), Product.class)
                        .build();
        adapter = new ProductListAdapter(productFirestoreRecyclerOptions, getContext(), this);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.categoryRecyclerView.setAdapter(adapter);


    }

    @Override
    public void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onCategoryClick(Product product) {

    }
}