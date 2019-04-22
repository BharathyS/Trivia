package com.appscrip.trivia.view.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appscrip.trivia.R;
import com.appscrip.trivia.datamodel.remote.Model.History;
import com.appscrip.trivia.util.PrefferenceManager;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<History> histories;

    HistoryAdapter(Context context) {
        this.context = context;
    }

    public void updateItems(ArrayList<History> histories) {
        this.histories = histories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_adapter_item_layout,parent, false);
        HistoryViewHolder holder = new HistoryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        History history = histories.get(position);
        HistoryViewHolder viewHolder = (HistoryViewHolder) holder;
        viewHolder.gameNametv.setText(history.getGameName());
        viewHolder.timeTv.setText(history.getTimeString());
        viewHolder.questionAnswer.setText(history.getQuestionAnswerString());
        viewHolder.nameTv.setText("Name : " + PrefferenceManager.getUserName(context));
    }

    @Override
    public int getItemCount() {
        return (histories == null) ? 0 : histories.size();
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView timeTv;
        TextView nameTv;
        TextView questionAnswer;
        TextView gameNametv;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            gameNametv = itemView.findViewById(R.id.history_tv_game_name);
            timeTv = itemView.findViewById(R.id.history_tv_game_date);
            nameTv = itemView.findViewById(R.id.history_tv_name);
            questionAnswer = itemView.findViewById(R.id.history_tv_question_answer);
        }
    }
}
