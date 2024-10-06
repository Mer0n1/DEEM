package com.example.deem.fragments.InfoFragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.deem.adapters.GroupsListRecycleAdapter;
import com.example.deem.adapters.UsersListRecycleAdapter;
import com.example.deem.databinding.FragmentListGroupsBinding;
import com.example.deem.databinding.FragmentListUsersBinding;
import com.example.deem.utils.Toolbar;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.Group;
import com.example.restful.models.Message;

import java.util.ArrayList;
import java.util.List;



public class ListUsersFragment extends Fragment {
    private FrameLayout main_layout;
    private FragmentListUsersBinding binding;
    private List<Account> users;

    private UsersListRecycleAdapter usersListRecycleAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListUsersBinding.inflate(getLayoutInflater());
        main_layout = (FrameLayout) inflater.inflate(R.layout.fragment_list_users, container, false);

        init();

        return main_layout;
    }

    private void init() {
        Toolbar.getInstance().setTitle("Пользователи", 18);

        if (!APIManager.getManager().statusInfo.isAccountListGot())
            return;

        users = APIManager.getManager().getListAccounts();

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

        initRecycle();
    }

    public void sort(String text) {
        //название
        List<Account> sort = new ArrayList<>();
        for (Account user : users)
            if (user.getUsername().equals(text) ||
                    user.getUsername().compareTo(text) == 0 || text.equals(""))
                sort.add(user);

        usersListRecycleAdapter.setList(sort);
        usersListRecycleAdapter.notifyDataSetChanged();
    }

    public void initRecycle() {

        List<Account> listAccounts = APIManager.getManager().getListAccounts();

        if (listAccounts != null) {
            main_layout.findViewById(R.id.progressBar).setVisibility(View.GONE);
            main_layout.findViewById(R.id.list_users_info).setVisibility(View.VISIBLE);
        } else
            return;

        usersListRecycleAdapter = new UsersListRecycleAdapter(listAccounts, this);
        recyclerView = main_layout.findViewById(R.id.list_users_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(usersListRecycleAdapter);
    }


}
