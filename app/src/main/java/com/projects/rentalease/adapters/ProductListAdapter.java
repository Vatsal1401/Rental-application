package com.projects.rentalease.adapters;

import android.content.Context;
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
import com.projects.rentalease.data.Category;
import com.projects.rentalease.data.Product;

public class ProductListAdapter  extends FirestoreRecyclerAdapter<Product, ProductListAdapter.VH> {

    CategoryListListeners categoryListListeners;


    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    private Context context;
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

        holder.itemView.setOnClickListener(v -> {
            categoryListListeners.onCategoryClick(model);
        });

        // todo implement this
//        StorageReference ref =  firebaseStorage.getReference().child("categories/"+model.imageUrl);
//
//        ref.getDownloadUrl().addOnSuccessListener(url -> {
//            Glide.with(context)
//                    .load(url)
//                    .placeholder(R.drawable.ic_launcher_foreground)
//                    .into(holder.imageView);
//        });

        holder.captionText.setText(model.name);
    }




    class VH extends RecyclerView.ViewHolder {

        // todo add all views from product_list_item.xml
        ImageView imageView = itemView.findViewById(R.id.itemImageView);
        TextView titleText = itemView.findViewById(R.id.caption);

        TextView nametext = itemView.findViewById(R.id.itemNameTextView);

        TextView descriptiontext = itemView.findViewById(R.id.itemDescriptionTextView);

        TextView pricetext = itemView.findViewById(R.id.itemPriceTextView);
        TextView addcarttext = itemView.findViewById(R.id.addToCartButton);
        public VH(View view) {
            super(view);
        }
    }
    public interface CategoryListListeners{
        void onCategoryClick(Product product);
    }
}

