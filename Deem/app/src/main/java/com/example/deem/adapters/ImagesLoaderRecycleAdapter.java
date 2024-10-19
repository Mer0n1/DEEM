package com.example.deem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.restful.models.Image;
import com.example.restful.models.MessageImage;

import java.util.List;


public class ImagesLoaderRecycleAdapter extends RecyclerView.Adapter<ImagesLoaderRecycleAdapter.ItemImage> {

    private List<ImageView> images;
    private List<MessageImage> FixedImages;
    private Context context;

    public ImagesLoaderRecycleAdapter(List<ImageView> images, List<MessageImage> images_, Context context) {
        this.images = images;
        this.context = context;
        this.FixedImages = images_;
    }

    @NonNull
    @Override
    public ItemImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_loaded, parent, false);

        return new ImagesLoaderRecycleAdapter.ItemImage(/*new ImageView(context)*/view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemImage holder, int position) {
        holder.setData(images.get(position), position);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class ItemImage extends RecyclerView.ViewHolder {

        ImageView view;
        ImageView button_close;

        public ItemImage(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.image_loaded);
            button_close = itemView.findViewById(R.id.button_delete_image);
        }

        public void setData(ImageView imageView, int position) {
            view.setImageDrawable(imageView.getDrawable());

            button_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    images.remove(position);
                    FixedImages.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
