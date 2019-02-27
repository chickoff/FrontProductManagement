package ru.a5x5retail.frontproductmanagement.newdocumentmaster.inventorymaster.dialogfragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryList;
import ru.a5x5retail.frontproductmanagement.interfaces.IRecyclerViewItemClick;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.inventorymaster.InventoryMasterViewModel;

    public class InventoryDialogFragment extends DialogFragment {

        public void setViewModel(InventoryMasterViewModel viewModel) {
            this.viewModel = viewModel;
        }

        InventoryMasterViewModel viewModel;



        public void setListener(IRecyclerViewItemClick<InventoryList> mListener) {
            this.mListener = mListener;
        }

        IRecyclerViewItemClick<InventoryList> mListener;


        @Nullable
        @Override

        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            View v = inflater.inflate(R.layout.dialogfragment_inventory_master, container);
            init(v);
            initButton(v);

            return v;
        }

        @Override
        public void onDetach() {
            super.onDetach();
           // dismiss();
        }

        @Override
        public void onPause() {
            super.onPause();
            dismiss();
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {

        }

        private void init(View v){
            RecyclerView rv = v.findViewById(R.id.master_rv);
            rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            rv.setAdapter(new InventoryRvAdapter(viewModel,mListener));
        }

        Button btn;
       private void initButton(View v){
            btn = v.findViewById(R.id.dlg_cancel_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
       }


    private void initDialod(){
        Dialog d = getDialog();

    }



}
