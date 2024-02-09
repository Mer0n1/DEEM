package com.example.deem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.CurriculumActivity;
import com.example.deem.R;
import com.example.restful.models.curriculum.Class;
import com.example.restful.models.curriculum.DayliSchedule;
import com.example.restful.utils.DateTranslator;

import java.util.Date;
import java.util.List;

public class CurriculumFullRecycleAdapter extends RecyclerView.Adapter<CurriculumFullRecycleAdapter.ItemEvent> {

    private List<DayliSchedule> dayliSchedules;
    private CurriculumActivity activity;

    public CurriculumFullRecycleAdapter(List<DayliSchedule> dayliSchedules, CurriculumActivity activity) {
        this.dayliSchedules = dayliSchedules;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CurriculumFullRecycleAdapter.ItemEvent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dayli_schedule, parent, false);

        return new CurriculumFullRecycleAdapter.ItemEvent(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurriculumFullRecycleAdapter.ItemEvent holder, int position) {
        holder.setData(dayliSchedules.get(position));
    }

    @Override
    public int getItemCount() {
        return dayliSchedules.size();
    }

    class ItemEvent extends RecyclerView.ViewHolder {

        private TextView dch_data_info;
        private TextView dch_day_info;
        private RecyclerView recyclerView;

        public ItemEvent(@NonNull View itemView) {
            super(itemView);

            dch_data_info  = itemView.findViewById(R.id.dch_data_info);
            dch_day_info = itemView.findViewById(R.id.dch_day_info);
            recyclerView = itemView.findViewById(R.id.dayli_schedule);
        }

        public void setData(DayliSchedule dl) {
            dch_data_info.setText(dl.getDate().getDate() + " " + DateTranslator.getInstance().IntToStringMonth(dl.getDate().getMonth()));
            dch_day_info.setText(DateTranslator.getInstance().IntToStringDay(dl.getDate().getDay()));

            initRecycle(dl.classes);
        }

        private void initRecycle(List<Class> classes) {
            CurriculumRecycleAdapter curriculumRecycleAdapter = new CurriculumRecycleAdapter(classes);

            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(curriculumRecycleAdapter);
            recyclerView.scrollToPosition(1);
        }
    }
}
