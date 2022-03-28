package com.ru.mirea.kalugina.layouttype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView myTextView = findViewById(R.id.duck);
        myTextView.setText("Certainly Duck");
        Button button = findViewById(R.id.callButton);
        button.setText("Call Duck");
        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setChecked(true);
    }
}