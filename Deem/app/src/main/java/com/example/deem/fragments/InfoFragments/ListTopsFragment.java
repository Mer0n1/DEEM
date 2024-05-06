package com.example.deem.fragments.InfoFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.deem.utils.Toolbar;
import com.example.restful.api.APIManager;
import com.example.restful.models.Group;
import com.example.restful.models.TopLoadCallback;
import com.example.restful.models.TopsUsers;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class ListTopsFragment extends Fragment {

    private FrameLayout main_layout;
    private MainActivity this_activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout) inflater.inflate(R.layout.fragment_list_tops_users, container, false);
        this_activity = ((MainActivity) (getActivity()));

        init();

        return main_layout;
    }

    public void init() {
        Toolbar.getInstance().setTitle("Топы");

        TopLoadCallback topLoadCallback = new TopLoadCallback() {
            @Override
            public void LoadTop(TopsUsers topsUsers) {
                setAdapter(main_layout.findViewById(R.id.list_users_top_general), topsUsers.topsUsersUniversity);
            }
        };
        TopLoadCallback topLoadCallback2 = new TopLoadCallback() {
            @Override
            public void LoadTop(TopsUsers topsUsers) {
                setAdapter(main_layout.findViewById(R.id.list_users_top), topsUsers.topsUsersFaculty);
            }
        };

        if (!APIManager.statusInfo.isTopsListUsersFacultyGot())
            APIManager.getManager().getTopStudentsUniversity(topLoadCallback);
        if (!APIManager.statusInfo.isTopsListUsersFacultyGot())
        APIManager.getManager().getTopStudentsFaculty(topLoadCallback2);

        //установим топы групп
        if (APIManager.statusInfo.isGroupsListGot()) {
            List<Group> groups = APIManager.getManager().listGroups;

            List<String> names = new ArrayList<>();
            for (Group group : groups)
                if (group.getCourse() != 0)
                    names.add(group.getName());

            ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, names);

            ((ListView)(main_layout.findViewById(R.id.list_groups_top))).setAdapter(listAdapter);
        }

        TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeList(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
        TabLayout tabLayout = main_layout.findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(tabSelectedListener);

    }

    public void setAdapter(ListView listView, List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(main_layout.getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    public void changeList(int pos) {
        ListView users_general_top = main_layout.findViewById(R.id.list_users_top_general);
        ListView users_top = main_layout.findViewById(R.id.list_users_top);
        ListView groups_top = main_layout.findViewById(R.id.list_groups_top);

        users_general_top.setVisibility(View.GONE);
        users_top.setVisibility(View.GONE);
        groups_top.setVisibility(View.GONE);

        if (pos == 0)
            users_general_top.setVisibility(View.VISIBLE);
        if (pos == 1)
            users_top.setVisibility(View.VISIBLE);
        if (pos == 2)
            groups_top.setVisibility(View.VISIBLE);
    }

}