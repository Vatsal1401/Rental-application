package com.projects.rentalease.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.projects.rentalease.R;
import com.projects.rentalease.data.Category;


import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.VH> {

    private List<Category> categoryList;
    private Context context;

    public CategoryListAdapter(List<Category> categoryList, Context context){
        this.categoryList = categoryList;
        this.context = context;
    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(View.inflate(parent.getContext(), R.layout.category_list_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Category category = categoryList.get(position);

        Glide.with(context)
                .load(category.image_uri)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);

        holder.captionText.setText(category.name);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class VH extends RecyclerView.ViewHolder {

        ImageView imageView = itemView.findViewById(R.id.category_image);
        TextView captionText = itemView.findViewById(R.id.caption);

        public VH(View view) {
            super(view);
        }
    }
}
