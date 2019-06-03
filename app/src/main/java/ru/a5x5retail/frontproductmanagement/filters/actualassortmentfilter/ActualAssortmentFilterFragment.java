package ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Date;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.db.models.DictionaryListGoods;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.db_local.loSkuContext;
import ru.a5x5retail.frontproductmanagement.filters.assortmentfilter.AssortmentListenerPresenter;
import ru.a5x5retail.frontproductmanagement.filters.assortmentfilter.IAssortmentFilterView;

public class ActualAssortmentFilterFragment extends BaseFragment
implements IActualAssortmentFilterView {

    public ActualAssortmentFilterFragment() {

    }

    IActualAssortmentFilterCloseListener filterCloseListener;

    ActualAssortmentFilterFragment.IActualAssortmentFilterResultListener resultListener;

    public static ActualAssortmentFilterFragment newInstance(IActualAssortmentFilterCloseListener filterCloseListener,ActualAssortmentFilterFragment.IActualAssortmentFilterResultListener resultListener) {
        ActualAssortmentFilterFragment fragment = new ActualAssortmentFilterFragment();
        fragment.filterCloseListener = filterCloseListener;
        fragment.resultListener = resultListener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_actual_assortment_filter, container, false);
        initUi(view);
        return view;
    }

    @InjectPresenter
    ActualAssortmentListenerPresenter presenter;

    private EditText editText;
    private RecyclerView recyclerViewDict;
    BasicRecyclerViewAdapter<loSkuContext> adapter;
    private void initUi(View view) {
        editText = view.findViewById(R.id.searchEditText);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateView();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
                   // presenter.Load(editText.getText().toString());
                }
                return false;
            }
        });


        recyclerViewDict = view.findViewById(R.id.recyclerViewDict);
        adapter = new BasicRecyclerViewAdapter<>();
        adapter.setHolderFactory(new PositionViewHolderFactory());
        adapter.setLayout(R.layout.item_assortment_filter);
        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<loSkuContext>() {
            @Override
            public void OnShortClick(int pos, View view, loSkuContext innerItem) {
                if (resultListener != null) {
                    resultListener.assortmentFilterResult(innerItem);
                }

                if (filterCloseListener != null) {
                    filterCloseListener.actualAssortmentFilterClose();
                }
            }
        });
        recyclerViewDict.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IActualAssortmentFilterResultListener) {
            resultListener = ((IActualAssortmentFilterResultListener)context);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void updateView() {
        adapter.setSourceList(presenter.getAssortmentList(editText.getText().toString()));
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onKeyUp(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
          //  presenter.Load(editText.getText().toString());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_actual_assortment_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.update_assortment :
                presenter.Load(new Date(2019,5,1));
                break;
        }
        return true;
    }





    /*******************************************************************************************************/

    public class PositionViewHolder extends BasicViewHolder<loSkuContext>{

    public PositionViewHolder(@NonNull View itemView) {
        super(itemView);
        tvCode = itemView.findViewById(R.id.tvCodeD);
        tvName = itemView.findViewById(R.id.tvNameD);
    }

    private TextView tvCode;
    private TextView tvName;


    @Override
    public void setSource(loSkuContext source) {
        tvCode.setText(Long.toString(source.Code));
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

public interface IActualAssortmentFilterResultListener{
        void assortmentFilterResult(loSkuContext sku);
    }

}
