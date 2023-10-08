package com.example.deem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.deem.MainActivity;
import com.example.deem.R;
import com.example.deem.fragments.InfoFragments.ListGroupsFragment;
import com.example.deem.fragments.InfoFragments.ListTopsFragment;
import com.example.deem.fragments.InfoFragments.ListUsersFragment;

import java.util.List;

/**
 * Фрагмент информации: новости групп, список топов (людей и групп), список участников
 */
public class InfoFragment extends Fragment {

    private FrameLayout main_layout;
    private MainActivity this_activity;

    private ListUsersFragment listUsersFragment;
    private ListGroupsFragment listGroupsFragment;
    private ListTopsFragment listTopsFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout)inflater.inflate(R.layout.fragment_news, container, false);
        this_activity = ((MainActivity)(getActivity()));

        this_activity.ClearIcons();
        init();

        return main_layout;
    }

    public void init() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        LinearLayout linearLayout = toolbar.findViewById(R.id.layout_toolbar);

        //Добавление иконок
        LinearLayout layout_icons = linearLayout.findViewById(R.id.icons_toolbar);

        int size = linearLayout.findViewById(R.id.profile_icon).getWidth();
        ImageView imgListTops = this_activity.loadIcon(layout_icons, size, R.drawable.icon_tops);
        ImageView imgListGroups = this_activity.loadIcon(layout_icons, size, R.drawable.icon_list_groups);
        ImageView imgListUsers  = this_activity.loadIcon(layout_icons, size, R.drawable.icon_list_users);


        //Изменение титла
        TextView textView = linearLayout.findViewById(R.id.text_toolbar);
        textView.setText("Новости");

        //init objects
        //fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        listUsersFragment = new ListUsersFragment();
        listGroupsFragment = new ListGroupsFragment();
        listTopsFragment = new ListTopsFragment();

        //Листенеры
        View.OnClickListener onClickListPersons = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Нужен для добавление фрагмента над (getFragmentManager().popBackStack())

                OpenFragment(listUsersFragment, "ListUsers");
                /*FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_main, listUsersFragment, "Test");
                ft.addToBackStack(null);
                ft.commit();*/

                //((MainActivity)(getActivity())).OpenFragment(listUsersFragment, R.id.fragment_main, true);
            }
        };

        View.OnClickListener onClickListGroups = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFragment(listGroupsFragment, "ListGroups");
            }
        };

        View.OnClickListener onClickTop = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFragment(listTopsFragment, "ListTopsFragment");
            }
        };

        imgListUsers.setOnClickListener(onClickListPersons);
        imgListGroups.setOnClickListener(onClickListGroups);
        imgListTops.setOnClickListener(onClickTop);
    }

    /*private ImageView loadIcon(LinearLayout layoutIcons, int size, int id_icon) {
        ImageView ii = new ImageView(main_layout.getContext());
        ii.setBackgroundResource(id_icon);
        ii.setScaleType(ImageView.ScaleType.FIT_XY);
        layoutIcons.addView(ii);

        ii.getLayoutParams().width = ii.getLayoutParams().height = size;
        ii.requestLayout();

        return ii;
    }*/

    /*private void ClearIcons() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        LinearLayout linearLayout = toolbar.findViewById(R.id.layout_toolbar);
        LinearLayout layout_icons = linearLayout.findViewById(R.id.icons_toolbar);
        layout_icons.removeAllViews();
    }*/

    private void OpenFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        //Сперва очистим
        //getFragmentManager().getBackStackEntryAt(0);
        //getFragmentManager().clearBackStack("ListGroups");
        //getFragmentManager().clearBackStack("ListUsers");

        //После заменим
        ft.replace(R.id.fragment_main, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void includeButtonBack() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        LinearLayout linearLayout = toolbar.findViewById(R.id.layout_toolbar);

        ImageView button_back = linearLayout.findViewById(R.id.button_back);
        button_back.setBackgroundResource(R.drawable.icon_back);
        button_back.getLayoutParams().height = button_back.getLayoutParams().width = 100;


        //listener
        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        };
        button_back.setOnClickListener(onClick);
    }

}
