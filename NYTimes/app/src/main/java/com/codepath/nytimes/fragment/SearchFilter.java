package com.codepath.nytimes.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.codepath.nytimes.R;

import static com.codepath.nytimes.R.id.btnSave;

/**
 * Created by manoj on 9/23/17.
 */

public class SearchFilter extends DialogFragment{

    public interface SearchFilterDialogueListener {
        void onSaveSearchFilterDone(String param1, String param2, String param3);
    }

    SearchFilterDialogueListener listener;

    private CompoundButton.OnCheckedChangeListener checkedChangeListener;

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

                listener.onSaveSearchFilterDone(param1,param2,param3);
                dismiss();
            }
        });

        setupCheckboxes(view);




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




}
