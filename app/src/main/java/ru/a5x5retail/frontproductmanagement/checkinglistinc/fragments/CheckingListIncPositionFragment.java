package ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;

import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.CheckingListIncActivity;


import ru.a5x5retail.frontproductmanagement.checkinglistinc.dialogs.CheckingListPositionDialogFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.holders.PositionViewHolder;


import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.dialogs.standart.StandartFragmentDialog;
import ru.a5x5retail.frontproductmanagement.interfaces.IReceiveScanerMessageListener;
import ru.a5x5retail.frontproductmanagement.interfaces.IRecyclerViewItemClick;


public class CheckingListIncPositionFragment extends BaseFragment implements ICheckingListIncView {


    @InjectPresenter(type = PresenterType.WEAK, tag = CheckingListIncPresenter.TAG)
    CheckingListIncPresenter presenter;

    /*************************************************************************************************************************************************/

    public CheckingListIncPositionFragment() {
        // Required empty public constructor
    }

    /*************************************************************************************************************************************************/

    public static CheckingListIncPositionFragment newInstance() {
        CheckingListIncPositionFragment fragment = new CheckingListIncPositionFragment();
        return fragment;
    }

    /*************************************************************************************************************************************************/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /*************************************************************************************************************************************************/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checking_list_inc_position, container, false);
        init();
        initUi(view);
        return view;
    }

    /*************************************************************************************************************************************************/

    private CheckingListPositionRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private void init(){
       /* blaBlaUoW = new BlaBlaUoW();
        blaBlaUoW.setBlaBlaUoWEventsListener(blaUoWEventsListener);*/

    /*************************************************************************************************************************************************/

    }

    private void initUi (View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new CheckingListPositionRecyclerAdapter();
        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<CheckingListPosition>() {
            @Override
            public void OnShortClick(int pos, View view, CheckingListPosition innerItem) {

            }
        }).setLayout(R.layout.item_checking_list_positions)
                .setHolderFactory(new PositionViewHolder.PositionViewHolderFactory());

        adapter.setQtyButtonClickListener(new Te() {
            @Override
            public void onButtonClick(CheckingListPosition source) {
                //blaBlaUoW.setPosition(source);
                presenter.enterInUoW.setPosition(source);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());

    }

    /*************************************************************************************************************************************************/

    @Override
    public void updateUi() {
        adapter.setSourceList(presenter.checkingListPositionList);
        adapter.notifyDataSetChanged();
    }

    /*************************************************************************************************************************************************/

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity instanceof CheckingListIncActivity) {
            ((CheckingListIncActivity)activity).setReceiveScanerMessageListener(new IReceiveScanerMessageListener() {
                @Override
                public void receiveMessage(String message) {
                    receiveBarcode(message);
                }
            });
        }
    }

    /*************************************************************************************************************************************************/

    private void receiveBarcode(String message) {

        presenter.enterInUoW.setBarcode(message);
    }

    /*************************************************************************************************************************************************/

    @Override
    public void onPause() {
        super.onPause();
        presenter.enterInUoW.clear();
    }

    /*************************************************************************************************************************************************/

    public class CheckingListPositionRecyclerAdapter extends BasicRecyclerViewAdapter<CheckingListPosition> {

        private Te buttonClickListener;

        @Override
        public void onBindViewHolder(@NonNull BasicViewHolder tBasicViewHolder, int i) {
            super.onBindViewHolder(tBasicViewHolder, i);
            PositionViewHolder positionViewHolder = (PositionViewHolder)tBasicViewHolder;
            positionViewHolder.setQtyButtonClickListener(new Te() {
                @Override
                public void onButtonClick(CheckingListPosition source) {
                    if (buttonClickListener != null) {
                        buttonClickListener.onButtonClick(source);
                    }
                }
            });
        }
        public int getPositionBySource(CheckingListPosition source) {
            if (list == null) return 0;
            return list.indexOf(source);
        }

        public void setQtyButtonClickListener(Te listener) {
            this.buttonClickListener = listener;
        }
    }

    /*************************************************************************************************************************************************/

    @Override
    public void openEditableDialog(CheckingListPosition source) {
        CheckingListPositionDialogFragment dialig = new CheckingListPositionDialogFragment();
        FragmentManager fm = getFragmentManager();
        dialig.setPosition(source);
        dialig.setiNewQtyListener(new CheckingListPositionDialogFragment.INewQtyListener() {
            @Override
            public void onNewQty(CheckingListPosition position, BigDecimal newQty) {
                presenter.enterInUoW.setNewQty(newQty);
            }

            @Override
            public void onCancel() {
                presenter.enterInUoW.clear();
            }
        });
        dialig.show(fm,"ghh");
    }

    /*************************************************************************************************************************************************/

    @Override
    public void openSelectiblePositionDialog(List<CheckingListPosition> checkingListPositionList) {

        StandartFragmentDialog<CheckingListPosition> dialog = new StandartFragmentDialog<>();
        dialog.setSourceList(checkingListPositionList);
        dialog.setTitle("Выберите позицию:");
        dialog.setViewHolderFactory(new PositionViewHolder.PositionViewHolderFactory());
        dialog.setRecyclerViewClickListener(new IRecyclerViewItemClick<CheckingListPosition>() {
            @Override
            public void OnClick(int pos, CheckingListPosition innerItem) {
                presenter.enterInUoW.setResultingPosition(innerItem);
            }

            @Override
            public void OnCancel() {
                presenter.enterInUoW.clear();
            }
        });

        dialog.show(getFragmentManager(),"dsgrtfhgrtf");
    }

}



