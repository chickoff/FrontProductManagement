package ru.a5x5retail.frontproductmanagement.inventories.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.CheckingListIncActivity;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.document.DictionaryGoodsActivity;
import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.ActualAssortmentFilterFragment;
import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.IActualAssortmentFilterCallListener;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.FilterActivity;
import ru.a5x5retail.frontproductmanagement.filters.skufilter.SkuFilterActivity;
import ru.a5x5retail.frontproductmanagement.interfaces.IReceiveScanerMessageListener;
import ru.a5x5retail.frontproductmanagement.inventories.InventoriesActivity;
import ru.a5x5retail.frontproductmanagement.inventories.dialogs.InventoryChangeQtyValueDialogFragment;
import ru.a5x5retail.frontproductmanagement.inventories.model.IInventoryUoWListener;
import ru.a5x5retail.frontproductmanagement.inventories.model.InputTatem;
import ru.a5x5retail.frontproductmanagement.inventories.model.InventoryUoW;
import ru.a5x5retail.frontproductmanagement.inventories.presenters.InventoryScanGoodsPresenter;
import ru.a5x5retail.frontproductmanagement.inventories.view.IInventoryScanGoodsView;

import static android.app.Activity.RESULT_OK;


public class InventoryScanGoodsFragment extends BaseFragment implements IInventoryScanGoodsView {

    @InjectPresenter
    InventoryScanGoodsPresenter presenter;

    public InventoryScanGoodsFragment() {

    }

    public static InventoryScanGoodsFragment newInstance() {
        InventoryScanGoodsFragment fragment = new InventoryScanGoodsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_scan_goods, container, false);
        setHasOptionsMenu(true);
        initUi(view);
        return view;
    }


    IActualAssortmentFilterCallListener assortmentFilterCallListener;

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity instanceof InventoriesActivity) {
            ((InventoriesActivity)activity).setReceiveScanerMessageListener(new IReceiveScanerMessageListener() {
                @Override
                public void receiveMessage(String message) {
                    receiveBarcode(message);
                }
            });
        }


        if (activity instanceof IActualAssortmentFilterCallListener) {
            assortmentFilterCallListener = (IActualAssortmentFilterCallListener) activity;
        }

        attachEvents();
    }

    private void attachEvents() {

        if (edit_text_1 != null) {
            edit_text_1.addTextChangedListener(edit_text_1_text_watcher);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        detachEvents();
    }

    private void detachEvents() {
        if (edit_text_1 != null) {
            edit_text_1.removeTextChangedListener(edit_text_1_text_watcher);
        }
    }



    private void receiveBarcode(String message) {
        clearSearchTextBox();
        presenter.setBarcode(message);
    }



    FloatingActionButton fab;
    LinearLayout focusLayout;
    AutoCompleteTextView edit_text_1;
    ImageView image_view_1;
    RecyclerView recyclerView;
    InventoryScanGoodsAdapter adapter;
    private void initUi(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        InventoryScanGoodsViewHolderFactory factory = new InventoryScanGoodsViewHolderFactory();
        adapter = new InventoryScanGoodsAdapter();
        adapter.setHolderFactory(factory);
        adapter.setLayout(factory.getItemLayoutId());
        adapter.setAdapterListener(new IAdapterListener() {
            @Override
            public void onButtonClick(int position, CheckingListGoods goods) {
                presenter.inventoryUoW.setGoods(goods);
            }

            @Override
            public void onLongButtonClick(int position, CheckingListGoods goods) {
                presenter.inventoryUoW.setGoods(goods,0);
            }

            @Override
            public void onCheckboxChangeChecked(int position, CheckingListGoods goods) {

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edit_text_1.isFocused()){
                    clearSearchTextBox();
                }
                return false;
            }
        });

        edit_text_1 = view.findViewById(R.id.edit_text_1);
        image_view_1 = view.findViewById(R.id.image_view_1);
        image_view_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_text_1.setText("");
            }
        });
        focusLayout = view.findViewById(R.id.focusLayout);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.callActualAssortmentFilter();
            }
        });
    }


    TextWatcher edit_text_1_text_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            applyFilter();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void updateUi() {
        adapter.setRawList(presenter.getCheckingListGoods());
        applyFilter();
        adapter.notifyDataSetChanged();
    }

    private void applyFilter(){
        String filterStr = edit_text_1.getText().toString();
        adapter.getFilter().filter(filterStr);
    }


    private void clearSearchTextBox() {
        edit_text_1.setText("");
        edit_text_1.clearFocus();
        focusLayout.requestFocus();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 234) {

        }

        if (requestCode == 23) {
            if (resultCode == RESULT_OK) {
                addCodesByFilter(data);
            }
        }

       presenter.refresh();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addCodesByFilter(Intent data) {
        ArrayList<CodeInfo> vv = data.getParcelableArrayListExtra("RE");
        presenter.addCodes(vv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_delete) {
          presenter.deleteSelectedCodes();
        }


        if (id == R.id.action_dictionary) {

            Intent intentdict = new Intent(getActivity(), DictionaryGoodsActivity.class);
            intentdict.putExtra("checkingListHeadGUID",presenter.getCheckListInventory().checkingListHeadGuid);
            startActivityForResult(intentdict,234);
            return true;
        }


        if (id == R.id.m_find_code) {
            Intent intentdict = new Intent(getActivity(), FilterActivity.class);
            startActivityForResult(intentdict,23);

        }
        if (id == R.id.action_select_alll) {
            presenter.selectAllGoods();


            /*MsSqlConnection con = new MsSqlConnection();

            CheckingListGoodsCheckedAllQuery query1 = new CheckingListGoodsCheckedAllQuery(checkingListHeadGUID.toString());
            con.CallQuery(query1);
            viewModel.Load(checkingListHeadGUID);
            initRecyclerView();*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*********************************************************************************************/

    InventoryChangeQtyValueDialogFragment dialog;
    @Override
    public void openEditableDialog(InputTatem source) {
        dialog = new InventoryChangeQtyValueDialogFragment();
        FragmentManager fm = getFragmentManager();
        dialog.setInputTatem(source);
        dialog.setiNewQtyListener(new InventoryChangeQtyValueDialogFragment.INewQtyListener() {

            @Override
            public void onNewQty(InputTatem inputTatem, BigDecimal newQty) {
                presenter.inventoryUoW.setQtyValue(newQty);
            }

            @Override
            public void onCancel() {
                presenter.inventoryUoW.clear();
            }
        });
        dialog.show(fm,"ghh");
    }

    @Override
    public void closeEditableDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void callActualAssortmentFilter(ActualAssortmentFilterFragment.IActualAssortmentFilterResultListener resultListener) {
        if (assortmentFilterCallListener != null) {
            assortmentFilterCallListener.actualAssortmentFilterCall(resultListener);
        }
    }

    /*********************************************************************************************/

    public class InventoryScanGoodsAdapter extends BasicRecyclerViewAdapter<CheckingListGoods>
            implements Filterable {


        private IAdapterListener adapterListener;
        public void setAdapterListener(IAdapterListener adapterListener) {
            this.adapterListener = adapterListener;
        }

        @Override
        public void onBindViewHolder(@NonNull BasicViewHolder tBasicViewHolder, int i) {
            super.onBindViewHolder(tBasicViewHolder, i);
            InventoryScanGoodsViewHolder holder = (InventoryScanGoodsViewHolder) tBasicViewHolder;
            holder.setListener(new IViewHolderListener() {
                @Override
                public void onButtonClick(int position) {
                    adapterListener.onButtonClick(position,list.get(position));

                }

                @Override
                public void onLongButtonClick(int position) {
                    adapterListener.onLongButtonClick(position,list.get(position));
                }

                @Override
                public void onCheckboxChangeChecked(int position) {
                    list.get(position);
                }
            });
        }


        /***************************************************************************************/

        private List<CheckingListGoods> rawList;
        public void setRawList(List<CheckingListGoods> rawList) {
            this.rawList = rawList;
            setSourceList(rawList);
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    String charString = constraint.toString();
                    if (charString.isEmpty()) {
                        filterResults.values = rawList;
                    } else {
                        List<CheckingListGoods> filteredList = new ArrayList<>();
                        for (CheckingListGoods row : rawList) {
                            if (row.Code.toString().concat(row.NameLong).toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                        filterResults.values = filteredList;
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    List<CheckingListGoods> result = (ArrayList<CheckingListGoods>) results.values;
                    InventoryScanGoodsAdapter.this.setSourceList(result);
                    notifyDataSetChanged();
                }
            };

        /***************************************************************************************/
        }


    }

    public class InventoryScanGoodsViewHolder extends BasicViewHolder<CheckingListGoods> {

        private CheckBox tvCbox;
        private TextView tvCode,tvName;
        private Button tvBut;
        private CheckingListGoods source;
        private IViewHolderListener mListener;
        public void setListener(IViewHolderListener mListener) {
            this.mListener = mListener;
        }

        public InventoryScanGoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCbox = itemView.findViewById(R.id.tvCbox);
            tvCbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    source.Check = isChecked;
                }
            });
            tvCode = itemView.findViewById(R.id.tvCode);
            tvName = itemView.findViewById(R.id.tvName);
            tvBut = itemView.findViewById(R.id.tvBut);
            tvBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onButtonClick(getAdapterPosition());
                }
            });

            tvBut.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.onLongButtonClick(getAdapterPosition());
                    return true;
                }
            });
        }

        @Override
        public void setSource(CheckingListGoods source) {
            this.source = source;
            tvCbox.setChecked(source.Check);
            tvCode.setText(source.Code.toString());
            tvName.setText(source.NameLong);
            if (source.MeasureUnitIDD == 1) {
                tvBut.setText(source.Qty.setScale(3).toString());
            } else {
                tvBut.setText(source.Qty.setScale(0).toString());
            }

        }
    }

    public class InventoryScanGoodsViewHolderFactory extends BasicViewHolderFactory {

        public InventoryScanGoodsViewHolderFactory() {
            setItemLayoutId(R.layout.item_checking_list_goods);
        }

        @Override
        public RecyclerView.ViewHolder getNewInstance(View itemView) {
            return new InventoryScanGoodsViewHolder(itemView);
        }
    }

    public interface IViewHolderListener {
        void onButtonClick(int position);
        void onLongButtonClick(int position);
        void onCheckboxChangeChecked(int position);
    }

    public interface IAdapterListener {
        void onButtonClick(int position, CheckingListGoods goods);
        void onLongButtonClick(int position, CheckingListGoods goods);
        void onCheckboxChangeChecked(int position, CheckingListGoods goods);
    }

}
