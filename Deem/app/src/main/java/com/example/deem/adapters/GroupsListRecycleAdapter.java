package com.example.deem.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.restful.api.APIManager;
import com.example.restful.models.Group;

import java.util.List;

public class GroupsListRecycleAdapter extends RecyclerView.Adapter<GroupsListRecycleAdapter.Group_info> {

    private List<Group> list;
    private Fragment fragment;

    public GroupsListRecycleAdapter(List<Group> list, Fragment fragment) {
        this.list = list;
        this.fragment = fragment;
    }

    public void setList(List<Group> list) { this.list = list; }

    @NonNull
    @Override
    public Group_info onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_info, parent, false);

        return new Group_info(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Group_info holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class Group_info extends RecyclerView.ViewHolder {
        private TextView course;
        private TextView institute;
        private TextView group_name;
        private TextView icon;

        public Group_info(@NonNull View itemView) {
            super(itemView);

            course     = itemView.findViewById(R.id.group_course_info);
            institute  = itemView.findViewById(R.id.group_institute_ingo);
            group_name = itemView.findViewById(R.id.group_word_ingo);
            icon       = itemView.findViewById(R.id.icon_group_main);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", group_name.getText().toString());

                    MainActivity mainActivity = (MainActivity) fragment.getActivity();
                    mainActivity.OpenMenu(MainActivity.FragmentType.group, bundle);
                }
            });
        }

        public void setData(Group group) {
            course.setText(String.valueOf(group.getCourse()));
            institute.setText(group.getFaculty());
            group_name.setText(group.getName());

            icon.setText(group.getName());
        }

    }
}
