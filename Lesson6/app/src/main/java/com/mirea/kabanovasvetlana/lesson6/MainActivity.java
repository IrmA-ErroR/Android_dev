package com.mirea.kabanovasvetlana.lesson6;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText groupNumberEditText, listNumberEditText, favoriteMovieEditText;
    Button saveButton;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_GROUP = "group_number";
    private static final String KEY_LIST = "list_number";
    private static final String KEY_MOVIE = "favorite_movie";

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
        groupNumberEditText = findViewById(R.id.groupNumberEditText);
        listNumberEditText = findViewById(R.id.listNumberEditText);
        favoriteMovieEditText = findViewById(R.id.favoriteMovieEditText);
        saveButton = findViewById(R.id.saveButton);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        groupNumberEditText.setText(prefs.getString(KEY_GROUP, ""));
        listNumberEditText.setText(prefs.getString(KEY_LIST, ""));
        favoriteMovieEditText.setText(prefs.getString(KEY_MOVIE, ""));

        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(KEY_GROUP, groupNumberEditText.getText().toString());
            editor.putString(KEY_LIST, listNumberEditText.getText().toString());
            editor.putString(KEY_MOVIE, favoriteMovieEditText.getText().toString());
            editor.apply(); // сохраняем
        });
    }
}