package com.projects.rentalease.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.projects.rentalease.R;

import java.util.List;

public class RentItemImageAdapter extends RecyclerView.Adapter<RentItemImageAdapter.VH> {


    List<Uri> images;
    Context context;

    public RentItemImageAdapter(List<Uri> images, Context context){
        this.images = images;
        this.context = context;
    }

    public void clearImages(){
        images.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH vh =
                new VH(View.inflate(parent.getContext(), R.layout.category_list_item, null));
        vh.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Uri imageUrl = images.get(position);

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class VH extends RecyclerView.ViewHolder {

        ImageView imageView = itemView.findViewById(R.id.category_image);

        public VH(View view) {
            super(view);
        }
    }
}
