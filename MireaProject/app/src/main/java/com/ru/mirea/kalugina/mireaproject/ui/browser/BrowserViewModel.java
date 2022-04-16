package com.ru.mirea.kalugina.mireaproject.ui.browser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BrowserViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public BrowserViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Введите адрес сайта");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
