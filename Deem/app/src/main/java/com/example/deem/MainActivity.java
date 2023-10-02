package com.example.deem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.deem.fragments.FirstPageFragment;
import com.example.deem.fragments.GroupFragment;
import com.example.deem.fragments.InfoFragment;


public class MainActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;
    //private ListUsersFragment listUsersFragment;
    private GroupFragment groupFragment;
    private InfoFragment infoFragment;
    private FirstPageFragment firstPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitResource();

        //тест
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.anim_shape_icon);
        findViewById(R.id.profile_icon).startAnimation(shake);

        //
        //OpenFragment(firstPageFragment, R.id.fragment_main);
        //


        View.OnClickListener onClickBottom = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (v == findViewById(R.id.bottom_events))
                    OpenFragment(listUsersFragment, R.id.fragment_main);*/

                if (v == findViewById(R.id.bottom_group))
                    OpenFragment(groupFragment, R.id.fragment_main);

                if (v == findViewById(R.id.bottom_info))
                    OpenFragment(infoFragment, R.id.fragment_main);

                if (v == findViewById(R.id.profile_icon)) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            }
        };

        findViewById(R.id.bottom_group).setOnClickListener(onClickBottom); //3
        findViewById(R.id.bottom_info).setOnClickListener(onClickBottom); //4
        findViewById(R.id.bottom_events).setOnClickListener(onClickBottom); //5

        findViewById(R.id.profile_icon).setOnClickListener(onClickBottom);
    }

    public void OpenFragment(Fragment fragment, int id_layout, boolean saveIcon) {
        FrameLayout frameLayout = findViewById(id_layout);
        frameLayout.removeAllViews();

        if (!saveIcon) {
            Toolbar toolbar = this.findViewById(R.id.toolbar);
            LinearLayout linearLayout = toolbar.findViewById(R.id.layout_toolbar);
            LinearLayout layout_icons = linearLayout.findViewById(R.id.icons_toolbar);
            layout_icons.removeAllViews();
        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(id_layout, fragment);
        fragmentTransaction.commit();
    }

    public void OpenFragment(Fragment fragment, int id_layout) {
        OpenFragment(fragment, id_layout, false);
    }

    public void InitResource() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //listUsersFragment = new ListUsersFragment();
        groupFragment = new GroupFragment();
        infoFragment = new InfoFragment();
        firstPageFragment = new FirstPageFragment();

    }

    public InfoFragment getInfoFragment() {
        return infoFragment;
    }


    public ImageView loadIcon(LinearLayout layoutIcons, int size, int id_icon) {
        ImageView ii = new ImageView(this);
        ii.setBackgroundResource(id_icon);
        ii.setScaleType(ImageView.ScaleType.FIT_XY);
        layoutIcons.addView(ii);

        ii.getLayoutParams().width = ii.getLayoutParams().height = size;
        ii.requestLayout();

        return ii;
    }

    public void ClearIcons() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        LinearLayout linearLayout = toolbar.findViewById(R.id.layout_toolbar);
        LinearLayout layout_icons = linearLayout.findViewById(R.id.icons_toolbar);
        layout_icons.removeAllViews();
    }
}

/**
 * Четче распределить ресурсы (как я понял это минус android потому что я делаю так как и нужно( )
 * Можно сделать статический класс Toolbar который будет отвечать за настройку интерфейса чтобы повторения кода не было
 * Возможно я ошибаюсь используя старую библиотеку getFragmentManager
 * Необходим подход с кэшированием данных, чтобы сервис не обращался к другому бесконечно обновляя информацию
 *
 */
