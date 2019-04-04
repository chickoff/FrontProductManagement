package ru.a5x5retail.frontproductmanagement.checkinglistinc.dialogs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;

public class CheckingListCangeDateOfManufactorDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialogfragment_checking_list_date_of_manufactor, container, false);
        initUi(view);
        return view;


    }

    private DatePicker date_picker_1;
    private TextView action_ok, action_cancel;
    private Date selectedDate;
    private void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    private INewDateListener mListener;
    public void setNewDateListener(INewDateListener mListener){
        this.mListener = mListener;
    }

    private CheckingListPosition position;
    public void setPosition(CheckingListPosition position) {
        this.position = position;
    }

    private void initUi(View view ) {

         Calendar today = Calendar.getInstance();

         date_picker_1 = view.findViewById(R.id.date_picker_1);

         date_picker_1.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                 today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                     @Override
                     public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                         Calendar c = Calendar.getInstance();
                         c.set(year, monthOfYear, dayOfMonth);
                         setSelectedDate(c.getTime());
                     }
                 });


         action_ok = view.findViewById(R.id.action_ok);
         action_ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (mListener != null) {

                    if (selectedDate == null) {
                        Calendar c = Calendar.getInstance();
                        c.set(date_picker_1.getYear(), date_picker_1.getMonth(), date_picker_1.getDayOfMonth());
                        selectedDate = c.getTime();
                    }
                     mListener.onNewDate(position,selectedDate);
                 }
                dismiss();
             }
         });
         action_cancel = view.findViewById(R.id.action_cancel);
         action_cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (mListener != null) {
                     mListener.onCancel();
                 }
                dismiss();
             }
         });
     }


    @Override
    public void onStop() {
        super.onStop();
        dismiss();
    }

    public interface INewDateListener {
        void onNewDate(CheckingListPosition position, Date date);

        void onCancel();
    }
}
