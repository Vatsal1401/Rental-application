package com.projects.rentalease.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.projects.rentalease.R;
import com.projects.rentalease.adapters.ProductListAdapter;
import com.projects.rentalease.data.Product;
import com.projects.rentalease.databinding.FragmentProductsBinding;

import java.util.Objects;

public class ProductsFragment extends Fragment implements ProductListAdapter.CategoryListListeners {

    private FragmentProductsBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Query productsRef = db.collection("products");


    FirebaseAuth auth = FirebaseAuth.getInstance();

    ProductListAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String category = "";
        if (getArguments() != null) {
            category = getArguments().getString("category");
        }

        FirestoreRecyclerOptions<Product> productFirestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Product>()
                        .setQuery(productsRef.whereEqualTo("category", category), Product.class)
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
    public void onProductClick(String productId) {
        Bundle bundle = new Bundle();
        bundle.putString("product_id", productId);
        Navigation.findNavController(requireView()).navigate(R.id.action_productsFragment_to_productInformationFragment, bundle);
    }

    @Override
    public void onProductLikeClick(String productId) {
        DocumentReference product =
                db.collection("products").
                        document(productId);
        product.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Product currentProduct = documentSnapshot.toObject(Product.class);
                assert currentProduct != null;
                if(currentProduct.getLikedBy().contains(auth.getUid())){
                    currentProduct.getLikedBy().remove(auth.getUid());
                    product.update("likedBy", currentProduct.getLikedBy());
                }else{
                    currentProduct.getLikedBy().add(auth.getUid());
                    product.update("likedBy",currentProduct.getLikedBy());
                }
            }
        });
    }
}