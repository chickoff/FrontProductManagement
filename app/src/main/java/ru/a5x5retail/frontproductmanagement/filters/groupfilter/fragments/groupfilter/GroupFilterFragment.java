package ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.groupfilter;


import android.annotation.SuppressLint;
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

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.sql.SQLException;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.components.threestatecheckbox.ThreeStateCheckbox;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.IFilterCompleteListener;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.SkuGroup;


public class GroupFilterFragment extends BaseFragment implements IGroupFilterView {


    public GroupFilterFragment() {
        // Required empty public constructor
    }


    public static GroupFilterFragment newInstance() {
        GroupFilterFragment fragment = new GroupFilterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_group_filter, container, false);

        init(view);
        return view;
    }

    private RecyclerView recyclerView;
    private BasicRecyclerViewAdapter<SkuGroup> adapter;

    @InjectPresenter
    GroupFIlterPresenter presenter;
    private ImageView back_btn,ok_btn;
    private IFilterCompleteListener mListener;

    /*@SuppressLint("RestrictedApi")
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
    }*/

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
        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<SkuGroup>() {
                    @Override
                    public void OnShortClick(int pos, View view, SkuGroup innerItem) {
                        shortClick(pos,innerItem);
                    }
                })
                .setHolderFactory(new SkuGroupViewHolderFactory())
                .setLayout(R.layout.item_group_filter_rv);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SkuGroupRvDecoration());
    }

    private void okFilter() {
        if (presenter.isEmptyChecked()) {
             showEventToast("Выберите подгруппу!",0);
            return;
        }
        presenter.getCode();
    }

    @Override
    public void setResultSkuList(List<CodeInfo> skuList) {
        if (mListener != null) {
            mListener.setResult(skuList);
        }
    }

    @Override
    public void updateUi() {
        adapter.setSourceList(presenter.getCurrentGroup().childList);
        adapter.notifyDataSetChanged();
    }

    private void shortClick(int pos, SkuGroup innerItem) {
        if (innerItem.childList == null || innerItem.childList.size() == 0) {
            return;
        }
        presenter.setCurrentGroup(innerItem);
        updAdapter();
    }

    private void updAdapter(){
        if (presenter.getCurrentGroup() == null) return;
        adapter.setSourceList(presenter.getCurrentGroup().childList);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void backSpace(){
        Log.d("sdf","backSpace1");
        if (presenter.getCurrentGroup() == null) return;
        if (presenter.getCurrentGroup().getParent() == null)  return;
        presenter.setCurrentGroup(presenter.getCurrentGroup().getParent());
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
