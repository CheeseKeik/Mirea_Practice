package com.ru.mirea.kalugina.clickbuttons;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tvOut;
    private Button okButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOut = findViewById(R.id.tvOut);
        okButton = findViewById(R.id.okButton);
        cancelButton = findViewById(R.id.cancelButton);

        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvOut.setText("Нажата кнопка OK");
            }
        };

        okButton.setOnClickListener(oclBtnOk);


    }
    public void onMyButtonClick(View view){
        Toast.makeText(this, "Нажата кнопка Cancel", Toast.LENGTH_SHORT).show();
        tvOut.setText("Нажата кнопка Cancel");
    }
}