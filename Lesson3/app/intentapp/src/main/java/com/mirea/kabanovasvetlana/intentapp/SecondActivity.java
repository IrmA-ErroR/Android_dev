package com.mirea.kabanovasvetlana.intentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView textView = findViewById(R.id.textViewResult);

        // получаем данные
        Intent intent = getIntent();
        int squareNumber = intent.getIntExtra("squareNumber", 0);
        String currentTime = intent.getStringExtra("currentTime");

        String resultText = "КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ "
                + squareNumber + ",\n\nа текущее время " + currentTime;
        textView.setText(resultText);
    }
}
