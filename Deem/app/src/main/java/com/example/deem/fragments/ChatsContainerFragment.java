package com.example.deem.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.deem.ChatActivity;
import com.example.deem.R;

public class ChatsContainerFragment extends Fragment {
    private FrameLayout main_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout) inflater.inflate(R.layout.fragment_messenger, container, false);

        init();

        return main_layout;
    }

    public void init() {


        //
        LinearLayout layout = main_layout.findViewById(R.id.container_chats);

        System.out.println("Count: " + layout.getChildCount());

        View.OnClickListener onClickChat = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        };

        for (int j = 0; j < layout.getChildCount(); j++)
            layout.getChildAt(j).setOnClickListener(onClickChat);
    }
}
