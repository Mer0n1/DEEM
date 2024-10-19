package com.example.deem.dialogs;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.example.deem.ProfileActivity;
import com.example.deem.R;
import com.example.deem.utils.ImageUtil;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;
import com.example.restful.models.IconImage;
import com.example.restful.models.Image;
import com.example.restful.utils.GeneratorUUID;

public class ImageLoadIconDialog extends DialogFragment {

    private ConstraintLayout main_layout;
    private Drawable drawable_icon;
    private Account account;
    private ImageButton iconImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_layout = (ConstraintLayout)inflater.inflate(R.layout.dialog_load_icon,
                container, false);

        account = APIManager.getManager().getMyAccount();
        iconImage = main_layout.findViewById(R.id.ImageIcon);

        if (account.getImageIcon() != null)
            iconImage.setImageBitmap(ImageUtil.getInstance().ConvertToBitmap(account.getImageIcon().getImgEncode()));


        setListeners();

        return main_layout;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

                if (account.getImageIcon() == null)
                    account.setImageIcon(new Image());

                account.getImageIcon().setImgEncode(image.getImgEncode());
                Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
                ((ProfileActivity)getActivity()).setImageIcon(drawable_icon);
                dismiss();
            }
        });

    }

    public void UpdateDrawableIcon(Drawable drawable_icon) {
        this.drawable_icon = drawable_icon;
        if (iconImage != null)
            iconImage.setImageDrawable(drawable_icon);
    }
}
