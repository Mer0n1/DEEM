package com.example.deem.fragments.InfoFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.deem.layer_server.DataCash;
import com.example.restful.models.Account;

import java.util.List;


public class ListUsersFragment extends Fragment {
    private FrameLayout main_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout) inflater.inflate(R.layout.fragment_list_users, container, false);

        init();
        System.out.println("List|Us " + getFragmentManager().getBackStackEntryCount());

        return main_layout;
    }

    private void init() {
        ((MainActivity)(getActivity())).getInfoFragment().includeButtonBack();

        //Загрузим элементы layout_info_person
        LinearLayout layout = main_layout.findViewById(R.id.layout_list_users);
        List<Account> users = DataCash.getAccounts();

        for (Account user : users) {
            Space space = new Space(getActivity());
            space.setMinimumHeight(100);

            layout.addView(space);
            LinearLayout layout1 = (LinearLayout) getLayoutInflater().inflate
                    (R.layout.layout_info_person, layout);

            //init new layout
            layout1 = (LinearLayout) layout1.getChildAt(layout1.getChildCount()-1);
            ((TextView)(layout1.findViewById(R.id.name_info))).setText(user.getName());
            ((TextView)(layout1.findViewById(R.id.surname_info))).setText(user.getFathername() + " " + user.getSurname());
            ((TextView)(layout1.findViewById(R.id.course_info))).setText(String.valueOf(user.getGroup().getCourse())); //user.getGroup().getCourse()
            ((TextView)(layout1.findViewById(R.id.faculty_info))).setText(user.getGroup().getFaculty());
            ((TextView)(layout1.findViewById(R.id.group_own_info))).setText(user.getGroup().getName());
            ((TextView)(layout1.findViewById(R.id.typePerson_info))).setText("Студент");
        }
    }



}
