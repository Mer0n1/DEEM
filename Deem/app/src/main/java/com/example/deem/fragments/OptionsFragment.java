package com.example.deem.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.deem.adapters.ChatRecycleAdapter;
import com.example.deem.adapters.ImagesListRecycleAdapter;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.IconImage;
import com.example.restful.models.Image;
import com.example.restful.utils.GeneratorUUID;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class OptionsFragment extends Fragment {

    private FrameLayout main_layout;
    private Drawable drawable_icon;
    private Account account;

    private EditText nickname;
    private ImageButton iconImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (FrameLayout)inflater.inflate(R.layout.fragment_options,
                container, false);


        account = APIManager.getManager().myAccount;
        nickname = main_layout.findViewById(R.id.profile_nicknameInf);
        iconImage = main_layout.findViewById(R.id.ImageIcon);

        nickname.setText(account.getUsername());
        if (account.getImageIcon() != null)
            iconImage.setImageBitmap(ImageUtil.getInstance().ConvertToBitmap(account.getImageIcon().getImgEncode()));


        setListeners();

        return main_layout;
    }

    private void setListeners() {
        //image loader
        iconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
        //Save
        main_layout.findViewById(R.id.save_options).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                IconImage icon_image = new IconImage();
                Image image = new Image();
                image.setImgEncode(ImageUtil.getInstance().ConvertToString(drawable_icon));
                icon_image.setImage(image);
                icon_image.setUuid(GeneratorUUID.getInstance().generateUUIDForIcon(account.getUsername()));

                APIManager.getManager().addIcon(icon_image);

                account.getImageIcon().setImgEncode(image.getImgEncode());
                Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void UpdateDrawableIcon(Drawable drawable_icon) {
        this.drawable_icon = drawable_icon;
        if (iconImage != null)
            iconImage.setImageDrawable(drawable_icon);
    }
}
