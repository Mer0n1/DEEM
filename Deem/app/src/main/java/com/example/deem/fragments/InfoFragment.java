package com.example.deem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.deem.Toolbar;
import com.example.deem.adapters.ChatRecycleAdapter;
import com.example.deem.adapters.NewsListRecycleAdapter;
import com.example.deem.fragments.InfoFragments.ListGroupsFragment;
import com.example.deem.fragments.InfoFragments.ListTopsFragment;
import com.example.deem.fragments.InfoFragments.ListUsersFragment;
import com.example.restful.api.APIManager;
import com.example.restful.models.News;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Фрагмент информации: новости групп, список топов (людей и групп), список участников
 */
public class InfoFragment extends Fragment {

    private FrameLayout main_layout;
    private MainActivity this_activity;

    private ListUsersFragment listUsersFragment;
    private ListGroupsFragment listGroupsFragment;
    private ListTopsFragment listTopsFragment;

    private NewsListRecycleAdapter newsListRecycleAdapter;
    private RecyclerView recyclerView;

    private List<News> listNews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout)inflater.inflate(R.layout.fragment_news, container, false);
        this_activity = ((MainActivity)(getActivity()));

        Toolbar.getInstance().ClearIcons();
        init();

        return main_layout;
    }

    public void init() {
        //init objects
        listNews = APIManager.getManager().listNews;
        listUsersFragment = new ListUsersFragment();
        listGroupsFragment = new ListGroupsFragment();
        listTopsFragment = new ListTopsFragment();

        //--Добавление иконок
        ImageView imgListTops   = Toolbar.getInstance().loadIcon(R.drawable.icon_tops);
        ImageView imgListGroups = Toolbar.getInstance().loadIcon(R.drawable.icon_list_groups);
        ImageView imgListUsers  = Toolbar.getInstance().loadIcon(R.drawable.icon_list_users);

        //--Изменение титла
        Toolbar.getInstance().setTitle("Новости");

        //Recycle
        recyclerView = main_layout.findViewById(R.id.news_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        newsListRecycleAdapter = new NewsListRecycleAdapter(listNews);
        recyclerView.setAdapter(newsListRecycleAdapter);

        //Листенеры
        View.OnClickListener onClickListPersons = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this_activity.OpenFragment(listUsersFragment, R.id.fragment_main, true);
            }
        };

        View.OnClickListener onClickListGroups = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this_activity.OpenFragment(listGroupsFragment, R.id.fragment_main, true);
            }
        };

        View.OnClickListener onClickTop = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this_activity.OpenFragment(listTopsFragment, R.id.fragment_main, true);
            }
        };

        imgListUsers.setOnClickListener(onClickListPersons);
        imgListGroups.setOnClickListener(onClickListGroups);
        imgListTops.setOnClickListener(onClickTop);
    }


    public void includeButtonBack() {
        //listener
        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this_activity.OpenFragment(InfoFragment.this, R.id.fragment_main, true);
                Toolbar.getInstance().TurnOffButtonBack();
            }
        };
        Toolbar.getInstance().includeButtonBack().setOnClickListener(onClick);
    }

}
