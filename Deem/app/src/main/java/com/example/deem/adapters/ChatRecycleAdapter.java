package com.example.deem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.restful.models.Message;

import java.util.List;

public class ChatRecycleAdapter extends RecyclerView.Adapter<ChatRecycleAdapter.MessageViewHolder> {

    private List<Message> list;

    public ChatRecycleAdapter(List<Message> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ChatRecycleAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == 1)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sent_message, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_received_message, parent, false);


        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType().equals("send")) {
            return 1;
        } else {
            return 2;
        }
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView textMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            textMessage = itemView.findViewById(R.id.textMessage);
        }

        public void setData(Message message) {
            textMessage.setText(message.getText());
        }

    }
}
