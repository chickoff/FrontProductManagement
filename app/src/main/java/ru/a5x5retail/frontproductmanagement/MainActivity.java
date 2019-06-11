package ru.a5x5retail.frontproductmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.sql.SQLException;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.BasicRecyclerViewAdapterBuilder;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;


import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetApkVersionApkQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetMainDivisionInfoQuery;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.updateapplication.UpdateApplicationActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class MainActivity extends BaseAppCompatActivity
implements IMainActivityView
{

    private SendExceptionFiles sendExceptionFiles;

    @InjectPresenter
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adapter.notifyDataSetChanged();
        setContentView(R.layout.activity_main);
        setTitle("Управление товарами " + AppConfigurator.GetCurrentVersion().toString());
        initRecyclerView();
        //backButtonEnabled(true);
        if (sendExceptionFiles == null) {
            sendExceptionFiles = new SendExceptionFiles();
        }
        if (!sendExceptionFiles.isWork()) {
            sendExceptionFiles.SendAsync();
        }

        WiFiSpeedTimer timer = new WiFiSpeedTimer(this);
        timer.StartTimer();
    }

    private DocType<AppCompatActivity> currentDocType;
    RecyclerView docTypesRecyclerView;
    BasicRecyclerViewAdapter<DocType> adapter;

    private void initRecyclerView(){
        docTypesRecyclerView = findViewById(R.id.docTypesRecyclerView);
        adapter = new BasicRecyclerViewAdapterBuilder<DocType>()
                .setHolderFactory(new DocTypeViewHolderFactory())
                .setLayout(R.layout.item_doctype_rv)
                .setSourceList(AppConfigurator.getAvailableDocTypes())
                .setShortClickListener(new IRecyclerViewItemShortClickListener<DocType>() {
                    @Override
                    public void OnShortClick(int pos, View view, final DocType innerItem) {
                        if (innerItem.getTypeOfDocument() != Constants.TypeOfDocument.SETTINGS) {
                            Log.d("sdsds","sdsd77777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777");
                            final GetApkVersionApkQuery query = new GetApkVersionApkQuery();
                            presenter.registerAwaitEvents(query);


                            /*query.addAsyncQueryEventListener(new CallableQAsync.IAsyncQueryEventListener() {
                                @Override
                                public void onAsyncQueryEvent(CallableQAsync query, int returnCode, String returnMessage) {
                                   // showEventToast(returnMessage,0);
                                }
                            });
*/

                           /* query.addAsyncExceptionEventListener(new CallableQAsync.IAsyncQueryExceptionListener() {
                                @Override
                                public void onAsyncQueryException(CallableQAsync query, Exception e) {
                                    showExceptionToast(e,e.getMessage());
                                }
                            });*/


                           /* query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
                                @Override
                                public void onPreExecute() {
                                    showAwaitDialog(true);
                                }
                            });*/

                            query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
                                @Override
                                public void onPostExecute() {
                                    if (query.isDoError()) {
                                        return;
                                    }

                                    Version dbVersion = query.getDbVersion();
                                    Version currentVersion = AppConfigurator.GetCurrentVersion();
                                    if (currentVersion.getRealise() != dbVersion.getRealise() ||
                                            currentVersion.getBuild() != dbVersion.getBuild()) {
                                        Intent updIntent = new Intent(MainActivity.this, UpdateApplicationActivity.class);
                                        updIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(updIntent);
                                        return;
                                    } else {
                                        ddd(innerItem);
                                    }
                                }
                            });


                            /*{
                                /*@Override
                                public void onPostExecute() {

                                    showAwaitDialog(false);

                                    if (query.isDoError()) {
                                        return;
                                    }

                                    Version dbVersion = query.getDbVersion();
                                    Version currentVersion = AppConfigurator.GetCurrentVersion();
                                    if (currentVersion.getRealise() != dbVersion.getRealise() ||
                                            currentVersion.getBuild() != dbVersion.getBuild()) {

                                        Intent updIntent = new Intent(MainActivity.this, UpdateApplicationActivity.class);
                                        updIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(updIntent);
                                        return;
                                    } else {
                                        ddd(innerItem);
                                    }
                                }*/


                            query.ExecuteAsync();
                        } else {

                            Intent intent = new Intent(MainActivity.this, innerItem.getClassOfActivity());
                            startActivity(intent);
                        }
                    }
                }).Build();
        docTypesRecyclerView.setAdapter(adapter);
        docTypesRecyclerView.addItemDecoration(new DocTypeItemDecor(4));
    }

    @Override
    protected void dffdsfdfdf() {
        super.dffdsfdfdf();

    }

    private void updateUi (List<DocType> docTypeList) {
        adapter.setSourceList(docTypeList);
        adapter.notifyDataSetChanged();
    }

    private void ddd (final DocType dt) {

        ProjectMap.setCurrentTypeDoc(dt);

        if (dt.getChildDocs() == null || dt.getChildDocs().size() == 0) {

            final GetMainDivisionInfoQuery query = new GetMainDivisionInfoQuery();
            query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
                @Override
                public void onPreExecute() {
                    showAwaitDialog(true);
                }
            });

            query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
                @Override
                public void onPostExecute() {
                    showAwaitDialog(false);
                    ProjectMap.setMainInfo(query.getDivisionInfo());

                    Intent intent = new Intent(MainActivity.this, dt.getClassOfActivity());
                    startActivity(intent);


/*                    if (dt.isSuperPwdProtect()) {
                        SuperPwdDialogFragment dialog = new SuperPwdDialogFragment();
                        dialog.setResult(new SuperPwdDialogFragment.SuperPwdDialogFragmentResult() {
                            @Override
                            public void onSuperPwdSuccess() {
                                Intent intent = new Intent(MainActivity.this, dt.getClassOfActivity());
                                startActivity(intent);
                            }
                        });
                        dialog.show(getSupportFragmentManager(),"dsfds");
                    } else {

                    }*/
                }
            });
            query.ExecuteAsync();
        } else {
            backButtonEnabled(true);
            updateUi(dt.getChildDocs());
        }


      /*  if (dt.getChildDocs() == null || dt.getChildDocs().size() == 0) {

            try {
                AppConfigurator.getMainInfo();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(MainActivity.this, dt.getClassOfActivity());
            startActivity(intent);
        } else {
            backButtonEnabled(true);
            updateUi(dt.getChildDocs());
        }*/
    }

    private void up () {
        backButtonEnabled(false);
        updateUi(AppConfigurator.getAvailableDocTypes());
    }

    @SuppressLint("RestrictedApi")
    private void backButtonEnabled(boolean enabled) {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(enabled);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                up();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        up();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class DocTypeViewHolder extends BasicViewHolder<DocType> {

        TextView i_doctype_rv_textview_1;
        TextView i_doctype_rv_textview_2;

        public DocTypeViewHolder(@NonNull View itemView) {
            super(itemView);

            i_doctype_rv_textview_1 = itemView.findViewById(R.id.i_doctype_rv_textview_1);
            i_doctype_rv_textview_2 = itemView.findViewById(R.id.i_doctype_rv_textview_2);
        }

        @Override
        public void setSource(DocType source) {
            i_doctype_rv_textview_1.setText(source.getName());
            i_doctype_rv_textview_2.setText("");
        }
    }

    public class DocTypeViewHolderFactory extends BasicViewHolderFactory {
        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new DocTypeViewHolder(itemView);
        }
    }

    public class DocTypeItemDecor extends RecyclerView.ItemDecoration {

        private int space;

        public DocTypeItemDecor(int space)
        {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            int viewPos = parent.getChildLayoutPosition(view);
            int modOfPos = viewPos % 2;
            if (space > 0) {

                int ordSpace = space % 2;
                if (ordSpace > 0){
                    space = space + 1;
                }

                int halfSpace = space / 2;
                if (modOfPos == 0) {
                    outRect.right = halfSpace;
                    outRect.left = space;

                } else {
                    outRect.left = halfSpace;
                    outRect.right = space;
                }
            }
            outRect.top = space;
        }
    }



}













   /* private void fff(){

        LayoutInflater layoutInflater
                = (LayoutInflater) ProdManApp.getAppContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_window, null);
        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                // Shrink the window to wrap the content rather than filling the screen
                WindowManager.LayoutParams.WRAP_CONTENT,
                0,//WindowManager.LayoutParams.WRAP_CONTENT,
                // Display it on top of other application windows, but only for the current user
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                // Don't let it grab the input focus
                WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
                // Make the underlying application window visible through any transparent parts
                PixelFormat.TRANSLUCENT);

// Define the position of the window within the screen
        p.gravity = Gravity.CENTER;
        p.x = 0;
        p.y = 100;

        WindowManager windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        windowManager.addView(popupView, p);
    }*/

  /*  private void sss(){





        LayoutInflater layoutInflater
                = (LayoutInflater) ProdManApp.getAppContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.popup_window, null);

        final ErrorPopup2 popupWindow = new ErrorPopup2(
                popupView,
                WindowManager.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        popupWindow.showAtLocation(b,Gravity.CENTER,0,0);



    }*/

