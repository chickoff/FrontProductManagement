package ru.a5x5retail.frontproductmanagement.packinglistpreview.view;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.CheckingListIncActivity;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.packinglistpreview.presenter.HelloPresenter;
import ru.a5x5retail.frontproductmanagement.сheckinglist.document.CheckingListGoodsActivity;

public class PreviewFragment extends BaseFragment
        implements IPackingListPreView

{
        public PreviewFragment() {
        // Required empty public constructor
    }




    public static PreviewFragment newInstance() {
        PreviewFragment fragment = new PreviewFragment();
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
        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        initUi(view);
        return view;

    }

    @InjectPresenter(type = PresenterType.WEAK, tag = HelloPresenter.TAG)
    HelloPresenter helloPresenter;

    TextView text_view_1;
    FloatingActionButton fab;
    private void initUi(View view) {
        text_view_1 = view.findViewById(R.id.text_view_1);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIntent();
            }
        });
    }




    public void updateUi() {
        text_view_1.setText(helloPresenter.getHead().NameDoc);
    }

    private void createIntent(){
        if (ProjectMap.getCurrentTypeDoc().getTypeOfDocument() == Constants.TypeOfDocument.OUTER_INCOME ||
                ProjectMap.getCurrentTypeDoc().getTypeOfDocument() == Constants.TypeOfDocument.INNER_INCOME) {
            Intent intent1 = new Intent(getActivity(), CheckingListIncActivity.class);
            startActivity(intent1);
        } else {
            Intent intent1 = new Intent(getActivity(), CheckingListGoodsActivity.class);

            if (helloPresenter.getHead() == null) {
                Log.d("ddd",
                        "null_11111111111111111111111111111111111111111111111111111111111111111111111");
            } else {
                Log.d("ddd","xzcdcxzxsxs"/*, helloPresenter.getHead().Guid*/);
            }

            CheckingListHead ddd = helloPresenter.getHead();

            try {
                UUID bb = UUID.fromString(helloPresenter.getHead().Guid);
            } catch (Exception e){
                e.printStackTrace();
            }
            
            intent1.putExtra("gggg", UUID.fromString(helloPresenter.getHead().Guid));
            startActivity(intent1);
        }
    }


    @Override
    public void showPreviewFragment() {

    }

    @Override
    public void showAcceptingFragment() {

    }

    @Override
    public void showAwaitDialog(boolean show) {

    }
}
