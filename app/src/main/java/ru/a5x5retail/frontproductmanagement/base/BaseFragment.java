package ru.a5x5retail.frontproductmanagement.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.lang.reflect.Array;

public class BaseFragment extends Fragment {




    private String STATE_FRAGMENT = "STATE_FRAGMENT";
    private int STATE_FRAGMENT_VALUE = 0;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            STATE_FRAGMENT_VALUE = savedInstanceState.getInt(STATE_FRAGMENT);
        }

    }


    private static boolean firstStart = true;
    public static boolean isFirstStart() {
        return firstStart;
    }

    public static void setFirstStart(boolean firstStart) {
        BaseFragment.firstStart = firstStart;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (STATE_FRAGMENT_VALUE == 0){
            onFirstInit();
            STATE_FRAGMENT_VALUE = 1;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (getActivity().isChangingConfigurations()) {
            firstStart = false;
        }  else {
            firstStart = true;
        }
    }

    protected void onFirstInit() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState == null) return;
        outState.putInt(STATE_FRAGMENT,STATE_FRAGMENT_VALUE);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        firstStart = false;
    }

}
