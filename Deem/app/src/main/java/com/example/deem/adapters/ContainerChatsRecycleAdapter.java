package com.example.deem.adapters;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.ChatActivity;
import com.example.deem.R;
import com.example.deem.fragments.ChatsContainerFragment;
import com.example.deem.utils.ImageLoadUtil;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.Chat;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.Message;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        private ImageView notice_img;
        private CircleImageView icon;

        public ItemChat(@NonNull View itemView) {
            super(itemView);

            name       = itemView.findViewById(R.id.group_name_item_info);
            begin_text = itemView.findViewById(R.id.begin_text);
            notice_img = itemView.findViewById(R.id.notice_chat_icon);
            icon       = itemView.findViewById(R.id.icon_chat);

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

            //проверим есть ли в этом чате непрочитанные сообщения.
            if (chat.getMessages().get(chat.getMessages().size() - 1).getRead()) //последнее сообщение не прочитано?
                notice_img.setVisibility(INVISIBLE);
            else
                notice_img.setVisibility(VISIBLE);


            DoImage(chat);
        }

        public void DoImage(Chat chat) {

            if (chat.getUsers().size() <= 2) { //если это не групповой чат то загружаем иконку
                Account account = null;

                if (!chat.getUsers().isEmpty()) {
                    Long accountId = chat.getUsers().get(0);
                    Long myId = APIManager.getManager().getMyAccount().getId();
                    for (Long i : chat.getUsers())
                        if (myId != i) {
                            accountId = i;
                            break;
                        }

                    for (Account account1 : APIManager.getManager().getListAccounts())
                        if (account1.getId() == accountId)
                            account = account1;
                }
                
                ImageLoadUtil.getInstance().LoadImageIcon(icon, account);

            } else {
                icon.setImageBitmap(ImageLoadUtil.getInstance().generateLetterIcon(
                        APIManager.getManager().getMyAccount().getGroup().getName(), CurrentFragment.getContext()));
            }
        }

    }

}
