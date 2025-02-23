package com.example.deem.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.utils.ImageUtil;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;


public class FullScreenImageAdapter extends RecyclerView.Adapter<FullScreenImageAdapter.ViewHolder> {
    private Context context;
    private List<ImageView> imageViews;

    public FullScreenImageAdapter(Context context, List<ImageView> imageViews) {
        this.context = context;
        this.imageViews = imageViews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fullscreen_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(imageViews.get(position));
    }

    @Override
    public int getItemCount() {
        return imageViews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        PhotoView photoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.photoView);
        }

        public void setData(ImageView imageView) {
            photoView.setImageDrawable(imageView.getDrawable());
        }
    }
}

