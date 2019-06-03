package ru.a5x5retail.frontproductmanagement.filters.skufilter;

import java.sql.SQLException;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetFreeSkuForCheckigListQuery;

public class SkuFilterViewModel extends TypedViewModel {


    public SkuFilterViewModel() throws SQLException, ClassNotFoundException {
        con = new MsSqlConnection();
    }


    MsSqlConnection con;

    private List<CodeInfo> codeInfoList;
    public List<CodeInfo> getCodeInfoList() {
        return codeInfoList;
    }
    public void setCodeInfoList(List<CodeInfo> codeInfoList) {
        this.codeInfoList = codeInfoList;
    }

    private String checkingListGuid;
    public String getCheckingListGuid() {
        return checkingListGuid;
    }
    public void setCheckingListGuid(String checkingListGuid) {
        this.checkingListGuid = checkingListGuid;
    }

    public static final int ONE_SOURCE = 1;

    private int CaseOfSource = -1;
    public int getCaseOfSource() {
        return CaseOfSource;
    }

    public void setCaseOfSource(int caseOfSource) {
        CaseOfSource = caseOfSource;
    }

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
        switch (getCaseOfSource()) {
            case ONE_SOURCE :
                load();
                break;
        }
        onDataChanged();
    }

    private void load() throws SQLException, ClassNotFoundException {
        final GetFreeSkuForCheckigListQuery query = new GetFreeSkuForCheckigListQuery(getCheckingListGuid());
        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                setCodeInfoList(query.getList());
            }
        });
        query.ExecuteAsync();

    }
}
