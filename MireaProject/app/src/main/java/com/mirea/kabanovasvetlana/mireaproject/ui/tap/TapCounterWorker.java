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

    public TapCounterWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        int repeatCount = getInputData().getInt(COUNTER_KEY, 0);
        repeatCount++;
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("TapPrefs", Context.MODE_PRIVATE);
        int counter = prefs.getInt(COUNTER_KEY, 0);
        counter++;
        prefs.edit().putInt(COUNTER_KEY, counter).apply();
        Log.d("TapGame", "Фоновая задача: счетчик = " + counter);
        if (repeatCount < 10) {
            Data inputData = new Data.Builder()
                    .putInt(COUNTER_KEY, repeatCount)
                    .build();
            OneTimeWorkRequest nextWork = new OneTimeWorkRequest.Builder(TapCounterWorker.class)
                    .setInitialDelay(10, TimeUnit.SECONDS)
                    .build();
            WorkManager.getInstance(getApplicationContext()).enqueue(nextWork);
        } else {
            Log.d("TapCounterWorker", "Фоновая задача остановлена");
        }

        return Result.success();
    }
}