package com.ru.mirea.kalugina.mireaproject.ui.audioPlayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ru.mirea.kalugina.mireaproject.MainActivity;
import com.ru.mirea.kalugina.mireaproject.R;
import com.ru.mirea.kalugina.mireaproject.databinding.FragmentAudioPlayerBinding;

import java.io.File;

public class AudioPlayerFragment extends Fragment {


    private FragmentAudioPlayerBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAudioPlayerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonPlay.setOnClickListener(this::onClickPlayMusic);
        binding.buttonStop.setOnClickListener(this::onClickStopMusic);

        return root;
    }

    public void onClickPlayMusic(View view) {
        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;
        activity.startService(
                new Intent(activity, PlayerService.class));
        binding.textPlayingNow.setText(activity.getFileName(R.raw.cuddlefish));
    }

    public void onClickStopMusic(View view) {
        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;
        activity.stopService(
                new Intent(activity, PlayerService.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}