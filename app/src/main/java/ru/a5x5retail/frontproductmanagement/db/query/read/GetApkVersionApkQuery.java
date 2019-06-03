package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import ru.a5x5retail.frontproductmanagement.Version;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetApkVersionApkQuery extends CallableQAsync {
    Version version;

    public GetApkVersionApkQuery() {

    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.GetCurrentSettingsByName ?, ?");
    }

    @Override
    protected void SetQueryParams()  {

        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.OTHER);
            getStmt().setString(parameterIndex++, "VersionApk_FPM");
            getStmt().registerOutParameter(parameterIndex, Types.OTHER);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }

    }

  /*  @Override
    protected void Execute() {
        super.Execute();
        try {
            boolean rex = getStmt().execute();
            String strVersion = null;
            strVersion = getStmt().getString(3);
            String[] splitDbVersion = strVersion.split("\\.");
            int
                    realise = Integer.parseInt(splitDbVersion[0] == "" ? "-1"
                    : splitDbVersion[0]),
                    build = Integer.parseInt(splitDbVersion[1] == "" ? "-1"
                            : splitDbVersion[1]);
            version = new Version(realise, build);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseOutputVars() throws SQLException {
            String strVersion = null;
            strVersion = getStmt().getString(3);
            String[] splitDbVersion = strVersion.split("\\.");
            int
                    realise = Integer.parseInt(splitDbVersion[0] == "" ? "-1"
                    : splitDbVersion[0]),
                    build = Integer.parseInt(splitDbVersion[1] == "" ? "-1"
                            : splitDbVersion[1]);
            version = new Version(realise, build);

    }

    public Version getDbVersion() {
        return version;
    }
}
