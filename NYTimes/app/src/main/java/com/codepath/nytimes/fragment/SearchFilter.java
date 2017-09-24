package com.codepath.nytimes.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.nytimes.R;

import java.util.Calendar;

import static android.R.attr.value;
import static android.content.ContentValues.TAG;
import static com.codepath.nytimes.R.id.btnSave;

/**
 * Created by manoj on 9/23/17.
 */

public class SearchFilter extends DialogFragment implements DatePickerFragment.DatePickerDialogListener {


    @Override
    public void onDateSelected(String date) {

        Log.d(TAG, "on Date Selected and date is : "+date);
        this.date = date;
        dateSelect.setText(date);
        dateSelect.setFocusable(false);
    }

    public interface SearchFilterDialogueListener {
        void onSaveSearchFilterDone(String date, String sort, String param1, String param2, String param3);
    }

    SearchFilterDialogueListener listener;



    private CompoundButton.OnCheckedChangeListener checkedChangeListener;
    private  Spinner spinner;
    private String date;
    private  EditText dateSelect;

    private String param1, param2,param3;
    public SearchFilter() {

    }

    public static SearchFilter newInstance(String title) {
        SearchFilter frag = new SearchFilter();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public void setSearchFilterDialogueListener(SearchFilterDialogueListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean checked) {

                switch(view.getId()) {
                    case R.id.checkbox_art:
                        if (checked) {
                            param1 = getResources().getString(R.string.art);
                        } else {
                            param1 = "";
                        }
                        break;
                    case R.id.checkbox_fashion:
                        if (checked) {
                            param2 = getResources().getString(R.string.fashion);
                        } else {
                            param2 = "";
                        }
                        break;
                    case R.id.checkbox_sports:
                        if (checked) {
                            param3 = getResources().getString(R.string.sports);
                        } else {
                            param3 = "";
                        }
                        break;
                }

            }
        };


        Button btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sort = spinner.getSelectedItem().toString();

                listener.onSaveSearchFilterDone(date, sort, param1,param2,param3);
                dismiss();
            }
        });

        setupCheckboxes(view);


        // Spinner Item

        spinner = (Spinner) view.findViewById(R.id.spinnerSort);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);


        // Date Picker
        dateSelect  = (EditText) view.findViewById(R.id.etDateSelect);

        dateSelect.setInputType(InputType.TYPE_NULL);
        dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();


    }

    private void setupCheckboxes(View view){

        CheckBox art = view.findViewById(R.id.checkbox_art);

        CheckBox fashion = getView().findViewById(R.id.checkbox_fashion);

        CheckBox sports = getView().findViewById(R.id.checkbox_sports);

        art.setOnCheckedChangeListener(checkedChangeListener);
        fashion.setOnCheckedChangeListener(checkedChangeListener);
        sports.setOnCheckedChangeListener(checkedChangeListener);
    }


    private void showDatePickerDialog(View v) {
        Log.d(TAG, "showDatePickerDialog");
        FragmentManager fm = getFragmentManager();
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setTargetFragment(SearchFilter.this, 300);
        datePickerFragment.show(fm, "datePicker");
    }




}
