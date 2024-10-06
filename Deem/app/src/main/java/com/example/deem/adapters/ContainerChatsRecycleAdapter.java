package com.example.deem.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.ChatActivity;
import com.example.deem.R;
import com.example.deem.fragments.ChatsContainerFragment;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.Chat;

import org.w3c.dom.Text;

import java.util.List;

public class ContainerChatsRecycleAdapter extends RecyclerView.Adapter<ContainerChatsRecycleAdapter.ItemChat> {

    private List<Chat> chats;
    private ChatsContainerFragment CurrentFragment;

    public ContainerChatsRecycleAdapter(List<Chat> chats, ChatsContainerFragment fragment) {
        this.CurrentFragment = fragment;
        this.chats = chats;
    }

    @NonNull
    @Override
    public ItemChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_story, parent, false);
        return new ItemChat(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemChat holder, int position) {
        holder.setData(chats.get(position));
    }

    @Override
    public int getItemCount() {
        if (chats == null)
            return -1;
        return chats.size();
    }

    class ItemChat extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView begin_text;

        public ItemChat(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.group_name_item_info);
            begin_text = itemView.findViewById(R.id.begin_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CurrentFragment.getActivity(), ChatActivity.class);
                    intent.putExtra("Nickname", name.getText().toString());
                    CurrentFragment.getActivity().startActivity(intent);
                }
            });
        }

        public void setData(Chat chat) {
            String username = "";

            if (chat.getUsers().size() > 2)
                username = APIManager.getManager().getMyAccount().getGroup().getName();
            else {
                List<Account> accounts = APIManager.getManager().getListAccounts();
                Long id = chat.getUsers().get(0);
                if (id == APIManager.getManager().getMyAccount().getId())
                    id = chat.getUsers().get(1);
                Long finalId = id;

                Account account = accounts.stream().filter(
                        s -> s.getId() == finalId).findAny().orElse(null);
                if (account != null)
                    username = account.getUsername();
            }

            name.setText(String.valueOf(username));
            if (chat.getMessages().size() != 0)
                begin_text.setText(chat.getMessages().get(chat.getMessages().size()-1).getText());
        }
    }
}
