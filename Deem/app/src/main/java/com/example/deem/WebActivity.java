package com.example.deem;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restful.api.APIManager;
import com.example.restful.models.Event;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.Web);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        Long examId = getIntent().getLongExtra("EXAM_ID",0);
        event = APIManager.getManager().listEvents.getValue().stream().filter(x->x.getId()==examId).findAny().orElse(null);
        loadExamHtml();
    }

    private void loadExamHtml() {
        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Authorization", "Bearer " + APIManager.getManager().getJwtKey());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request.Builder builder = new Request.Builder()
                            .url(request.getUrl().toString())
                            .header("Authorization", "Bearer " + APIManager.getManager().getJwtKey());
                    for (String header : request.getRequestHeaders().keySet()) {
                        builder.addHeader(header, request.getRequestHeaders().get(header));
                    }
                    Response response = client.newCall(builder.build()).execute();
                    return new WebResourceResponse(
                            response.body().contentType().type() + "/" + response.body().contentType().subtype(),
                            response.header("content-encoding", "utf-8"),
                            response.body().byteStream()
                    );
                } catch (Exception e) {
                    return super.shouldInterceptRequest(view, request);
                }
            }
        });

        webView.loadUrl(event.getExam().getAddressToExamService(), extraHeaders);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true; // Возвращаем true, чтобы не передавать управление системному браузеру
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true; // Возвращаем true, чтобы не передавать управление системному браузеру
        }
    }
}
