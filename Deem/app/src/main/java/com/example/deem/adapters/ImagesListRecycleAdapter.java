package com.example.deem.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.restful.models.Image;
import com.example.restful.models.News;

import java.util.List;

public class ImagesListRecycleAdapter extends RecyclerView.Adapter<ImagesListRecycleAdapter.ItemImage> {

    private List<ImageView> images;
    private Context context;

    public ImagesListRecycleAdapter(List<ImageView> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImagesListRecycleAdapter.ItemImage(new ImageView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemImage holder, int position) {
        holder.setData(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ItemImage extends RecyclerView.ViewHolder {

        ImageView view;

        public ItemImage(@NonNull View itemView) {
            super(itemView);
            view = (ImageView) itemView;
        }

        public void setData(ImageView imageView) {
            view.setImageDrawable(imageView.getDrawable());

        }
    }
}
