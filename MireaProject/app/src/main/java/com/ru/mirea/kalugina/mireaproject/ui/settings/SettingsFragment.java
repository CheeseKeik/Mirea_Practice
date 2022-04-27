package com.ru.mirea.kalugina.mireaproject.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ru.mirea.kalugina.mireaproject.R;
import com.ru.mirea.kalugina.mireaproject.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private SharedPreferences preferences;
    private FragmentSettingsBinding binding;
    private ViewGroup background;
    private boolean colorCheck = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        background = binding.ConsLayout;
        binding.seekBarVolume.setOnSeekBarChangeListener(seekBarChangeListener);

        binding.switchColor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                colorCheck = true;
                background.setBackgroundColor(getResources().getColor(R.color.dark_red));
            } else {
                colorCheck = false;
                background.setBackgroundColor(getResources().getColor(R.color.white));
            }
            preferences.edit().putBoolean("color", colorCheck).apply();
        });

        binding.buttonLoad.setOnClickListener(this::onLoadText);

        return root;
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            TextView textView = binding.textNumber;
            textView.setText(String.valueOf(progress));
            preferences.edit().putInt("volume", progress).apply();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public void onLoadText(View view) {
        Integer savedVolume = preferences.getInt("volume", 0);
        Boolean savedColor = preferences.getBoolean("color", false);
        String savedText = preferences.getString("beautiful", "");
        binding.switchColor.setChecked(savedColor);
        binding.seekBarVolume.setProgress(savedVolume);
        binding.textNumber.setText(String.valueOf(savedVolume));
        binding.editTextBeautiful.setText(savedText);
    }

    @Override
    public void onDestroyView() {
        binding.editTextBeautiful.getText();
        preferences.edit().putString("beautiful", binding.editTextBeautiful.getText().toString()).apply();
        super.onDestroyView();
        binding = null;
    }
}