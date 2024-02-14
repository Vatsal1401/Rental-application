package com.projects.rentalease.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.projects.rentalease.R;
import com.projects.rentalease.data.Product;

import java.util.ArrayList;
import java.util.Objects;

public class ProductListAdapter  extends FirestoreRecyclerAdapter<Product, ProductListAdapter.VH> {

    CategoryListListeners categoryListListeners;





    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    private final Context context;
    public ProductListAdapter(@NonNull FirestoreRecyclerOptions<Product> options, Context context, CategoryListListeners categoryListListeners) {
        super(options);
        this.context = context;
        this.categoryListListeners = categoryListListeners;

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);

        return new VH(layout);
    }


    @Override
    protected void onBindViewHolder(@NonNull VH holder, int position, @NonNull Product model) {



        ProductImageAdapter adapter = new ProductImageAdapter(context, new ArrayList<>());

        holder.recyclerView.setAdapter(adapter);



        holder.itemView.setOnClickListener(v -> {
            categoryListListeners.onCategoryClick(model);
        });

        StorageReference ref =  firebaseStorage.getReference().child("images")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child(model.images);

        ref.listAll().addOnSuccessListener(listings -> {
            for (StorageReference item : listings.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(adapter::addImage);
            }
        });

        holder.nametext.setText(model.name);
        holder.descriptiontext.setText(model.description);
        holder.pricetext.setText(model.price);


    }


    class VH extends RecyclerView.ViewHolder {

        RecyclerView recyclerView = itemView.findViewById(R.id.itemImageRecyclerView);


        TextView nametext = itemView.findViewById(R.id.itemNameTextView);

        TextView descriptiontext = itemView.findViewById(R.id.itemDescriptionTextView);

        TextView pricetext = itemView.findViewById(R.id.itemPriceTextView);
        ImageView addcarttext = itemView.findViewById(R.id.addToCartButton);


        public VH(View view) {
            super(view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setNestedScrollingEnabled(false);
        }
    }
    public interface CategoryListListeners{
        void onCategoryClick(Product product);
    }
}

