package com.example.deem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.Toolbar;
import com.example.deem.dialogs.CreateNewsDialog;
import com.example.deem.R;
import com.example.deem.adapters.NewsListRecycleAdapter;
import com.example.deem.databinding.FragmentGroupBinding;
import com.example.restful.api.APIManager;
import com.example.restful.models.Group;
import com.example.restful.models.News;

import java.util.ArrayList;
import java.util.List;

public class GroupFragment extends Fragment {

    private FrameLayout main_layout;
    private FragmentGroupBinding fragmentGroupBinding;

    private Group myGroup;
    private List<News> newsList;

    private NewsListRecycleAdapter newsListRecycleAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentGroupBinding = FragmentGroupBinding.inflate(getActivity().getLayoutInflater());
        main_layout = (FrameLayout)inflater.inflate(R.layout.fragment_group, container, false);

        Toolbar.getInstance().setTitle("Группы");
        init();

        return main_layout;
    }

    public void init() {
        myGroup = APIManager.getManager().myAccount.getGroup();

        //init news
        List<News> allNews = APIManager.getManager().listNews;
        newsList = new ArrayList<>();

        for (News news : allNews)
            if (news.getIdGroup() == myGroup.getId())
                newsList.add(news);

        //init recycle
        newsListRecycleAdapter = new NewsListRecycleAdapter(newsList);
        recyclerView = main_layout.findViewById(R.id.list_news_group);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(newsListRecycleAdapter);

        setListeners();
    }

    public void setListeners() {
        main_layout.findViewById(R.id.create_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewsDialog createNewsDialog = new CreateNewsDialog();
                createNewsDialog.setNewsList(newsList);
                createNewsDialog.show(getActivity().getSupportFragmentManager(), "creation_menu");
                newsListRecycleAdapter.notifyDataSetChanged();
            }
        });


    }

}
