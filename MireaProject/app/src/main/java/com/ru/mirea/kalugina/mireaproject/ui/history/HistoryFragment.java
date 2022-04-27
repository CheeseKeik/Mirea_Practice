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

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    ArrayList<History> histories = new ArrayList<>();
    private FragmentHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        setInitialStories();
        HistoryAdapter adapter = new HistoryAdapter(getActivity(), histories);
        binding.recycler.setAdapter(adapter);

        binding.addHistoryButton.setOnClickListener(this::onClickAddHistory);


        return binding.getRoot();
    }

    private void setInitialStories(){
        histories.add(new History("Skyrim", "You were trying to cross the border, right? " +
                                                        "\nWalked right into that Imperial ambush, same as us, and that thief over there. " +
                                                        "\nDamn you Stormcloaks. Skyrim was fine until you came along."));
        histories.add(new History("Skyrim IV", "I was born 87 years ago. For 65 years I've ruled as Tamriel's Emperor. " +
                                                                "\nBut for all these years I have never been the ruler of my own dreams. I have seen the Gates of Oblivion, beyond which no waking eye may see. " +
                                                                "\nBehold, in Darkness a Doom sweeps the land. This is the 27th of Last Seed; the Year of Akatosh 433. " +
                                                                "\nThese are the closing days of the 3rd Era, and the final hours of my life."));
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
            histories.add(new History(historyTitle, historyValue));
        });

        alert.setNegativeButton("Отмена", (dialogInterface, i) -> {});
        alert.show();
    }
}