package com.example.deem;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        View.OnClickListener onClickExit = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };

        findViewById(R.id.button_back_profile).setOnClickListener(onClickExit);
    }
}
