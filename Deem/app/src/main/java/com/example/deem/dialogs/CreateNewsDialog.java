package com.example.deem.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.example.deem.R;
import com.example.deem.adapters.NewsListRecycleAdapter;
import com.example.deem.databinding.DialogNewNewsBinding;
import com.example.deem.fragments.GroupFragment;
import com.example.restful.models.News;

import java.util.Date;
import java.util.List;


public class CreateNewsDialog extends DialogFragment {

    private List<News> newsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_new_news, container, false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.editTextContentMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newsList != null)
                {
                    News news = new News();
                    news.setContent(((TextView)view.findViewById(R.id.editTextContentMessage)).toString());
                    news.setDate(new Date(System.currentTimeMillis()));
                    //news.setIdGroup();
                    newsList.add(news);
                    dismiss();
                }
            }
        });
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}