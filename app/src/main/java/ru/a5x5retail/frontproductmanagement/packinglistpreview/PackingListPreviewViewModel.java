package ru.a5x5retail.frontproductmanagement.packinglistpreview;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.AcceptResult;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.models.QueryReturnCode;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.UpdateCheckingListIncSourceQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.UpdateDecommissionSpoilInRrQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.UpdateExternalIncomeInRrQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.UpdateInternalIncomeInRrQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.UpdateLocalInventoryInRrQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.ValidationAndAcceptInvoiceQuery;

public class PackingListPreviewViewModel extends TypedViewModel {

    public CheckingListHead head;
    private MsSqlConnection con;

    public PackingListPreviewViewModel() throws SQLException, ClassNotFoundException {
        con = new MsSqlConnection();
    }

    public QueryReturnCode UpdateInRr(String headGuid, Constants.TypeOfDocument typeDoc) throws SQLException, ClassNotFoundException {


        CallableQuery q = null;
        switch (typeDoc){
            case PARTIAL_INVENTORY:
                q = new UpdateLocalInventoryInRrQuery(con.getConnection(),headGuid);
                break;
            case FULL_INVENTORY:
                break;
            case OUTER_INCOME:
                q = new UpdateCheckingListIncSourceQuery(con.getConnection(),headGuid);
                break;
            case INNER_INCOME:
                q = new UpdateCheckingListIncSourceQuery(con.getConnection(),headGuid);
                break;
            case DISCARD:
                q = new UpdateDecommissionSpoilInRrQuery(con.getConnection(),headGuid);
                break;
            case SETTINGS:
                break;
        }
        q.Execute();
        QueryReturnCode r = new QueryReturnCode();
        r.returnCode = q.returnCode;
        r.eventMessage = q.eventMessage;
        return r;
    }

    public AcceptResult ValideteAndAccept() throws SQLException {
        ValidationAndAcceptInvoiceQuery query = new ValidationAndAcceptInvoiceQuery(con.getConnection(),head.Guid);
        query.Execute();
        return query.getAcceptResult();
    }
}
