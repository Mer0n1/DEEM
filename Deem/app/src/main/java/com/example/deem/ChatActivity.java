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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(activityChatBinding.getRoot());

        init(0);
    }

    private void init(int nChat) {

        //loading chat
        currentChat = APIManager.getManager().listChats.get(nChat);

        if (currentChat != null) {
            activityChatBinding.progressBar.setVisibility(View.GONE);
            activityChatBinding.listMessages.setVisibility(View.VISIBLE);

            messages = currentChat.getMessages();
            sort();
        }

        //init recycle
        recyclerView = findViewById(R.id.list_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chatRecycleAdapter = new ChatRecycleAdapter(messages);
        recyclerView.setAdapter(chatRecycleAdapter);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

        //
        SetListeners();
    }

    private void SetListeners() {

        activityChatBinding.enterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = activityChatBinding.EditText;
                String content = editText.getText().toString();
                messages.add(new Message(content, "send"));
                chatRecycleAdapter.notifyDataSetChanged();

                //Send message
                Message message = new Message();
                message.setText(content);
                message.setAuthor(APIManager.getManager().myAccount.getUsername());
                message.setChat(currentChat);
                //message.setDate(new Date(System.currentTimeMillis()));
                APIManager.getManager().sendMessage(message);

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

    public void sort() {
        String myName = APIManager.getManager().myAccount.getUsername();

        for (Message message : messages) {
            if (myName.equals(message.getAuthor())) {
                message.setType("send");
            } else
                message.setType("receive");

        }
        //Возможно сортировка по времени
        //Date.before

    }

}
