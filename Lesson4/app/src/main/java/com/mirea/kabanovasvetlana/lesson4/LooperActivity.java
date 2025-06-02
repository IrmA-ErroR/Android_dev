package com.mirea.kabanovasvetlana.lesson4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

public class LooperActivity extends AppCompatActivity {
    private static final String TAG = "LooperActivity";

    private Button btnEnterInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);

        btnEnterInfo = findViewById(R.id.btnEnterInfo);

        btnEnterInfo.setOnClickListener(v -> showAgeInputDialog());
    }

    private void showAgeInputDialog() {
        final EditText inputAge = new EditText(this);
        inputAge.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        inputAge.setHint("Введите ваш возраст");

        new AlertDialog.Builder(this)
                .setTitle("Возраст")
                .setMessage("Введите ваш возраст (кол-во лет)")
                .setView(inputAge)
                .setPositiveButton("OK", (dialog, which) -> {
                    String ageStr = inputAge.getText().toString().trim();
                    if (!ageStr.isEmpty()) {
                        try {
                            int age = Integer.parseInt(ageStr);
                            showProfessionInputDialog(age);
                        } catch (NumberFormatException e) {
                            Log.d(TAG, "Ошибка ввода возраста");
                        }
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void showProfessionInputDialog(int age) {
        final EditText inputProfession = new EditText(this);
        inputProfession.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
        inputProfession.setHint("Введите вашу профессию");

        new AlertDialog.Builder(this)
                .setTitle("Профессия")
                .setMessage("Введите вашу профессию")
                .setView(inputProfession)
                .setPositiveButton("OK", (dialog, which) -> {
                    String profession = inputProfession.getText().toString().trim();
                    if (!profession.isEmpty()) {
                        runWithDelay(age, profession);
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void runWithDelay(int age, String profession) {
        Log.d(TAG, "Ожидание " + age + " секунд");

        new Thread(() -> {
            try {
                Thread.sleep(age * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "Возраст: " + age + ", Профессия: " + profession);
        }).start();
    }
}
