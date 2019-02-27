package ru.a5x5retail.frontproductmanagement.newdocumentmaster.inventorymaster.dialogfragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryList;
import ru.a5x5retail.frontproductmanagement.interfaces.IRecyclerViewItemClick;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.inventorymaster.InventoryMasterViewModel;

public class InventoryRvAdapter extends RecyclerView.Adapter<InventoryRvAdapter.Vho> {

    private InventoryMasterViewModel viewModel;

    IRecyclerViewItemClick<InventoryList> mListener;

    public InventoryRvAdapter(InventoryMasterViewModel viewModel,IRecyclerViewItemClick<InventoryList> mListener) {
        this.viewModel = viewModel;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public Vho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_dialog_rv,parent,false);
        return new Vho(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vho holder, int position) {
        holder.tv1.setText(viewModel.getInventoryList().get(position).nameLong);
    }

    @Override
    public int getItemCount() {
        if ( viewModel == null ) return 0;
        if ( viewModel.getInventoryList() == null ) return 0;
        return viewModel.getInventoryList().size();
    }

    public class Vho extends RecyclerView.ViewHolder{

        TextView tv1;

        public Vho(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.nameDocTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    mListener.OnClick(pos,viewModel.getInventoryList().get(pos));
                }
            });

        }
    }

}
