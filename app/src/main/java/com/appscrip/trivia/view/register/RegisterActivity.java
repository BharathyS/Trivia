package com.appscrip.trivia.view.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.appscrip.trivia.R;
import com.appscrip.trivia.util.PrefferenceManager;
import com.appscrip.trivia.view.BaseActivity;
import com.appscrip.trivia.view.Game.GameActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText usernameEt;
    private FloatingActionButton nextFb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        usernameEt.setText(PrefferenceManager.getUserName(context));
        usernameEt.setSelection(usernameEt.getText().toString().length());
    }

    @Override
    protected void initViews() {
        usernameEt = findViewById(R.id.register_et_username);
        nextFb = findViewById(R.id.register_fb_next);
    }

    @Override
    protected void setViewListeners() {
        nextFb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == nextFb.getId()) {
            validateAndSave();
        }
    }

    private void validateAndSave() {
        if (!usernameEt.getText().toString().trim().isEmpty()) {
            if (usernameEt.getText().toString().trim().length() > 3) {
                saveUserNameAndStartGame();
            } else {
                Toast.makeText(context, R.string.username_should_be_more_than_3, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, R.string.enter_valid_name, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserNameAndStartGame() {
        PrefferenceManager.setUserName(context, usernameEt.getText().toString().trim());
        launchGameActivity();
    }

    private void launchGameActivity() {
        startActivity(new Intent(context, GameActivity.class));
        finish();
    }
}
