package ru.a5x5retail.frontproductmanagement.db_local;

import java.math.BigDecimal;
import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.DocType;
import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.CheckListInventory;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.models.DivisionInfo;

public class ProjectMap {

    public static DocType getCurrentTypeDoc(){
        DaoSession daoSession = DatabaseInit.getDaoSession();
        CurrentTypeOfDocumentDao dao = daoSession.getCurrentTypeOfDocumentDao();
        CurrentTypeOfDocument docId = dao.load((long) 0);
        Constants.TypeOfDocument typeOfDoc = Constants.TypeOfDocument.getByOrd(docId.getTypeOfDoc());
        DocType docType = AppConfigurator.getTypeDocByType(typeOfDoc);
        return docType;
    }

    public static void setCurrentTypeDoc(DocType docType){
        DaoSession daoSession = DatabaseInit.getDaoSession();
        CurrentTypeOfDocumentDao dao = daoSession.getCurrentTypeOfDocumentDao();
        CurrentTypeOfDocument currentTypeOfDocument = new CurrentTypeOfDocument();
        currentTypeOfDocument.setTypeOfDoc(docType.getTypeOfDocument().getIndex());
        currentTypeOfDocument.setId(0);
        dao.insertOrReplace(currentTypeOfDocument);

    }

    public static CheckingListHead getCurrentCheckingListHead(){
        DaoSession daoSession = DatabaseInit.getDaoSession();
        loCheckingListHeadDao dao = daoSession.getLoCheckingListHeadDao();
        loCheckingListHead lo_head = dao.load((long) 0);

        CheckingListHead head = new CheckingListHead();
        head.Guid = lo_head.getGuid();
        head.StatusID = lo_head.getStatusID() ;
        head.TypeDocID = lo_head.getTypeDocID() ;
        head.sourceGuid = lo_head.getSourceGuid() ;
        head.NameDoc = lo_head.getNameDoc() ;
        head.Note = lo_head.getNote() ;
        head.IMEI = lo_head.getIMEI() ;
        head.LDM = lo_head.getLDM() ;
        head.LDC = lo_head.getLDC() ;
        head.errorCode =  lo_head.getErrorCode();
        head.errorMessage = lo_head.getErrorMessage() ;
        head.summ = BigDecimal.valueOf(lo_head.getSumm()).scaleByPowerOfTen(-4);
        head.contractorName = lo_head.getContractorName() ;
        head.summVat = BigDecimal.valueOf(lo_head.getSummVat()).scaleByPowerOfTen(-4);
        return head;
    }

    public static void setCurrentCheckListHead(CheckingListHead head) {
        DaoSession daoSession = DatabaseInit.getDaoSession();
        loCheckingListHeadDao dao = daoSession.getLoCheckingListHeadDao();
        loCheckingListHead lo_head = new loCheckingListHead();

        try {
            UUID bb = UUID.fromString(head.Guid);
        } catch (Exception e){
            e.printStackTrace();
        }


        lo_head.setGuid(head.Guid);
        lo_head.setStatusID(head.StatusID);
        lo_head.setTypeDocID(head.TypeDocID);
        lo_head.setSourceGuid(head.sourceGuid);
        lo_head.setNameDoc(head.NameDoc);
        lo_head.setNote(head.Note);
        lo_head.setIMEI(head.IMEI);
        lo_head.setLDM(head.LDM);
        lo_head.setLDC(head.LDC);
        lo_head.setErrorCode(head.errorCode);
        lo_head.setErrorMessage(head.errorMessage);
        lo_head.setSumm(head.summ.scaleByPowerOfTen(4).longValue());
        lo_head.setContractorName(head.contractorName);
        lo_head.setSummVat(head.summVat.scaleByPowerOfTen(4).longValue());
        lo_head.setId(0);
        dao.insertOrReplace(lo_head);

    }

    public static CheckListInventory getCurrentInventoryCheckList(){
        DaoSession daoSession = DatabaseInit.getDaoSession();
        loCheckListInventoryDao dao = daoSession.getLoCheckListInventoryDao();
        loCheckListInventory lo_head = dao.load((long) 0);
        CheckListInventory head = new CheckListInventory();
        head.checkingListHeadGuid = lo_head.getCheckingListHeadGuid();
        head.nameDoc = lo_head.getNameDoc();


        /*CheckingListHead head = new CheckingListHead();
        head.Guid = lo_head.getGuid();
        head.StatusID = lo_head.getStatusID() ;
        head.TypeDocID = lo_head.getTypeDocID() ;
        head.sourceGuid = lo_head.getSourceGuid() ;
        head.NameDoc = lo_head.getNameDoc() ;
        head.Note = lo_head.getNote() ;
        head.IMEI = lo_head.getIMEI() ;
        head.LDM = lo_head.getLDM() ;
        head.LDC = lo_head.getLDC() ;
        head.errorCode =  lo_head.getErrorCode();
        head.errorMessage = lo_head.getErrorMessage() ;
        head.summ = BigDecimal.valueOf(lo_head.getSumm()).scaleByPowerOfTen(-4);
        head.contractorName = lo_head.getContractorName() ;
        head.summVat = BigDecimal.valueOf(lo_head.getSummVat()).scaleByPowerOfTen(-4);*/
        return head;
    }

    public static void setInventoryCheckList(CheckListInventory head) {
        DaoSession daoSession = DatabaseInit.getDaoSession();
        loCheckListInventoryDao dao = daoSession.getLoCheckListInventoryDao();
        loCheckListInventory lo_head = new loCheckListInventory();

        lo_head.setCheckingListHeadGuid(head.checkingListHeadGuid);
        lo_head.setNameDoc(head.nameDoc);
        lo_head.setId(0);

     /*   try {
            UUID bb = UUID.fromString(head.Guid);
        } catch (Exception e){
            e.printStackTrace();
        }


        lo_head.setGuid(head.Guid);
        lo_head.setStatusID(head.StatusID);
        lo_head.setTypeDocID(head.TypeDocID);
        lo_head.setSourceGuid(head.sourceGuid);
        lo_head.setNameDoc(head.NameDoc);
        lo_head.setNote(head.Note);
        lo_head.setIMEI(head.IMEI);
        lo_head.setLDM(head.LDM);
        lo_head.setLDC(head.LDC);
        lo_head.setErrorCode(head.errorCode);
        lo_head.setErrorMessage(head.errorMessage);
        lo_head.setSumm(head.summ.scaleByPowerOfTen(4).longValue());
        lo_head.setContractorName(head.contractorName);
        lo_head.setSummVat(head.summVat.scaleByPowerOfTen(4).longValue());*/

        dao.insertOrReplace(lo_head);

    }

    public static DivisionInfo getMainInfo(){
        DaoSession daoSession = DatabaseInit.getDaoSession();
        loDivisionInfoDao dao = daoSession.getLoDivisionInfoDao();
        loDivisionInfo obj = dao.load((long) 0);

        DivisionInfo di = new DivisionInfo();
        di.guid = obj.getGuid();
        di.inn = obj.getInn();
        di.kpp =obj.getKpp();
        di.nameLong = obj.getNameLong();
        di.postalAddress = obj.getPostalAddress();

        return di;
    }

    public static void setMainInfo(DivisionInfo obj){
        DaoSession daoSession = DatabaseInit.getDaoSession();
        loDivisionInfoDao dao = daoSession.getLoDivisionInfoDao();
        loDivisionInfo loDivisionInfo = new loDivisionInfo();

        loDivisionInfo.setGuid(obj.guid);
        loDivisionInfo.setNameLong(obj.nameLong);
        loDivisionInfo.setInn(obj.inn);
        loDivisionInfo.setKpp(obj.kpp);
        loDivisionInfo.setPostalAddress(obj.postalAddress);
        loDivisionInfo.setId(0);
        dao.insertOrReplace(loDivisionInfo);
    }
}
