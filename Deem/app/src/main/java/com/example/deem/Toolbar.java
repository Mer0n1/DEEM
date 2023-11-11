package com.example.deem;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/** Сингдтон. Интерфейс тулбара. */
public class Toolbar {
    private static MainActivity mainActivity;
    private static Toolbar instance;
    private static androidx.appcompat.widget.Toolbar toolbarInterface;

    private static ImageView button_back;

    private Toolbar() {}
    public static Toolbar getInstance() {
        if (instance == null & mainActivity != null) {
            instance = new Toolbar();
            toolbarInterface = mainActivity.findViewById(R.id.toolbar);
        }
        return instance;
    }
    public static void setActivity(MainActivity mainActivity) { Toolbar.mainActivity = mainActivity;}

    /* Загрузить иконку в Toolbar */
    public ImageView loadIcon(int id_icon) {
        LinearLayout linearLayout = toolbarInterface.findViewById(R.id.layout_toolbar);
        linearLayout = linearLayout.findViewById(R.id.icons_toolbar);

        ImageView ii = new ImageView(mainActivity);
        ii.setBackgroundResource(id_icon);
        ii.setScaleType(ImageView.ScaleType.FIT_XY);
        linearLayout.addView(ii);

        ii.getLayoutParams().width = ii.getLayoutParams().height =
                toolbarInterface.findViewById(R.id.profile_icon).getLayoutParams().width;;
        ii.requestLayout();

        return ii;
    }

    /* Очистить иконки из Toolbar */
    public void ClearIcons() {
        LinearLayout linearLayout = toolbarInterface.findViewById(R.id.layout_toolbar);
        LinearLayout layout_icons = linearLayout.findViewById(R.id.icons_toolbar);
        layout_icons.removeAllViews();
    }

    public void setTitle(String title) {
        TextView textView = toolbarInterface.findViewById(R.id.text_toolbar);
        textView.setText(title);
    }

    public ImageView includeButtonBack() {
        if (button_back == null) {
            LinearLayout linearLayout = toolbarInterface.findViewById(R.id.layout_toolbar);
            button_back = linearLayout.findViewById(R.id.button_back);
            button_back.setBackgroundResource(R.drawable.icon_back);
            button_back.getLayoutParams().height = button_back.getLayoutParams().width = 80;
        } else
            button_back.setVisibility(View.VISIBLE);

        return button_back;
    }

    public void TurnOffButtonBack() {
        if (button_back != null)
            button_back.setVisibility(View.INVISIBLE);
    }
}
