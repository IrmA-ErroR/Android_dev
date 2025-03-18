package com.mirea.kabanovasvetlana.dialog;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends DialogFragment {

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Загрузка...");
        progressDialog.setCancelable(false);  // Не закрывается по нажатию вне окна
        return progressDialog;
    }
}

