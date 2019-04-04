package ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;

import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.base.TestFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.CheckingListIncActivity;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.CheckingListIncViewModel;

import ru.a5x5retail.frontproductmanagement.checkinglistinc.dialogs.CheckingListPositionDialogFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.holders.PositionViewHolder;

import ru.a5x5retail.frontproductmanagement.BInfo;

import ru.a5x5retail.frontproductmanagement.checkinglistinc.models.EnterInUoW;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.models.IEnterInUoWEventsListener;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.models.In;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.dialogs.standart.StandartFragmentDialog;
import ru.a5x5retail.frontproductmanagement.interfaces.IRecyclerViewItemClick;


public class CheckingListIncPositionFragment extends TestFragment<CheckingListIncViewModel> {


    public CheckingListIncPositionFragment() {
        // Required empty public constructor
    }


    public static CheckingListIncPositionFragment newInstance() {
        CheckingListIncPositionFragment fragment = new CheckingListIncPositionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checking_list_inc_position, container, false);
        init();
        initUi(view);
        initViewModel();
        return view;
    }



    private CheckingListPositionRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private void init(){
       /* blaBlaUoW = new BlaBlaUoW();
        blaBlaUoW.setBlaBlaUoWEventsListener(blaUoWEventsListener);*/

        enterInUoW = new EnterInUoW();
        enterInUoW.setmListener(listener);

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
                enterInUoW.setPosition(source);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());
    }



    private void inputQty(CheckingListPosition position, BigDecimal newQty) {
        try {
            getViewModel().addQty(position,newQty,0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initViewModel() {
        FragmentActivity activity = getActivity();
        setViewModel( ViewModelProviders.of(activity).get(CheckingListIncViewModel.class));
    }

    private void updateUi() {

       // blaBlaUoW.setCheckingListPositionList(getViewModel().checkingListPositionList);
        if (getViewModel() == null) return;
        adapter.setSourceList(getViewModel().checkingListPositionList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity instanceof CheckingListIncActivity) {
            ((CheckingListIncActivity)activity).setReceiveScanerMessageListener(new CheckingListIncActivity.IReceiveScanerMessageListener() {
                @Override
                public void receiveMessage(String message) {
                    receiveBarcode(message);
                }
            });
        }
    }

    private void receiveBarcode(String message) {

        enterInUoW.setBarcode(message);

        /*List<SKUContext> skuContexts = null;
        try {
            skuContexts = getViewModel().getSkuContextByBarcode(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BarcodeInfo bi = new BarcodeInfo(message);

        bi.

        skuContexts = skuContexts;*/
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void viewModelDataIsChanged() {
        updateUi();
    }

    @Override
    public void listenerChangedListenerRemove() {

    }

    @Override
    public void listenerChangedListenerAdded() {
        updateUi();
    }

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

        public void setQtyButtonClickListener(Te listener) {
            this.buttonClickListener = listener;
        }
    }

    EnterInUoW enterInUoW;
    IEnterInUoWEventsListener listener = new IEnterInUoWEventsListener() {
        @Override
        public void getSkuContext(BInfo barcodeInfo) {
            try {
                enterInUoW.setSkuContext(getViewModel().getSkuContextByBarcode(barcodeInfo.barcode));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void findLocalPosition(BInfo barcodeInfo) {
            List<CheckingListPosition> pl = new ArrayList<>();
            for (CheckingListPosition checkingListPosition : getViewModel().checkingListPositionList) {
                if (checkingListPosition.code == barcodeInfo.getSkuContext().Code) {
                    pl.add(checkingListPosition);
                }
            }
            enterInUoW.setPositionListForChange(pl);
        }

        @Override
        public void getNewQty(CheckingListPosition position) {
            openEditableDialog(position);
        }

        @Override
        public void selectOnePosition(List<CheckingListPosition> checkingListPositionList) {
            openSelectiblePositionDialog(checkingListPositionList);
        }

        @Override
        public void saveNewQty(In input) {
            try {
                if (input.getInType() == In.InputType.ByClick) {
                    getViewModel().addQty(input.getSelectedPosition(), input.getNewQty(), 0);
                } else {
                    getViewModel().addQty(input.getSelectedPosition(), input.getNewQty(), 1);
                }
                enterInUoW.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void raiseError(String message) {
            ProdManApp.Alerts.MakeDoubleVibrate();
            ProdManApp.Alerts.MakeToast(message, 0);
        }

        @Override
        public void raiseClear() {

        }
    };

    /*private BlaBlaUoW blaBlaUoW;
    BlaBlaUoW.IBlaBlaUoWEventsListener blaUoWEventsListener;

    {
        blaUoWEventsListener = new BlaBlaUoW.IBlaBlaUoWEventsListener() {
            @Override
            public void getSkuContext(BarcodeInfo barcodeInfo) {
                try {
                    blaBlaUoW.setSkuContext(getViewModel().getSkuContextByBarcode(barcodeInfo.barcode));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void getNewQty(CheckingListPosition position) {
                openEditableDialog(position);
            }

            @Override
            public void getNewQty(BarcodeInfo barcodeInfo) {
                openEditableDialog(barcodeInfo.getSelectedPosition());
            }

            @Override
            public void selectOnePosition(BarcodeInfo barcodeInfo) {
                ProdManApp.Alerts.MakeAlertVibrate();
                openSelectiblePositionDialod(barcodeInfo);
            }

            @Override
            public void changeNewQty(CheckingListPosition position, BigDecimal newQty) {
                try {
                    getViewModel().addQty(position, newQty, 0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void changeNewQty(BarcodeInfo barcodeInfo, BigDecimal newQty) {
                try {
                    getViewModel().addQty(barcodeInfo.getSelectedPosition(), newQty, 1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void raiseError(String message) {
                ProdManApp.Alerts.MakeDoubleVibrate();
                ProdManApp.Alerts.MakeToast(message, 0);
            }

            @Override
            public void raiseClear() {

            }
        };
    }*/


    private void openEditableDialog(CheckingListPosition source) {
        CheckingListPositionDialogFragment dialig = new CheckingListPositionDialogFragment();
        FragmentManager fm = getFragmentManager();
        dialig.setPosition(source);
        dialig.setiNewQtyListener(new CheckingListPositionDialogFragment.INewQtyListener() {
            @Override
            public void onNewQty(CheckingListPosition position, BigDecimal newQty) {
                enterInUoW.setNewQty(newQty);
            }

            @Override
            public void onCancel() {
                enterInUoW.clear();
            }
        });
        dialig.show(fm,"ghh");
    }

    private void openSelectiblePositionDialog(List<CheckingListPosition> checkingListPositionList) {

        StandartFragmentDialog<CheckingListPosition> dialog = new StandartFragmentDialog();
        dialog.setSourceList(checkingListPositionList);
        dialog.setTitle("Выберите позицию:");
        dialog.setViewHolderFactory(new PositionViewHolder.PositionViewHolderFactory());
        dialog.setRecyclerViewClickListener(new IRecyclerViewItemClick<CheckingListPosition>() {
            @Override
            public void OnClick(int pos, CheckingListPosition innerItem) {
                //blaBlaUoW.setBarcodeInfoSelectedPosition(innerItem);
                enterInUoW.setResultingPosition(innerItem);
            }

            @Override
            public void OnCancel() {
                enterInUoW.clear();
            }
        });
        dialog.show(getFragmentManager(),"dsgrtfhgrtf");
    }





}



