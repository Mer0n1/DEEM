package com.example.deem;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.adapters.ChatRecycleAdapter;
import com.example.deem.databinding.ActivityChatBinding;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.Chat;
import com.example.restful.models.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ChatRecycleAdapter chatRecycleAdapter;
    private RecyclerView recyclerView;

    private ActivityChatBinding activityChatBinding;

    private Chat currentChat;
    private List<Message> messages;

    private boolean newChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(activityChatBinding.getRoot());

        init();
    }

    private void init() {

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

        activityChatBinding.NameChat.setText(getIntent().getStringExtra("Nickname"));
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
                message.setType("send");

                Chat chatformessage = new Chat();
                chatformessage.setId(currentChat.getId());
                message.setChat(chatformessage);

                messages.add(message);
                chatRecycleAdapter.notifyDataSetChanged();

                System.out.println("--------- " + newChat);
                if (!newChat)
                    APIManager.getManager().sendMessage(message);
                else
                    APIManager.getManager().sendNewChat(currentChat);

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
        Long MyId = APIManager.getManager().myAccount.getId();

        for (Message message : messages) {
            if (MyId == message.getAuthor()) {
                message.setType("send");
            } else
                message.setType("receive");

        }
        //Возможно сортировка по времени
        //Date.before

    }

}
