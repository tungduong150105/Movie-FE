package com.example.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.movieapp.Domain.SliderItems;
import com.example.movieapp.R;

import java.util.List;

public class SliderAdapters extends RecyclerView.Adapter<SliderAdapters.SliderViewHolder> {
    private List<SliderItems> sliderItems;
    private ViewPager2 viewPager2;
    private Context mContext;

    public SliderAdapters(List<SliderItems> sliderItems, ViewPager2 viewPager2, Context mContext) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SliderAdapters.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapters.SliderViewHolder holder, int position) {
        SliderItems sliderItem = sliderItems.get(position);
        holder.setImageView(sliderItem.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public SliderViewHolder(@NonNull View itemView) { // find id of view from "slider_layout"
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlider);
        }
        public void setImageView(String imageUrl) {
            Glide.with(mContext).load(imageUrl).into(imageView);
        }
    }
}
