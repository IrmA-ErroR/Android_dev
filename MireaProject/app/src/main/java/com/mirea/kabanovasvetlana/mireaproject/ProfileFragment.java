package com.mirea.kabanovasvetlana.mireaproject;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private EditText nameEditText, mailEditText, hobbyEditText;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        nameEditText = view.findViewById(R.id.editTextName);
        mailEditText = view.findViewById(R.id.editTextMail);
        hobbyEditText = view.findViewById(R.id.editTextHobby);
        saveButton = view.findViewById(R.id.buttonSave);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());

        // Загрузка сохранённых данных
        nameEditText.setText(prefs.getString("profile_name", ""));
        mailEditText.setText(prefs.getString("profile_male", ""));
        hobbyEditText.setText(prefs.getString("profile_hobby", ""));

        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("profile_name", nameEditText.getText().toString());
            editor.putString("profile_mail", mailEditText.getText().toString());
            editor.putString("profile_hobby", hobbyEditText.getText().toString());
            editor.apply();
        });
    }
}
