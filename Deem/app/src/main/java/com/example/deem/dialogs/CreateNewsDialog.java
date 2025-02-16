package com.example.deem.dialogs;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;

import com.example.deem.R;
import com.example.deem.adapters.NewsListRecycleAdapter;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.CreateNewsDTO;
import com.example.restful.models.Image;
import com.example.restful.models.News;
import com.example.restful.models.NewsImage;
import com.example.restful.models.StandardCallback;
import com.example.restful.models.VideoMetadata;
import com.example.restful.utils.ConverterDTO;
import com.example.restful.utils.DateUtil;
import com.example.restful.utils.GeneratorUUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class CreateNewsDialog extends DialogFragment {

    private List<News> newsListGroup;
    private NewsListRecycleAdapter updateAddedNews;
    private ConstraintLayout main_layout;

    //img
    private List<Drawable> listDrawables;
    private TextView imgInfo;

    //video
    private boolean isVideoLoaded;
    private VideoMetadata videoMetadata;

    private Account myAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main_layout = (ConstraintLayout)inflater.inflate(R.layout.dialog_new_news, container, false);
        listDrawables = new ArrayList<>();
        imgInfo = main_layout.findViewById(R.id.test_image_loaded);
        myAccount = APIManager.getManager().getMyAccount();


        main_layout.findViewById(R.id.layout_video_loaded).setVisibility(GONE);
        main_layout.findViewById(R.id.button_close_video_loaded).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_layout.findViewById(R.id.layout_video_loaded).setVisibility(GONE);
                isVideoLoaded = false;
                videoMetadata = null;
            }
        });

        return main_layout;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.send_news).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                sendNews(view);
            }
        });

        view.findViewById(R.id.image_loader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                String[] mimeTypes = {"image/*", "video/*"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(Intent.createChooser(intent, "Select Media"), 0);
            }
        });
    }

    private void sendNews(View view) {
        if (newsListGroup != null)
        {
            News news = new News();
            news.setContent(((TextView)view.findViewById(R.id.editTextContentMessage)).getText().toString());
            news.setDate(new Date(System.currentTimeMillis()));
            news.setFaculty(myAccount.getGroup().getFaculty());
            news.setIdAuthor(myAccount.getId());
            news.setIdGroup(myAccount.getGroup().getId());
            news.setImages(new MutableLiveData<>(new ArrayList<>()));
            news.setCompleted(true);

            //настройка видео
            if (isVideoLoaded) {
                news.setVideoUUID(videoMetadata.getUuid());
                news.setVideoMetadata(videoMetadata);
                news.setThereVideo(true);
            }

            for (Drawable drawable : listDrawables) {
                String str = ImageUtil.getInstance().ConvertToString(drawable);

                Image image = new Image();
                image.setImgEncode(str);

                NewsImage newsImage = new NewsImage();
                newsImage.setImage(image);
                newsImage.setUuid(GeneratorUUID.getInstance().generateUUIDForNews(
                        DateUtil.getInstance().getDateToForm(news.getDate()), myAccount.getGroup().getName()));
                newsImage.setId_news(news.getId());

                news.getImages().getValue().add(newsImage);
            }

            //отправляем на сервер
            APIManager.getManager().addNews(news);
            //Обновляем в кэше
            List<News> allNews = APIManager.getManager().getListNews().getValue();
            allNews.add(0, news);
            APIManager.getManager().getListNews().setValue(allNews);
            //Обновляем ui
            newsListGroup.add(0, news);
            updateAddedNews.notifyDataSetChanged();

            dismiss();
        }
    }


    public void initialize(List<News> newsList, NewsListRecycleAdapter updateAddedNews) {
        this.newsListGroup = newsList;
        this.updateAddedNews = updateAddedNews;
    }

    public void addDrawable(Drawable drawable) {
        if (listDrawables == null)
            listDrawables = new ArrayList<>();
        listDrawables.add(drawable);

        imgInfo.setText(String.valueOf(listDrawables.size()) + " изображений загружено");
    }

    public void initVideo(byte[] videoData) {
        String videoUUID = GeneratorUUID.getInstance().generateUUIDforVideo(videoData);
        videoMetadata = new VideoMetadata(VideoMetadata.TypeVideoData.news_video, videoUUID, videoData);
        isVideoLoaded = true;

        main_layout.findViewById(R.id.layout_video_loaded).setVisibility(View.VISIBLE);
    }
}