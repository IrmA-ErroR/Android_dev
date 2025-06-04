package com.mirea.kabanovasvetlana.mireaproject;

import android.os.Bundle;
import android.Manifest;
import androidx.fragment.app.Fragment;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MicrophoneFragment} factory method to
 * create an instance of this fragment.
 */
public class MicrophoneFragment extends Fragment {
    private static final int REQUEST_MICROPHONE_PERMISSION = 200;
    private MediaRecorder recorder;
    private boolean isRecording = false;
    private TextView statusText;
    private String fileName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_microphone, container, false);
        Button recordButton = root.findViewById(R.id.btnRecord);
        statusText = root.findViewById(R.id.textStatusMic);

        fileName = requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC) + "/audiorecord.3gp";

        recordButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                statusText.setText("Нет разрешения на использование микрофона");
            } else {
                toggleRecording();
            }
        });

        return root;
    }

    private void toggleRecording() {
        if (!isRecording) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(fileName);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                recorder.prepare();
                recorder.start();
                isRecording = true;
                statusText.setText("Идёт запись...");
            } catch (IOException e) {
                statusText.setText("Ошибка записи: " + e.getMessage());
            }
        } else {
            recorder.stop();
            recorder.release();
            recorder = null;
            isRecording = false;
            statusText.setText("Запись остановлена. Файл сохранён: " + fileName);
        }
    }
}