package com.example.deem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.deem.adapters.ClubsRecycleAdapter;
import com.example.deem.adapters.GroupsListRecycleAdapter;
import com.example.deem.utils.Toolbar;
import com.example.restful.api.APIManager;
import com.example.restful.models.Club;

import java.util.List;

public class ClubsFragment extends Fragment {

    private FrameLayout main_layout;

    private ClubsRecycleAdapter recycleAdapterClubs;
    private RecyclerView recyclerView;

    private List<Club> clubs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout) inflater.inflate(R.layout.fragment_recycle_menu, container, false);

        init();

        return main_layout;
    }

    private void init() {

        Toolbar.getInstance().setTitle("Клубы", 18);

        if (APIManager.statusInfo.isClubListGot()) {
            clubs = APIManager.getManager().listClubs.getValue();
            main_layout.findViewById(R.id.clubs_recycle).setVisibility(View.VISIBLE);

            recycleAdapterClubs = new ClubsRecycleAdapter(clubs, this);
            recyclerView = main_layout.findViewById(R.id.clubs_recycle);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            recyclerView.setAdapter(recycleAdapterClubs);
        }

    }

}
