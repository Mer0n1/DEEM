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

import com.example.deem.R;
import com.example.restful.api.APIManager;
import com.example.restful.models.Group;

import java.util.List;

public class ListGroupsFragment extends Fragment {
    private FrameLayout main_layout;

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


        //Загрузим элементы layout_info_person
        LinearLayout layout = main_layout.findViewById(R.id.layout_list_groups);
        List<Group> groups = APIManager.getManager().listGroups;

        for (Group group : groups) {
            Space space = new Space(getActivity());
            space.setMinimumHeight(100);

            layout.addView(space);
            LinearLayout layout1 = (LinearLayout) getLayoutInflater().inflate
                    (R.layout.layout_group, layout);

            //init new layout
            layout1 = (LinearLayout) layout1.getChildAt(layout1.getChildCount()-1);
            ((TextView)(layout1.findViewById(R.id.group_course_info))).setText(String.valueOf(group.getCourse()));
            ((TextView)(layout1.findViewById(R.id.group_institute_ingo))).setText(group.getFaculty());
            ((TextView)(layout1.findViewById(R.id.group_word_ingo))).setText(group.getName());
            //((TextView)(layout1.findViewById(R.id.group_hierarchy_ingo))).setText(group.getName());

        }
    }
}