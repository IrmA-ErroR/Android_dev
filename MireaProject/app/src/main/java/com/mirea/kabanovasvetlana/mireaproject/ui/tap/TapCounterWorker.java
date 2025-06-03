package com.mirea.kabanovasvetlana.mireaproject.ui.tap;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.work.Data;
import java.util.concurrent.TimeUnit;

public class TapCounterWorker extends Worker {

    private static final String COUNTER_KEY = "tap_counter";
    private static final String PREFS_NAME = "TapPrefs";
    private static final String BACKGROUND_ENABLED_KEY = "isBackgroundWorkEnabled";

    public TapCounterWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isBackgroundEnabled = prefs.getBoolean(BACKGROUND_ENABLED_KEY, false);

        if (!isBackgroundEnabled) {
            Log.d("TapGame", "Фоновая задача отключена");
            return Result.success(); // Просто завершить работу
        }

        int repeatCount = getInputData().getInt(COUNTER_KEY, 0);
        repeatCount++;

        int counter = prefs.getInt(COUNTER_KEY, 0);
        counter++;
        prefs.edit().putInt(COUNTER_KEY, counter).apply();
        Log.d("TapGame", "Фоновая задача: счётчик = " + counter);

        if (repeatCount < 10) {
            Data inputData = new Data.Builder()
                    .putInt(COUNTER_KEY, repeatCount)
                    .build();

            OneTimeWorkRequest nextWork = new OneTimeWorkRequest.Builder(TapCounterWorker.class)
                    .setInitialDelay(10, TimeUnit.SECONDS)
                    .setInputData(inputData)
                    .build();

            WorkManager.getInstance(getApplicationContext()).enqueue(nextWork);
        } else {
            Log.d("TapCounterWorker", "Фоновая задача завершена");
        }

        return Result.success();
    }
}