package com.mirea.kabanovasvetlana.lesson4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javax.crypto.SecretKey;
import android.util.Base64;


public class CryptoLoader extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final int LOADER_ID = 1234;
    private static final String TAG = "CryptoLoader";
    private EditText editText;
    private Button button;
    private SecretKey secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crypto_loader);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.buttonEncrypt);

        secretKey = CryptoUtils.generateKey();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton(v);
            }
        });
    }

    public void onClickButton(View view) {
        String inputText = editText.getText().toString().trim();
        if (inputText.isEmpty()) {
            Toast.makeText(this, "Введите фразу", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            byte[] encrypted = CryptoUtils.encryptMsg(inputText, secretKey);
            String encryptedBase64 = Base64.encodeToString(encrypted, Base64.DEFAULT);
            Log.d(TAG, "Зашифрованное сообщение (Base64): " + encryptedBase64);

            Bundle bundle = new Bundle();
            bundle.putByteArray(MyLoader.ARG_WORD, encrypted);
            bundle.putByteArray("key", secretKey.getEncoded());

            LoaderManager.getInstance(this).restartLoader(LOADER_ID, bundle, this);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка шифрования", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == LOADER_ID) {
            Toast.makeText(this, "onCreateLoader: " + id, Toast.LENGTH_SHORT).show();
            return new MyLoader(this, args);
        }
        throw new IllegalArgumentException("Invalid loader id");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (loader.getId() == LOADER_ID) {
            Log.d(TAG, "onLoadFinished: " + data);
            Toast.makeText(this, "Расшифрованная фраза: " + data, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG, "onLoaderReset");
    }
}