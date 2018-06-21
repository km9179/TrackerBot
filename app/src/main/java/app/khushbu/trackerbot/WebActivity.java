package app.khushbu.trackerbot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.http.SslError;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class WebActivity extends AppCompatActivity {

    WebView contestSite;
    SwipeRefreshLayout swipeRefreshLayout;
    FrameLayout frameLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        contestSite = (WebView)findViewById(R.id.contestSite);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        progressBar.setMax(100);

        contestSite.setWebViewClient(new MyWebViewClient());
        contestSite.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView webView,int progress){
                frameLayout.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);

                if(progress == 100){
                    frameLayout.setVisibility(View.GONE);
                }
                super.onProgressChanged(webView,progress);
            }
        });
        contestSite.getSettings().setJavaScriptEnabled(true);
        contestSite.setVerticalScrollBarEnabled(false);

        contestSite.getSettings().setLoadWithOverviewMode(true);
        contestSite.getSettings().setUseWideViewPort(true);

        contestSite.getSettings().setUserAgentString("Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) AppleWebKit/<WebKit Rev> (KHTML, like Gecko) Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>");

        contestSite.getSettings().setBuiltInZoomControls(true);

        Intent intent=getIntent();

        String url=intent.getStringExtra("url");

        contestSite.loadUrl(url);
        progressBar.setProgress(0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contestSite.reload();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            frameLayout.setVisibility(View.VISIBLE);
            return true;
        }

        public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
            // TODO Auto-generated method stub
            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(contestSite.canGoBack()) {
                contestSite.goBack();
                return true;

            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
