package com.pyarinc.webviewjavascriptdemo;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
/*  todo Internet persmisson :: NO*/

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private String url = "https://your.site.com/";
    private Singleton mSingleton;
    private Button btLogut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView  = (WebView) findViewById(R.id.webview);
        /*Accept  cookie*/
        CookieManager.getInstance().setAcceptCookie(true);
        WebSettings  webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setDomStorageEnabled(true);

        webSettings.setSaveFormData(true);
        webSettings.setSavePassword(true);


        /* init Logout button*/
        btLogut  = (Button)findViewById(R.id.btLogout);
        btLogut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hello You clicked html Button!", Toast.LENGTH_SHORT).show();
            mSingleton.l("html button executed");
            }
        });
        /* init singleton */
        mSingleton = Singleton.getInstance();


        mSingleton.getSharedpreferences(this);

      /*  mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);*/

        /* Find html button and add interface to it*/
        mWebView.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void logout() {
                Toast.makeText(MainActivity.this, "button logout  clicked!", Toast.LENGTH_SHORT).show();
            }
            @JavascriptInterface
            public void signin(int value) {
                Toast.makeText(MainActivity.this, "button signin clicked!"+value, Toast.LENGTH_SHORT).show();
            }
        },"member");

        mWebView.loadUrl(url);

       /* Load from assets*/
      /* mWebView.loadUrl("file:///android_asset/LogoutEvent.html")*/;


     /*   final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        mWebView.setWebChromeClient(new WebChromeClient());
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        String cookieString = "param=value";
        cookieManager.setCookie(url, cookieString);
        CookieSyncManager.getInstance().sync();

        Map<String, String> abc = new HashMap<String, String>();
        abc.put("Cookie", cookieString);
        mWebView.loadUrl(url, abc);*/

    }

    WebViewClient mWebViewClient = new WebViewClient(){

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mSingleton.l("MainActivity","page finished");
            try {
                String[] mCookies  = mSingleton.getCookie(url);
                mSingleton.l("MainActivity","Cookies length "+mCookies.length);
                for (int i = 0; i < mCookies.length; i++) {
                    mSingleton.l("MainActivity","for loop :"+i+" is"+mCookies[i]);
                    mSingleton.setString("cookie"+i,mCookies[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    WebChromeClient mWebChromeClient = new WebChromeClient(){

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            return true;
        }
    };


}
