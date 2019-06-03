package ru.a5x5retail.frontproductmanagement.db_local;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true,nameInDb = "loCheckListInventory")
public class loCheckListInventory {
    @Id
    private long id;
    private String checkingListHeadGuid;
    private String nameDoc;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1694199612)
    private transient loCheckListInventoryDao myDao;
    @Generated(hash = 162535098)
    public loCheckListInventory(long id, String checkingListHeadGuid,
            String nameDoc) {
        this.id = id;
        this.checkingListHeadGuid = checkingListHeadGuid;
        this.nameDoc = nameDoc;
    }
    @Generated(hash = 1329578070)
    public loCheckListInventory() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getCheckingListHeadGuid() {
        return this.checkingListHeadGuid;
    }
    public void setCheckingListHeadGuid(String checkingListHeadGuid) {
        this.checkingListHeadGuid = checkingListHeadGuid;
    }
    public String getNameDoc() {
        return this.nameDoc;
    }
    public void setNameDoc(String nameDoc) {
        this.nameDoc = nameDoc;
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
    @Generated(hash = 103452550)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLoCheckListInventoryDao() : null;
    }

/*CheckingListHeadGUID
StatusID
TypeDocID
SourceGUID
NameDoc
IMEI
InventoryGUID
InventoryNameLong
DivisionGUID
DivisionNameLong
StartDate
InventoryTypeIDD
InventoryTypeNameLong
DivisionPlacementNameLong
IsToTSD	LockInfo
LockFlag
ParentGUID
InventoryStateIDD
InventoryStateNameLong
Note*/
}
