package com.ru.mirea.kalugina.mireaproject.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.mirea.kalugina.mireaproject.R;
import com.ru.mirea.kalugina.mireaproject.ui.history.db.History;
import com.ru.mirea.kalugina.mireaproject.ui.history.db.HistoryDao;
import com.ru.mirea.kalugina.mireaproject.ui.history.db.HistoryDatabase;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    HistoryDatabase db;
    HistoryDao historyDao;
    History history;

    HistoryAdapter(Context context, HistoryDatabase db) {
        this.db = db;
        historyDao = db.historyDao();
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        history = historyDao.getById(position + 1);
        holder.storyTitle.setText(history.title);
        holder.storyContent.setText(history.content);
    }

    @Override
    public int getItemCount() {
        return db.historyDao().getCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView storyTitle, storyContent;
        ViewHolder(View view){
            super(view);
            storyTitle = view.findViewById(R.id.historyTitle);
            storyContent = view.findViewById(R.id.historyContent);
        }
    }
}
