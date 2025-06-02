package com.mirea.kabanovasvetlana.lesson4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {

    public static final String ARG_WORD = "encrypted";
    public static final String ARG_KEY = "key";

    private byte[] encryptedText;
    private byte[] keyBytes;

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null) {
            encryptedText = args.getByteArray(ARG_WORD);
            keyBytes = args.getByteArray(ARG_KEY);
        }
    }

    @Override
    protected void onStartLoading() {
        if (encryptedText == null || keyBytes == null) {
            return;
        }
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        try {
            SecretKey originalKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
            return CryptoUtils.decryptMsg(encryptedText, originalKey);
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка дешифровки";
        }
    }
}

