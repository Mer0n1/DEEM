package com.example.deem.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.CurriculumActivity;
import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.deem.fragments.InfoFragments.RatingGroupsFragment;
import com.example.restful.api.ServerStatusInfo;
import com.example.restful.utils.DateTranslator;
import com.example.deem.utils.Toolbar;
import com.example.deem.adapters.NewsListRecycleAdapter;
import com.example.restful.api.APIManager;
import com.example.restful.models.Event;
import com.example.restful.models.Group;
import com.example.restful.models.News;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirstPageFragment extends Fragment {

    private FrameLayout main_layout;
    private MainActivity activity;

    private NewsListRecycleAdapter recycleAdapterNews;
    private RecyclerView recyclerView;

    private List<News> adminNews;

    //fragments
    private RatingGroupsFragment ratingGroupsFragment;
    private ClubsFragment clubsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout)inflater.inflate(R.layout.fragment_first, container, false);
        activity = (MainActivity) getActivity();

        init();

        return main_layout;
    }

    public void init() {
        ratingGroupsFragment = new RatingGroupsFragment();
        clubsFragment = new ClubsFragment();

        initToolbar();
        initEvent();
        initListNews();
        initRecycle();
        initClickListeners();
    }

    private void initToolbar() {
        Toolbar.getInstance().reset();
        Toolbar.getInstance().setTitle("Основное меню");
    }

    private void initRecycle() {
        //recycle
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);

        recycleAdapterNews = new NewsListRecycleAdapter(adminNews, this);
        recyclerView = main_layout.findViewById(R.id.news_administrative);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleAdapterNews);
    }

    private void initEvent() {
        if (APIManager.getManager().statusInfo.isEventsListGot()) {
            List<Event> events = APIManager.getManager().listEvents.getValue();
            Event event = events.get(events.size()-1);

            Date date_exam = event.getStart_date();
            TextView text_name_exam = main_layout.findViewById(R.id.text_exam_first);
            TextView text_date_exam = main_layout.findViewById(R.id.text_date_first);
            text_date_exam.setText(DateTranslator.getInstance().toString(date_exam));
            text_name_exam.setText(event.getNameExam());
        }
    }

    private void initListNews() {
        if (APIManager.statusInfo.isGroupsListGot() && APIManager.getManager().statusInfo.isNewsListGot()) {
            adminNews = new ArrayList<>();
            List<News> news = APIManager.getManager().listNews.getValue();
            List<Group> adminGroups = APIManager.getManager().adminGroups;

            for (News news1 : news)
                for (Group group : adminGroups)
                    if (news1.getIdGroup().equals(group.getId()))
                        adminNews.add(news1);

            main_layout.findViewById(R.id.news_administrative).setVisibility(View.VISIBLE);
        }

    }

    private void initClickListeners() {
        //OnClick
        View.OnClickListener onClickOther = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == main_layout.findViewById(R.id.rat_button))
                    activity.OpenFragment(ratingGroupsFragment, R.id.fragment_main, false);

                if (v == main_layout.findViewById(R.id.curriculum_button)) {
                    Intent intent = new Intent(getActivity(), CurriculumActivity.class);
                    startActivity(intent);
                }

                if (v == main_layout.findViewById(R.id.club_menu))
                    activity.OpenFragment(clubsFragment, R.id.fragment_main, false);

            }
        };
        main_layout.findViewById(R.id.rat_button).setOnClickListener(onClickOther);
        main_layout.findViewById(R.id.curriculum_button).setOnClickListener(onClickOther);
        main_layout.findViewById(R.id.club_menu).setOnClickListener(onClickOther);
    }
}
