package com.example.deem.dialogs;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.deem.R;
import com.example.deem.adapters.FullScreenImageAdapter;
import java.util.List;

public class FullScreenImageDialog extends DialogFragment {
    private int startPosition;
    private List<ImageView> imageViews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fullscreen_image, container, false);

        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        ImageView closeButton = view.findViewById(R.id.closeButton);

        FullScreenImageAdapter adapter = new FullScreenImageAdapter(requireContext(), imageViews);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(startPosition, false);

        closeButton.setOnClickListener(v -> dismiss());

        return view;
    }

    public void initialize(List<ImageView> imageViews, int startPosition) {
        this.imageViews = imageViews;
        this.startPosition = startPosition;
    }
}

