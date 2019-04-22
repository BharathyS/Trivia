package com.appscrip.trivia.view.Game;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.appscrip.trivia.R;
import com.appscrip.trivia.datamodel.remote.Model.Game;
import com.appscrip.trivia.datamodel.remote.Model.GameRequest;
import com.appscrip.trivia.datamodel.remote.Model.Question;
import com.appscrip.trivia.util.PrefferenceManager;
import com.appscrip.trivia.view.BaseActivity;
import com.appscrip.trivia.view.GameOverActivity;
import com.appscrip.trivia.view.history.HistoryActivity;
import com.appscrip.trivia.viewmodel.GameViewModel;

import java.util.ArrayList;

public class GameActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout optionsLayout;
    private Button nextFinishBt;
    private TextView gameNameTv;
    private ImageView historyIv;
    private TextView questionNumberDetailTv;
    private TextView questionTv;

    private GameViewModel gameViewModel;
    private ArrayList<String> selectedOptions = new ArrayList<>();
    private int answeringQuestionNumber = 0;//index bases

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
    }

    private void init() {
        gameViewModel = (GameViewModel) obtainViewModel(GameViewModel.class);
        selectedOptions = new ArrayList<>();
        getGameData();
        setObservers();
    }

    private void getGameData() {
        showProgressDialog(getString(R.string.loading), true);
        gameViewModel.getGameForDataBase(getGameRequest());
    }

    private void setObservers() {
        gameViewModel.getGame().observe(this, new Observer<Game>() {
            @Override
            public void onChanged(@Nullable Game game) {
                setUpUI();
                hideProgreeDialog();
            }
        });

        gameViewModel.getOnGameResponseError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                hideProgreeDialog();
            }
        });

        gameViewModel.getOnHistoryAddedSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                launchGameOverActivity();
            }
        });

        gameViewModel.getOnHistoryAddedError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
    }

    private void disableEnableNextFinishButton() {
        if (selectedOptions.size() > 0) {
            nextFinishBt.setVisibility(View.VISIBLE);
        } else {
            nextFinishBt.setVisibility(View.GONE);
        }
    }

    private void setUpUI() {
        gameNameTv.setText(gameViewModel.getGame().getValue().getGamename());
        answeringQuestionNumber = 0;
        showNextQuestionData();
    }

    private void showNextQuestionData() {
        Game game = gameViewModel.getGame().getValue();
        if (game.getQuestions().size() > answeringQuestionNumber) {
            Question question = game.getQuestions().get(answeringQuestionNumber);

            selectedOptions = new ArrayList<>();
            optionsLayout.removeAllViews();
            questionTv.setText(question.getQuestion());
            int currentQuestionNumber = answeringQuestionNumber + 1;
            int totalQuestionsCount = game.getQuestions().size();
            if (currentQuestionNumber == totalQuestionsCount) {
                nextFinishBt.setText(R.string.finish);
            } else {
                nextFinishBt.setText(R.string.next);
            }
            questionNumberDetailTv.setText("Question Number : " + currentQuestionNumber + " / " + totalQuestionsCount);
            addOptiopsLayout(question);
        }
        disableEnableNextFinishButton();
    }

    private void addOptiopsLayout(Question question) {
        if (question.getCorrectAnswers().size() > 1) {
            addMutipleOptionsSelectionLayout(question);
        } else {
            addsingleOptionSelectionLayout(question);
        }
    }

    private void addsingleOptionSelectionLayout(final Question question) {
        optionsLayout.removeAllViews();

        for (int i = 0; i < question.getOptions().size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.option_radio_button_layout, null);
            final String option = question.getOptions().get(i);
            final RadioButton optionRb = view.findViewById(R.id.option_rb);
            optionRb.setText(option);
            if (selectedOptions.contains(option)) {
                optionRb.setChecked(true);
            } else {
                optionRb.setChecked(false);
            }
            optionRb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedOptions.clear();
                    if (optionRb.isChecked()) {
                        selectedOptions.add(option);
                    }
                    addsingleOptionSelectionLayout(question);
                    disableEnableNextFinishButton();
                }
            });
            optionsLayout.addView(view);
        }
    }

    private void addMutipleOptionsSelectionLayout(Question question) {
        for (int i = 0; i < question.getOptions().size(); i++) {
            final String option = question.getOptions().get(i);
            View view = LayoutInflater.from(context).inflate(R.layout.option_check_box_layout, null);

            CheckBox optionValuecb = view.findViewById(R.id.option_cb);
            optionValuecb.setText(option);

            optionValuecb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        selectedOptions.remove(option);
                    } else {
                        selectedOptions.add(option);
                    }
                    disableEnableNextFinishButton();
                }
            });
            optionsLayout.addView(view);
        }
    }

    private GameRequest getGameRequest() {
        GameRequest gameRequest = new GameRequest();
        gameRequest.setGameNumber(PrefferenceManager.getGameNumber(context));
        return gameRequest;
    }

    @Override
    protected void initViews() {
        optionsLayout = findViewById(R.id.game_ll_options);
        nextFinishBt = findViewById(R.id.game_bt_net_finish);
        gameNameTv = findViewById(R.id.game_tv_name);
        historyIv = findViewById(R.id.game_iv_history);
        questionNumberDetailTv = findViewById(R.id.game_tv_question_number);
        questionTv = findViewById(R.id.game_tv_question);
    }

    @Override
    protected void setViewListeners() {
        nextFinishBt.setOnClickListener(this);
        historyIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == nextFinishBt.getId()) {
            showNextQuestionOrCompleteGame();
        } else if (v.getId() == historyIv.getId()) {
            launchHistoryActivity();
        }
    }

    private void launchHistoryActivity() {
        startActivity(new Intent(context, HistoryActivity.class));
    }

    private void showNextQuestionOrCompleteGame() {
        Question question = gameViewModel.getGame().getValue().getQuestions().get(answeringQuestionNumber);
        ArrayList<String> mselectedOptions = new ArrayList(selectedOptions);
        question.setSelectedAnswerIndex(mselectedOptions);
        question.setAnsweredCorrectly(isAnsweredCorrectly(question));

        answeringQuestionNumber++;
        Game game = gameViewModel.getGame().getValue();
        if (game.getQuestions().size() > answeringQuestionNumber) {
            showNextQuestionData();
        } else {
            gameCompleted();
        }
    }

    private void gameCompleted() {
        gameViewModel.addGameToHistory(gameViewModel.getGame().getValue());
    }

    private boolean isAnsweredCorrectly(Question question) {
        if (selectedOptions.size() == question.getCorrectAnswers().size()) {
            if (question.getCorrectAnswers().containsAll(selectedOptions)) {
                return true;
            }
        }
        return false;
    }

    private void launchGameOverActivity() {
        Game game = gameViewModel.getGame().getValue();
        int totalQuestions = game.getQuestions().size();
        int totalansweredCorrectQuestion = 0;

        for (Question question : game.getQuestions()) {
            if (question.isAnsweredCorrectly()) {
                totalansweredCorrectQuestion++;
            }
        }

        Intent intent = new Intent(context, GameOverActivity.class);
        intent.putExtra(GameOverActivity.TOTAL_QUESTIONS, totalQuestions);
        intent.putExtra(GameOverActivity.CORRECTLY_ANSWERED_QUESTION, totalansweredCorrectQuestion);
        startActivity(intent);
        finish();
    }
}
