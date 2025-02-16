package com.example.deem;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.adapters.ChatRecycleAdapter;
import com.example.deem.adapters.ImagesListRecycleAdapter;
import com.example.deem.adapters.ImagesLoaderRecycleAdapter;
import com.example.deem.databinding.ActivityChatBinding;
import com.example.restful.models.CreateMessageDTO;
import com.example.restful.models.Group;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.MessageImage;
import com.example.restful.models.News;
import com.example.restful.models.StandardCallback;
import com.example.restful.models.VideoMetadata;
import com.example.restful.utils.ConverterDTO;
import com.example.restful.utils.DateUtil;
import com.example.restful.utils.GeneratorUUID;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.Chat;
import com.example.restful.models.Image;
import com.example.restful.models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/** Обязательное extra - Nickname:value. Название группы/аккаунта */
public class ChatActivity extends AppCompatActivity {

    private ChatRecycleAdapter chatRecycleAdapter;
    private RecyclerView recyclerView;

    private ImagesLoaderRecycleAdapter imagesLoaderRecycleAdapter;
    private RecyclerView imagesLoadedRecycle;

    private ActivityChatBinding activityChatBinding;

    private Chat currentChat;
    private List<Message> messages;
    private Integer CurrentMessages;

    private boolean newChat;
    private static boolean lock = false;
    /** Если отправляем запрос на обновление данных а информация не приходит или
     * приходит null то isSendUpdate блокирует повторные запросы*/
    private boolean isSendUpdate = false;
    private Account myAccount;

    //Image
    private List<MessageImage> FixedImages; //зафиксированные изображения перед отправкой сообщения
    private List<ImageView> FixedImageViews;

    //Video
    private VideoMetadata videoMetadata;
    private boolean isVideoLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activityChatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(activityChatBinding.getRoot());

        init();
    }

    private void init() {
        myAccount = APIManager.getManager().getMyAccount();
        FixedImages = new ArrayList<>();
        FixedImageViews = new ArrayList<>();

        activityChatBinding.NameChat.setText(getIntent().getStringExtra("Nickname"));
        findViewById(R.id.layout_video_loaded).setVisibility(GONE);

        if (APIManager.statusInfo.isChatsListGot() || APIManager.statusCacheInfo.isListChatsLoaded()) {
            activityChatBinding.progressBar.setVisibility(GONE);
            activityChatBinding.listMessages.setVisibility(VISIBLE);
        } else
            return;

        loadChat();
        initRecycle();
        SetListeners();

        //Подпись на наблюдение за новыми сообщениями
        APIManager.getManager().getListChats().observeForever(new Observer<List<Chat>>() {
            @Override
            public void onChanged(List<Chat> newsList) {
                chatRecycleAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        });

        //Установка изображений
        if (currentChat.getUsers().size() == 2) {
            ImageLoadCallback imageLoadCallback = new ImageLoadCallback() {
                @Override
                public void onImageLoaded(String decodeStr) {
                    Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(decodeStr);
                    activityChatBinding.iconChat.setImageBitmap(bitmap);
                }
            };

            Account account_with_chat = getСorrespondent();

            if (account_with_chat != null) {
                if (account_with_chat.getImageIcon() != null) {
                    imageLoadCallback.onImageLoaded(account_with_chat.getImageIcon().getImgEncode());
                } else
                    APIManager.getManager().getIconImageLazy(account_with_chat, imageLoadCallback);
            }
        }
    }

    /** Загрузка видео и изображения. */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri selectedMediaUri = data.getData();
            String mimeType = getContentResolver().getType(selectedMediaUri);

            if (mimeType != null) {
                if (mimeType.startsWith("image/")) {
                    System.err.println("IMAGE ");
                    handleImage(data);
                } else if (mimeType.startsWith("video/")) {
                    System.err.println("VIDEO ");
                    handleVideo(data);
                }
            }
        }

    }

    private void handleVideo(Intent data) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(data.getData());

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] temp = new byte[8192];
            int bytesRead;

            while ((bytesRead = inputStream.read(temp)) != -1)
                buffer.write(temp, 0, bytesRead);

            inputStream.close();
            byte[] videoData = buffer.toByteArray();
            String videoUUID = GeneratorUUID.getInstance().generateUUIDforVideo(videoData);

            findViewById(R.id.layout_video_loaded).setVisibility(VISIBLE);
            videoMetadata = new VideoMetadata(VideoMetadata.TypeVideoData.message_video, videoUUID, videoData);
            isVideoLoaded = true;

        } catch (Exception e) {
            findViewById(R.id.layout_video_loaded).setVisibility(GONE);
            Toast.makeText(this, "Ошибка загрузки видео.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleImage(Intent data) {
        Uri selectedImageUri = data.getData();

        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(selectedImageUri);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Ошибка загрузки изображения.", Toast.LENGTH_SHORT).show();
        }
        Drawable drawable = Drawable.createFromStream(inputStream, selectedImageUri.toString());

        String str = ImageUtil.getInstance().ConvertToString(drawable);
        Image image = new Image();
        image.setImgEncode(str);

        MessageImage messageImage = new MessageImage();
        messageImage.setImage(image);

        FixedImages.add(messageImage);

        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(drawable);
        FixedImageViews.add(imageView);

        imagesLoaderRecycleAdapter.notifyDataSetChanged();
        updateRecyclerViewVisibility();
    }

    private void SetListeners() {

        activityChatBinding.buttonBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });

        activityChatBinding.enterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = activityChatBinding.EditText;
                String content = editText.getText().toString();

                sendMessage(content);

                //Update Interface
                editText.setText("");
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                chatRecycleAdapter.notifyDataSetChanged();
            }
        });


        //image loader
        activityChatBinding.imageExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                String[] mimeTypes = {"image/*", "video/*"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(Intent.createChooser(intent, "Select Media"), 0);
            }
        });

        activityChatBinding.iconChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = getСorrespondent();

                if (account != null) {
                    Intent intent = new Intent(ChatActivity.this, ProfileActivity.class);
                    intent.putExtra("Nickname", account.getUsername());
                    startActivity(intent);
                }
            }
        });

        //Удаление загруженного видео
        activityChatBinding.buttonCloseVideoLoaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.layout_video_loaded).setVisibility(GONE);
                isVideoLoaded = false;
                videoMetadata = null;
            }
        });

    }

    private Account getСorrespondent() {
        Account account_with_chat = null;
        if (currentChat.getUsers().get(0).equals(myAccount.getId()))
            account_with_chat = APIManager.getManager().getListAccounts().stream()
                    .filter(x->x.getId().equals(currentChat.getUsers().get(1))).findAny().orElse(null);
        else
            account_with_chat = APIManager.getManager().getListAccounts().stream()
                    .filter(x->x.getId().equals(currentChat.getUsers().get(0))).findAny().orElse(null);

        return account_with_chat;
    }

    private void loadChat() {
        //loading chat
        String name = getIntent().getStringExtra("Nickname");
        if (name.isEmpty()) return;

        // имеем ли мы чат с этим пользователем
        List<Chat> chats = APIManager.getManager().getListChats().getValue();
        List<Account> accounts = APIManager.getManager().getListAccounts();

        //
        boolean isGroup = false;
        if (name.length() == 1)
            isGroup = true;

        if (isGroup) {
            Group group = APIManager.getManager().getListGroups().stream().filter(x->x.getName().equals(name)).findAny().orElse(null);

            if (group == null)
                return;

            Long chat_id = myAccount.getGroup().getChat_id();
            currentChat = chats.stream().filter(x->x.getId() == chat_id).findAny().orElse(null);

            if (currentChat == null)
                return;

            newChat = false;
            CurrentMessages = currentChat.getMessages().size();
            messages = currentChat.getMessages();

        } else {

            Account wr = null; //аккаунт собеседника
            for (Account account : accounts)
                if (account.getUsername().equals(name))
                    wr = account;

            if (wr != null)
                for (Chat chat : chats)
                    if (chat.getUsers().get(0) == wr.getId() ||
                            chat.getUsers().get(1) == wr.getId()) {
                        currentChat = chat;
                        break;
                    }

            //
            if (currentChat != null) {
                messages = currentChat.getMessages();
                newChat = false;
            } else {
                currentChat = new Chat();
                messages = currentChat.getMessages();

                List<Long> usersOfChat = new ArrayList<>();
                usersOfChat.add(myAccount.getId());
                usersOfChat.add(wr.getId());
                currentChat.setUsers(usersOfChat);
                newChat = true;
            }

            CurrentMessages = messages.size();
        }

    }

    private void initRecycle() {
        recyclerView = findViewById(R.id.list_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chatRecycleAdapter = new ChatRecycleAdapter(messages, this);
        recyclerView.setAdapter(chatRecycleAdapter);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(-1)) {
                    if (!lock && !isSendUpdate) {
                        APIManager.getManager().updateMessagesFeed(new StandardCallback() {
                            @Override
                            public void call() {
                                chatRecycleAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(11);
                                lock = false;
                                isSendUpdate = false;
                            }
                        }, Long.valueOf(currentChat.getId()));
                        isSendUpdate = true;
                    }
                    lock = true;
                }
            }
        });

        //Images List Recycle
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        imagesLoadedRecycle = findViewById(R.id.images_loaded_recycle);
        imagesLoaderRecycleAdapter = new ImagesLoaderRecycleAdapter(FixedImageViews, FixedImages, this);
        imagesLoadedRecycle.setAdapter(imagesLoaderRecycleAdapter);
        updateRecyclerViewVisibility();

        //обновление видимости при изменениях
        imagesLoaderRecycleAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                updateRecyclerViewVisibility();
            }
        });
    }

    private void updateRecyclerViewVisibility() {
        if (FixedImageViews.isEmpty()) {
            imagesLoadedRecycle.setVisibility(GONE);
        } else {
            imagesLoadedRecycle.setVisibility(VISIBLE);
        }
    }

    private void sendMessage(String content) {
        //Send message
        Message message = new Message();
        message.setText(content);
        message.setAuthor(myAccount.getId());
        message.setImages(new MutableLiveData<>());
        message.getImages().setValue(FixedImages);
        message.setDate(new Date(System.currentTimeMillis()));
        message.setCompleted(true);

        //Создание uuid
        Date date = message.getDate();
        FixedImages.forEach(x->x.setUuid(GeneratorUUID.getInstance().generateUUIDForMessage(
                DateUtil.getInstance().getDateToForm(date),
                myAccount.getUsername()
        )));

        //Игнорирование рекурсии
        Chat chatformessage = new Chat();
        chatformessage.setId(currentChat.getId());
        message.setChat(chatformessage);
        messages.add(message);

        //Настройка видео
        if (isVideoLoaded) {
            message.setThereVideo(true);
            message.setVideoMetadata(videoMetadata);
            message.setVideoUUID(videoMetadata.getUuid());
        }


        //Отправляем на сервер
        APIManager.getManager().sendMessage(message, newChat); 
        //Обновление в кэше
        APIManager.getManager().getListChats().setValue(APIManager.getManager().getListChats().getValue());

        //Clear
        newChat = false;
        FixedImages = new ArrayList<>();
        FixedImageViews.clear();
        message.setChat(currentChat);
        message.setCompleted(true);
        updateRecyclerViewVisibility();
    }


}
