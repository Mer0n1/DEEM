package com.example.deem.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Image;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.Message;
import com.example.restful.models.MessageImage;

import java.util.ArrayList;
import java.util.List;

public class ChatRecycleAdapter extends RecyclerView.Adapter<ChatRecycleAdapter.MessageViewHolder> {

    private List<Message> list;
    private Activity activity;

    public ChatRecycleAdapter(List<Message> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ChatRecycleAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == 1)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sent_message, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_received_message, parent, false);


        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getAuthor() == APIManager.getManager().myAccount.getId()) {
            return 1;
        } else {
            return 2;
        }
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView textMessage;
        private RecyclerView recyclerView;
        private List<ImageView> views;
        private ImagesListRecycleAdapter imagesListRecycleAdapter;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            textMessage  = itemView.findViewById(R.id.textMessage);
            recyclerView = itemView.findViewById(R.id.list_images);

            views = new ArrayList<>();
            recyclerView.setLayoutManager(new GridLayoutManager(activity, 1));
            imagesListRecycleAdapter = new ImagesListRecycleAdapter(views, activity);
            recyclerView.setAdapter(imagesListRecycleAdapter);
            recyclerView.scrollToPosition(1);
        }

        public void setData(Message message) {
            textMessage.setText(message.getText());
            views.clear();

            //Обновление Holder изображений
            if (message.isNoMessages() || message.getImages() == null)
                recyclerView.setVisibility(View.GONE);
            else
                for (MessageImage image : message.getImages()) {
                    Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(image.getImage().getImgEncode());
                    ImageView imageView = new ImageView(activity);
                    imageView.setImageBitmap(bitmap);
                    views.add(imageView);
                    recyclerView.setVisibility(View.VISIBLE);
                }

            //Lazy система подгрузки изображений
            if (!message.isNoMessages() && (message.getImages() == null
                    || message.getImages().size() == 0))
                APIManager.getManager().getMessageImagesLazy(message, new ImageLoadCallback() {
                    @Override
                    public void onImageLoaded(String decodeStr) {
                        if (decodeStr == null) return;
                        Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(decodeStr);

                        ImageView imageView = new ImageView(activity);
                        imageView.setImageBitmap(bitmap);
                        views.add(imageView);

                        recyclerView.setLayoutManager(new GridLayoutManager(activity, views.size()));
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
        }


    }
}
