package com.example.deem;

import static java.lang.String.format;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.restful.api.APIManager;
import com.example.restful.models.AuthRequest;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
                APIManager.getManager().auth(authRequest);

                if (APIManager.getManager().isAuth())
                {
                    saveData(username, password); //save log in
                    APIManager.getManager().UpdateData();
                    APIManager.getManager().getMyAccount();


                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Не удалось авторизироваться" ,
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        findViewById(R.id.login).setOnClickListener(onClickLogIn);
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





