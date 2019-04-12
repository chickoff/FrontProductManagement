package ru.a5x5retail.frontproductmanagement.conversions;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converter {

    @BindingAdapter("android:text")
    public static void Date4TextView(TextView view, Date date) {
        if (date == null) return;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        view.setText(df.format(date));
    }

    public static String QtyToString(BigDecimal qty) {
        Locale currentLocale = Locale.getDefault();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("0.000##",otherSymbols);
        return df.format(qty);
    }
}
