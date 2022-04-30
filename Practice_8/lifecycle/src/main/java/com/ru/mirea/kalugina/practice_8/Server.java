package com.ru.mirea.kalugina.practice_8;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class Server implements LifecycleObserver {
    private final String TAG = "lifecycle";

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner source, Lifecycle.Event event) {
        Log.d(TAG, "Current State: " + event);
    }
}
