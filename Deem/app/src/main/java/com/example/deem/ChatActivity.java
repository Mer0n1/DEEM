package com.example.deem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.adapters.ChatRecycleAdapter;
import com.example.deem.databinding.ActivityChatBinding;
import com.example.deem.utils.GeneratorUUID;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.Chat;
import com.example.restful.models.DataImage;
import com.example.restful.models.Image;
import com.example.restful.models.Message;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ChatRecycleAdapter chatRecycleAdapter;
    private RecyclerView recyclerView;

    private ActivityChatBinding activityChatBinding;

    private Chat currentChat;
    private List<Message> messages;

    private boolean newChat;

    private Handler handler;
    private Runnable updateRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(activityChatBinding.getRoot());

        init();

    }

    private void init() {

        activityChatBinding.NameChat.setText(getIntent().getStringExtra("Nickname"));

        if (APIManager.getManager().listChats != null) {
            activityChatBinding.progressBar.setVisibility(View.GONE);
            activityChatBinding.listMessages.setVisibility(View.VISIBLE);
        } else
            return;


        loadChat();

        //init recycle
        recyclerView = findViewById(R.id.list_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chatRecycleAdapter = new ChatRecycleAdapter(messages);
        recyclerView.setAdapter(chatRecycleAdapter);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

        //
        SetListeners();

        //
        handler = new Handler();
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                // Обновить RecycleView
                chatRecycleAdapter.notifyDataSetChanged();
                // Запустить этот Runnable снова через некоторое время
                handler.postDelayed(this, 1000);
            }
        };
        updateRunnable.run();

        //
        activityChatBinding.imageExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(selectedImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Drawable drawable = Drawable.createFromStream(inputStream, selectedImageUri.toString());

            //ImageView view = activityChatBinding.imageView;
            //view.setBackground(drawable);


            //Отправка изображения на сервер
            DataImage dataImage = new DataImage();
            dataImage.setType("message_image");
            dataImage.setName("Test");
            dataImage.setUuid(GeneratorUUID.getInstance().generateUUIDForMessage(
                    ImageUtil.getInstance().getDate(), APIManager.getManager().myAccount.getUsername()));

            //преобразование в строку
            String str = ImageUtil.getInstance().ConvertToString(drawable);
            /*Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] ImgEncode = stream.toByteArray();
            String str = Base64.getEncoder().encodeToString(ImgEncode);*/
            //-----

            Image image = new Image();
            image.setImgEncode(str);
            dataImage.setImage(image);

            List<DataImage> imgs = new ArrayList<>();
            imgs.add(dataImage);

            APIManager.getManager().addImages(imgs);
        }
    }


    private void SetListeners() {

        activityChatBinding.enterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = activityChatBinding.EditText;
                String content = editText.getText().toString();

                //Send message
                Message message = new Message();
                message.setText(content);
                message.setAuthor(APIManager.getManager().myAccount.getId());

                Chat chatformessage = new Chat();
                chatformessage.setId(currentChat.getId());
                message.setChat(chatformessage);

                messages.add(message);
                chatRecycleAdapter.notifyDataSetChanged();

                if (!newChat)
                    APIManager.getManager().sendMessage(message);
                else
                    APIManager.getManager().sendNewChat(currentChat);

                message.setChat(currentChat)
                ;
                //Update Interface
                editText.setText("");
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        });


        activityChatBinding.buttonBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadChat() {
        //loading chat
        String nicknamePerson = getIntent().getStringExtra("Nickname");
        if (nicknamePerson.isEmpty()) return;

        // имеем ли мы чат с этим пользователем
        List<Chat> chats = APIManager.getManager().listChats;
        List<Account> accounts = APIManager.getManager().listAccounts;

        Account wr = null; //аккаунт собеседника
        for (Account account : accounts)
            if (account.getUsername().equals(nicknamePerson))
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
            sort();
        } else {
            currentChat = new Chat();
            messages = currentChat.getMessages();

            List<Long> usersOfChat = new ArrayList<>();
            usersOfChat.add(APIManager.getManager().myAccount.getId());
            usersOfChat.add(wr.getId());
            currentChat.setUsers(usersOfChat);
            newChat = true;
        }
    }

    public void sort() {
        //Возможно сортировка по времени
        //Date.before

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateRunnable);
    }

}
