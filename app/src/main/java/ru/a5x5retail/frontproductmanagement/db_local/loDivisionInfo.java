package ru.a5x5retail.frontproductmanagement.db_local;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;


@Entity(active = true,nameInDb = "loDivisionInfo")
public class loDivisionInfo {
    @Id
    private long id;
    private String guid;
    private String nameLong;
    private String inn;
    private String kpp;
    private String postalAddress;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2067919714)
    private transient loDivisionInfoDao myDao;
    @Generated(hash = 1498519982)
    public loDivisionInfo(long id, String guid, String nameLong, String inn,
            String kpp, String postalAddress) {
        this.id = id;
        this.guid = guid;
        this.nameLong = nameLong;
        this.inn = inn;
        this.kpp = kpp;
        this.postalAddress = postalAddress;
    }
    @Generated(hash = 1538356826)
    public loDivisionInfo() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getGuid() {
        return this.guid;
    }
    public void setGuid(String guid) {
        this.guid = guid;
    }
    public String getNameLong() {
        return this.nameLong;
    }
    public void setNameLong(String nameLong) {
        this.nameLong = nameLong;
    }
    public String getInn() {
        return this.inn;
    }
    public void setInn(String inn) {
        this.inn = inn;
    }
    public String getKpp() {
        return this.kpp;
    }
    public void setKpp(String kpp) {
        this.kpp = kpp;
    }
    public String getPostalAddress() {
        return this.postalAddress;
    }
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
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
    @Generated(hash = 1698089638)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLoDivisionInfoDao() : null;
    }
}
