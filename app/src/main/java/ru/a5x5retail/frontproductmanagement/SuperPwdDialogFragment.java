package ru.a5x5retail.frontproductmanagement;

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

import ru.a5x5retail.frontproductmanagement.settings.SettingsActivity;

import static ru.a5x5retail.frontproductmanagement.configuration.Constants.PWD_SI;
import static ru.a5x5retail.frontproductmanagement.configuration.Constants.PWD_SUPER;

public class SuperPwdDialogFragment extends DialogFragment {
    public SuperPwdDialogFragment() {
    }

    private SuperPwdDialogFragmentResult result;
    public SuperPwdDialogFragmentResult getResult() {
        return result;
    }
    public void setResult(SuperPwdDialogFragmentResult result) {
        this.result = result;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_super_pwd,container);
        init(view);
        getDialog().setTitle("Пароль:");
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

                if (pwd == null || !pwd.equals(PWD_SUPER)){
                    df_pwd_editText_1.setError("Пароль не подходит!");
                    return;
                }
                if (result != null){
                    result.onSuperPwdSuccess();
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
        if (context instanceof SuperPwdDialogFragmentResult){
            result = (SuperPwdDialogFragmentResult)context;
        }
    }

    public interface SuperPwdDialogFragmentResult {
        void onSuperPwdSuccess();
    }
}
