package ru.a5x5retail.frontproductmanagement.packinglistpreview.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.DocType;
import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.QueryReturnCode;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.dialogs.working.ProcessFragmentDialog;
import ru.a5x5retail.frontproductmanagement.help.HelpDialog;
import ru.a5x5retail.frontproductmanagement.packinglistpreview.presenter.HelloPresenter;



public class PackingListPreviewActivity extends BaseAppCompatActivity
implements IPackingListPreView
{

    //TextView a_packing_list_preview_textview_1;

    FrameLayout fragment_frame_layout;

    @InjectPresenter(type = PresenterType.WEAK, tag = HelloPresenter.TAG)
    HelloPresenter helloPresenter;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_list_preview);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        initUi();
        initViewModel();
        setView();
    }

    private void init(){
      //  a_packing_list_preview_textview_1 = findViewById(R.id.a_packing_list_preview_textview_1);

       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIntent();
            }
        });*/

        fragment_frame_layout = findViewById(R.id.fragment_frame_layout);
    }

    private void initUi() {
        /*if (isFirstStart()) {
            helloPresenter.showPreviewFragment();
            //replaceFragment(PreviewFragment.newInstance(),false);
        }*/
    }


    private void replaceFragment(Fragment fragment, boolean useBackStack) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fmTra = fragmentManager.beginTransaction();
        fmTra.replace(R.id.fragment_frame_layout, fragment);
        if (useBackStack) {
            fmTra.addToBackStack(null);
        }

        fmTra.commit();
    }

    private void initViewModel(){

        /*if (!(getIntent() == null)) {
            CheckingListHead head = getIntent().getParcelableExtra(PACKINGLISTHEAD_CONST);
            helloPresenter.setHead(head);
        }*/

        /*viewModel = ViewModelProviders.of(this).get(PackingListPreviewViewModel.class);
        if (!viewModel.isInitialized()){
            viewModel.head = getIntent().getParcelableExtra(PACKINGLISTHEAD_CONST);



            viewModel.setInitialized(true);
        }*/
    }

    private void setView(){
        //if (viewModel.head != null)
            //a_packing_list_preview_textview_1.setText(viewModel.head.NameDoc);
    }

    /*private void createIntent(){
       if (Constants.getCurrentDoc().getTypeOfDocument() == Constants.TypeOfDocument.OUTER_INCOME ||
           Constants.getCurrentDoc().getTypeOfDocument() == Constants.TypeOfDocument.INNER_INCOME) {
           Intent intent1 = new Intent(this, CheckingListIncActivity.class);
           intent1.putExtra(Constants.PACKINGLISTHEAD_CONST, viewModel.head);
           startActivity(intent1);
       } else {
           Intent intent1 = new Intent(this, CheckingListGoodsActivity.class);
           intent1.putExtra("gggg", UUID.fromString(viewModel.head.Guid));
           startActivity(intent1);
       }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.packing_list_preview_activity_menu, menu);
        if (ProjectMap.getCurrentTypeDoc().getTypeOfDocument() == Constants.TypeOfDocument.PARTIAL_INVENTORY) {
            MenuItem item =  menu.findItem(R.id.m_accept_item);
            if (item != null) {
                item.setVisible(false);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home :

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStack();
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    finish();
                }
                break;

            case R.id.itemHelp :
                HelpDialog hd = new HelpDialog();
                hd.showFor(this.getSupportFragmentManager(),this.getClass());
                break;
            case R.id.a_pl_preview_upd_item_1 :
                helloPresenter.doSyncInRr();
                break;
            case R.id.m_accept_item :
                helloPresenter.showAcceptingFragment();
                break;
            default:
                return true;
        }
        return true;
    }


    @Override
    public void showPreviewFragment() {
        replaceFragment(PreviewFragment.newInstance(),false);
    }

    @Override
    public void showAcceptingFragment() {
        replaceFragment(AcceptFragment.newInstance(),true);
    }

    @Override
    public void updateUi() {

    }

    @Override
    public void onBackPressed() {
        return;
    }

    ProcessFragmentDialog dialog;
    //@SuppressLint("RestrictedApi")

    /*@Override
    public void showAwaitDialog(boolean show) {

        if (show) {
            Log.d("11111111111111111111111",String.valueOf(show));
            dialog = new ProcessFragmentDialog();
            dialog.show(getSupportFragmentManager(),"dsgrtfhgrtf");
        } else {
            Log.d("22222222222222222222222",String.valueOf(show));

            /*dialog.isVisible();
            dialog.isAdded();
            dialog.isCancelable();
            dialog.isDetached();
            dialog.isHidden();
            dialog.isInLayout();
            dialog.isMenuVisible();
            dialog.isRemoving();
            dialog.isResumed();
            dialog.isStateSaved();*/



            /*if (dialog != null && dialog.getDialog() != null && dialog.getDialog().isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }*/
}
