package com.example.deem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.utils.Toolbar;
import com.example.deem.adapters.ContainerChatsRecycleAdapter;
import com.example.restful.api.APIManager;
import com.example.restful.models.Chat;

import java.util.List;

public class ChatsContainerFragment extends Fragment {
    private FrameLayout main_layout;
    private List<Chat> listChats;

    private ContainerChatsRecycleAdapter containerChatsRecycleAdapter;
    private RecyclerView recyclerView;


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
        initToolbar();

        if (!APIManager.statusInfo.isChatsListGot() &&
            !APIManager.statusCacheInfo.isListChatsLoaded())
            return;

        listChats = APIManager.getManager().listChats.getValue();
        initListAndRecycle();

        //View.inflate(getContext(), R.layout.item_chat_story, layout);
    }

    public void initToolbar() {
        Toolbar.getInstance().reset();
        Toolbar.getInstance().setTitle("Чаты");
    }

    public void initListAndRecycle() {
        if (listChats != null) {
            main_layout.findViewById(R.id.progressBar).setVisibility(View.GONE);
            main_layout.findViewById(R.id.list_chats).setVisibility(View.VISIBLE);
        } else
            return;

        //Recycle
        recyclerView = main_layout.findViewById(R.id.list_chats);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        containerChatsRecycleAdapter = new ContainerChatsRecycleAdapter(listChats, this);
        recyclerView.setAdapter(containerChatsRecycleAdapter);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
    }
}
