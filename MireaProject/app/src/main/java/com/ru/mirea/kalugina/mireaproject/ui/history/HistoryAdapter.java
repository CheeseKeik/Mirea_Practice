package com.ru.mirea.kalugina.mireaproject.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.mirea.kalugina.mireaproject.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<History> histories;

    HistoryAdapter(Context context, List<History> histories) {
        this.histories = histories;
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
        History history = histories.get(position);
        holder.storyTitle.setText(history.getTitle());
        holder.storyContent.setText(history.getContent());
    }

    @Override
    public int getItemCount() {
        return histories.size();
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
