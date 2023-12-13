package com.example.deem.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.ChatActivity;
import com.example.deem.ProfileActivity;
import com.example.deem.R;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.MessageImage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersListRecycleAdapter extends RecyclerView.Adapter<UsersListRecycleAdapter.User_Info> {

    private List<Account> list;
    private Fragment fragment;

    public UsersListRecycleAdapter(List<Account> list, Fragment fragment) {
        this.list = list;
        this.fragment = fragment;
    }
    public void setList(List<Account> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public User_Info onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person_info, parent, false);

        return new User_Info(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User_Info holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class User_Info extends RecyclerView.ViewHolder {
        private TextView name_info;
        private TextView surname_info;
        private TextView course_info;
        private TextView faculty_info;
        private TextView group_own_info;
        private TextView typePerson_info;
        private CircleImageView icon;

        private Account user;

        public User_Info(@NonNull View itemView) {
            super(itemView);

            name_info       = itemView.findViewById(R.id.name_info);
            surname_info    = itemView.findViewById(R.id.surname_info);
            course_info     = itemView.findViewById(R.id.course_info);
            faculty_info    = itemView.findViewById(R.id.faculty_info);
            group_own_info  = itemView.findViewById(R.id.group_own_info);
            typePerson_info = itemView.findViewById(R.id.typePerson_info);
            icon            = itemView.findViewById(R.id.profile_image);

            itemView.findViewById(R.id.OpenProfile_info).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user != null) {
                        Intent intent = new Intent(fragment.getActivity(), ProfileActivity.class);
                        intent.putExtra("Nickname", user.getUsername());
                        fragment.getActivity().startActivity(intent);
                    }
                }
            });

            itemView.findViewById(R.id.OpenMessengerOfUser_info).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user != null) {
                        Intent intent = new Intent(fragment.getActivity(), ChatActivity.class);
                        intent.putExtra("Nickname", user.getUsername());
                        fragment.getActivity().startActivity(intent);
                    }
                }
            });
        }

        public void setData(Account user) {
            this.user = user;

            name_info.setText(user.getName());
            surname_info.setText(user.getFathername() + " " + user.getSurname());
            course_info.setText(String.valueOf(user.getGroup().getCourse())); //user.getGroup().getCourse()
            faculty_info.setText(user.getGroup().getFaculty());
            group_own_info.setText(user.getGroup().getName());
            typePerson_info.setText("Студент");
            icon.setImageResource(R.drawable.icon_person_elon);

            //Загрузка изображения
            ImageLoadCallback imageLoadCallback = new ImageLoadCallback() {
                @Override
                public void onImageLoaded(String decodeStr) {
                    Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(decodeStr);
                    icon.setImageBitmap(bitmap);
                }
            };
            System.out.println("----- " + user.getImageIcon() + " " + user.getUsername());

            if (user.getImageIcon() != null) {
                imageLoadCallback.onImageLoaded(user.getImageIcon().getImgEncode());
            } else
                APIManager.getManager().getIconImageLazy(user, imageLoadCallback);


        }

    }
}
