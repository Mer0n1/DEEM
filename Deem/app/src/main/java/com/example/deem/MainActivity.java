package com.example.deem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.deem.fragments.ChatsContainerFragment;
import com.example.deem.fragments.EventsFragment;
import com.example.deem.fragments.FirstPageFragment;
import com.example.deem.fragments.GroupFragment;
import com.example.deem.fragments.InfoFragment;
import com.example.deem.utils.GeneratorUUID;
import com.example.deem.utils.Toolbar;
import com.example.restful.api.APIManager;
import com.example.restful.models.DataImage;
import com.example.restful.models.Image;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;
    private GroupFragment groupFragment;
    private InfoFragment infoFragment;
    private FirstPageFragment firstPageFragment;
    private ChatsContainerFragment chatsContainerFragment;
    private EventsFragment eventsFragment;

    public enum FragmentType { first, group, info_, messenger, events }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitResource();

        //тест
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.anim_shape_icon);
        findViewById(R.id.profile_icon).startAnimation(shake);
        //

        OpenMenu(FragmentType.first);



        View.OnClickListener onClickBottom = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == findViewById(R.id.bottom_first))
                    OpenMenu(FragmentType.first);
                if (v == findViewById(R.id.bottom_group))
                    OpenMenu(FragmentType.group);
                if (v == findViewById(R.id.bottom_info))
                    OpenMenu(FragmentType.info_);
                if (v == findViewById(R.id.bottom_message))
                    OpenMenu(FragmentType.messenger);
                if (v == findViewById(R.id.bottom_events))
                    OpenMenu(FragmentType.events);


                if (v == findViewById(R.id.profile_icon)) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("Nickname", APIManager.getManager().myAccount.getUsername());
                    startActivity(intent);
                }

            }
        };

        findViewById(R.id.bottom_first).setOnClickListener(onClickBottom);
        findViewById(R.id.bottom_message).setOnClickListener(onClickBottom);
        findViewById(R.id.bottom_group).setOnClickListener(onClickBottom);
        findViewById(R.id.bottom_info).setOnClickListener(onClickBottom);
        findViewById(R.id.bottom_events).setOnClickListener(onClickBottom);

        findViewById(R.id.profile_icon).setOnClickListener(onClickBottom);
    }

    public void OpenFragment(Fragment fragment, int id_layout, boolean saveIcon) {
        if (!saveIcon)
            Toolbar.getInstance().ClearIcons();

        FrameLayout frameLayout = findViewById(id_layout);
        frameLayout.removeAllViews();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(id_layout, fragment);
        fragmentTransaction.commit();
    }

    public void OpenFragment(Fragment fragment, int id_layout) {
        OpenFragment(fragment, id_layout, false);
    }

    /** Отличие от OpenFragment - более высокий уровень. Открывает фрагмент изменяя дизайн*/
    public void OpenMenu(FragmentType fragmentType) {
        Fragment fragment = firstPageFragment;
        if (fragmentType == FragmentType.group)
            fragment =  groupFragment;
        if (fragmentType == FragmentType.messenger)
            fragment = chatsContainerFragment;
        if (fragmentType == FragmentType.info_)
            fragment =  infoFragment;
        if (fragmentType == FragmentType.first)
            fragment = firstPageFragment;
        if (fragmentType == FragmentType.events)
            fragment = eventsFragment;


        if (fragment.getClass() == GroupFragment.class)
            changeDesignOfIcon(R.id.bottom_group);
        if (fragment.getClass() == EventsFragment.class)
            changeDesignOfIcon(R.id.bottom_events);
        if (fragment.getClass() == InfoFragment.class)
            changeDesignOfIcon(R.id.bottom_info);
        if (fragment.getClass() == ChatsContainerFragment.class)
            changeDesignOfIcon(R.id.bottom_message);

        OpenFragment(fragment, R.id.fragment_main, false);
    }


    private void InitResource() {
        Toolbar.setActivity(this);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //listUsersFragment = new ListUsersFragment();
        groupFragment = new GroupFragment();
        infoFragment = new InfoFragment();
        firstPageFragment = new FirstPageFragment();
        chatsContainerFragment = new ChatsContainerFragment();
        eventsFragment = new EventsFragment();
    }


    public InfoFragment getInfoFragment() {
        return infoFragment;
    }

    /* Изменение дизайна иконок */
    public void changeDesignOfIcon(int RId) {
        ImageView bottom_message = findViewById(R.id.bottom_message);
        ImageView bottom_group = findViewById(R.id.bottom_group);
        ImageView bottom_events = findViewById(R.id.bottom_events);
        ImageView bottom_info = findViewById(R.id.bottom_info);

        bottom_message.setBackgroundResource(R.drawable.icon_messenger);
        bottom_group.setBackgroundResource(R.drawable.icon_group);
        bottom_events.setBackgroundResource(R.drawable.icon_events);
        bottom_info.setBackgroundResource(R.drawable.icon_news);

        switch (RId) {
            case R.id.bottom_message: bottom_message.setBackgroundResource(R.drawable.icon_messenger_pressed); break;
            case R.id.bottom_group: bottom_group.setBackgroundResource(R.drawable.icon_group_pressed); break;
            case R.id.bottom_info: bottom_info.setBackgroundResource(R.drawable.icon_news_pressed); break;
            case R.id.bottom_events: bottom_events.setBackgroundResource(R.drawable.icon_events_pressed); break;
        }

    }
}



