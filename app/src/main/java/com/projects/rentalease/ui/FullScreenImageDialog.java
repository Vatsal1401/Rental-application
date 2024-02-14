package com.projects.rentalease.ui;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.projects.rentalease.R;

public class FullScreenImageDialog extends Dialog {
    private Context context;
    private Uri imageUri;

    public FullScreenImageDialog(@NonNull Context context, Uri imageUri) {
        super(context);
        this.context = context;
        this.imageUri = imageUri;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.full_screen_image_dialog_layout);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView imageView = findViewById(R.id.imageViewFullScreen);
        Glide.with(context)
                .load(imageUri)
                .into(imageView);

        ImageView closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
