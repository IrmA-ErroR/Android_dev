package com.mirea.kabanovasvetlana.lesson4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.mirea.kabanovasvetlana.lesson4.databinding.ActivityMainBinding;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Установка текстов
        binding.songTitleTextView.setText("Ordinary");
        binding.artistTextView.setText("Alex Warren");

        // Кнопоки
        binding.playButton.setOnClickListener(v ->
                Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show());

        binding.prevButton.setOnClickListener(v ->
                Toast.makeText(this, "Previous", Toast.LENGTH_SHORT).show());

        binding.nextButton.setOnClickListener(v ->
                Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show());
    }
}