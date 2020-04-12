package com.bookstoreapp.view.home.slider;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookstoreapp.R;
import com.squareup.picasso.Picasso;

public class SliderHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;

    public SliderHolder(@NonNull View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View view) {
        imageView = view.findViewById(R.id.slider_imageView);
    }

    void bindView(String image) {
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.img_placeholder)
                .into(imageView);
    }
}
