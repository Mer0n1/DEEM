package com.example.deem;

import static java.lang.String.format;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.restful.api.APIManager;
import com.example.restful.api.websocket.MessageEncoder;
import com.example.restful.api.websocket.PushClient;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Message;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.glassfish.tyrus.client.ClientManager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

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
                APIManager.getManager().auth(authRequest);

                if (APIManager.getManager().isAuth())
                {
                    saveData(username, password); //save log in
                    APIManager.getManager().UpdateData();
                    APIManager.getManager().getMyAccount();

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

/*

@javax.websocket.ClientEndpoint
class ExampleClient {
    private static Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println(format("Connection established. session id: %s", session.getId()));
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    public static void sendMessage(String message) {
        if (session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }


    public void start() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            URI uri = new URI("ws://192.168.1.104:8025/ws/chat");
            container.connectToServer(ExampleClient.class, uri);
            ExampleClient.sendMessage("str");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
*/

