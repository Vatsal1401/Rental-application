package com.projects.rentalease.adapters;

import android.content.Context;
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



public class CategoryListAdapter extends FirestoreRecyclerAdapter<Category, CategoryListAdapter.VH> {


    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    private Context context;
    public CategoryListAdapter(@NonNull FirestoreRecyclerOptions<Category> options,Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(View.inflate(parent.getContext(), R.layout.category_list_item, null));
    }


    @Override
    protected void onBindViewHolder(@NonNull VH holder, int position, @NonNull Category model) {


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
}
