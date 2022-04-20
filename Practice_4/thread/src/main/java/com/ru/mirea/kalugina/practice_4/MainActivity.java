package com.ru.mirea.kalugina.practice_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView infoTextView = findViewById(R.id.textView);
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Текущий поток: " + mainThread.getName());
        // Меняем имя и выводим в текстовом поле
        mainThread.setName("MireaThread");
        infoTextView.append("\nНовое имя потока: " + mainThread.getName());
    }

    public void onClick(View view) {
        Runnable runnable = new Runnable() {
            public void run() {
                int numberThread = counter++;
                Log.i("ThreadProject", "Запущен поток № " + numberThread);
                long endTime = System.currentTimeMillis()
                        + 20 * 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime -
                                    System.currentTimeMillis());
                        } catch (Exception e) {
                        }
                    }
                }
                Log.i("ThreadProject", "Выполнен поток № " + numberThread);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void onClickCountPairs (View view) {
        Runnable runnable = () -> {
            EditText editText = findViewById(R.id.editTextTextPairs);
            float pairs = Float.parseFloat(editText.getText().toString());
            EditText editText2 = findViewById(R.id.editTextTextDays);
            float days = Float.parseFloat(editText2.getText().toString());
            TextView textView = findViewById(R.id.pairEncounter);
            textView.setText("Среднее количество пар в день: " + pairs / days);
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}