package com.example.deem.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.WebActivity;
import com.example.restful.utils.DateTranslator;
import com.example.restful.models.Event;

import java.util.List;


public class EventRecycleAdapter extends RecyclerView.Adapter<EventRecycleAdapter.ItemEvent> {

    private List<Event> eventList;
    private Fragment fragment;

    public EventRecycleAdapter(List<Event> newsList, Fragment fragment) {
        this.eventList = newsList;
        this.fragment = fragment;
    }
    public void setEventList(List<Event> eventList) { this.eventList = eventList;}

    @NonNull
    @Override
    public ItemEvent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);

        return new ItemEvent(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemEvent holder, int position) {
        holder.setData(eventList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class ItemEvent extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView content;
        private TextView header;

        private View itemView;

        public ItemEvent(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            content = itemView.findViewById(R.id.event_content_info);
            header  = itemView.findViewById(R.id.event_header_info);
            date    = itemView.findViewById(R.id.event_date);
        }

        public void setData(Event event) {
            header.setText(event.getNameExam());
            content.setText(event.getGeneralDescription());
            date.setText("Начало: " + DateTranslator.getInstance().toString(event.getStart_date()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(fragment.getActivity(), WebActivity.class);
                    intent.putExtra("EXAM_ID", event.getId());
                    fragment.getActivity().startActivity(intent);
                }
            });
        }
    }
}
