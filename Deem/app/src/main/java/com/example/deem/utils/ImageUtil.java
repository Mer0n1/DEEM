package com.example.deem.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;

public class ImageUtil {

    private static ImageUtil instance;

    private ImageUtil() {}

    public static ImageUtil getInstance() {
        if (instance == null)
            instance = new ImageUtil();
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String ConvertToString(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] ImgEncode = stream.toByteArray();
        return Base64.getEncoder().encodeToString(ImgEncode);
    }

    public Bitmap ConvertToBitmap(String encode) {
        if(encode == null || encode.isEmpty())
            return null;
        byte[] decodedBytes = android.util.Base64.decode(encode, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    /** Особый формат для создания UUID: h_m_s d_m_y*/
    /*public String getDate() {
        Date date = new Date(System.currentTimeMillis());
        String date_str = date.getHours() + "_" + date.getMinutes() + "_" +
                date.getSeconds() + " " + date.getDay() + "_" + date.getMonth() +
                "_" + date.getYear();
        return date_str;
    }*/


}
