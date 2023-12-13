package com.example.deem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.utils.Toolbar;
import com.example.deem.adapters.NewsListRecycleAdapter;
import com.example.restful.api.APIManager;
import com.example.restful.models.News;

import java.util.List;

public class FirstPageFragment extends Fragment {

    private FrameLayout main_layout;

    private NewsListRecycleAdapter recycleAdapterNews;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout)inflater.inflate(R.layout.fragment_first, container, false);

        init();

        return main_layout;
    }

    public void init() {
        initToolbar();

        if (!APIManager.getManager().statusInfo.isNewsListGot())
            return;

        //...event
        List<News> listNews = APIManager.getManager().listNews;
        //возьмем только новости из особой группы

        if (listNews != null) {
            //main_layout.findViewById(R.id.progressBar).setVisibility(View.GONE);
            main_layout.findViewById(R.id.news_administrative).setVisibility(View.VISIBLE);
        } else
            return;

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);

        recycleAdapterNews = new NewsListRecycleAdapter(listNews, this);
        recyclerView = main_layout.findViewById(R.id.news_administrative);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleAdapterNews);
    }

    public void initToolbar() {
        Toolbar.getInstance().setTitle("Основное меню");
        Toolbar.getInstance().ClearIcons();
    }
}
