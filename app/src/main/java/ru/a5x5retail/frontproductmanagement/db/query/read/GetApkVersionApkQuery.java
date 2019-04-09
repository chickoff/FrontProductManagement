package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import ru.a5x5retail.frontproductmanagement.Version;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetApkVersionApkQuery extends CallableQuery<IncomeInvoiceHead> {
    Version version;

    public GetApkVersionApkQuery(Connection connection) {
        super(connection);
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.GetCurrentSettings (?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.registerOutParameter(1, Types.OTHER);
        stmt.setString(2, "VersionApk_FPM");
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        String strVersion = stmt.getString(1);
        String[] splitDbVersion = strVersion.split("\\.");
        int
                realise = Integer.parseInt(splitDbVersion[0] == "" ? "-1"
                :splitDbVersion[0]),
                build = Integer.parseInt(splitDbVersion[1] == "" ? "-1"
                        :splitDbVersion[1]);
        version = new Version(realise,build);
    }

    public Version getDbVersion() {
        return version;
    }
}
