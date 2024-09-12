package com.example.deem;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.adapters.ChatRecycleAdapter;
import com.example.deem.databinding.ActivityChatBinding;
import com.example.restful.models.Group;
import com.example.restful.models.MessageImage;
import com.example.restful.models.News;
import com.example.restful.utils.DateUtil;
import com.example.restful.utils.GeneratorUUID;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.Chat;
import com.example.restful.models.Image;
import com.example.restful.models.Message;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/** Обязательное extra - Nickname:value. Название группы/аккаунта */
public class ChatActivity extends AppCompatActivity {

    private ChatRecycleAdapter chatRecycleAdapter;
    private RecyclerView recyclerView;

    private ActivityChatBinding activityChatBinding;

    private Chat currentChat;
    private List<Message> messages;
    private Integer CurrentMessages;

    private boolean newChat;

    private Handler handler;
    private Runnable updateRunnable;

    private List<MessageImage> FixedImages; //зафиксированные изображения перед отправкой сообщения

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activityChatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(activityChatBinding.getRoot());

        init();

    }

    private void init() {

        FixedImages = new ArrayList<>();
        activityChatBinding.NameChat.setText(getIntent().getStringExtra("Nickname"));

        activityChatBinding.buttonBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });

        if (APIManager.statusInfo.isChatsListGot() || APIManager.statusCacheInfo.isListChatsLoaded()) {
            activityChatBinding.progressBar.setVisibility(View.GONE);
            activityChatBinding.listMessages.setVisibility(View.VISIBLE);

        } else
            return;


        loadChat();
        initRecycle();
        SetListeners();

        //Подпись на наблюдение за новыми сообщениями
        APIManager.getManager().listChats.observe(this, new Observer<List<Chat>>() {
            @Override
            public void onChanged(List<Chat> newsList) {
                chatRecycleAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
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

            String str = ImageUtil.getInstance().ConvertToString(drawable);
            Image image = new Image();
            image.setImgEncode(str);

            MessageImage messageImage = new MessageImage();
            messageImage.setImage(image);

            FixedImages.add(messageImage);
        }
    }


    private void SetListeners() {

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
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void loadChat() {
        //loading chat
        String name = getIntent().getStringExtra("Nickname");
        if (name.isEmpty()) return;

        // имеем ли мы чат с этим пользователем
        List<Chat> chats = APIManager.getManager().listChats.getValue();
        List<Account> accounts = APIManager.getManager().listAccounts;

        //
        boolean isGroup = false;
        if (name.length() == 1)
            isGroup = true;

        if (isGroup) {
            Group group = APIManager.getManager().listGroups.stream().filter(x->x.getName().equals(name)).findAny().orElse(null);

            if (group == null)
                return;

            Long chat_id = APIManager.getManager().myAccount.getGroup().getChat_id();
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
                usersOfChat.add(APIManager.getManager().myAccount.getId());
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
    }

    private void sendMessage(String content) {
        //Send message
        Message message = new Message();
        message.setText(content);
        message.setAuthor(APIManager.getManager().myAccount.getId());
        message.getImages().setValue(FixedImages);
        message.setDate(new Date(System.currentTimeMillis()));

        //Создание uuid
        Date date = message.getDate();
        FixedImages.forEach(x->x.setUuid(GeneratorUUID.getInstance().generateUUIDForMessage(
                DateUtil.getInstance().getDateToForm(date),
                APIManager.getManager().myAccount.getUsername()
        )));

        //Игнорирование рекурсии
        Chat chatformessage = new Chat();
        chatformessage.setId(currentChat.getId());
        message.setChat(chatformessage);

        messages.add(message);

        if (!newChat)
            APIManager.getManager().sendMessage(message);
        else
            APIManager.getManager().sendNewChat(currentChat);

        message.setChat(currentChat);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null)
            handler.removeCallbacks(updateRunnable);
    }

}
