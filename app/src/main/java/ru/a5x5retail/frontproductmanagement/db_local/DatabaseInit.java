package ru.a5x5retail.frontproductmanagement.db_local;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import java.io.File;

import ru.a5x5retail.frontproductmanagement.ProdManApp;

public  class DatabaseInit {

    public static DaoMaster.DevOpenHelper getHelper() {
        return helper;
    }

    private static DaoMaster.DevOpenHelper helper;

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    private static DaoSession daoSession;

    public  static void InitDao(Application app)
    {


        File dir = ProdManApp.getAppContext().getExternalFilesDir("db");
        File outputFile = new File(dir, "localdb.sqlite");
        helper = new DaoMaster.DevOpenHelper(app,outputFile.getAbsolutePath());


        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }
}
