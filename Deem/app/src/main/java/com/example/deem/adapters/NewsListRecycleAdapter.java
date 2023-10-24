package com.example.deem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
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

        public ItemNews(@NonNull View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.news_content_info);
            date = itemView.findViewById(R.id.news_date_info);
        }

        public void setData(News news) {
            date.setText(news.getDate().toString());
            content.setText(news.getContent());
        }
    }
}
