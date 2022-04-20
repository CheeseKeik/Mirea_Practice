package com.ru.mirea.kalugina.mireaproject.ui.audioPlayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ru.mirea.kalugina.mireaproject.MainActivity;
import com.ru.mirea.kalugina.mireaproject.databinding.FragmentAudioPlayerBinding;

public class AudioPlayerFragment extends Fragment {


    private FragmentAudioPlayerBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AudioPlayerViewModel audioPlayerViewModel =
                new ViewModelProvider(this).get(AudioPlayerViewModel.class);

        binding = FragmentAudioPlayerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonPlay.setOnClickListener(this::onClickPlayMusic);
        binding.buttonStop.setOnClickListener(this::onClickStopMusic);

        final TextView textView = binding.textPlayingNow;
        audioPlayerViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onClickPlayMusic(View view) {
        MainActivity activity = (MainActivity) getActivity();
        activity.startService(
                new Intent(activity, PlayerService.class));
    }
    public void onClickStopMusic(View view) {
        MainActivity activity = (MainActivity) getActivity();
        activity.stopService(
                new Intent(activity, PlayerService.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}