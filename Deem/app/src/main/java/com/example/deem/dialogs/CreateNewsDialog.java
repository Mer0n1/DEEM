package com.example.deem.dialogs;

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
    private static List<Drawable> listDrawables;
    private static TextView imgInfo;

    //video
    private static boolean isVideoLoaded;
    private static VideoMetadata videoMetadata;

    private Account myAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main_layout = (ConstraintLayout)inflater.inflate(R.layout.dialog_new_news, container, false);
        listDrawables = new ArrayList<>();
        imgInfo = main_layout.findViewById(R.id.test_image_loaded);

        myAccount = APIManager.getManager().getMyAccount();

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
                //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent, 1);

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

            /*CreateNewsDTO news = new CreateNewsDTO();
            news.setContent(((TextView)view.findViewById(R.id.editTextContentMessage)).getText().toString());
            news.setDate(new Date(System.currentTimeMillis()));
            news.setFaculty(myAccount.getGroup().getFaculty());
            news.setIdAuthor(myAccount.getId());
            news.setIdGroup(myAccount.getGroup().getId());
            news.setImages(new ArrayList<>());

            //convert to News
            News NaturalNews = ConverterDTO.CreateNewsDTOToNews(news);
            NaturalNews.setCompleted(true);*/

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

    public static void addDrawable(Drawable drawable) {
        if (listDrawables == null)
            listDrawables = new ArrayList<>();
        listDrawables.add(drawable);

        imgInfo.setText(String.valueOf(listDrawables.size()) + " изображений загружено");
    }

    public static void initVideo(byte[] videoData) {
        String videoUUID = GeneratorUUID.getInstance().generateUUIDforVideo(videoData);
        videoMetadata = new VideoMetadata(VideoMetadata.TypeVideoData.news_video, videoUUID, videoData);
        isVideoLoaded = true;
    }
}