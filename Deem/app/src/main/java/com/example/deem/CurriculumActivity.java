package com.example.deem;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.adapters.CurriculumFullRecycleAdapter;
import com.example.deem.adapters.CurriculumRecycleAdapter;
import com.example.restful.api.APIManager;
import com.example.restful.models.curriculum.Class;
import com.example.restful.models.curriculum.DayliSchedule;
import com.example.restful.utils.DateTranslator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurriculumActivity extends AppCompatActivity {

    private CurriculumFullRecycleAdapter curriculumFullRecycleAdapter;
    private RecyclerView recyclerView;

    private List<DayliSchedule> dayliSchedules;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);

        init();
    }

    public void init() {
        if (APIManager.statusInfo.isTeacherListClassesGot())
            dayliSchedules = APIManager.getManager().getDayliSchedules();
        else
            return;


        curriculumFullRecycleAdapter = new CurriculumFullRecycleAdapter(dayliSchedules, this);
        recyclerView = findViewById(R.id.dayli_schedule_full);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(curriculumFullRecycleAdapter);

    }
}
