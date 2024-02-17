package com.projects.rentalease.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.projects.rentalease.R;
import com.projects.rentalease.model.Product;

public class LikedProductsAdapter extends FirestoreRecyclerAdapter<Product, LikedProductsAdapter.ViewHolder> {



    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();


    public LikedProductsAdapter(@NonNull FirestoreRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Product model) {
        holder.productName.setText(model.getName());
        holder.productDescription.setText(model.getDescription());

        StorageReference ref =  firebaseStorage.getReference().child("images")
                .child(model.userId)
                .child(model.images);

        ref.listAll().addOnSuccessListener(listings -> {

                listings.getItems().get(0).getDownloadUrl().addOnSuccessListener(uri -> {
                    Glide.with(holder.itemView.getContext())
                            .load(uri)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(holder.productImage);
                });

        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.liked_products_list_item,
                        parent,false
                )
        );

        return viewHolder;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage = itemView.findViewById(R.id.product_image);
        TextView productName = itemView.findViewById(R.id.product_name);
        TextView productDescription = itemView.findViewById(R.id.product_description);


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
