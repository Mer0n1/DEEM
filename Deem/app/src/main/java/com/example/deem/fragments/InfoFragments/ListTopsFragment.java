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

import java.util.Collections;
import java.util.List;

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

        /*if (APIManager.statusInfo.isGroupsListGot()) {
            List<Group> groups = APIManager.getManager().listGroups;

            String[] names = new String[groups.size()];
            for (int j = 0; j < groups.size(); j++)
                names[j] = groups.get(j).getName();

            ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, names);

            ((ListView)(main_layout.findViewById(R.id.list_groups_top))).setAdapter(listAdapter);
        }*/


        //((ListView)(main_layout.findViewById(R.id.list_users_top))).setAdapter(listAdapter);
        //((ListView)(main_layout.findViewById(R.id.list_users_top_general))).setAdapter(listAdapter);
    }
}