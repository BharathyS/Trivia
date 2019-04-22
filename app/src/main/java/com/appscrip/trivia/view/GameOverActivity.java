package com.appscrip.trivia.view;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appscrip.trivia.R;
import com.appscrip.trivia.util.PrefferenceManager;
import com.appscrip.trivia.view.history.HistoryActivity;
import com.appscrip.trivia.view.register.RegisterActivity;
import com.appscrip.trivia.viewmodel.GameOverViewModel;

public class GameOverActivity extends BaseActivity implements View.OnClickListener {

    public static final String TOTAL_QUESTIONS = "total_questions";
    public static final String CORRECTLY_ANSWERED_QUESTION = "answered_questions";

    private TextView scoreDetailTv;
    private Button replayBt;
    private Button newGameBt;
    private Button historyBt;
    private GameOverViewModel gameOverViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        init();
    }

    private void init() {
        gameOverViewModel = (GameOverViewModel) obtainViewModel(GameOverViewModel.class);
        setObservers();
        getMaximumGameLevel();
        int totalQuest = getIntent().getIntExtra(TOTAL_QUESTIONS, 0);
        int answeredQues = getIntent().getIntExtra(CORRECTLY_ANSWERED_QUESTION, 0);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(answeredQues + " correctly answered out of " + totalQuest + " Questions ");
        stringBuilder.append("\n\n");
        if (answeredQues == 0) {
            stringBuilder.append("Better Luck next time");
        } else {
            stringBuilder.append("Congratulations!!!");
        }
        stringBuilder.append("\n" + PrefferenceManager.getUserName(context));
        scoreDetailTv.setText(stringBuilder.toString());
    }

    private void getMaximumGameLevel() {
        showProgressDialog(getString(R.string.loading), true);
        gameOverViewModel.getMaxGameLevelValue();
    }

    private void setObservers() {
        gameOverViewModel.isNextLevelAvailable.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isNextLevelAvailable) {
                if (isNextLevelAvailable) {
                    newGameBt.setVisibility(View.VISIBLE);
                } else {
                    newGameBt.setVisibility(View.GONE);
                }
                hideProgreeDialog();
            }
        });

        gameOverViewModel.onError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                hideProgreeDialog();
            }
        });
    }

    @Override
    protected void initViews() {
        scoreDetailTv = findViewById(R.id.gameover_tv_scoredetail);
        replayBt = findViewById(R.id.gameover_bt_replay);
        newGameBt = findViewById(R.id.gameover_bt_newgame);
        historyBt = findViewById(R.id.gameover_bt_history);
    }

    @Override
    protected void setViewListeners() {
        replayBt.setOnClickListener(this);
        newGameBt.setOnClickListener(this);
        historyBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (replayBt.getId() == v.getId()) {
            launchRegisterActivity();
        } else if (newGameBt.getId() == v.getId()) {
            int gameId = PrefferenceManager.getGameNumber(context);
            PrefferenceManager.setGameNumber(context, ++gameId);
            launchRegisterActivity();
        } else if (historyBt.getId() == v.getId()) {
            launchHistoryActivity();
        }
    }

    private void launchRegisterActivity() {
        startActivity(new Intent(context, RegisterActivity.class));
        finish();
    }

    private void launchHistoryActivity() {
        startActivity(new Intent(context, HistoryActivity.class));
    }
}
