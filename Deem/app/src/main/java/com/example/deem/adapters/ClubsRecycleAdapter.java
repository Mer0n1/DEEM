package com.example.deem.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.restful.models.Club;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClubsRecycleAdapter extends RecyclerView.Adapter<ClubsRecycleAdapter.ClubViewHolder> {

    private List<Club> clubs;
    private Fragment fragment;

    public ClubsRecycleAdapter(List<Club> clubs, Fragment fragment) {
        this.clubs = clubs;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClubViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_club_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {
        holder.setData(clubs.get(position));
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    class ClubViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView description;
        private TextView leader;
        private ConstraintLayout club_window;
        private CircleImageView icon;

        public ClubViewHolder(@NonNull View itemView) {
            super(itemView);

            name        = itemView.findViewById(R.id.club_name_info);
            description = itemView.findViewById(R.id.club_description_info);
            leader      = itemView.findViewById(R.id.club_lead_info);
            icon        = itemView.findViewById(R.id.icon_club_info);
            club_window = itemView.findViewById(R.id.club_window);
        }

        public void setData(Club club) {
            if (club.getLeader() == null || club.getGroup() == null)
                return;

            name.setText(club.getName());
            leader.setText("Глава: " + club.getLeader().getUsername());

            if (club.getGroup().getDescription() != null) {
                description.setText(club.getGroup().getDescription());
                description.setVisibility(View.GONE);
            } else
                description.setVisibility(View.GONE);


            club_window.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", club.getGroup().getName());
                    bundle.putString("type", "club");

                    MainActivity mainActivity = (MainActivity) fragment.getActivity();
                    mainActivity.OpenMenu(MainActivity.FragmentType.group, bundle);
                }
            });
        }

    }
}
