package com.mirea.kabanovasvetlana.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {
    private EditText editTextBookName, editTextQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView textViewDevBook = findViewById(R.id.textViewDevBook);
        TextView textViewDevQuote = findViewById(R.id.textViewDevQuote);
        editTextBookName = findViewById(R.id.editTextBookName);
        editTextQuote = findViewById(R.id.editTextQuote);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String bookName = extras.getString(MainActivity.BOOK_NAME_KEY);
            String quote = extras.getString(MainActivity.QUOTES_KEY);
            textViewDevBook.setText("Любимая книга разработчика: " + bookName);
            textViewDevQuote.setText("Цитата из книги: " + quote);
        }
    }

    public void sendDataBack(View view) {
        String userBook = editTextBookName.getText().toString().trim();
        String userQuote = editTextQuote.getText().toString().trim();

        String resultMessage = "Название Вашей любимой книги:\n" + userBook + "\nЦитата:\n" + userQuote;

        Intent data = new Intent();
        data.putExtra(MainActivity.USER_MESSAGE, resultMessage);
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}

