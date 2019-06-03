package ru.a5x5retail.frontproductmanagement.dialogs.working;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.base.IAttachProcessFragmentDialog;

public class ProcessFragmentDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
                //do your stuff
            }

            @Override
            public void setOnCancelListener( DialogInterface.OnCancelListener listener) {
                super.setOnCancelListener(listener);
            }
        };
    }
private ImageButton dlg_cancel_btn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialog_process_fragment = inflater.inflate(R.layout.dialogfragment_process, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCancelable(false);

        dlg_cancel_btn = dialog_process_fragment.findViewById(R.id.dlg_cancel_btn);
        dlg_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelListener != null) {
                    cancelListener.onCancel();
                }
            }
        });


        return dialog_process_fragment;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().alpha = 0.9f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IAttachProcessFragmentDialog) {
            ((IAttachProcessFragmentDialog) context).attachProcessDialog(this);
        }
    }
    private IProcessDialogCancelListener cancelListener;



    public void setCancelListener(IProcessDialogCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public interface IProcessDialogCancelListener{
        void onCancel();
    }
}
