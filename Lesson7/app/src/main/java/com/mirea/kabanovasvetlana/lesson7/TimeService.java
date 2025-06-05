package com.mirea.kabanovasvetlana.lesson7;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TimeService extends AppCompatActivity {
    private static final String TAG = "TimeService";
    private TextView tvDate, tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_time_service);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnGetTime = findViewById(R.id.btnGetTime);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);

        btnGetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchTimeFromServer();
            }
        });
    }

    private void fetchTimeFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String response = TimeClient.getTimeFromServer();
                    Log.d(TAG, "Ответ сервера: " + response);
                    // исходная строка: 60831 25-06-05 07:27:40 50 0 0 387.5 UTC(NIST) *
                    final String[] parts = response.split(" ");
                    final String date = parts[1];
                    final String time = parts[2];

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvDate.setText("Дата: " + date);
                            tvTime.setText("Время: " + time);
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TimeService.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
}