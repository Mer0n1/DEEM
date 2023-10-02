package com.example.deem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.deem.R;
import com.example.deem.layer_server.DataCash;
import com.example.deem.layer_server.MainAPI;
import com.example.restful.api.GroupsAPI;
import com.example.restful.models.AuthRequest;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identefication_form);

        if (checkRemember()) {
            SharedPreferences preferences = getSharedPreferences("auth", MODE_PRIVATE);
            ((TextView) findViewById(R.id.username_enter)).setText(preferences.getString("username", ""));
            ((TextView) findViewById(R.id.password_enter)).setText(preferences.getString("password", ""));
        }


        View.OnClickListener onClickLogIn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((TextView)findViewById(R.id.username_enter)).getText().toString();
                String password = ((TextView)findViewById(R.id.password_enter)).getText().toString();

                AuthRequest authRequest = new AuthRequest(username, password);

                if (MainAPI.authentication(authRequest))
                {
                    saveData(username, password); //save log in

                    DataCash.UpdateData();
                    DataCash.setMyAccount(MainAPI.getMyAccount());

                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    System.out.println("non auth");
                }
            }
        };

        findViewById(R.id.login).setOnClickListener(onClickLogIn);
    }

    public void validate() {

    }

    public boolean checkRemember() {
        SharedPreferences preferences = getSharedPreferences("auth", MODE_PRIVATE);
        String str = preferences.getString("auth", "");
        return str.equals("true");
    }

    public void saveData(String username, String password) {
        SharedPreferences preferences = getSharedPreferences("auth", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("auth", "true");
        editor.apply();
    }
}
