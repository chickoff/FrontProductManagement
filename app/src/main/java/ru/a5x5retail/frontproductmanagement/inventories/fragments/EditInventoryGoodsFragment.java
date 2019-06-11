package ru.a5x5retail.frontproductmanagement.inventories.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.conversions.Converter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryCode;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.document.DictionaryGoodsActivity;
import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.ActualAssortmentFilterFragment;
import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.IActualAssortmentFilterCallListener;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.FilterActivity;
import ru.a5x5retail.frontproductmanagement.interfaces.IReceiveScanerMessageListener;
import ru.a5x5retail.frontproductmanagement.inventories.InventoriesActivity;
import ru.a5x5retail.frontproductmanagement.inventories.dialogs.InventoryChangeQtyValueDialogFragment;
import ru.a5x5retail.frontproductmanagement.inventories.model.InputTatem;
import ru.a5x5retail.frontproductmanagement.inventories.presenters.EditInventoryGoodsPresenter;
import ru.a5x5retail.frontproductmanagement.inventories.presenters.InventoryScanGoodsPresenter;
import ru.a5x5retail.frontproductmanagement.inventories.view.IEditInventoryGoodsView;
import ru.a5x5retail.frontproductmanagement.inventories.view.IInventoryScanGoodsView;

import static android.app.Activity.RESULT_OK;

/** это фрагмент, где происходит сканирование!!! **/
public class EditInventoryGoodsFragment extends BaseFragment implements IEditInventoryGoodsView {

    @InjectPresenter
    EditInventoryGoodsPresenter presenter;

    public EditInventoryGoodsFragment() {

    }

    public static EditInventoryGoodsFragment newInstance() {
        EditInventoryGoodsFragment fragment = new EditInventoryGoodsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_edit_goods, container, false);
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
        //presenter.setBarcode(message);
    }




    LinearLayout focusLayout;
    AutoCompleteTextView edit_text_1;
    ImageView image_view_1;
    RecyclerView recyclerView;
    InventoryScanGoodsAdapter adapter;


    private void showDialog(InputTatem tatem) {
        FragmentManager fm = getFragmentManager();
        InventoryChangeQtyValueDialogFragment dialog = new InventoryChangeQtyValueDialogFragment();
        dialog.setInputTatem(tatem);
        dialog.setiNewQtyListener(new InventoryChangeQtyValueDialogFragment.INewQtyListener() {
            @Override
            public void onNewQty(InputTatem inputTatem, BigDecimal newQty) {
                presenter.updatePosition(inputTatem.getInventoryCode(),newQty);
            }

            @Override
            public void onCancel() {

            }
        });
        dialog.show(fm,"ghh");
    }

    private void initUi(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        InventoryScanGoodsViewHolderFactory factory = new InventoryScanGoodsViewHolderFactory();
        adapter = new InventoryScanGoodsAdapter();
        adapter.setHolderFactory(factory);
        adapter.setLayout(factory.getItemLayoutId());

        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<InventoryCode>() {
            @Override
            public void OnShortClick(int pos, View view, InventoryCode innerItem) {
                InputTatem tatem = new InputTatem();
                tatem.setInputType(InputTatem.InputType.byEdit);
                tatem.setOperationType(0);
                tatem.setInventoryCode(innerItem);
                showDialog(tatem);

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
        adapter.setRawList(presenter.getInventoryCodes());
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
       // presenter.addCodes(vv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_delete) {
      //    presenter.deleteSelectedCodes();
        }


        if (id == R.id.action_dictionary) {

            Intent intentdict = new Intent(getActivity(), DictionaryGoodsActivity.class);
           // intentdict.putExtra("checkingListHeadGUID",presenter.getCheckListInventory().checkingListHeadGuid);
            startActivityForResult(intentdict,234);
            return true;
        }


        if (id == R.id.m_find_code) {
            Intent intentdict = new Intent(getActivity(), FilterActivity.class);
            startActivityForResult(intentdict,23);

        }
        if (id == R.id.action_select_alll) {
          //  presenter.selectAllGoods();


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
             //   presenter.inventoryUoW.setQtyValue(newQty);
            }

            @Override
            public void onCancel() {
            //    presenter.inventoryUoW.clear();
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

    public class InventoryScanGoodsAdapter extends BasicRecyclerViewAdapter<InventoryCode>
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

        private List<InventoryCode> rawList;
        public void setRawList(List<InventoryCode> rawList) {
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
                        List<InventoryCode> filteredList = new ArrayList<>();
                        for (InventoryCode row : rawList) {
                            if (String.valueOf(row.code).concat(row.nameLong).toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                        filterResults.values = filteredList;
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    List<InventoryCode> result = (ArrayList<InventoryCode>) results.values;
                    InventoryScanGoodsAdapter.this.setSourceList(result);
                    notifyDataSetChanged();
                }
            };

        /***************************************************************************************/
        }


    }

    public class InventoryScanGoodsViewHolder extends BasicViewHolder<InventoryCode> {


        private TextView text_view_1, text_view_2, text_view_3;
        private InventoryCode source;
        private IViewHolderListener mListener;

        public void setListener(IViewHolderListener mListener) {
            this.mListener = mListener;
        }

        public InventoryScanGoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            text_view_1 = itemView.findViewById(R.id.text_view_1);
            text_view_2 = itemView.findViewById(R.id.text_view_2);
            text_view_3 = itemView.findViewById(R.id.text_view_3);
        }

        @Override
        public void setSource(InventoryCode source) {
            this.source = source;

            text_view_1.setText(String.valueOf(source.code));
            text_view_2.setText(source.nameLong);
            text_view_3.setText(Converter.QtyToString(source.qty,source.measureUnitIDD));
        }
    }

    public class InventoryScanGoodsViewHolderFactory extends BasicViewHolderFactory {

        public InventoryScanGoodsViewHolderFactory() {
            setItemLayoutId(R.layout.item_edit_inventory_goods);
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
        void onButtonClick(int position, InventoryCode ic);
        void onLongButtonClick(int position, InventoryCode ic);
        void onCheckboxChangeChecked(int position, InventoryCode ic);
    }




}
