package com.example.deem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Group;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.News;

import java.util.List;

public class NewsListRecycleAdapter extends RecyclerView.Adapter<NewsListRecycleAdapter.ItemNews> {

    private List<News> newsList;

    public NewsListRecycleAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ItemNews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);

        return new ItemNews(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemNews holder, int position) {
        holder.setData(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }




class ItemNews extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView content;
        private TextView name_group;
        private ImageView imageView;

        public ItemNews(@NonNull View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.news_content_info);
            date = itemView.findViewById(R.id.news_date_info);
            name_group = itemView.findViewById(R.id.news_namegroup_info);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void setData(News news) {
            date.setText(news.getDate().toString());
            content.setText(news.getContent());

            List<Group> listGroups = APIManager.getManager().listGroups;
            Group group = null;

            if (listGroups != null)
                group = listGroups.stream()
                    .filter(s->s.getId() == news.getIdGroup()).findAny().orElse(null);

            if (group != null)
                name_group.setText(group.getName());

            //Загрузка изображений
            if (news.getImages() != null) {
                if (news.getImages().size() != 0)
                    imageView.setImageBitmap(ImageUtil.getInstance().ConvertToBitmap(
                            news.getImages().get(0).getImage().getImgEncode())); //TODO
            } else
                APIManager.getManager().getNewsImagesLazy(news, new ImageLoadCallback() {
                    @Override
                    public void onImageLoaded(String decodeStr) {
                        imageView.setImageBitmap(ImageUtil.getInstance().ConvertToBitmap(decodeStr));
                    }
                });
        }
    }
}
