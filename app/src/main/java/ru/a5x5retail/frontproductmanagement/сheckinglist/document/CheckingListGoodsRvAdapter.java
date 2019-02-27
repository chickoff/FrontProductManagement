package ru.a5x5retail.frontproductmanagement.сheckinglist.document;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IRecyclerViewItemClick;
import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IViewHolderClickPosition;

public class CheckingListGoodsRvAdapter extends RecyclerView.Adapter<CheckingListGoodsRvVievHolder>
implements IViewHolderClickPosition
{


    private List<CheckingListGoods> items;
   // private Context cnt;

    IRecyclerViewItemClick<CheckingListGoods> listener;


    public CheckingListGoodsRvAdapter (List<CheckingListGoods> items, IRecyclerViewItemClick<CheckingListGoods> listener) {
        this.items = items;
        this.listener = listener;
       // cnt=_cnt;

    }



    @NonNull
    @Override
    public CheckingListGoodsRvVievHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_checking_list_goods,parent,false);
        return new CheckingListGoodsRvVievHolder(view,this);
        //return new CheckingListGoodsRvVievHolder(view,cnt);
//return new CheckingListGoodsRvVievHolder(view,this);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckingListGoodsRvVievHolder holder, int position) {

        holder.ResListenerOff();

        CheckingListGoods item = items.get(position);
        holder.itemView.setTag(item);
        holder.gettvCode().setText(item.Code.toString());
        holder.gettvName().setText(item.NameLong);
        if(item.MeasureUnitIDD==1)
        {
            DecimalFormat df = new DecimalFormat("0.000##");
            holder.gettvBut().setText(df.format(item.Qty));
        }
        else
        {
            Integer valInt= item.Qty.toBigInteger().intValue();
            holder.gettvBut().setText( valInt.toString());
        }

        holder.gettvCbox().setChecked(item.Check);
        holder.ResListenerOn();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    @Override
    public void OnClickPosition(int pos) {
        CheckingListGoods item = items.get(pos);
        if (listener != null){
            listener.OnClick(pos,item);
        }




    }
}
