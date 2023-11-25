package com.example.deem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deem.databinding.ActivityProfileBinding;
import com.example.deem.utils.ImageUtil;
import com.example.restful.utils.GeneratorUUID;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.ImageLoadCallback;


public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding activity;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activity.getRoot());

        init();
    }

    private void init() {

        String nickname = getIntent().getStringExtra("Nickname");
        if (nickname.isEmpty())
            return;

        account = APIManager.getManager().listAccounts.stream()
                .filter(s->s.getUsername().equals(nickname)).findAny().orElse(null);


        //Set information
        activity.profileFullName.setText(account.getSurname() + " " + account.getName() + " " + account.getFathername());
        activity.profileCourseInf.setText(account.getGroup().getCourse() + " " + account.getGroup().getFaculty());
        activity.profileGroupNumber.setText(account.getGroup().getName());
        activity.profileMail.setText("----");
        activity.myScore.setText(String.valueOf(account.getScore()));
        activity.profilePersonalId.setText(String.valueOf(account.getId())); //personal identifier

        SetListeners();

        //test
        //Bitmap bitmap = BitmapFactory.decodeStream(APIManager.getManager().thes);
        //activity.profileMyIcon.setImageBitmap(bitmap);

        //test with image + news
        /*System.out.println("listNews " + APIManager.getManager().listNews.size());
        News news = APIManager.getManager().listNews.get(0);
        byte[] decodedBytes = Base64.decode(news.getListImg().get(0), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        activity.profileMyIcon.setImageBitmap(decodedBitmap);*/

        //Технология CallBack на загрузку изображений
        /*String uuid = GeneratorUUID.getInstance().generateUUIDForIcon(account.getUsername());
        APIManager.getManager().GetImage(uuid, "profile_icon", new ImageLoadCallback() {
            @Override
            public void onImageLoaded(String decodeStr) {
                //byte[] decodedBytes = Base64.decode(decodeStr, Base64.DEFAULT);
                //Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(decodeStr);
                if (bitmap != null)
                    activity.profileMyIcon.setImageBitmap(bitmap);
            }
        });*/

        Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(account.getImageIcon().getImgEncode());
        if (bitmap != null)
            activity.profileMyIcon.setImageBitmap(bitmap);
    }

    private void SetListeners() {
        activity.buttonBackProfile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activity.exitFromAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AuthActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //close all activities
                startActivity(intent);
            }
        });
    }



}
