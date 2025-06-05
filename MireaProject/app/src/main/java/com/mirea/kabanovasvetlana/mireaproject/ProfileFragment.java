package com.mirea.kabanovasvetlana.mireaproject;

import android.content.Intent;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mirea.kabanovasvetlana.mireaproject.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private EditText nameEditText, mailEditText, hobbyEditText;
    private Button saveButton, signOutButton;
    private TextView firebaseEmailText;


    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());

        // Загрузка сохранённых данных
        binding.editTextName.setText(prefs.getString("profile_name", ""));
        binding.editTextMail.setText(prefs.getString("profile_mail", ""));
        binding.editTextHobby.setText(prefs.getString("profile_hobby", ""));

        // Сохранение данных
        binding.buttonSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("profile_name", binding.editTextName.getText().toString());
            editor.putString("profile_mail", binding.editTextMail.getText().toString());
            editor.putString("profile_hobby", binding.editTextHobby.getText().toString());
            editor.apply();
        });

        // Получение пользователя из Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            binding.textFirebaseEmail.setText("Firebase email: " + user.getEmail());
        } else {
            binding.textFirebaseEmail.setText("Не вошли в аккаунт");
        }

        // Кнопка выхода
        binding.buttonSignOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}