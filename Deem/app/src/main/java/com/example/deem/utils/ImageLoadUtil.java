package com.example.deem.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.deem.R;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.ImageLoadCallback;

public class ImageLoadUtil {
    private static ImageLoadUtil instance;

    private ImageLoadUtil() {}

    public static ImageLoadUtil getInstance() {
        if (instance == null)
            instance = new ImageLoadUtil();
        return instance;
    }

    public void LoadImageIcon(ImageView icon, Account account) {
        ImageLoadCallback imageLoadCallback = new ImageLoadCallback() {
            @Override
            public void onImageLoaded(String decodeStr) {
                Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(decodeStr);
                icon.setImageBitmap(bitmap);
            }
        };

        if (account != null)
            if (account.getImageIcon() != null && account.isThereImageIcon() != null && account.isThereImageIcon())
                imageLoadCallback.onImageLoaded(account.getImageIcon().getImgEncode());
            else
                APIManager.getManager().getIconImageLazy(account, imageLoadCallback);
    }


    public Bitmap generateLetterIcon(String letter, Context context) {
        int size = 200;

        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Drawable background = ContextCompat.getDrawable(context, R.drawable.shape_icon);
        background.setBounds(0, 0, size, size);
        background.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(90);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextAlign(Paint.Align.CENTER);

        float x = size / 2f;
        float y = (size / 2f) - ((paint.descent() + paint.ascent()) / 2f);
        canvas.drawText(letter, x, y, paint);

        return bitmap;
    }
}
