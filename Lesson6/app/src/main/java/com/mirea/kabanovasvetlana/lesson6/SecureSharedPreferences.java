package com.mirea.kabanovasvetlana.lesson6;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.SharedPreferences;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SecureSharedPreferences extends AppCompatActivity {
    private static final String SECURE_PREFS_NAME = "secure_prefs";
    private static final String KEY_POET_NAME = "poet_name";
    private static final String KEY_POET_IMAGE = "poet_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_secure_shared_preferences);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView nameTextView = findViewById(R.id.poetNameTextView);
        ImageView imageView = findViewById(R.id.poetImageView);

        try {
            // мастер-ключ
            MasterKey masterKey = new MasterKey.Builder(this)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            SharedPreferences securePrefs = EncryptedSharedPreferences.create(
                    this,
                    SECURE_PREFS_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // запись при первом запуске
            if (!securePrefs.contains(KEY_POET_NAME)) {
                SharedPreferences.Editor editor = securePrefs.edit();
                editor.putString(KEY_POET_NAME, "Vladimir Mayakovsky");
                editor.putInt(KEY_POET_IMAGE, R.drawable.vladimir_mayakovsky);
                editor.apply();
            }

            // отображение
            nameTextView.setText(securePrefs.getString(KEY_POET_NAME, ""));
            imageView.setImageResource(securePrefs.getInt(KEY_POET_IMAGE, R.drawable.ic_launcher_foreground));

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}