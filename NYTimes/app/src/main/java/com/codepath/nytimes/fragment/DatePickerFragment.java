
package com.codepath.nytimes.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.app.Dialog;
import android.app.Dialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;  // do not import java.icu.utils.Calendar

import static android.content.ContentValues.TAG;

//public class DatePickerFragment extends DialogFragment {
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the current time as the default values for the picker
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//
//        // Activity needs to implement this interface
////        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getFragmentManager();
//
//        SearchFilter. listener = (SearchFilter) getTargetFragment();
//
//
//        // Create a new instance of TimePickerDialog and return it
//        return new DatePickerDialog(getActivity(), listener, year, month, day);
//    }
//}


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public interface DatePickerDialogListener {
        void onDateSelected(String date);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(c.getTime());
        Log.d(TAG, "Formatted Date String is : "+formattedDate);
        // call back to the parent fragment
        DatePickerDialogListener listener = (DatePickerDialogListener) getTargetFragment();
        listener.onDateSelected(formattedDate);
    }
}