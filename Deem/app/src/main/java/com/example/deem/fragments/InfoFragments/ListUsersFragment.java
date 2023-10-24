package com.example.deem.fragments.InfoFragments;

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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.deem.adapters.GroupsListRecycleAdapter;
import com.example.deem.adapters.UsersListRecycleAdapter;
import com.example.deem.databinding.FragmentListGroupsBinding;
import com.example.deem.databinding.FragmentListUsersBinding;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.Group;

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
        System.out.println("List|Us " + getFragmentManager().getBackStackEntryCount());

        return main_layout;
    }

    private void init() {
        ((MainActivity)(getActivity())).getInfoFragment().includeButtonBack();
        users = APIManager.getManager().listAccounts;

        //Загрузим элементы layout_info_person
        /*LinearLayout layout = main_layout.findViewById(R.id.layout_list_users);
        List<Account> users = APIManager.getManager().getAccounts();
        users = APIManager.getManager().listAccounts;

        for (Account user : users) {
            Space space = new Space(getActivity());
            space.setMinimumHeight(100);

            layout.addView(space);
            LinearLayout layout1 = (LinearLayout) getLayoutInflater().inflate
                    (R.layout.item_person_info, layout);

            //init new layout
            layout1 = (LinearLayout) layout1.getChildAt(layout1.getChildCount()-1);
            ((TextView)(layout1.findViewById(R.id.name_info))).setText(user.getName());
            ((TextView)(layout1.findViewById(R.id.surname_info))).setText(user.getFathername() + " " + user.getSurname());
            ((TextView)(layout1.findViewById(R.id.course_info))).setText(String.valueOf(user.getGroup().getCourse())); //user.getGroup().getCourse()
            ((TextView)(layout1.findViewById(R.id.faculty_info))).setText(user.getGroup().getFaculty());
            ((TextView)(layout1.findViewById(R.id.group_own_info))).setText(user.getGroup().getName());
            ((TextView)(layout1.findViewById(R.id.typePerson_info))).setText("Студент");
        }*/


        //recycle
        usersListRecycleAdapter = new UsersListRecycleAdapter(APIManager.getManager().listAccounts, this);

        recyclerView = main_layout.findViewById(R.id.list_users_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(usersListRecycleAdapter);

        //search
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
    }

    public void sort(String text) {
        //название группы
        List<Account> sort = new ArrayList<>();
        for (Account user : users)
            if (user.getUsername().equals(text) ||
                    user.getUsername().compareTo(text) == 0 || text.equals(""))
                sort.add(user);

        usersListRecycleAdapter.setList(sort);
        usersListRecycleAdapter.notifyDataSetChanged();
    }



}
