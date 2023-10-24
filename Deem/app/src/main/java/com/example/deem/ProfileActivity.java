package com.example.deem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deem.databinding.ActivityProfileBinding;
import com.example.restful.api.APIManager;
import com.example.restful.models.Account;


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
        //account = APIManager.getManager().myAccount;

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
