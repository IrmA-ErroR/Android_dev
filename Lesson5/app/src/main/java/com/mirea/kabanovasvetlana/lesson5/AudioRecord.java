package com.mirea.kabanovasvetlana.lesson5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.mirea.kabanovasvetlana.lesson5.databinding.ActivityAudioRecordBinding;

import java.io.IOException;

public class AudioRecord extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 200;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String audioFilePath;
    private boolean isRecording = false;

    private ActivityAudioRecordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAudioRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.recordButton.setEnabled(false);
        binding.playButton.setEnabled(false);
        audioFilePath = getExternalFilesDir(null).getAbsolutePath() + "/recorded_audio.3gp";

        binding.recordButton.setOnClickListener(v -> {
            if (!isRecording) {
                startRecording();
            } else {
                stopRecording();
            }
        });

        binding.playButton.setOnClickListener(v -> playAudio());

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            binding.recordButton.setEnabled(true);
            binding.playButton.setEnabled(true);
        }
    }

    private void startRecording() {
        if (!checkPermissions()) {
            requestPermissions();
            return;
        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(audioFilePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            binding.recordButton.setText("Остановить запись");
            showToast("Запись начата");
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Ошибка записи");
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            binding.recordButton.setText("Начать запись");
            showToast("Запись завершена");
        }
    }

    private void playAudio() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            showToast("Воспроизведение...");
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Ошибка воспроизведения");
        }

        mediaPlayer.setOnCompletionListener(mp -> {
            mediaPlayer.release();
            showToast("Конец записи");
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешение получено", Toast.LENGTH_SHORT).show();
                binding.recordButton.setEnabled(true);
                binding.playButton.setEnabled(true);
            } else {
                Toast.makeText(this, "Разрешите в настройках", Toast.LENGTH_LONG).show();
                finish();
                }
            }
        }
    }
