package ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.base.TestFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.CheckingListIncViewModel;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.dialogs.CheckingListCangeDateOfManufactorDialogFragment;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateOfManufactorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateOfManufactorFragment extends TestFragment<CheckingListIncViewModel> {
    public DateOfManufactorFragment() {
        // Required empty public constructor
    }

    public static DateOfManufactorFragment newInstance() {
        DateOfManufactorFragment fragment = new DateOfManufactorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_date_of_manufactor, container, false);
        initUi(view);
        initViewModel();
        return view;

    }

    private CheckingListPositionRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private void initUi(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);

        adapter = new CheckingListPositionRecyclerAdapter();

        adapter.setQtyButtonClickListener(new Te() {
            @Override
            public void onButtonClick(CheckingListPosition source) {
                showDialog(source);
            }
        });
        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<CheckingListPosition>() {
            @Override
            public void OnShortClick(int pos, View view, CheckingListPosition innerItem) {

            }
        }).setLayout(R.layout.item_checking_list_date_of_manufacturer)
                .setHolderFactory(new DateOfManufactorViewHolderFactory());

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());
    }

    private void initViewModel() {
        FragmentActivity activity = getActivity();
        setViewModel( ViewModelProviders.of(activity).get(CheckingListIncViewModel.class));
    }

    private void updateUi() {
        if (getViewModel() == null) return;
        adapter.setSourceList(getViewModel().getPositionListForDateOfManufacturer());
        adapter.notifyDataSetChanged();
    }

    private void showDialog(CheckingListPosition source) {

        CheckingListCangeDateOfManufactorDialogFragment dialog = new CheckingListCangeDateOfManufactorDialogFragment();
        dialog.setPosition(source);
        dialog.setNewDateListener(new CheckingListCangeDateOfManufactorDialogFragment.INewDateListener() {
            @Override
            public void onNewDate(CheckingListPosition position, Date date) {
                try {
                    getViewModel().addDate(position,date);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {

            }
        });

        dialog.show(getFragmentManager(),"sfds");
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
            DateOfManufactorViewHolder positionViewHolder = (DateOfManufactorViewHolder)tBasicViewHolder;
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


    public class DateOfManufactorViewHolder extends BasicViewHolder<CheckingListPosition> {


        private TextView text_view_1, text_view_2, text_view_3, text_view_4;

        private CheckingListPosition source;
        private Te buttonClickListener;

        public DateOfManufactorViewHolder(@NonNull View itemView) {
            super(itemView);

            text_view_1 = itemView.findViewById(R.id.text_view_1);
            text_view_2 = itemView.findViewById(R.id.text_view_2);
            text_view_3 = itemView.findViewById(R.id.text_view_3);
            text_view_4 = itemView.findViewById(R.id.text_view_4);

        }

        @Override
        public void setSource(final CheckingListPosition source) {
            this.source = source;
            text_view_1.setText(String.valueOf(this.source.orderBy));
            text_view_2.setText(String.valueOf(this.source.code));
            text_view_3.setText(String.valueOf(this.source.nameLong));

            if (this.source.manufacturingDate != null) {
                text_view_4.setText(String.valueOf(this.source.manufacturingDate));
            } else {
                text_view_4.setText("");
            }

            text_view_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

    public class DateOfManufactorViewHolderFactory extends BasicViewHolderFactory {
        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new DateOfManufactorViewHolder(itemView);
        }
    }
}
