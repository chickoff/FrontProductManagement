package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.creators.newinvoice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.models.DivisionInfo;
import ru.a5x5retail.frontproductmanagement.db.models.PlanIncome;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.create.CreateRrIncomeQuery;
import ru.a5x5retail.frontproductmanagement.db.query.create.CreateRrIncomeViaPlanIncomeQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetDivisionInfoQuery;
import ru.a5x5retail.frontproductmanagement.dialogs.standart.StandartFragmentDialog;
import ru.a5x5retail.frontproductmanagement.dialogs.standart.holders.DivisionInfoViewHolder;
import ru.a5x5retail.frontproductmanagement.interfaces.IRecyclerViewItemClick;

import static ru.a5x5retail.frontproductmanagement.configuration.Constants.CONTRACTOR_INFO_CONST;
import static ru.a5x5retail.frontproductmanagement.configuration.Constants.PLAN_INCOME_CONST;


/**
 * У нас есть класс активити. Ему для работы нужно понимать, создаем новую накладную с нуля или из планируемой поставки.
 * Так же следует учитывать какой тип документа создается. Например приходный документ - м.б. внешний, или внутренний.
 * Для внешнего нужно указать номер и дату довумента, нужно чтобы поставщик не был пустым.
 * Для внутреннего прихода необходимо, чтобы пользователь выбрал подразделение, откуда пришла накладная, а поле поставщик неважен. Так же нужна поле  планируемая поставка.
 * */



public class CreateNewInvoiceActivity extends AppCompatActivity
implements IRecyclerViewItemClick<DivisionInfo>
{


    public static final int BASIS_OF_CREATION_NEW = 0;
    public static final int BASIS_OF_CREATION_ON_PP = 1;
    public static final String BASIS_OF_CREATION = "BASIS_OF_CREATION";

    private ImageView cat_foot;
    private EditText num_doc_edittext,date_doc_edittext;
    private TextView division_textview,cancel_textview;
    private Button ok_button;

    private static DivisionInfo divisionInfo;
    private static ContractorExtendedInfo contractorInfo;
    private static PlanIncome planIncome;
    private static int basisOfCreation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_invoice);
        init();
        initUi();
        updateUi();
    }

    private void init() {
        contractorInfo = getIntent().getParcelableExtra(CONTRACTOR_INFO_CONST);
        planIncome = getIntent().getParcelableExtra(PLAN_INCOME_CONST);
        basisOfCreation = getIntent().getIntExtra(BASIS_OF_CREATION,-1);
    }

    private void initUi() {
        cat_foot = findViewById(R.id.cat_foot);
        cat_foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    showDialog();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        num_doc_edittext = findViewById(R.id.num_doc_edittext);
        date_doc_edittext = findViewById(R.id.date_doc_edittext);
        division_textview = findViewById(R.id.division_textview);
        cancel_textview = findViewById(R.id.cancel_textview);
        cancel_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok_button  = findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okButtonOnClick();
            }
        });

       /* if (Constants.getCurrentTypeOfDocument() == Constants.TypeOfDocument.OUTER_INCOME) {
            cat_foot.setVisibility(View.GONE);
            division_textview.setVisibility(View.GONE);
        }*/



        if (basisOfCreation == BASIS_OF_CREATION_NEW) {

            if (Constants.getCurrentDoc().getTypeOfDocument() == Constants.TypeOfDocument.OUTER_INCOME) {
                cat_foot.setVisibility(View.GONE);
                division_textview.setVisibility(View.GONE);
            }

        } else if (basisOfCreation == BASIS_OF_CREATION_ON_PP) {
            cat_foot.setVisibility(View.GONE);
            division_textview.setVisibility(View.GONE);
            date_doc_edittext.setVisibility(View.GONE);
        }
    }

    private void okButtonOnClick() {
        if (checkValues()) {
            try {
                createInvoice();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            finish();
        }
    }

    private void updateUi() {
        if (divisionInfo == null) {return;}
        division_textview.setText(divisionInfo.nameLong);
    }

    private void showDialog() throws SQLException, ClassNotFoundException {

        MsSqlConnection con = new MsSqlConnection();
        GetDivisionInfoQuery query = new GetDivisionInfoQuery(con.getConnection());
        query.Execute();

        StandartFragmentDialog<DivisionInfo> dialog = new StandartFragmentDialog();
        dialog.setViewHolderFactory(new DivisionInfoViewHolder.DivisionInfoViewHolderFactory());
        dialog.setSourceList(query.getList());
        dialog.setTitle("Выберите подразделение");
        dialog.show(getSupportFragmentManager(),"sadsdf");
    }


    private boolean checkValues(){

        return  checkNumDoc() &&
                checkDateDoc() &&
                checkDivision();
    }

    private boolean checkNumDoc(){
        if (num_doc_edittext.getText().length() == 0) {
            num_doc_edittext.setError("Укажите номер накладной!");
            return false;
        }
        return true;
    }

    private boolean checkDateDoc(){

        if (basisOfCreation == BASIS_OF_CREATION_ON_PP) return true;

        if (date_doc_edittext.getText().length() == 0) {
            date_doc_edittext.setError("Укажите дату накладной! Формат: дд.мм.гггг");
            return false;
        }
        return true;
    }

    private boolean checkDivision(){

        if(Constants.getCurrentDoc().getTypeOfDocument()
                == Constants.TypeOfDocument.OUTER_INCOME) { return true; }

        if (basisOfCreation == BASIS_OF_CREATION_ON_PP) { return true; }


        if (division_textview.getText().length() == 0) {
            division_textview.setError("Выберите подразделение нажав на лапку!");
            return false;
        }
        return true;
    }



    private void createInvoice() throws SQLException, ClassNotFoundException {
        if (basisOfCreation == BASIS_OF_CREATION_NEW ) {
            createInvoiceNew();
        } else if (basisOfCreation == BASIS_OF_CREATION_ON_PP ) {
            createInvoiceOnPp();
        }
    }

    private void createInvoiceOnPp() throws SQLException, ClassNotFoundException {

        String      planIncomeGuid = planIncome.planIncomeGuid
                ,   numDoc = num_doc_edittext.getText().toString()
                ,   rrHeadGuid;

        MsSqlConnection con = new MsSqlConnection();
        CreateRrIncomeViaPlanIncomeQuery query
                = new CreateRrIncomeViaPlanIncomeQuery(con.getConnection(),planIncomeGuid,numDoc);
        query.Execute();
    }

    private void createInvoiceNew() throws SQLException, ClassNotFoundException {

        int checkListTypeId;
        String contractorGUID,divisionGUIDin,divisionGUIDout,numDoc,dateDoc,rrHeadGuid;

        checkListTypeId = Constants.getCurrentDoc().getTypeOfDocument().getIndex();
        contractorGUID = contractorInfo.contractorGuid;
        divisionGUIDin = Constants.getDivisionInfo().guid;
        divisionGUIDout = divisionInfo == null ? null : divisionInfo.guid;
        numDoc = num_doc_edittext.getText().toString();
        dateDoc = date_doc_edittext.getText().toString();

        MsSqlConnection con = new MsSqlConnection();
        CreateRrIncomeQuery query = new CreateRrIncomeQuery(con.getConnection()
                ,checkListTypeId
                ,contractorGUID
                ,divisionGUIDin
                ,divisionGUIDout
                ,numDoc
                ,dateDoc
        );
        query.Execute();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            divisionInfo = null;
            contractorInfo = null;
            planIncome = null;
            basisOfCreation = -1;
        }
    }

    @Override
    public void OnClick(int pos, DivisionInfo innerItem) {
        divisionInfo = innerItem;
        updateUi();
    }

    @Override
    public void OnCancel() {

    }
}
