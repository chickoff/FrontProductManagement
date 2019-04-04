package ru.a5x5retail.frontproductmanagement.printprice.document;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceItem;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IPPChecked;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IRecyclerViewItemClick;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IViewHolderClickPosition;

public class PrintPriceItemRvAdapter extends RecyclerView.Adapter<PrintPriceItemRvVievHolder>
        implements IViewHolderClickPosition
        , IPPChecked
{

    private List<PrintPriceItem> items;
    // private Context cnt;

    IRecyclerViewItemClick<PrintPriceItem> listener;


    public PrintPriceItemRvAdapter (List<PrintPriceItem> items, IRecyclerViewItemClick<PrintPriceItem> listener) {
        this.items = items;
        this.listener = listener;



    }


    @NonNull
    @Override
    public PrintPriceItemRvVievHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_print_price,parent,false);
        PrintPriceItemRvVievHolder hh = new PrintPriceItemRvVievHolder(view, this);
        hh.setmPPChecked(this);
        return hh;
    }

    @Override
    public void onBindViewHolder(@NonNull PrintPriceItemRvVievHolder holder, int position) {

        holder.resListenerOff();

        PrintPriceItem item = items.get(position);
        holder.itemView.setTag(item);
        holder.gettvCode().setText(item.Code.toString());
        holder.gettvName().setText(item.NameLong);
        holder.gettvBut().setText(item.Qty.toString());
        holder.gettvCbox().setChecked(item.Check);
        holder.gettPriceTextView().setText(String.valueOf(item.Price.setScale(2)));


        holder.resListenerOn();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    @Override
    public void OnClickPosition(int pos) {
        PrintPriceItem item = items.get(pos);
        if (listener != null){
            listener.OnClick(pos,item);
        }

    }

    @Override
    public void OnPPItemClick(int pos, boolean isChecked) {
        PrintPriceItem item = items.get(pos);
        item.Check=isChecked;

    }


}
