package ru.a5x5retail.frontproductmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import ru.a5x5retail.frontproductmanagement.updateapplication.UpdateApplicationActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static ru.a5x5retail.frontproductmanagement.configuration.Constants.TYPEOFDOCUMENT_CONST;

public class MainActivity extends BaseAppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Управление товарами " + AppConfigurator.GetCurrentVersion().toString());
        initRecyclerView();
        backButtonEnabled(true);
    }

    private DocType currentDocType;
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
                    public void OnShortClick(int pos, View view, DocType innerItem) {
                        if (innerItem.getTypeOfDocument() != Constants.TypeOfDocument.SETTINGS) {
                            int isNewVersion = - 1;
                            isNewVersion = AppConfigurator.checkNewVersion();
                            switch (isNewVersion) {
                                case 1 :
                                    Intent updIntent = new Intent(MainActivity.this, UpdateApplicationActivity.class);
                                    updIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(updIntent);
                                    return;
                                case -1 :
                                    ProdManApp.Alerts.MakeToast("Нет связи с сервером обновлений. Работа с ТСД приостановлена.", Toast.LENGTH_LONG);
                                    return;
                                case 0 :
                            }
                        }

                        ddd(innerItem);

                    }
                }).Build();
        docTypesRecyclerView.setAdapter(adapter);
        docTypesRecyclerView.addItemDecoration(new DocTypeItemDecor(4));

        try {
            AppConfigurator.getMainInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateUi (List<DocType> docTypeList) {
        adapter.setSourceList(docTypeList);
        adapter.notifyDataSetChanged();
    }


    private void ddd (DocType dt) {

        currentDocType = dt;

        if (dt.getChildDocs() == null || dt.getChildDocs().size() == 0) {
            Intent intent = new Intent(MainActivity.this, dt.getClassOfActivity());
            Constants.setCurrentDoc(dt);
            startActivity(intent);
        } else {
            backButtonEnabled(true);
            updateUi(dt.getChildDocs());
        }
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

