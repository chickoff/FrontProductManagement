package ru.a5x5retail.frontproductmanagement.db_local;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.math.BigDecimal;


@Entity(active = true,nameInDb = "loCheckingListHead")
public class loCheckingListHead {

    @Id
    private long id;
    private String Guid;
    private String StatusID;
    private String TypeDocID;
    private String sourceGuid;
    private String NameDoc;
    private String Note;
    private String IMEI;
    private String LDM;
    private String LDC;
    private int errorCode;
    private String errorMessage;
    private long summ;
    private String contractorName;
    private long summVat;





    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1086986644)
    private transient loCheckingListHeadDao myDao;

    @Generated(hash = 308055393)
    public loCheckingListHead(long id, String Guid, String StatusID, String TypeDocID,
            String sourceGuid, String NameDoc, String Note, String IMEI, String LDM,
            String LDC, int errorCode, String errorMessage, long summ, String contractorName,
            long summVat) {
        this.id = id;
        this.Guid = Guid;
        this.StatusID = StatusID;
        this.TypeDocID = TypeDocID;
        this.sourceGuid = sourceGuid;
        this.NameDoc = NameDoc;
        this.Note = Note;
        this.IMEI = IMEI;
        this.LDM = LDM;
        this.LDC = LDC;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.summ = summ;
        this.contractorName = contractorName;
        this.summVat = summVat;
    }

    @Generated(hash = 1317607151)
    public loCheckingListHead() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1775495555)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLoCheckingListHeadDao() : null;
    }

    public String getGuid() {
        return this.Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getStatusID() {
        return this.StatusID;
    }

    public void setStatusID(String StatusID) {
        this.StatusID = StatusID;
    }

    public String getTypeDocID() {
        return this.TypeDocID;
    }

    public void setTypeDocID(String TypeDocID) {
        this.TypeDocID = TypeDocID;
    }

    public String getSourceGuid() {
        return this.sourceGuid;
    }

    public void setSourceGuid(String sourceGuid) {
        this.sourceGuid = sourceGuid;
    }

    public String getNameDoc() {
        return this.NameDoc;
    }

    public void setNameDoc(String NameDoc) {
        this.NameDoc = NameDoc;
    }

    public String getNote() {
        return this.Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getIMEI() {
        return this.IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getLDM() {
        return this.LDM;
    }

    public void setLDM(String LDM) {
        this.LDM = LDM;
    }

    public String getLDC() {
        return this.LDC;
    }

    public void setLDC(String LDC) {
        this.LDC = LDC;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getSumm() {
        return this.summ;
    }

    public void setSumm(long summ) {
        this.summ = summ;
    }

    public String getContractorName() {
        return this.contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public long getSummVat() {
        return this.summVat;
    }

    public void setSummVat(long summVat) {
        this.summVat = summVat;
    }
}
