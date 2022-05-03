package com.ru.mirea.kalugina.mireaproject.ui.history;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ru.mirea.kalugina.mireaproject.databinding.FragmentHistoryBinding;
import com.ru.mirea.kalugina.mireaproject.ui.history.db.App;
import com.ru.mirea.kalugina.mireaproject.ui.history.db.History;
import com.ru.mirea.kalugina.mireaproject.ui.history.db.HistoryDao;
import com.ru.mirea.kalugina.mireaproject.ui.history.db.HistoryDatabase;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    HistoryDatabase db;
    HistoryDao historyDao;
    History history;
    private FragmentHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        db = App.getInstance().getDatabase();
        historyDao = db.historyDao();

        HistoryAdapter adapter = new HistoryAdapter(getActivity(), db);
        binding.recycler.setAdapter(adapter);

        binding.addHistoryButton.setOnClickListener(this::onClickAddHistory);


        return binding.getRoot();
    }

    private void onClickAddHistory(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText historyTitle = new EditText(getContext());
        historyTitle.setSingleLine(true);
        alert.setTitle("Создание истории");
        alert.setMessage("Введите название истории");

        alert.setView(historyTitle);

        alert.setPositiveButton("Далее", (dialogInterface, i) -> {
            String titleValue = historyTitle.getText().toString();
            acceptStoryContent(titleValue);
        });

        alert.setNegativeButton("Отмена", (dialogInterface, i) -> {});

        alert.show();
    }

    private void acceptStoryContent(String historyTitle){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText historyContent = new EditText(getContext());
        alert.setTitle("Создание истории");
        alert.setMessage("Введите содержание истории");
        alert.setView(historyContent);

        alert.setPositiveButton("Создать", (dialogInterface, i) -> {
            String historyValue = historyContent.getText().toString();
            createHistory(historyTitle, historyValue);
        });

        alert.setNegativeButton("Отмена", (dialogInterface, i) -> {});
        alert.show();
    }

    private void createHistory(String title, String content){
        history = new History(title, content);
        historyDao.insert(history);
    }
}