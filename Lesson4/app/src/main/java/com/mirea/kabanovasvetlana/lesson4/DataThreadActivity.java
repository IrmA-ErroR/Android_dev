package com.mirea.kabanovasvetlana.lesson4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class DataThreadActivity extends AppCompatActivity {
    private TextView infoTextView;
    private Handler mainHandler;
    private static final String TAG = "DataThreadActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_thread);
        infoTextView = findViewById(R.id.infoTextView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainHandler = new Handler(Looper.getMainLooper());

        String description = "Различия и порядок запуска:\n" +
                "1) runOnUiThread(Runnable) — запускает Runnable в UI потоке немедленно, если уже в UI потоке, " +
                "иначе ставит в очередь UI потока.\n" +
                "2) post(Runnable) — ставит Runnable в очередь обработки View в UI потоке, выполняется после текущих задач.\n" +
                "3) postDelayed(Runnable, delay) — ставит Runnable в очередь UI с задержкой delay мс.\n\n" +
                "Далее смотрим порядок выполнения этих методов из фонового потока:\n";

        infoTextView.setText(description);
        Log.d(TAG, "Запуск теста из фонового потока");

        // Основной тест запуска runnables с логами
        new Thread(() -> {
            runOnUiThread(() -> {
                appendText("runOnUiThread выполнен");
                Log.d(TAG, "runOnUiThread выполнен");
            });

            infoTextView.post(() -> {
                appendText("post выполнен");
                Log.d(TAG, "post выполнен");
            });

            infoTextView.postDelayed(() -> {
                appendText("postDelayed (500ms) выполнен");
                Log.d(TAG, "postDelayed (500ms) выполнен");
            }, 500);

            // Дополнительные Runnable с задержками и логированием
            final Runnable runn1 = () -> {
                appendText("runn1");
                Log.d(TAG, "runn1 выполнен");
            };
            final Runnable runn2 = () -> {
                appendText("runn2");
                Log.d(TAG, "runn2 выполнен");
            };
            final Runnable runn3 = () -> {
                appendText("runn3");
                Log.d(TAG, "runn3 выполнен");
            };

            Thread t = new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);  // Сразу после 2 секунд

                    TimeUnit.SECONDS.sleep(1);
                    infoTextView.postDelayed(runn3, 2000);  // Запускается с задержкой 2 сек после поста

                    infoTextView.post(runn2);  // В очередь после текущих
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG, "InterruptedException в потоке", e);
                }
            });

            t.start();

        }).start();
    }

    // Метод для безопасного добавления текста в TextView из UI потока
    private void appendText(String text) {
        Log.d(TAG, text);
        String current = infoTextView.getText().toString();
        infoTextView.setText(current + "\n" + text);
    }

    // Для добавления текста из фонового потока — через mainHandler
    private void appendTextFromBackground(String text) {
        mainHandler.post(() -> appendText(text));
    }
}
