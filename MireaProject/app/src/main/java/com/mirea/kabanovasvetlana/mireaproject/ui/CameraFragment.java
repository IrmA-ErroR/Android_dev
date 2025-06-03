package com.mirea.kabanovasvetlana.mireaproject.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.app.Activity;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirea.kabanovasvetlana.mireaproject.R;

public class CameraFragment extends Fragment {
    private final Bitmap[] photoSlots = new Bitmap[4];
    private int currentSlot = 0;

    private ImageView[] imageViews;
    private TextView statusText;
    private Button button;

    private final ActivityResultLauncher<Intent> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    if (currentSlot < 4) {
                        photoSlots[currentSlot] = imageBitmap;
                        imageViews[currentSlot].setImageBitmap(imageBitmap);
                        currentSlot++;

                        if (currentSlot < 4) {
//                            statusText.setText("Фото " + currentSlot + "/4 готово. Снимок следующий...");
                            openCamera(); // автоматически следующая
                        } else {
                            statusText.setText("Готово");
                            button.setEnabled(true);
                        }
                    }
                } else {
                    statusText.setText("Фото не сделано");
                    button.setEnabled(true);
                }
            });

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    startPhotoSequence();
                } else {
                    statusText.setText("Нет разрешения на камеру");
                    button.setEnabled(true);
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_camera, container, false);

        imageViews = new ImageView[]{
                root.findViewById(R.id.image1),
                root.findViewById(R.id.image2),
                root.findViewById(R.id.image3),
                root.findViewById(R.id.image4)
        };

        statusText = root.findViewById(R.id.textStatus);
        button = root.findViewById(R.id.btnOpenCamera);

        button.setOnClickListener(v -> {
            button.setEnabled(false);
            resetImages();
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                startPhotoSequence();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });

        return root;
    }

    private void startPhotoSequence() {
        currentSlot = 0;
        statusText.setText("Делаем фото 1 из 4...");
        openCamera();
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            takePictureLauncher.launch(takePictureIntent);
        } else {
            statusText.setText("Камера не найдена");
            button.setEnabled(true);
        }
    }

    private void resetImages() {
        for (ImageView imageView : imageViews) {
            imageView.setImageBitmap(null);
        }
    }
}
