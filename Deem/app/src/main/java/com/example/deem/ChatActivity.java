package com.example.deem;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.adapters.ChatRecycleAdapter;
import com.example.deem.databinding.ActivityChatBinding;
import com.example.restful.models.Group;
import com.example.restful.models.MessageImage;
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
        activityChatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(activityChatBinding.getRoot());

        init();

    }

    private void init() {

        FixedImages = new ArrayList<>();
        activityChatBinding.NameChat.setText(getIntent().getStringExtra("Nickname"));

        if (APIManager.getManager().statusInfo.isChatsListGot()) {
            activityChatBinding.progressBar.setVisibility(View.GONE);
            activityChatBinding.listMessages.setVisibility(View.VISIBLE);
        } else
            return;


        loadChat();
        initRecycle();
        SetListeners();

        //Хэндлер для обновления приходящих сообщений
        handler = new Handler();
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                // Обновить RecycleView
                //chatRecycleAdapter.notifyDataSetChanged(); //старый слабый метод
                if (messages.size() > CurrentMessages) { //TODO: протестить
                    for (int j = messages.size(); j < messages.size(); j++)
                        chatRecycleAdapter.notifyItemInserted(j);
                    CurrentMessages = messages.size();
                }
                // Запустить этот Runnable снова через некоторое время
                handler.postDelayed(this, 1000);
            }
        };
        updateRunnable.run();

        //image loader
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

                //Send message
                Message message = new Message();
                message.setText(content);
                message.setAuthor(APIManager.getManager().myAccount.getId());
                message.setImages(FixedImages);

                //Создание uuid
                FixedImages.forEach(x->x.setUuid(GeneratorUUID.getInstance().generateUUIDForMessage(
                         DateUtil.getInstance().getDateToForm(new Date(System.currentTimeMillis())),
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

                //Update Interface
                editText.setText("");
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                chatRecycleAdapter.notifyDataSetChanged();
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
        String name = getIntent().getStringExtra("Nickname");
        if (name.isEmpty()) return;

        // имеем ли мы чат с этим пользователем
        List<Chat> chats = APIManager.getManager().listChats;
        List<Account> accounts = APIManager.getManager().listAccounts;

        //
        boolean isGroup = false;
        if (name.length() == 1)
            isGroup = true;

        if (isGroup) {
            Optional<Group> group = APIManager.getManager().listGroups.stream().filter(x->x.getName().equals(name)).findAny();

            if (group.isEmpty())
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
