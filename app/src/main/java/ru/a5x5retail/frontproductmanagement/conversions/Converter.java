package ru.a5x5retail.frontproductmanagement.conversions;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

    @BindingAdapter("android:text")
    public static void Date4TextView(TextView view, Date date) {
        if (date == null) return;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        view.setText(df.format(date));
    }
}
