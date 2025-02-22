package ru.a5x5retail.frontproductmanagement.configuration;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.DocType;
import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.Version;
import ru.a5x5retail.frontproductmanagement.assortmentcard.AssortmentCardActivity;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetApkVersionApkQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetMainDivisionInfoQuery;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.inventories.InventoriesActivity;
import ru.a5x5retail.frontproductmanagement.packinglistitems.PackingListItemsActivity;
import ru.a5x5retail.frontproductmanagement.printprice.document.PrintPriceActivity;
import ru.a5x5retail.frontproductmanagement.settings.SettingsActivity;

import static android.content.Context.MODE_PRIVATE;


public class AppConfigurator {

    public static String getDeviceId(Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return
                //"863961030323132";
                manager.getDeviceId();
    }

    public static String[] getConnectionString(){
        SharedPreferences prefs = getPreferences();
        String ip = prefs.getString("ip","");
        String login =  prefs.getString("login","");
        String password =  prefs.getString("password","");
        return new String[] { ip,login,password };
    }

    public static String getServerIp() {
        SharedPreferences prefs = getPreferences();
        return prefs.getString("ip","");
    }

    public static String getUpdSvrPort() {
        SharedPreferences prefs = getPreferences();
        return prefs.getString("upd_svr_port","");
    }

    public static boolean isDebug() {
        SharedPreferences prefs = getPreferences();
        return prefs.getBoolean("is_debug",false);
    }

    private static List<DocType> list;
    private static List<DocType> searchList;



    private static SharedPreferences getPreferences() {
        SharedPreferences prefs = ProdManApp.getAppContext()
                .getSharedPreferences("PswdPref", MODE_PRIVATE);
        return prefs;
    }

    public  static List<DocType> getAvailableDocTypes(){
        list = new ArrayList<>();
        searchList = new ArrayList<>();

        DocType doc = new DocType<PackingListItemsActivity>();
        doc.setClassOfActivity(PrintPriceActivity.class);
        doc.setTypeOfDocument(Constants.TypeOfDocument.PRINT_PRICE);
        doc.setName("Печать ценников");
        doc.setShortName("Ценники");
        list.add(doc);
        searchList.add(doc);


/************************************************************************************************/
/**      Блок инвентаризации          */

        doc = new DocType<PackingListItemsActivity>();
        doc.setClassOfActivity(PackingListItemsActivity.class);
        doc.setTypeOfDocument(Constants.TypeOfDocument.PARTIAL_INVENTORY);
        doc.setName("Инвентаризация");
        doc.setShortName("");

            /**************************
             *      Дочки !
             * */

            List<DocType> inventoryChilds = new ArrayList<>();

            DocType loChild = new DocType();
            loChild.setClassOfActivity(InventoriesActivity.class);
            loChild.setTypeOfDocument(Constants.TypeOfDocument.PARTIAL_INVENTORY);
            loChild.setName("Локальная инвентаризация");
            loChild.setShortName("Локалка");
            loChild.setParentDoc(doc);
            inventoryChilds.add(loChild);
            searchList.add(loChild);

            loChild = new DocType();
            loChild.setClassOfActivity(InventoriesActivity.class);
            loChild.setTypeOfDocument(Constants.TypeOfDocument.FULL_INVENTORY);
            loChild.setName("Полная инвентаризация");
            loChild.setShortName("Полная инв.");
            loChild.setSuperPwdProtect(true);
            loChild.setParentDoc(doc);
            inventoryChilds.add(loChild);
            searchList.add(loChild);

            doc.setChildDocs(inventoryChilds);

            /****************************/

        list.add(doc);
        searchList.add(doc);
/************************************************************************************************/

        doc = new DocType<PackingListItemsActivity>();
        doc.setClassOfActivity(PackingListItemsActivity.class);
        doc.setTypeOfDocument(Constants.TypeOfDocument.OUTER_INCOME);
        doc.setName("Приход от стороннего поставщика");
        doc.setShortName("Приход (линия 1)");
        list.add(doc);
        searchList.add(doc);

        doc = new DocType<PackingListItemsActivity>();
        doc.setClassOfActivity(PackingListItemsActivity.class);
        doc.setTypeOfDocument(Constants.TypeOfDocument.INNER_INCOME);
        doc.setName("Приход по внутреннему перемещению");
        doc.setShortName("Приход (линия 11)");
        list.add(doc);
        searchList.add(doc);


        doc = new DocType<PackingListItemsActivity>();
        doc.setClassOfActivity(PackingListItemsActivity.class);
        doc.setTypeOfDocument(Constants.TypeOfDocument.DISCARD);
        doc.setName("Внутреннее перемещение");
        doc.setShortName("Внутр. перемещение");
        list.add(doc);
        searchList.add(doc);


        doc = new DocType<AssortmentCardActivity>();
        doc.setClassOfActivity(AssortmentCardActivity.class);
        doc.setTypeOfDocument(Constants.TypeOfDocument.ASSORTMENT_CARD);
        doc.setName("Карточка товара");
        list.add(doc);
        searchList.add(doc);

        doc = new DocType<SettingsActivity>();
        doc.setClassOfActivity(SettingsActivity.class);
        doc.setTypeOfDocument(Constants.TypeOfDocument.SETTINGS);
        doc.setName("Настройки");
        list.add(doc);
        searchList.add(doc);

        return list;
    }

    public static DocType getTypeDocByType(Constants.TypeOfDocument type){
        for (DocType docType : searchList) {
            if (docType.getTypeOfDocument().equals(type)){
                return docType;
            }
        }
        return null;
    }

    public static Version GetCurrentVersion () {
        Version ver = new Version();
        try {
            PackageInfo pinfo = ProdManApp.getAppContext().getPackageManager()
                    .getPackageInfo(ProdManApp.getAppContext().getPackageName(), 0);
            String versionName = pinfo.versionName;
            String[] splitVersionName = versionName.split("\\.");

            int
                realise = Integer.parseInt(splitVersionName[0] == "" ? "-1"
                        :splitVersionName[0]),
                build = Integer.parseInt(splitVersionName[1] == "" ? "-1"
                        :splitVersionName[1]);
            ver.setRealise(realise);
            ver.setBuild(build);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ver;
    }

   /*public static int checkNewVersion() {
        int result = 0;
        GetApkVersionApkQuery query;
        try {

            query = new GetApkVersionApkQuery();
            query.Execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return -1;
        }

        Version dbVersion = query.getDbVersion();
        Version currentVersion = GetCurrentVersion();
        if (currentVersion.getRealise() != dbVersion.getRealise() ||
            currentVersion.getBuild() != dbVersion.getBuild()) {
                result = 1;


            /*Context context = ProdManApp.getAppContext();
            Intent intent = new Intent(context, UpdateApplicationActivity.class);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);*/

       /*}
        return result;
    }*/

    public static void getMainInfo() throws SQLException, ClassNotFoundException {
        final GetMainDivisionInfoQuery query = new GetMainDivisionInfoQuery();
        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                ProjectMap.setMainInfo(query.getDivisionInfo());
            }
        });
        query.ExecuteAsync();

    }

    public static class Period {

        public static Date getStartDate() {
            return null;
        }
        public static Date getEndDate() {
            return null;
        }
    }



}
