package com.mirea.kabanovasvetlana.dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;
import android.widget.DatePicker;
import java.util.Calendar;

public class MyDateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance(); // текущая дата
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Toast.makeText(getActivity(), "Вы выбрали дату: " + dayOfMonth + "/" + (month+1) + "/" + year, Toast.LENGTH_SHORT).show();
    }
}

