package ru.a5x5retail.frontproductmanagement.db_local;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true,nameInDb = "SkuContext")
public class loSkuContext {
    @Id
    public long Code;
    public String NameLong;
    public Integer MeasureUnitIDD;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 74108760)
    private transient loSkuContextDao myDao;
    @Generated(hash = 9257594)
    public loSkuContext(long Code, String NameLong, Integer MeasureUnitIDD) {
        this.Code = Code;
        this.NameLong = NameLong;
        this.MeasureUnitIDD = MeasureUnitIDD;
    }
    @Generated(hash = 899962834)
    public loSkuContext() {
    }
    public long getCode() {
        return this.Code;
    }
    public void setCode(long Code) {
        this.Code = Code;
    }
    public String getNameLong() {
        return this.NameLong;
    }
    public void setNameLong(String NameLong) {
        this.NameLong = NameLong;
    }
    public Integer getMeasureUnitIDD() {
        return this.MeasureUnitIDD;
    }
    public void setMeasureUnitIDD(Integer MeasureUnitIDD) {
        this.MeasureUnitIDD = MeasureUnitIDD;
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
    @Generated(hash = 680791932)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLoSkuContextDao() : null;
    }
    public void setCode(int Code) {
        this.Code = Code;
    }

}
