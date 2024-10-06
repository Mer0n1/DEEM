package com.example.deem.fragments.InfoFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.adapters.GroupsListRecycleAdapter;
import com.example.deem.utils.Toolbar;
import com.example.restful.api.APIManager;
import com.example.restful.models.Group;

import java.util.List;

public class RatingGroupsFragment extends Fragment {
    private FrameLayout main_layout;
    private List<Group> groups;

    private GroupsListRecycleAdapter recycleAdapterGroups;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout) inflater.inflate(R.layout.fragment_list_groups, container, false);

        init();

        return main_layout;
    }


    public void init() {
        Toolbar.getInstance().setTitle("Группы");
        main_layout.findViewById(R.id.search_bar).setVisibility(View.GONE);

        if (!APIManager.getManager().statusInfo.isGroupsListGot())
            return;

        //Загрузим элементы layout_info_person
        groups = APIManager.getManager().getListGroupsOfFaculty();

        initRecycle();
    }


    public void initRecycle() {
        if (groups != null) {
            main_layout.findViewById(R.id.progressBar).setVisibility(View.GONE);
            main_layout.findViewById(R.id.list_groups_info).setVisibility(View.VISIBLE);
        } else
            return;

        recycleAdapterGroups = new GroupsListRecycleAdapter(groups, this);
        recyclerView = main_layout.findViewById(R.id.list_groups_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(recycleAdapterGroups);
    }
}
