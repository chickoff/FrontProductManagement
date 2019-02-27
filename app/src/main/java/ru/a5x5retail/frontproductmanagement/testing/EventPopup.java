package ru.a5x5retail.frontproductmanagement.testing;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;


public class EventPopup extends PopupWindow {

    public EventPopup(int width, int height){
        LayoutInflater layoutInflater
                = (LayoutInflater) ProdManApp.getAppContext()
                .getSystemService(ProdManApp.getAppContext().LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_window, null);

        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventPopup.this.dismiss();
            }
        });

        setContentView(popupView);
        setWidth(width);
        setHeight(height);
        setFocusable(true);
        init();
    }

    private void init(){
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(android.R.style.Animation_Dialog);

    }
}
