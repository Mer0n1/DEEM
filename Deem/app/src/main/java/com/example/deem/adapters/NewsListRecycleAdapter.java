package com.example.deem.adapters;

import static java.security.AccessController.getContext;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.restful.utils.DateTranslator;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.News;
import com.example.restful.models.NewsImage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        private final int indent = 110; //test

        public ItemNews(@NonNull View itemView) {
            super(itemView);

            content      = itemView.findViewById(R.id.news_content_info);
            date         = itemView.findViewById(R.id.news_date_info);
            name_group   = itemView.findViewById(R.id.news_namegroup_info);
            recyclerView = itemView.findViewById(R.id.list_images);
            icon         = itemView.findViewById(R.id.icon_group_main);

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", icon.getText().toString());

                    MainActivity mainActivity = (MainActivity) fragment.getActivity();
                    mainActivity.OpenMenu(MainActivity.FragmentType.group, bundle);
                }
            });

        }

        public void setData(News news) {
            date.setText(DateTranslator.getInstance().toString(news.getDate()));
            content.setText(news.getContent());

            if (news.getGroup() != null) {
                name_group.setText(news.getGroup().getName());
                icon      .setText(news.getGroup().getName());
            }

            //Загрузка изображений
            if (fragment.getContext() != null)
            if (news.getImages().getValue() != null && news.getImages().getValue().size() != 0) {
                List<ImageView> imageViews = new ArrayList<>();

                for (NewsImage newsImage : news.getImages().getValue())
                    imageLoad(imageViews, newsImage.getImage().getImgEncode());

                initRecycle(imageViews.size(), imageViews);

            } else if (!news.isCompleted()) { //загружаем новые изображения из сервера или кэша
                List<ImageView> imageViews = new ArrayList<>();
                initRecycle(1, imageViews);

                APIManager.getManager().getNewsImagesLazy(news, new ImageLoadCallback() { //TODO проверить обновление
                    @Override
                    public void onImageLoaded(String decodeStr) {
                        imageLoad(imageViews, decodeStr);
                    }
                });

            } else
                recyclerView.setVisibility(View.GONE); //если изображений нет то отключаем recycle
        }

        /** Преобразовываем строку в изображение ImageView */
        private void imageLoad(List<ImageView> imageViews, String decodeStr) {
            if (fragment.getContext() != null && fragment.getContext().getResources() != null) { //баг
                ImageView imageView = new ImageView(fragment.getContext());
                Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(decodeStr);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); //Обрезает лишнее и заполняет
                imageView.setImageBitmap(getScaledBitmap(bitmap));
                imageViews.add(imageView);

                recyclerView.setLayoutManager(new GridLayoutManager(fragment.getActivity(), imageViews.size()));
            }
        }

        private void initRecycle(int count, List<ImageView> views) {
            recyclerView.setLayoutManager(new GridLayoutManager(fragment.getActivity(), count));
            ImagesListRecycleAdapter imagesListRecycleAdapter =
                    new ImagesListRecycleAdapter(views, fragment.getContext());
            recyclerView.setAdapter(imagesListRecycleAdapter);
            recyclerView.scrollToPosition(1);
        }

        /** Задаем размер bitmap соответственно текущему устройству */
        private Bitmap getScaledBitmap(Bitmap bitmap) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            fragment.getActivity().getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);

            int targetWidth = displayMetrics.widthPixels - indent;
            int targetHeight = Math.round((float) bitmap.getHeight() * ((float) targetWidth / (float) bitmap.getWidth()));
System.err.println("--------- " + bitmap.getHeight() + " " + bitmap.getWidth() + " === " + targetHeight + " " + targetWidth);
            return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
        }

    }
}
