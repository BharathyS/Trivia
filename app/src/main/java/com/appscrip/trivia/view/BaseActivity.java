package com.appscrip.trivia.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appscrip.trivia.viewmodel.ViewModelFactory;

abstract public class BaseActivity extends AppCompatActivity {

    protected Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initViews();
        setViewListeners();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initViews();
        setViewListeners();
    }

    protected <T extends AndroidViewModel> AndroidViewModel obtainViewModel(Class<T> viewModelType) {
        ViewModelFactory factory = ViewModelFactory.getIntance(getApplication());
        return ViewModelProviders.of(this, factory).get(viewModelType);
    }

    protected abstract void initViews();

    protected abstract void setViewListeners();

    protected void showProgressDialog(String message, boolean isCancellable) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setMessage(message);
        progressDialog.setCancelable(isCancellable);
        progressDialog.show();
    }

    protected void hideProgreeDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
