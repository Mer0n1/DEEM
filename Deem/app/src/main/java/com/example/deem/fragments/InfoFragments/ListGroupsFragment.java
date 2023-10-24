package com.example.deem.fragments.InfoFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.adapters.GroupsListRecycleAdapter;
import com.example.deem.databinding.FragmentListGroupsBinding;
import com.example.restful.api.APIManager;
import com.example.restful.models.Group;

import java.util.ArrayList;
import java.util.List;

public class ListGroupsFragment extends Fragment {
    private FrameLayout main_layout;
    private FragmentListGroupsBinding binding;
    private List<Group> groups;

    private GroupsListRecycleAdapter recycleAdapterGroups;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListGroupsBinding.inflate(getLayoutInflater());
        main_layout = (FrameLayout) inflater.inflate(R.layout.fragment_list_groups, container, false);

        init();

        return main_layout;
    }

    public void init() {
        //Загрузим элементы layout_info_person
        groups = APIManager.getManager().listGroups;

        //search
        binding.iconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editTextSearch.callOnClick();
            }
        });

        EditText editText = main_layout.findViewById(R.id.editText_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {sort(s.toString());}
        });

        //test recycle
        recycleAdapterGroups = new GroupsListRecycleAdapter(APIManager.getManager().listGroups, this);

        recyclerView = main_layout.findViewById(R.id.list_groups_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(recycleAdapterGroups);

    }

    public void sort(String text) {
        //название группы
        List<Group> sort = new ArrayList<>();
        for (Group group : groups)
            if (group.getName().equals(text) ||
                group.getName().compareTo(text) == 0 || text.equals(""))
                sort.add(group);

        recycleAdapterGroups.setList(sort);
        recycleAdapterGroups.notifyDataSetChanged();
    }

}