package com.example.deem.fragments.InfoFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.deem.databinding.ActivityChatBinding;
import com.example.deem.databinding.FragmentListTopsUsersBinding;
import androidx.fragment.app.Fragment;

import com.example.deem.MainActivity;
import com.example.deem.R;

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

        ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, new String[]{"Рыжик", "Барсик", "Мурзик"});

        ((ListView)(main_layout.findViewById(R.id.list_users_top))).setAdapter(listAdapter);
        ((ListView)(main_layout.findViewById(R.id.list_groups_top))).setAdapter(listAdapter);
        ((ListView)(main_layout.findViewById(R.id.list_users_top_general))).setAdapter(listAdapter);
    }
}