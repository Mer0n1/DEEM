package com.example.deem.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.datebase.CacheSystem;
import com.example.restful.models.Account;
import com.example.restful.models.Image;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.Message;
import com.example.restful.models.MessageImage;
import com.example.restful.utils.DateTranslator;
import com.example.restful.utils.DateUtil;
import com.example.restful.utils.GeneratorUUID;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatRecycleAdapter extends RecyclerView.Adapter<ChatRecycleAdapter.MessageViewHolder> {

    private List<Message> list;
    private Activity activity;

    private int current_position;

    public ChatRecycleAdapter(List<Message> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        current_position = -1;
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
    public void onBindViewHolder(@NonNull MessageViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textMessage.setText(null);
        holder.message_time.setText("");
        holder.include_view.setVisibility(View.GONE);
        holder.item_date.setText("");
        holder.views.clear();
        holder.recyclerView.setVisibility(View.GONE);

        current_position = position;

        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getAuthor() == APIManager.getManager().getMyAccount().getId()) {
            return 1;
        } else {
            return 2;
        }
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView textMessage;
        private TextView message_time;
        private RecyclerView recyclerView;
        private List<ImageView> views;
        private ImagesListRecycleAdapter imagesListRecycleAdapter;

        private View include_view;
        private TextView item_date;

        private boolean isMyMessage;
        private View itview;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            itview = itemView;

            textMessage  = itemView.findViewById(R.id.textMessage);
            recyclerView = itemView.findViewById(R.id.list_images);
            message_time = itemView.findViewById(R.id.message_time);
            include_view = itemView.findViewById(R.id.item_date_include);
            item_date = include_view.findViewById(R.id.text_chat_date);

            views = new ArrayList<>();
            recyclerView.setLayoutManager(new GridLayoutManager(activity, 1));
            imagesListRecycleAdapter = new ImagesListRecycleAdapter(views, activity);
            recyclerView.setAdapter(imagesListRecycleAdapter);
            recyclerView.scrollToPosition(1);

            isMyMessage = false;
        }

        public void setData(Message message) {
            textMessage.setText(message.getText());
            message_time.setText(DateTranslator.getInstance().TimeToString(message.getDate()));

            if (message.getAuthor().equals(APIManager.getManager().getMyAccount().getId()))
                isMyMessage = true;

            //настройка item_date
            boolean isLastMessage = false;

            Date up_date = list.get(current_position).getDate();
            if (current_position-1 >= 0)
                up_date = list.get(current_position-1).getDate();
            else isLastMessage = true;

            if (up_date != null) {
                if (!DateTranslator.getInstance().DayMonthToString(list.get(current_position).getDate()).equals(
                        DateTranslator.getInstance().DayMonthToString(up_date)) || isLastMessage) {
                    item_date.setText(DateTranslator.getInstance().DayMonthToString(message.getDate()));
                    include_view.setVisibility(View.VISIBLE);
                }
            }

            //Обновление Holder изображений
            if (message.isNoImages() || message.getImages().getValue() == null)
                recyclerView.setVisibility(View.GONE);

            if (message.getImages().getValue().size() != 0) { //загружаем изображения те что есть
                for (MessageImage image : message.getImages().getValue()) {
                    setImageOnView(image.getImage().getImgEncode());
                    imagesListRecycleAdapter.notifyDataSetChanged();
                }
            } else {
                APIManager.getManager().getMessageImagesLazy(message, new ImageLoadCallback() {
                    @Override
                    public void onImageLoaded(String decodeStr) { setImageOnView(decodeStr);}
                });
            }

        }

        private void setImageOnView(String decodeStr) {
            if (decodeStr == null) return;
            Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(decodeStr);

            ImageView imageView = new ImageView(activity);
            Bitmap roundedBitmap = getRoundedBitmap(bitmap, 20);
            imageView.setImageBitmap(getScaledBitmap(roundedBitmap));

            views.add(imageView);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new GridLayoutManager(activity, views.size()));
        }

        public Bitmap getRoundedBitmap(Bitmap bitmap, int radius) {
            if (bitmap == null) return null;
            Bitmap roundedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(roundedBitmap);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);

            return roundedBitmap;
        }

        private Bitmap getScaledBitmap(Bitmap bitmap) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            if (!isMyMessage)
                width -= 350;

            float aspectRatio = (float) width / (float) bitmap.getWidth();
            int targetHeight = Math.round(bitmap.getHeight() * aspectRatio);

            return Bitmap.createScaledBitmap(bitmap, width, targetHeight, true);
        }
    }
}
