package com.example.deem.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.ChatActivity;
import com.example.deem.utils.Toolbar;
import com.example.deem.dialogs.CreateNewsDialog;
import com.example.deem.R;
import com.example.deem.adapters.NewsListRecycleAdapter;
import com.example.deem.databinding.FragmentGroupBinding;
import com.example.restful.api.APIManager;
import com.example.restful.models.Club;
import com.example.restful.models.Group;
import com.example.restful.models.News;
import com.example.restful.models.StandardCallback;

import java.util.ArrayList;
import java.util.List;

public class GroupFragment extends Fragment {

    private FrameLayout main_layout;
    private FragmentGroupBinding fragmentGroupBinding;

    private Group group;
    private List<News> newsList;

    private NewsListRecycleAdapter newsListRecycleAdapter;
    private RecyclerView recyclerView;

    private boolean itsMyGroup;

    private TextView GroupDescription;
    private TextView place;
    private TextView score;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentGroupBinding = FragmentGroupBinding.inflate(getActivity().getLayoutInflater());
        main_layout = (FrameLayout)inflater.inflate(R.layout.fragment_group, container, false);

        init();

        return main_layout;
    }

    public void init() {
        initToolbar();
        GroupDescription = main_layout.findViewById(R.id.GroupDescription);
        place = main_layout.findViewById(R.id.ier);
        score = main_layout.findViewById(R.id.score);
        GroupDescription.setText("");
        place.setText("");
        score.setText("");

        if (!checkWorkingCondition())
            return;


        //Инициализация значений
        ((TextView)main_layout.findViewById(R.id.quality_users)).setText(
                String.valueOf(group.getAccounts().size()) + " участников");
        GroupDescription.setText(group.getDescription());

        if (group.getDescription() == null || group.getDescription().isEmpty())
            GroupDescription.setVisibility(View.GONE);

        if (group.getName().equals(APIManager.getManager().getMyAccount().getGroup().getName())) {
            itsMyGroup = true;
            group.setRank(APIManager.getManager().getListGroups().indexOf(group) + 1);
            score.setText("Баллов: " + String.valueOf(group.getScore()));
            place.setText(String.valueOf("Рейтинг: " + group.getRank()));

            main_layout.findViewById(R.id.chat_button_group).setVisibility(View.VISIBLE);
            main_layout.findViewById(R.id.create_news).setVisibility(View.VISIBLE);
        } else { //если это чужая группа
            itsMyGroup = false;

            main_layout.findViewById(R.id.chat_button_group).setVisibility(View.GONE);
            main_layout.findViewById(R.id.create_news).setVisibility(View.GONE);
        }

        //Установим значок
        TextView icon = main_layout.findViewById(R.id.icon_group_main);
        icon.setText(group.getName());


        initNews();
        initRecycle();
        setListeners();
    }

    public void setListeners() {

        if (itsMyGroup) {
            main_layout.findViewById(R.id.create_news).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateNewsDialog createNewsDialog = new CreateNewsDialog();
                    createNewsDialog.initialize(newsList, newsListRecycleAdapter);
                    createNewsDialog.show(getActivity().getSupportFragmentManager(), "creation_menu");
                }
            });

            main_layout.findViewById(R.id.chat_button_group).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("Nickname", group.getName());
                    getActivity().startActivity(intent);
                }
            });
        }
    }

    public void initRecycle() {
        newsListRecycleAdapter = new NewsListRecycleAdapter(newsList, this);
        recyclerView = main_layout.findViewById(R.id.list_news_group);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(newsListRecycleAdapter);

        //Observer
        APIManager.getManager().getListNews().observe(getViewLifecycleOwner(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> newsList) {
                initNews();
                newsListRecycleAdapter.notifyDataSetChanged();
            }
        });
    }

    public void initToolbar() {
        Toolbar.getInstance().reset();
        Toolbar.getInstance().setTitle("Группы");
    }

    public void initNews() {
        List<News> allNews = APIManager.getManager().getListNews().getValue();
        if (newsList == null)
            newsList = new ArrayList<>();
        else
            newsList.clear();

        if (allNews != null)
            for (News news : allNews)
                if (news.getIdGroup() == group.getId())
                    newsList.add(news);
    }

    public boolean checkWorkingCondition() {
        if (!APIManager.statusInfo.isGroupsListGot()) {
            main_layout.findViewById(R.id.layout_progress_bar).setVisibility(View.VISIBLE);
            main_layout.findViewById(R.id.scroll_view).setVisibility(View.GONE);
            return false;
        } else {
            main_layout.findViewById(R.id.layout_progress_bar).setVisibility(View.GONE);
            main_layout.findViewById(R.id.scroll_view).setVisibility(View.VISIBLE);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String name = bundle.getString("name");

            if (name != null)
                if (bundle.getString("type") == null) {
                    group = APIManager.getManager().getListGroups().stream().filter(
                            x -> x.getName().equals(name)).findAny().orElse(null);
                } else if (bundle.getString("type").equals("club")) {
                    Club club = APIManager.getManager().getListClubs().getValue().stream().filter(
                            x -> x.getGroup().getName().equals(name)).findAny().orElse(null);

                    if (club != null)
                        group = club.getGroup();
                }
        }

        return group != null;
    }

}
