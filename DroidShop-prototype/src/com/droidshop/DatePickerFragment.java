package com.droidshop;

import java.util.*;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		Button activityButton = (Button)getActivity().findViewById(R.id.btnDOB);
		String yy = Integer.toString(year);
		String mm = Integer.toString(monthOfYear+1);
		if(mm.length()==1){
			mm = "0" + mm;
		}
		String dd = Integer.toString(dayOfMonth);
		if (dd.length()==1){
			dd = "0" + dd;
		}
		activityButton.setText(yy + "/" + mm + "/" + dd);
	}
}
