package com.mirea.kabanovasvetlana.mireaproject.ui.tap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.work.Data;
import androidx.fragment.app.Fragment;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.mirea.kabanovasvetlana.mireaproject.R;

import java.util.concurrent.TimeUnit;

public class TapFragment extends Fragment {

    private TextView counterTextView;
    private Button tapButton;
    private SharedPreferences sharedPreferences;
    private Handler handler;

    private static final String COUNTER_KEY = "tap_counter";
    private static final String ENABLE_WORK_KEY = "enable_background_task";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tap, container, false);

        counterTextView = view.findViewById(R.id.counterText);
        tapButton = view.findViewById(R.id.tapButton);
        sharedPreferences = requireActivity().getSharedPreferences("TapPrefs", Context.MODE_PRIVATE);

        handler = new Handler(Looper.getMainLooper());
        handler.post(updateRunnable);

        // Запуск фоновой задачи если true
        boolean enableWork = sharedPreferences.getBoolean(ENABLE_WORK_KEY, false);
        if (enableWork) {
            scheduleRepeatingWork(0);
        }

        tapButton.setOnClickListener(v -> {
            int counter = sharedPreferences.getInt(COUNTER_KEY, 0) + 1;
            sharedPreferences.edit().putInt(COUNTER_KEY, counter).apply();
            updateCounterView(counter);
            Log.d("TapGame", "Нажатие: счетчик = " + counter);
        });

        return view;
    }

    private void updateCounterView(int counter) {
        counterTextView.setText("Счёт: " + counter);
    }

    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            int counter = sharedPreferences.getInt(COUNTER_KEY, 0);
            updateCounterView(counter);
            handler.postDelayed(this, 100);
        }
    };

    private void scheduleRepeatingWork(int repeatCount) {
        Data inputData = new Data.Builder()
                .putInt(COUNTER_KEY, repeatCount)
                .build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(TapCounterWorker.class)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(requireContext())
                .enqueueUniqueWork("tap_counter_work", ExistingWorkPolicy.REPLACE, workRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(updateRunnable);
    }
}
