package com.morshed.bdshoppingzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set No Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //---------------------------//
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        if (dataconnected()) {
            startSplash();

        } else {
            startActivity(new Intent(SplashActivity.this, ConnectionActivity.class));
            finish();
        }
    }

    private boolean dataconnected() {
        boolean dataConnected = false;
        boolean wifiIsAvailable, mobileDataIsAvailable;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            wifiIsAvailable = networkInfo.isConnected();
            networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            mobileDataIsAvailable = networkInfo.isConnected();
            dataConnected = wifiIsAvailable || mobileDataIsAvailable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataConnected;
    }

    private void startSplash() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();


            }
        },SPLASH_TIME_OUT);
    }
}