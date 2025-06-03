package com.mirea.kabanovasvetlana.mireaproject.ui;

import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import com.mirea.kabanovasvetlana.mireaproject.R;

public class PermissionsFragment extends Fragment {

    private TextView permissionsStatusTextView;
    private final String[] requiredPermissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.POST_NOTIFICATIONS
    };

    public PermissionsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permissions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        permissionsStatusTextView = view.findViewById(R.id.permissionsStatusTextView);
        updatePermissionsStatus();
    }

    private void updatePermissionsStatus() {
        StringBuilder builder = new StringBuilder();
        for (String permission : requiredPermissions) {
            String shortName = getPermissionName(permission);
            if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
                builder.append("✅ ").append(shortName).append(" — разрешение получено\n");
            } else {
                builder.append("❌ ").append(shortName).append(" — нет разрешения\n");
            }
        }
        permissionsStatusTextView.setText(builder.toString());
    }

    private String getPermissionName(String permission) {
        switch (permission) {
            case Manifest.permission.CAMERA:
                return "Камера";
            case Manifest.permission.RECORD_AUDIO:
                return "Микрофон";
            case Manifest.permission.POST_NOTIFICATIONS:
                return "Уведомления";
            default:
                return permission;
        }
    }
}
