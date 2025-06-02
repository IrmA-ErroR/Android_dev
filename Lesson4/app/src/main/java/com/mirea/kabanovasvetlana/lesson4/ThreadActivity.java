package com.mirea.kabanovasvetlana.lesson4;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mirea.kabanovasvetlana.lesson4.databinding.ActivityThreadBinding;

import java.util.Arrays;

public class ThreadActivity extends AppCompatActivity {
    private ActivityThreadBinding binding;
    private int counter = 1;  // счётчик потоков, стартуем с 1
    private static final String TAG = ThreadActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        binding = ActivityThreadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Thread mainThread = Thread.currentThread();
        binding.infoTextView.setText("Имя текущего потока: " + mainThread.getName());

        String newName = "МОЙ НОМЕР ГРУППЫ: БИСО-01-20, \nНОМЕР ПО СПИСКУ: 14, \nМОЙ ЛЮБИМЫЙ ФИЛЬМ: Интерстеллар";
        mainThread.setName(newName);

        binding.infoTextView.append("\nНовое имя потока: " + mainThread.getName());
        Log.d(TAG, "Stack: " + Arrays.toString(mainThread.getStackTrace()));

        binding.calculateButton.setOnClickListener(v -> {
            String pairsStr = binding.totalPairsEditText.getText().toString().trim();
            String daysStr = binding.studyDaysEditText.getText().toString().trim();

            if (pairsStr.isEmpty() || daysStr.isEmpty()) {
                binding.infoTextView.append("\n\nПожалуйста, введите оба значения.");
                return;
            }

            int totalPairs = Integer.parseInt(pairsStr);
            int studyDays = Integer.parseInt(daysStr);

            int threadNumber = counter++;  // увеличиваем счётчик при каждом запуске потока
            String groupNumber = "БИСО-01-20";
            int listNumber = 14;

            Log.d(TAG, String.format(
                    "Запущен поток № %d студентом группы № %s номер по списку № %d",
                    threadNumber, groupNumber, listNumber));

            // Запуск фонового потока
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                double avgPairs = (double) totalPairs / studyDays;
                String result = "\n\nСреднее количество пар в день: " + String.format("%.2f", avgPairs);

                runOnUiThread(() -> {
                    binding.infoTextView.setText("");  // очистка перед выводом результата
                    binding.infoTextView.append(result);
                });
            }).start();
        });
    }
}
