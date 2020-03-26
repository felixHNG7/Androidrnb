package com.felix.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewsWeb extends AppCompatActivity {
    private WebView webView;
    private static final String URL = "URL";
    private String url;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, NewsWeb.class);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_news);
        Intent intent = getIntent();
        url = intent.getStringExtra(URL);
        webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
