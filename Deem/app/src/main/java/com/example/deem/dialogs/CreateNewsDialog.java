package com.example.deem.dialogs;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.example.deem.R;
import com.example.deem.adapters.NewsListRecycleAdapter;
import com.example.deem.databinding.DialogNewNewsBinding;
import com.example.deem.fragments.GroupFragment;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Image;
import com.example.restful.models.News;
import com.example.restful.models.NewsImage;
import com.example.restful.utils.DateUtil;
import com.example.restful.utils.GeneratorUUID;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CreateNewsDialog extends DialogFragment {

    private List<News> newsList;
    private ConstraintLayout main_layout;

    private static List<Drawable> listDrawables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main_layout = (ConstraintLayout)inflater.inflate(R.layout.dialog_new_news, container, false);
        listDrawables = new ArrayList<>();
        newsList = APIManager.getManager().listNews;

        return main_layout;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.send_news).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (newsList != null)
                {
                    News news = new News();
                    news.setContent(((TextView)view.findViewById(R.id.editTextContentMessage)).getText().toString());
                    news.setDate(new Date(System.currentTimeMillis()));
                    news.setFaculty(APIManager.getManager().myAccount.getGroup().getFaculty());
                    news.setIdGroup(APIManager.getManager().myAccount.getGroup().getId());
                    newsList.add(news);

                    for (Drawable drawable : listDrawables) {
                        String str = ImageUtil.getInstance().ConvertToString(drawable);

                        Image image = new Image();
                        image.setImgEncode(str);

                        NewsImage newsImage = new NewsImage();
                        newsImage.setImage(image);
                        newsImage.setUuid(GeneratorUUID.getInstance().generateUUIDForNews
                                (DateUtil.getInstance().getDate(), APIManager.getManager().myAccount.getGroup().getName()));

                        news.setImages(new ArrayList<>());
                        news.getImages().add(newsImage);
                    }

                    APIManager.getManager().addNews(news);
                    dismiss();
                }
            }
        });

        view.findViewById(R.id.image_loader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
    }


    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public static void addDrawable(Drawable drawable) {
        if (listDrawables == null)
            listDrawables = new ArrayList<>();
        listDrawables.add(drawable);
    }
}