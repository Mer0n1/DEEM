package com.example.deem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.deem.MainActivity;
import com.example.deem.R;

public class EventsFragment extends Fragment {
    private FrameLayout main_layout;
    private MainActivity this_activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout) inflater.inflate(R.layout.fragment_list_groups, container, false);
        this_activity = ((MainActivity) (getActivity()));

        init();

        return main_layout;
    }

    private void init() {

        //Создадим пару значков
        //ImageView imgListGroups = this_activity.loadIcon(layout_icons, size, R.drawable.icon_list_groups);

    }
}