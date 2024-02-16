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
import com.projects.rentalease.model.Category;



public class CategoryListAdapter extends FirestoreRecyclerAdapter<Category, CategoryListAdapter.VH> {

    CategoryListListeners categoryListListeners;


    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    private Context context;
    public CategoryListAdapter(@NonNull FirestoreRecyclerOptions<Category> options,Context context,CategoryListListeners categoryListListeners) {
        super(options);
        this.context = context;
        this.categoryListListeners = categoryListListeners;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item,parent,false);

        return new VH(layout);
    }


    @Override
    protected void onBindViewHolder(@NonNull VH holder, int position, @NonNull Category model) {

        holder.itemView.setOnClickListener(v -> {
            categoryListListeners.onCategoryClick(model);
        });

        StorageReference ref =  firebaseStorage.getReference().child("categories/"+model.imageUrl);

        ref.getDownloadUrl().addOnSuccessListener(url -> {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.imageView);
        });

        holder.captionText.setText(model.name);
    }




    class VH extends RecyclerView.ViewHolder {

        ImageView imageView = itemView.findViewById(R.id.category_image);
        TextView captionText = itemView.findViewById(R.id.caption);

        public VH(View view) {
            super(view);
        }
    }
    public interface CategoryListListeners{
        void onCategoryClick(Category category);
    }
}
