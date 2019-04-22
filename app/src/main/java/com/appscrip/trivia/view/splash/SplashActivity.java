package com.appscrip.trivia.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.appscrip.trivia.R;
import com.appscrip.trivia.view.BaseActivity;
import com.appscrip.trivia.view.Game.GameActivity;
import com.appscrip.trivia.view.register.RegisterActivity;
import com.google.firebase.FirebaseApp;

public class SplashActivity extends BaseActivity {

    private final int LAUNCH_DELAY = 3000;//in millis Seconds

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        FirebaseApp.initializeApp(context);
        startActivityAfterDelay();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setViewListeners() {

    }

    private void startActivityAfterDelay() {
        Handler handler = new Handler();
        Runnable nextActivityLaunchrunnable = new Runnable() {
            @Override
            public void run() {
                startNextActivity();
                finish();
            }
        };
        handler.postDelayed(nextActivityLaunchrunnable, LAUNCH_DELAY);
    }

    private void startNextActivity() {
        launchRegisterActivity();
    }

    private void launchRegisterActivity() {
        startActivity(new Intent(context, RegisterActivity.class));
    }

    private void launchGameActivity() {
        startActivity(new Intent(context, GameActivity.class));
    }
}
