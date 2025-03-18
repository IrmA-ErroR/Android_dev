package com.mirea.kabanovasvetlana.dialog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;
import android.widget.TimePicker;
import java.util.Calendar;

public class MyTimeDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance(); // текущее время
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(getActivity(), "Вы выбрали время: " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
    }
}

