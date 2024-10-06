package com.example.deem;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.deem.databinding.ActivityProfile2Binding;
import com.example.deem.databinding.ActivityProfileBinding;
import com.example.deem.fragments.OptionsFragment;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.ImageLoadCallback;

import java.io.FileNotFoundException;
import java.io.InputStream;

/** Обязательное требование: Extra Nickname:название_аккаунта */
public class ProfileActivity extends AppCompatActivity {

    //private ActivityProfileBinding binding;
    private ActivityProfile2Binding binding;
    private Account account;

    private OptionsFragment optionsFragment;
    private FragmentTransaction fragmentTransaction;

    private boolean isMyAccount;
    private Account myAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityProfile2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        if (!APIManager.statusInfo.isAccountListGot())
            return;

        myAccount = APIManager.getManager().getMyAccount();
        optionsFragment = new OptionsFragment();

        String nickname = getIntent().getStringExtra("Nickname");
        if (nickname.isEmpty())
            return;

        if (nickname.equals(myAccount.getUsername()))
            isMyAccount = true;
        else
            findViewById(R.id.options_card).setVisibility(View.GONE);

        if (nickname.equals(myAccount.getUsername()))
            account = APIManager.getManager().getMyAccount();
        else
            account = APIManager.getManager().getListAccounts().stream()
                .filter(s->s.getUsername().equals(nickname)).findAny().orElse(null);


        //Set information
        binding.profileFullName.setText(account.getSurname() + " " + account.getName() + " " + account.getFathername());
        binding.profileCourseInf.setText(account.getGroup().getCourse() + " " + account.getGroup().getFaculty());
        binding.profileGroupNumber.setText(account.getGroup().getName());
        binding.profileMail.setText(""); //TODO
        binding.profilePersonalId.setText(String.valueOf(account.getId())); //personal identifier
        binding.profileNicknameInf.setText(account.getUsername());
        binding.profileNicknameInf2.setText(account.getUsername());
        ((TextView) findViewById(R.id.rank)).setText("No rank");
        binding.myScore.setText((account.getScore() != null) ? String.valueOf(account.getScore()) : "Скрыто");

        SetListeners();

        //Установка изображений
        ImageLoadCallback imageLoadCallback = new ImageLoadCallback() {
            @Override
            public void onImageLoaded(String decodeStr) {
                Bitmap bitmap = ImageUtil.getInstance().ConvertToBitmap(decodeStr);
                binding.profileMyIcon.setImageBitmap(bitmap);
            }
        };

        if (account.getImageIcon() != null) {
            imageLoadCallback.onImageLoaded(account.getImageIcon().getImgEncode());
        } else
            APIManager.getManager().getIconImageLazy(account, imageLoadCallback);

        optionsFragment.UpdateDrawableIcon(binding.profileMyIcon.getDrawable());

        //Hide options menu
        if (!nickname.equals(myAccount.getUsername()))
            findViewById(R.id.options_card).setVisibility(View.GONE);
        else
            findViewById(R.id.options_card).setVisibility(View.VISIBLE);


    }


    private void SetListeners() {
        /*binding.buttonBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        binding.exitFromAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AuthActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //close all activities
                startActivity(intent);
            }
        });

        binding.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.profile_fragment, optionsFragment);
                fragmentTransaction.commit();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
            optionsFragment.UpdateDrawableIcon(drawable);

        }

    }


}
