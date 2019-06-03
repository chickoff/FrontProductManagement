package ru.a5x5retail.frontproductmanagement.filters.assortmentfilter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.db.models.DictionaryListGoods;
import ru.a5x5retail.frontproductmanagement.interfaces.IDGChecked;
import ru.a5x5retail.frontproductmanagement.interfaces.IRecyclerViewItemClick;
import ru.a5x5retail.frontproductmanagement.interfaces.IViewHolderClickPosition;

public class AssortmentFilterFragment extends BaseFragment
implements IAssortmentFilterView {

    public AssortmentFilterFragment() {

    }


    public static AssortmentFilterFragment newInstance() {
        AssortmentFilterFragment fragment = new AssortmentFilterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assortment_filter, container, false);
        initUi(view);
        return view;
    }

    @InjectPresenter
    AssortmentListenerPresenter presenter;

    private EditText editText;
    private RecyclerView recyclerViewDict;
    BasicRecyclerViewAdapter<DictionaryListGoods> adapter;
    private void initUi(View view) {
        editText = view.findViewById(R.id.searchEditText);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
                    presenter.Load(editText.getText().toString());
                }
                return false;
            }
        });


        recyclerViewDict = view.findViewById(R.id.recyclerViewDict);
        adapter = new BasicRecyclerViewAdapter<>();
        adapter.setHolderFactory(new PositionViewHolderFactory());
        adapter.setLayout(R.layout.item_assortment_filter);
        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<DictionaryListGoods>() {
            @Override
            public void OnShortClick(int pos, View view, DictionaryListGoods innerItem) {
                if (resultListener != null) {
                    resultListener.assortmentFilterResult(innerItem);
                }
            }
        });
        recyclerViewDict.setAdapter(adapter);
    }


    IAssortmentFilterResultListener resultListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IAssortmentFilterResultListener) {
            resultListener = ((IAssortmentFilterResultListener)context);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void updateView() {
        adapter.setSourceList(presenter.list);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onKeyUp(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
            presenter.Load(editText.getText().toString());
        }
    }

    /*******************************************************************************************************/

    public class PositionViewHolder extends BasicViewHolder<DictionaryListGoods>{

    public PositionViewHolder(@NonNull View itemView) {
        super(itemView);
        tvCode = itemView.findViewById(R.id.tvCodeD);
        tvName = itemView.findViewById(R.id.tvNameD);
    }

    private TextView tvCode;
    private TextView tvName;


    @Override
    public void setSource(DictionaryListGoods source) {
        tvCode.setText(source.Code.toString());
        tvName.setText(source.NameLong);
    }
}

/*******************************************************************************************************/

    public class PositionViewHolderFactory extends BasicViewHolderFactory {
        public PositionViewHolderFactory() {
            setItemLayoutId(R.layout.item_assortment_filter);
        }

        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new PositionViewHolder(itemView);
        }
    }

/*******************************************************************************************************/
/*
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


        @Override
        public DictionaryGoodsRvVievHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.item_dictonary_list_goods,viewGroup,false);
            DictionaryGoodsRvVievHolder hh = new DictionaryGoodsRvVievHolder(view);
            hh.setmDGChecked(this);
            return hh;
        }

        @Override
        public void onBindViewHolder( DictionaryGoodsRvVievHolder dictionaryGoodsRvVievHolder, int position) {

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

   *//* @Override
    public void OnDGItemClick(int pos, boolean isChecked) {

        DictionaryListGoods item = items.get(pos);
        item.Check=isChecked;
    }*//*
    }*/

    /***********************************************************************************************/

 /*   public class DictionaryGoodsRvVievHolder extends RecyclerView.ViewHolder {

        private TextView tvCode;
        private TextView tvName;

        private CheckBox tvCbox;
        //  private IViewHolderClickPosition buttonClickListener;
        //  private IPPChecked mPPChecked;

        private IDGChecked mDGChecked;

        public DictionaryGoodsRvVievHolder( View itemView) {
            super(itemView);

            tvCode = itemView.findViewById(R.id.tvCodeD);
            tvName = itemView.findViewById(R.id.tvNameD);

            tvCbox=itemView.findViewById(R.id.tvCboxD);
            tvCbox.setOnCheckedChangeListener(myCheckChangList);

        }

        public void EnableListener()
        {
            tvCbox.setOnCheckedChangeListener(myCheckChangList);
        }

        public void DisableListener() {
            tvCbox.setOnCheckedChangeListener(null);
        }


        public TextView gettvCode() {
            return tvCode;
        }

        public TextView gettvName() {
            return tvName;
        }


        public CheckBox gettvCbox() {
            return tvCbox;
        }

*//*
        // обработчик для чекбоксов
        CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // меняем данные товара
                mDGChecked.OnDGItemClick(getAdapterPosition(),isChecked);

          /*  DictionaryListGoods  ddd = (DictionaryListGoods) itemView.getTag();

          MsSqlConnection con = new MsSqlConnection();
            PrintPriceItemCheckedQuery query2 = new PrintPriceItemCheckedQuery(ddd.HeaderGuid
                    ,ddd.Code
                    ,isChecked);
            con.CallQuery(query2);

            mPPChecked.OnPPItemClick(getAdapterPosition(),isChecked);*//*
                //отослать команду в адаптер
      *//*      }
        };

        *//*

        public void setmDGChecked(IDGChecked mDGChecked) {
            this.mDGChecked = mDGChecked;
        }





    }*/

public interface IAssortmentFilterResultListener{
        void assortmentFilterResult(DictionaryListGoods sku);
    }

}
