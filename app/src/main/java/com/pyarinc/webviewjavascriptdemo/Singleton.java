package com.pyarinc.webviewjavascriptdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.CookieManager;

class Singleton {
    private static SharedPreferences sharedpreferences;
    private Context mContext;
    private static final Singleton ourInstance = new Singleton();
    private static final String MyPREFERENCES = "MyPrefs";
    private Class mClass;

    static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }


    public static SharedPreferences getSharedpreferences(Context context) {
        if (sharedpreferences == null)
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences;
    }

    public static void setString(String key, String value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key) {
        return sharedpreferences.getString(key, "");
    }
    public void initLog(Class aClass){
        mClass = aClass;
    }
    public void l(String key, String value) {
        Log.d(key,value);
    }
    public void l(String value) {
        Log.d(mClass.getName(),value);
    }


    public String[] getCookie(String siteName){
        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(siteName);
        return cookies.split(";");
    }

}
 