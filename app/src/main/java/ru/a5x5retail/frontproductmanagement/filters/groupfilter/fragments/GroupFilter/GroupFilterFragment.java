package ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.GroupFilter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.components.threestatecheckbox.ThreeStateCheckbox;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.IFilterCompleteListener;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.SkuGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFilterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public GroupFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFilterFragment newInstance(String param1, String param2) {
        GroupFilterFragment fragment = new GroupFilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_group_filter, container, false);
        initViewModel();
        init(view);
        return view;
    }

    private RecyclerView recyclerView;
    private BasicRecyclerViewAdapter<SkuGroup> adapter;
    private GroupFilterViewModel viewModel;
    private ImageView back_btn,ok_btn;
    private IFilterCompleteListener mListener;

    @SuppressLint("RestrictedApi")
    private void initViewModel(){
        FragmentActivity act = getActivity();
        viewModel = new GroupFilterViewModel();//ViewModelProviders.of(act).get(GroupFilterViewModel.class);
        try {
            viewModel.Load();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void init(View view) {

        back_btn =  view.findViewById(R.id.f_group_filter_back_bnt);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backSpace();
            }
        });

        ok_btn = view.findViewById(R.id.f_group_filter_ok_bnt);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okFilter();
            }
        });

        recyclerView = view.findViewById(R.id.a_groupfilter_recyclerview);
        adapter = new BasicRecyclerViewAdapter<>();
        adapter .setHolderFactory(new SkuGroupViewHolderFactory())
                .setLayout(R.layout.item_group_filter_rv)
                .setSourceList(viewModel.getCurrentGroup().childList)
                .setShortClickListener(new BasicRecyclerViewAdapter.IRecyclerViewItemShortClickListener<SkuGroup>() {
                    @Override
                    public void OnShortClick(int pos, SkuGroup innerItem) {
                        shortClick(pos,innerItem);
                    }
                });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SkuGroupRvDecoration());
    }

    private void okFilter() {
        if (viewModel.isEmptyChecked()) {
            ProdManApp.Alerts.MakeToast("Выберите подгруппу!",0);
            return;
        }

        List<CodeInfo> skuList = null;

        try {
            skuList = viewModel.getCode();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (mListener != null) {
            mListener.setResult(skuList);
        }
    }

    private void shortClick(int pos, SkuGroup innerItem) {
        if (innerItem.childList == null || innerItem.childList.size() == 0) {
            return;
        }
        viewModel.setCurrentGroup(innerItem);
        updAdapter();
    }

    private void updAdapter(){
        if (viewModel.getCurrentGroup() == null) return;
        adapter.setSourceList(viewModel.getCurrentGroup().childList);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void backSpace(){
        Log.d("sdf","backSpace1");
        if (viewModel.getCurrentGroup() == null) return;
        if (viewModel.getCurrentGroup().getParent() == null)  return;
        viewModel.setCurrentGroup(viewModel.getCurrentGroup().getParent());
        updAdapter();
        Log.d("sdf","backSpace");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFilterCompleteListener) {
            mListener = (IFilterCompleteListener) context;
        }
    }


    public class SkuGroupViewHolder extends BasicViewHolder<SkuGroup> {

        private TextView i_group_filter_text_view_1;
        private ThreeStateCheckbox i_group_filter_switch_1;

        public SkuGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view){
            i_group_filter_text_view_1 = view.findViewById(R.id.i_group_filter_text_view_1);
            i_group_filter_switch_1 =  view.findViewById(R.id.i_group_filter_switch_1);
        }

        private void reset() {
            i_group_filter_switch_1.setOnCheckedChangeListener(null);
        }

        @Override
        public void setSource(final SkuGroup source) {
            final SkuGroup src = source;
            reset();
            i_group_filter_text_view_1.setText(source.nameLong);
            i_group_filter_switch_1.setChecked(source.isChecked());
            i_group_filter_switch_1.setUnknownState(src.getState() == SkuGroup.SKU_STATE.PARTIAL);
            i_group_filter_switch_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    src.setChecked(isChecked);
                    setState(src);
                }
            });
        }

        private void setState(final SkuGroup source){
            i_group_filter_switch_1.setUnknownState(source.getState() == SkuGroup.SKU_STATE.PARTIAL);
        }
    }

    public class SkuGroupViewHolderFactory extends BasicViewHolderFactory {
        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new SkuGroupViewHolder(itemView);
        }
    }

    public class SkuGroupRvDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = 5;
        }
    }

}
