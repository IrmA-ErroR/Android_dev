package com.mirea.kabanovasvetlana.buttonclicker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textViewOut;
    private Button buttonWhoAmI;
    private Button buttonItIsNotMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textViewOut = findViewById(R.id.textViewOut);
        buttonWhoAmI = findViewById(R.id.buttonWhoAmI);
        buttonItIsNotMe = findViewById(R.id.buttonItIsNotMe);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Обработчик для btnWhoAmI через setOnClickListener
        View.OnClickListener oclBtnWhoAmI = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewOut.setText("Мой номер по списку 14");
            }
        };
        buttonWhoAmI.setOnClickListener(oclBtnWhoAmI);
    }

    // Обработчик для buttonItIsNotMe через атрибут onClick
    public void ClickIsNotMe(View view ) {
        textViewOut.setText("Это не я сделал");
    }

}