package ru.a5x5retail.frontproductmanagement.dictionarygoods.document;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.db.models.DictionaryListGoods;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.db.query.DictionaryListChekedItemSentCheckingListQuery;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.interfaces.IDGChecked;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.interfaces.IRecyclerViewItemClick;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.interfaces.IViewHolderClickPosition;

public class DictionaryGoodsRvAdapter extends RecyclerView.Adapter<DictionaryGoodsRvVievHolder>
        implements IViewHolderClickPosition
        , IDGChecked
{


    private List<DictionaryListGoods> items;
    IRecyclerViewItemClick<DictionaryListGoods> listener;
    private String checkingListHeadGUID;

    public DictionaryGoodsRvAdapter (List<DictionaryListGoods> items, IRecyclerViewItemClick<DictionaryListGoods> listener, String checkingListHeadGUID) {
        this.items = items;
        this.listener = listener;
        this.checkingListHeadGUID=checkingListHeadGUID;

    }

    @NonNull
    @Override
    public DictionaryGoodsRvVievHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_dictonary_list_goods,viewGroup,false);
        DictionaryGoodsRvVievHolder hh = new DictionaryGoodsRvVievHolder(view);
       hh.setmDGChecked(this);
        return hh;
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryGoodsRvVievHolder dictionaryGoodsRvVievHolder, int position) {

        dictionaryGoodsRvVievHolder.DisableListener();


        DictionaryListGoods item = items.get(position);
        dictionaryGoodsRvVievHolder.itemView.setTag(item);
        dictionaryGoodsRvVievHolder.gettvCode().setText(item.Code.toString());
        dictionaryGoodsRvVievHolder.gettvName().setText(item.NameLong);
       // dictionaryGoodsRvVievHolder.gettvBut().setText(item.Qty.toString());
        if(item.Check!=null) {
            dictionaryGoodsRvVievHolder.gettvCbox().setChecked(item.Check);
        }
        else
            {
                dictionaryGoodsRvVievHolder.gettvCbox().setChecked(false);
            }

        dictionaryGoodsRvVievHolder.EnableListener();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public void OnClickPosition(int pos) {
        DictionaryListGoods item = items.get(pos);
        if (listener != null){
            listener.OnClick(pos,item);
        }

    }

    @Override
    public void OnDGItemClick(int pos, boolean isChecked) {
        DictionaryListGoods item = items.get(pos);
       // if( item.Check!=isChecked) {
            item.Check = isChecked;

            MsSqlConnection con = new MsSqlConnection();
            DictionaryListChekedItemSentCheckingListQuery query2 = new DictionaryListChekedItemSentCheckingListQuery(checkingListHeadGUID
                    , item.Code);
            con.CallQuery(query2);
       // }
    }

   /* @Override
    public void OnDGItemClick(int pos, boolean isChecked) {

        DictionaryListGoods item = items.get(pos);
        item.Check=isChecked;
    }*/
}
