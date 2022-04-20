package com.ru.mirea.kalugina.mireaproject.ui.audioPlayer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AudioPlayerViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AudioPlayerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Is now playing: Cuddlefish.mp3");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
