package com.example.deem.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.dialogs.FullScreenImageDialog;
import com.example.deem.utils.ImageLoadUtil;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.Message;
import com.example.restful.models.MessageImage;
import com.example.restful.models.VideoCallback;
import com.example.restful.utils.DateTranslator;
import com.example.restful.utils.HLS_ProtocolSystem;
import com.google.android.flexbox.FlexboxLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRecycleAdapter extends RecyclerView.Adapter<ChatRecycleAdapter.MessageViewHolder> {

    private List<Message> list;
    private AppCompatActivity activity;

    private int current_position;

    public ChatRecycleAdapter(List<Message> list, AppCompatActivity activity) {
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
        holder.container.setVisibility(View.GONE);
        holder.container.removeAllViews();
        holder.imageViews.clear();

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
        private FlexboxLayout container;
        private View include_view;
        private TextView item_date;
        private List<ImageView> imageViews;
        private CircleImageView icon;

        private boolean isMyMessage;
        private View itview;
        private final int radiusImage = 20;

        //video
        private PlayerView playerView;
        private ExoPlayer exoPlayer;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            itview = itemView;

            textMessage  = itemView.findViewById(R.id.textMessage);
            container    = itemView.findViewById(R.id.list_images);
            message_time = itemView.findViewById(R.id.message_time);
            include_view = itemView.findViewById(R.id.item_date_include);
            item_date    = include_view.findViewById(R.id.text_chat_date);
            playerView   = itemView.findViewById(R.id.player_view);
            icon         = itemView.findViewById(R.id.icon_message);

            imageViews = new ArrayList<>();
        }

        public void setData(Message message) {
            textMessage.setText(message.getText());
            message_time.setText(DateTranslator.getInstance().TimeToString(message.getDate()));
            message.setRead(true); //делаем сообщение прочитанным так как оно отрисовалось

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
                container.setVisibility(View.GONE);

            if (message.getImages().getValue().size() != 0) { //загружаем изображения те что есть
                for (MessageImage image : message.getImages().getValue())
                    addImageOnView(image.getImage().getImgEncode());
            } else {
                APIManager.getManager().getMessageImagesLazy(message, new ImageLoadCallback() {
                    @Override
                    public void onImageLoaded(String decodeStr) { addImageOnView(decodeStr);}
                });
            }

            //Video
            DoVideo(message);

            DoImageIcon(message);
        }

        @SuppressLint("UnsafeOptInUsageError")
        private void DoVideo(Message message) {

            //Проверка на видео и загрузка из сервера
            if (exoPlayer == null) {

                if (!message.isCompleted()) {
                    //В случае если нужно загрузить видео от сервера.
                    if (message.getVideoUUID() != null && !message.getVideoUUID().isEmpty())

                        APIManager.getManager().getVideoManifest(new VideoCallback() {
                            @Override
                            public void loadVideo(String url) {
                                playerView.setVisibility(View.VISIBLE);

                                exoPlayer = new ExoPlayer.Builder(activity).build();
                                playerView.setPlayer(exoPlayer);

                                exoPlayer.setMediaSource(HLS_ProtocolSystem.getInstance().createHLSMediaSource(url));
                                exoPlayer.prepare();
                                exoPlayer.play();
                            }
                        }, message.getVideoUUID());
                } else
                    //В случае если мы отправители видео и у нас уже оно есть.
                    if (message.getThereVideo() && message.getVideoMetadata() != null) {
                        playerView.setVisibility(View.VISIBLE);

                        try {
                            File tempFile = File.createTempFile("video", ".mp4", activity.getCacheDir());
                            tempFile.deleteOnExit();

                            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                                fos.write(message.getVideoMetadata().getVideo());
                            }

                            exoPlayer = new ExoPlayer.Builder(activity).build();
                            playerView.setPlayer(exoPlayer);

                            MediaItem mediaItem = MediaItem.fromUri(Uri.fromFile(tempFile));
                            exoPlayer.setMediaItem(mediaItem);
                            exoPlayer.prepare();
                            exoPlayer.play();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            } else {
                int state = exoPlayer.getPlaybackState();
                switch (state) {
                    case Player.STATE_READY:
                        if (exoPlayer.isPlaying()) {
                            System.out.println("PLAYING VIDEO");
                        } else {
                            System.out.println("PAUSE VIDEO");
                        }
                        break;
                    case Player.STATE_ENDED:
                        System.out.println("FINISHED VIDEO");
                        break;
                }
            }
        }

        private void DoImageIcon(Message message) {
            if (message.getAuthor() != APIManager.getManager().getMyAccount().getId()) {
                Account account = null;

                for (Account account1 : APIManager.getManager().getListAccounts())
                    if (account1.getId() == message.getAuthor()) {
                        account = account1;
                        break;
                    }

                ImageLoadUtil.getInstance().LoadImageIcon(icon, account);
            }
        }

        private void addImageOnView(String decodeStr) {
            if (decodeStr == null) return;
            Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(decodeStr);

            ImageView imageView = new ImageView(activity);
            Bitmap roundedBitmap = getRoundedBitmap(bitmap, radiusImage);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(getScaledBitmap(roundedBitmap));
            imageViews.add(imageView);

            //container.removeAllViews();
            container.setVisibility(View.VISIBLE);
            container.addView(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = 0;
                    for (int j = 0; j < imageViews.size(); j++)
                        if (imageViews.get(j) == imageView)
                            n = j;

                    FullScreenImageDialog dialog = new FullScreenImageDialog();
                    dialog.initialize(imageViews, n);
                    dialog.show(activity.getSupportFragmentManager(), "full_screen_dialog");
                }
            });
        }

        /** Округлить изображение */
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

            int targetWidth = displayMetrics.widthPixels;

            if (!isMyMessage)
                targetWidth -= 250;

            int targetHeight = Math.round((float) bitmap.getHeight() * ((float) targetWidth / (float) bitmap.getWidth()));

            return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
        }
    }
}
