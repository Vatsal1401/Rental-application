package com.projects.rentalease.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.projects.rentalease.R;
import com.projects.rentalease.ui.FullScreenImageDialog;

import java.util.ArrayList;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ViewHolder> {
    Context context;
    ArrayList<Uri> arrayList;

    public ProductImageAdapter(Context context, ArrayList<Uri> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void addImage(Uri path) {
        arrayList.add(path);
        notifyItemInserted(arrayList.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(arrayList.get(position))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(view -> {
            FullScreenImageDialog dialog = new FullScreenImageDialog(context, arrayList.get(position));
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView = itemView.findViewById(R.id.list_item_image);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
