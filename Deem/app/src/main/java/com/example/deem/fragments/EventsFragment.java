package com.example.deem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.deem.utils.Toolbar;
import com.example.deem.adapters.EventRecycleAdapter;
import com.example.restful.api.APIManager;
import com.example.restful.models.Event;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EventsFragment extends Fragment {
    private FrameLayout main_layout;
    private MainActivity this_activity;

    private List<Event> AllEvents;
    private List<Event> pastEvents;
    private List<Event> currentEvents;

    private EventRecycleAdapter eventRecycleAdapter;
    private RecyclerView recyclerView;

    private boolean isStory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout) inflater.inflate(R.layout.fragment_events, container, false);
        this_activity = ((MainActivity) (getActivity()));

        init();

        return main_layout;
    }

    private void init() {
        if (!APIManager.statusInfo.isEventsListGot())
            return;

        AllEvents = APIManager.getManager().listEvents.getValue();
        Date currentDate = new Date(System.currentTimeMillis());
        pastEvents = AllEvents.stream().filter(x->x.getStart_date().before(currentDate)).collect(Collectors.toList());
        currentEvents = AllEvents.stream().filter(x->x.getStart_date().after(currentDate)).collect(Collectors.toList());
        isStory = false;
        initToolbar();

        if (!APIManager.statusInfo.isEventsListGot()) {
            main_layout.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            main_layout.findViewById(R.id.list_events).setVisibility(View.GONE);
            Toolbar.getInstance().ClearIcons();
            return;
        } else {
            main_layout.findViewById(R.id.progressBar).setVisibility(View.GONE);
            main_layout.findViewById(R.id.list_events).setVisibility(View.VISIBLE);
        }

        if (currentEvents.size() == 0)
            main_layout.findViewById(R.id.exams_not_found).setVisibility(View.VISIBLE);
        else
            main_layout.findViewById(R.id.exams_not_found).setVisibility(View.GONE);

        initRecycle();
    }

    public void initToolbar() {
        Toolbar.getInstance().reset();
        Toolbar.getInstance().setTitle("Текущие экзамены");

        ImageView history_events_img = Toolbar.getInstance().loadIcon(R.drawable.icon_story_events);

        history_events_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStory) {
                    eventRecycleAdapter.setEventList(AllEvents);
                    Toolbar.getInstance().setTitle("Текущие экзамены");
                    main_layout.findViewById(R.id.exams_not_found).setVisibility(View.VISIBLE);
                } else {
                    eventRecycleAdapter.setEventList(pastEvents);
                    Toolbar.getInstance().setTitle("История экзаменов");
                    main_layout.findViewById(R.id.exams_not_found).setVisibility(View.GONE);
                }
                eventRecycleAdapter.notifyDataSetChanged();
            }
        });
    }

    public void initRecycle() {
        //recycle
        recyclerView = main_layout.findViewById(R.id.list_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        eventRecycleAdapter = new EventRecycleAdapter(currentEvents, this);
        recyclerView.setAdapter(eventRecycleAdapter);
    }

}