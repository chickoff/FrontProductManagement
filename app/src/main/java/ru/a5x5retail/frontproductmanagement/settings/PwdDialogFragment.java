package ru.a5x5retail.frontproductmanagement.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.a5x5retail.frontproductmanagement.R;
import static ru.a5x5retail.frontproductmanagement.configuration.Constants.PWD_SI;

public class PwdDialogFragment extends DialogFragment {
    public PwdDialogFragment() {
    }

    private SettingsActivity result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_settings_pwd,container);
        init(view);
        getDialog().setTitle("Пароль SI");
        return view;
    }


    EditText df_pwd_editText_1;



    private void init(View view){
        df_pwd_editText_1 = view.findViewById(R.id.df_pwd_editText_1);

        Button df_pwd_button_1 = view.findViewById(R.id.df_pwd_button_1);
        df_pwd_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pwd = df_pwd_editText_1.getText().toString();

                if (pwd == null || !pwd.equals(PWD_SI)){
                    df_pwd_editText_1.setError("Дичь какая-то!");
                    return;
                }
                if (result != null){
                    result.OnSetDialogResult();
                    dismiss();
                }
            }
        });
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SettingsActivity){
            result = (SettingsActivity)context;
        }
    }
}
