package com.bookstoreapp.view.home.slider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookstoreapp.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderHolder> {

    private List<String> images;

    public SliderAdapter(List<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public SliderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, parent, false);
        return new SliderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderHolder holder, int position) {
        holder.bindView(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
