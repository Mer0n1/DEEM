package com.example.deem;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.deem.dialogs.CreateNewsDialog;
import com.example.deem.fragments.ChatsContainerFragment;
import com.example.deem.fragments.EventsFragment;
import com.example.deem.fragments.FirstPageFragment;
import com.example.deem.fragments.GroupFragment;
import com.example.deem.fragments.InfoFragment;
import com.example.deem.fragments.InfoFragments.RatingGroupsFragment;
import com.example.deem.utils.Toolbar;
import com.example.restful.api.APIManager;
import com.example.restful.datebase.CacheSystem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;
    private GroupFragment groupFragment;
    private InfoFragment infoFragment;
    private FirstPageFragment firstPageFragment;
    private ChatsContainerFragment chatsContainerFragment;
    private EventsFragment eventsFragment;

    public enum FragmentType { first, group, info_, messenger, events }

    private int currentFragmentId = R.id.firstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        InitResource();

        //тест
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.anim_shape_icon);
        findViewById(R.id.profile_icon).startAnimation(shake);
        //

        OpenMenu(FragmentType.first);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == currentFragmentId)
                return false;
            currentFragmentId = item.getItemId();


            switch (item.getItemId()) {
                case R.id.firstFragment:
                    OpenMenu(FragmentType.first);
                    return true;
                case R.id.messengerFragment:
                    OpenMenu(FragmentType.messenger);
                    return true;
                case R.id.groupFragment:
                    Bundle bundle = new Bundle();
                    bundle.putString("name", APIManager.getManager().getMyAccount().getGroup().getName());
                    OpenMenu(FragmentType.group, bundle);
                    return true;
                case R.id.newsFragment:
                     OpenMenu(FragmentType.info_);
                     return true;
                case R.id.eventFragment:
                     OpenMenu(FragmentType.events);
                    return true;

            }
            return false;
        });

        View.OnClickListener onClickOther = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.profile_icon)) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("Nickname", APIManager.getManager().getMyAccount().getUsername());
                    startActivity(intent);
                }
            }
        };

        findViewById(R.id.profile_icon).setOnClickListener(onClickOther);
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
    public void OpenMenu(FragmentType fragmentType, Bundle bundle) {
        Fragment fragment = firstPageFragment;
        if (fragmentType == FragmentType.group)
            fragment = groupFragment;
        if (fragmentType == FragmentType.messenger)
            fragment = chatsContainerFragment;
        if (fragmentType == FragmentType.info_)
            fragment =  infoFragment;
        if (fragmentType == FragmentType.first)
            fragment = firstPageFragment;
        if (fragmentType == FragmentType.events)
            fragment = eventsFragment;

        fragment.setArguments(bundle);
        OpenFragment(fragment, R.id.fragment_main, false);
    }
    public void OpenMenu(FragmentType fragmentType) {
        OpenMenu(fragmentType, new Bundle());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheSystem.getCacheSystem().saveAll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CacheSystem.getCacheSystem().saveAll();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(selectedImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Drawable drawable = Drawable.createFromStream(inputStream, selectedImageUri.toString());
            CreateNewsDialog.addDrawable(drawable);
        }
    }
}

