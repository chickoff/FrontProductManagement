package ru.a5x5retail.frontproductmanagement.packinglistpreview;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.db.models.AcceptResult;
import ru.a5x5retail.frontproductmanagement.db.models.AcceptValidateMessage;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptFragment extends Fragment {

    private PackingListPreviewViewModel viewModel;
    private AcceptResult lastAcceptResult;

    public AcceptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        initViewModel();
        View view = inflater.inflate(R.layout.fragment_accept, container, false);
        initUi(view);
        return view;
    }


    public static AcceptFragment newInstance() {
        AcceptFragment fragment = new AcceptFragment();
        return fragment;
    }

    private RecyclerView recyclerView;
    private Button button_1;
    private BasicRecyclerViewAdapter<AcceptValidateMessage> adapter;

    private TextView text_view_1,text_view_2,text_view_3;
    private ImageView image_view_1,image_view_2;

    private void initUi(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new BasicRecyclerViewAdapter<>();
        adapter.setLayout(R.layout.item_validation_accept);
        adapter.setHolderFactory(new ValidationMessageViewHolderFactory());

        button_1 = view.findViewById(R.id.button_1);
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndAccept();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());

        text_view_1 = view.findViewById(R.id.text_view_1);
        text_view_2 = view.findViewById(R.id.text_view_2);
        text_view_3 = view.findViewById(R.id.text_view_3);

        image_view_1 = view.findViewById(R.id.image_view_1);
        image_view_2 = view.findViewById(R.id.image_view_2);

    }

    private void updateUi() {
        if (lastAcceptResult != null) {
            adapter.setSourceList(lastAcceptResult.acceptValidateMessageList);
            adapter.notifyDataSetChanged();
            image_view_1.setImageResource(lastAcceptResult.isValidated == 0 ? R.drawable.ic_stop_red_24dp : R.drawable.ic_check_circle_black_24dp);
            image_view_2.setImageResource(lastAcceptResult.isAccepted == 0 ? R.drawable.ic_stop_red_24dp : R.drawable.ic_check_circle_black_24dp);
            text_view_1.setText("Проверка на корректронсть");
            text_view_2.setText("Утверждение");
            text_view_3.setText(lastAcceptResult.eventMessage);
        }
    }

    private void validateAndAccept() {
        try {
            lastAcceptResult = viewModel.ValideteAndAccept();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateUi();
    }

    private void initViewModel(){
        viewModel = ViewModelProviders.of(getActivity()).get(PackingListPreviewViewModel.class);
    }

    public class ValidationMessageViewHolder extends BasicViewHolder<AcceptValidateMessage> {

        private TextView text_view_1;//,text_view_2,text_view_3,text_view_4;
        private ImageView image_view_1;


        public ValidationMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            text_view_1 = itemView.findViewById(R.id.text_view_1);
            image_view_1 = itemView.findViewById(R.id.image_view_1);


           /* text_view_2 = itemView.findViewById(R.id.text_view_2);
            text_view_3 = itemView.findViewById(R.id.text_view_3);
            text_view_4 = itemView.findViewById(R.id.text_view_4);*/
        }

        @Override
        public void setSource(AcceptValidateMessage source) {
            text_view_1.setText(source.text);

            switch (source.level) {
                case 1 :
                    image_view_1.setImageResource(R.drawable.ic_info_blue_24dp);
                    break;
                case 2 :
                    image_view_1.setImageResource(R.drawable.ic_warning_orange_24dp);
                    break;
                case 3 :
                    image_view_1.setImageResource(R.drawable.ic_stop_red_24dp);
                    break;

            }


  /*          text_view_2.setText(source.contractorName);
            text_view_3.setText(source.summ.toString());
            text_view_4.setText(source.summVat.toString());*/


        }
    }

    public class ValidationMessageViewHolderFactory extends BasicViewHolderFactory {
        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new ValidationMessageViewHolder(itemView);
        }
    }
}
