package com.appscrip.trivia.view.history;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.appscrip.trivia.R;
import com.appscrip.trivia.datamodel.remote.Model.History;
import com.appscrip.trivia.view.BaseActivity;
import com.appscrip.trivia.viewmodel.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity implements View.OnClickListener {

    private TextView backTv;
    private RecyclerView historyRv;
    private HistoryViewModel historyViewModel;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        init();
    }

    private void init() {
        historyViewModel = (HistoryViewModel) obtainViewModel(HistoryViewModel.class);
        setUpHistoryAdapter();
        setObservers();
        getHistoryList();
    }

    private void getHistoryList() {
        showProgressDialog(getString(R.string.loading), true);
        historyViewModel.getHistoryListFormDataBase();
    }

    private void setObservers() {
        historyViewModel.getHistoryList().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(@Nullable List<History> histories) {
                historyAdapter.updateItems((ArrayList<History>) histories);
                hideProgreeDialog();
            }
        });

        historyViewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
    }

    private void setUpHistoryAdapter() {
        historyAdapter = new HistoryAdapter(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        historyRv.setLayoutManager(linearLayoutManager);
        historyRv.setAdapter(historyAdapter);
    }

    @Override
    protected void initViews() {
        backTv = findViewById(R.id.history_tv_back);
        historyRv = findViewById(R.id.history_rv);
    }

    @Override
    protected void setViewListeners() {
        backTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == backTv.getId()) {
            finish();
        }
    }
}
