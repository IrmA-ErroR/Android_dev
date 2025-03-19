package com.mirea.kabanovasvetlana.simplefragmentapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;


public class MainActivity extends AppCompatActivity {
    private Fragment fragment1, fragment2;

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

        // Инициализация фрагментов
        fragment1 = new FirstFragment();
        fragment2 = new SecondFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Установка обработчиков для кнопок


        // Управление фрагментами в зависимости от ориентации экрана
        if (isLandscapeMode()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment1Container, fragment1);
            transaction.replace(R.id.fragment2Container, fragment2);  // или замените на другой контейнер для второго фрагмента, если есть
            transaction.commit();
        } else {
            // Первый фрагмент по умолчанию
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment1)
                    .commit();
            Button btnFirstFragment = findViewById(R.id.btnFirstFragment);
            btnFirstFragment.setOnClickListener(v -> fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment1)
                    .commit());

            Button btnSecondFragment = findViewById(R.id.btnSecondFragment);
            btnSecondFragment.setOnClickListener(v -> fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment2)
                    .commit());
        }
    }

    // Проверка на ландшафтный режим
    private boolean isLandscapeMode() {
        WindowMetrics metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this);
        return metrics.getBounds().width() > metrics.getBounds().height();
    }
}