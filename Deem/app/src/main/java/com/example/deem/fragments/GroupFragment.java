package com.example.deem.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.ChatActivity;
import com.example.deem.utils.Toolbar;
import com.example.deem.dialogs.CreateNewsDialog;
import com.example.deem.R;
import com.example.deem.adapters.NewsListRecycleAdapter;
import com.example.deem.databinding.FragmentGroupBinding;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.Group;
import com.example.restful.models.News;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupFragment extends Fragment {

    private FrameLayout main_layout;
    private FragmentGroupBinding fragmentGroupBinding;

    private Group group;
    private List<News> newsList;

    private NewsListRecycleAdapter newsListRecycleAdapter;
    private RecyclerView recyclerView;

    private boolean itsMyGroup;

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

        if (!checkWorkingCondition())
            return;


        //Инициализация значений
        ((TextView)main_layout.findViewById(R.id.quality_users)).setText(
                String.valueOf(group.getAccounts().size()) + " участников");

        if (group.getName().equals(APIManager.getManager().myAccount.getGroup().getName())) {
            itsMyGroup = true;
            ((TextView) main_layout.findViewById(R.id.score)).setText(String.valueOf(CountAverageValue()));

            main_layout.findViewById(R.id.chat_button_group).setVisibility(View.VISIBLE);
            main_layout.findViewById(R.id.create_news).setVisibility(View.VISIBLE);
        } else { //если это чужая группа
            itsMyGroup = false;
            ((TextView) main_layout.findViewById(R.id.score)).setText("");

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
                    createNewsDialog.setNewsList(newsList);
                    createNewsDialog.show(getActivity().getSupportFragmentManager(), "creation_menu");
                    newsListRecycleAdapter.notifyDataSetChanged();
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
    }

    public void initToolbar() {
        Toolbar.getInstance().reset();
        Toolbar.getInstance().setTitle("Группы");
    }

    public void initNews() {
        List<News> allNews = APIManager.getManager().listNews;
        newsList = new ArrayList<>();

        if (allNews != null)
        for (News news : allNews)
            if (news.getIdGroup() == group.getId())
                newsList.add(news);
        Collections.reverse(newsList);
    }

    public boolean checkWorkingCondition() {
        if (!APIManager.getManager().statusInfo.isGroupsListGot()) {
            main_layout.findViewById(R.id.layout_progress_bar).setVisibility(View.VISIBLE);
            main_layout.findViewById(R.id.scroll_view).setVisibility(View.GONE);
            return false;
        } else {
            main_layout.findViewById(R.id.layout_progress_bar).setVisibility(View.GONE);
            main_layout.findViewById(R.id.scroll_view).setVisibility(View.VISIBLE);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String name = bundle.getString("name", null);

            if (name != null)
                group = APIManager.getManager().listGroups.stream().filter(
                        x->x.getName().equals(name)).findAny().orElse(null);
        }

        if (group == null) return false;
        return true;
    }

    private int CountAverageValue() {
        int score = 0;

        for (Account account : group.getAccounts())
            score += account.getScore();

        if (score != 0)
            score /= group.getAccounts().size();

        return score;
    }
}
