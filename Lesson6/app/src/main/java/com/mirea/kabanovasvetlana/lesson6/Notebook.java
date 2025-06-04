package com.mirea.kabanovasvetlana.lesson6;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Notebook extends AppCompatActivity {
    private EditText fileNameEditText, quoteEditText;
    private final String directoryType = Environment.DIRECTORY_DOCUMENTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notebook);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fileNameEditText = findViewById(R.id.fileNameEditText);
        quoteEditText = findViewById(R.id.quoteEditText);

        findViewById(R.id.saveButton).setOnClickListener(v -> saveToFile());
        findViewById(R.id.loadButton).setOnClickListener(v -> loadFromFile());

        // Проверка разрешений
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
    }

    private void saveToFile() {
        String fileName = fileNameEditText.getText().toString();
        String quote = quoteEditText.getText().toString();

        File path = Environment.getExternalStoragePublicDirectory(directoryType);
        File file = new File(path, fileName);

        try {
            path.mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.write(quote);
            writer.close();
            Toast.makeText(this, "Сохранено в файл: " + fileName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("FileSave", "Ошибка: " + e.getMessage());
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        String fileName = fileNameEditText.getText().toString();
        File path = Environment.getExternalStoragePublicDirectory(directoryType);
        File file = new File(path, fileName);

        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            quoteEditText.setText(builder.toString().trim());
            Toast.makeText(this, "Файл загружен", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("FileRead", "Ошибка чтения: " + e.getMessage());
            Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show();
        }
    }
}
