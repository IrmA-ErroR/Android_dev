package com.mirea.kabanovasvetlana.lesson6;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileOutputStream;

public class InternalFileStorage extends AppCompatActivity {
    private EditText inputField;
    private TextView outputText;
    private Button saveButton;
    private final String fileName = "importaint_date.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_internal_file_storage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inputField = findViewById(R.id.editTextInput);
        outputText = findViewById(R.id.textViewOutput);
        saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(v -> saveToFile());
        readFromFile();
    }

    private void saveToFile() {
        String text = inputField.getText().toString();

        if (text.isEmpty()) {
            Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(text.getBytes());
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }
    private void readFromFile() {
        try (FileInputStream fis = openFileInput(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            outputText.setText(sb.toString().trim());

        } catch (IOException e) {
            outputText.setText("Файл пока не создан");
        }
    }
}