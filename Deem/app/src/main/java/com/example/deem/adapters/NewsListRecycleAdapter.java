package com.example.deem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Group;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.News;
import com.example.restful.models.NewsImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewsListRecycleAdapter extends RecyclerView.Adapter<NewsListRecycleAdapter.ItemNews> {

    private List<News> newsList;
    private Fragment fragment;

    public NewsListRecycleAdapter(List<News> newsList, Fragment fragment) {
        this.newsList = newsList;
        this.fragment = fragment;
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
        private TextView icon;
        private RecyclerView recyclerView;

        public ItemNews(@NonNull View itemView) {
            super(itemView);

            content      = itemView.findViewById(R.id.news_content_info);
            date         = itemView.findViewById(R.id.news_date_info);
            name_group   = itemView.findViewById(R.id.news_namegroup_info);
            recyclerView = itemView.findViewById(R.id.list_images);
            icon         = itemView.findViewById(R.id.icon_group_main);
        }

        public void setData(News news) {
            date.setText(news.getDate().toString());
            content.setText(news.getContent());

            if (APIManager.getManager().listGroups != null) { //установим иконку для группы
                List<Group> list = APIManager.getManager().listGroups;
                Long idGroup = news.getIdGroup();
                Optional<Group> group = list.stream().filter(x->x.getId() == idGroup).findAny();

                if (!group.isEmpty())
                    icon.setText(group.get().getName());
            }

            List<Group> listGroups = APIManager.getManager().listGroups;
            Group group = null;

            if (listGroups != null)
                group = listGroups.stream()
                    .filter(s->s.getId() == news.getIdGroup()).findAny().orElse(null);

            if (group != null)
                name_group.setText(group.getName());

            //Загрузка изображений
            if (fragment != null)
            if (news.getImages() != null) {
                if (news.getImages().size() != 0) {
                    List<ImageView> imageViews = new ArrayList<>();

                    for (NewsImage newsImage : news.getImages()) {
                        ImageView imageView = new ImageView(content.getContext());
                        imageView.setImageBitmap(ImageUtil.getInstance().ConvertToBitmap(
                                newsImage.getImage().getImgEncode()));
                        imageViews.add(imageView);
                    }

                    initRecycle(imageViews.size(), imageViews);
                }
            } else {
                List<ImageView> imageViews = new ArrayList<>();
                initRecycle(1, imageViews);

                APIManager.getManager().getNewsImagesLazy(news, new ImageLoadCallback() {
                    @Override
                    public void onImageLoaded(String decodeStr) {
                        ImageView imageView = new ImageView(fragment.getContext());
                        imageView.setImageBitmap(ImageUtil.getInstance().ConvertToBitmap(decodeStr));
                        imageViews.add(imageView);

                        recyclerView.setLayoutManager(new GridLayoutManager(fragment.getActivity(), imageViews.size()));
                    }
                });
            }
        }

        private void initRecycle(int count, List<ImageView> views) {
            recyclerView.setLayoutManager(new GridLayoutManager(fragment.getActivity(), count));
            ImagesListRecycleAdapter imagesListRecycleAdapter =
                    new ImagesListRecycleAdapter(views, fragment.getContext());
            recyclerView.setAdapter(imagesListRecycleAdapter);
            recyclerView.scrollToPosition(1);
        }
    }
}
