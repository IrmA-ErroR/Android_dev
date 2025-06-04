package com.mirea.kabanovasvetlana.mireaproject;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.widget.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FilesFragment extends Fragment {

    private LinearLayout filesLayout;
    private FloatingActionButton fabAddFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_files, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        filesLayout = view.findViewById(R.id.filesLayout);
        fabAddFile = view.findViewById(R.id.fabAddFile);

        fabAddFile.setOnClickListener(v -> showAddFileDialog());

        displayFileList();
    }

    private void displayFileList() {
        filesLayout.removeAllViews();
        String[] files = requireContext().fileList();
        Arrays.sort(files);
        for (String filename : files) {
            addFileItem(filename);
        }
    }

    private void addFileItem(String filename) {
        TextView fileItem = new TextView(requireContext());
        fileItem.setText(filename);
        fileItem.setTextSize(18);
        fileItem.setPadding(16, 16, 16, 16);
        fileItem.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

        fileItem.setOnClickListener(v -> showFileDialog(filename));

        filesLayout.addView(fileItem);
    }

    private void showAddFileDialog() {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_file, null);

        EditText fileNameInput = dialogView.findViewById(R.id.editTextFileName);
        EditText fileContentInput = dialogView.findViewById(R.id.editTextFileContent);

        new AlertDialog.Builder(requireContext())
                .setTitle("Добавить файл")
                .setView(dialogView)
                .setPositiveButton("Сохранить", (dialog, which) -> {
                    String fileName = fileNameInput.getText().toString();
                    String content = fileContentInput.getText().toString();
                    saveFile(fileName, content);
                    displayFileList();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void showFileDialog(String filename) {
        String content = readFile(filename);
        if (content == null) return;

        new AlertDialog.Builder(requireContext())
                .setTitle("Файл: " + filename)
                .setMessage(content)
                .setPositiveButton("Зашифровать", (dialog, which) -> {
                    String encrypted = Base64.encodeToString(content.getBytes(), Base64.DEFAULT);
                    saveFile(filename, encrypted);
                    Toast.makeText(requireContext(), "Файл зашифрован", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Расшифровать", (dialog, which) -> {
                    try {
                        byte[] decoded = Base64.decode(content, Base64.DEFAULT);
                        saveFile(filename, new String(decoded));
                        Toast.makeText(requireContext(), "Файл расшифрован", Toast.LENGTH_SHORT).show();
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(requireContext(), "Не удалось расшифровать", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("Закрыть", null)
                .show();
    }

    private void saveFile(String filename, String content) {
        try (FileOutputStream fos = requireContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile(String filename) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(requireContext().openFileInput(filename)))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line).append("\n");
            return builder.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Ошибка чтения файла", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}