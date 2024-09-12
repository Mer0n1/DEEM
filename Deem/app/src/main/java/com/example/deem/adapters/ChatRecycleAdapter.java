package com.example.deem.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
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
import com.example.restful.models.NewsImage;
import com.example.restful.utils.DateUtil;
import com.example.restful.utils.GeneratorUUID;

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
            if (message.isNoImages() || message.getImages().getValue() == null)
                recyclerView.setVisibility(View.GONE);

            if (message.getImages().getValue().size() != 0) { //загружаем изображения те что есть
                for (MessageImage image : message.getImages().getValue()) {
                    setImageOnView(image.getImage().getImgEncode());
                    imagesListRecycleAdapter.notifyDataSetChanged();
                }
            } else {

                if (!message.isNoImages()) {
                    Account account = APIManager.getManager().listAccounts.stream().filter(x -> x.getId()
                            == message.getAuthor()).findAny().orElse(null);

                    String UUID = GeneratorUUID.getInstance().generateUUIDForMessage(
                            DateUtil.getInstance().getDateToForm(message.getDate()), account.getUsername());

                    Image image = CacheSystem.getCacheSystem().getImageByUuid(UUID);

                    if (image != null) {
                        MessageImage messageImage = new MessageImage();
                        messageImage.setImage(image);
                        messageImage.setId_message(message.getId());
                        messageImage.setUuid(UUID);
                        if (message.getImages().getValue() == null)
                            message.getImages().setValue(new ArrayList<>());
                        message.getImages().getValue().add(messageImage);

                        setImageOnView(image.getImgEncode());

                    } else {
                        //Lazy система подгрузки изображений
                        APIManager.getManager().getMessageImagesLazy(message, new ImageLoadCallback() {
                            @Override
                            public void onImageLoaded(String decodeStr) {
                                setImageOnView(decodeStr);
                            }
                        });
                    }
                }
            }

        }

        private void setImageOnView(String decodeStr) {
            if (decodeStr == null) return;
            Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(decodeStr);

            ImageView imageView = new ImageView(activity);
            Bitmap roundedBitmap = getRoundedBitmap(bitmap, 20);
            imageView.setImageBitmap(roundedBitmap);
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


    }
}
