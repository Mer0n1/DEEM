package com.example.deem;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.adapters.ChatRecycleAdapter;
import com.example.deem.databinding.ActivityChatBinding;
import com.example.restful.models.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ChatRecycleAdapter chatRecycleAdapter;
    private RecyclerView recyclerView;

    private ActivityChatBinding activityChatBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(activityChatBinding.getRoot());

        activityChatBinding.progressBar.setVisibility(View.GONE);
        activityChatBinding.listMessages.setVisibility(View.VISIBLE);

        List<Message> list = new ArrayList<>();
        list.add(new Message("Привет, как ты", "receive"));
        list.add(new Message("Привет, как ты", "send"));
        list.add(new Message("2. 29 мая 10:00зачёт у Шевцовой" +
                "3. 4 июня в 10:00, консультация у Курышкина" +
                "4. 5 июня в 14:00 экзамен по теории машин и механизмов" +
                "" +
                "5. Вопросы к экзамену" +
                "5.2.2 Оценочные средства при промежуточной аттестации" +
                "Оценочными средствами при промежуточной аттестации являются контрольные вопросы к\n" +
                "экзамену." +
                "1. Классификация механизмов." +
                "2. Классификация кинематических пар." +
                "3. Число степеней свободы механизма. Структурная формула механизма.", "send"));
        list.add(new Message("2. 29 мая 10:00зачёт у Шевцовой" +
                "3. 4 июня в 10:00, консультация у Курышкина" +
                "4. 5 июня в 14:00 экзамен по теории машин и механизмов" +
                "" +
                "5. Вопросы к экзамену" +
                "5.2.2 Оценочные средства при промежуточной аттестации" +
                "Оценочными средствами при промежуточной аттестации являются контрольные вопросы к" +
                "экзамену." +
                "1. Классификация механизмов." +
                "2. Классификация кинематических пар" +
                "3. Число степеней свободы механизма. Структурная формула механизма.", "receive"));


        recyclerView = findViewById(R.id.list_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chatRecycleAdapter = new ChatRecycleAdapter(list);
        recyclerView.setAdapter(chatRecycleAdapter);

    }


}
