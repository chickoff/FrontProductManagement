package ru.a5x5retail.frontproductmanagement.db_local.repository;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.db_local.DatabaseInit;
import ru.a5x5retail.frontproductmanagement.db_local.loSkuContext;
import ru.a5x5retail.frontproductmanagement.db_local.loSkuContextDao;

public class SkuContextRepository {
    loSkuContextDao contextDao;
    public SkuContextRepository() {
        contextDao = DatabaseInit.getDaoSession().getLoSkuContextDao();
    }

    public List<loSkuContext> getAll() {
        return DatabaseInit.getDaoSession().getLoSkuContextDao().loadAll();
    }

    public List<loSkuContext> find(String value) {

        String expr = "%".concat(value).concat("%");

        loSkuContextDao dao = DatabaseInit.getDaoSession().getLoSkuContextDao();
        QueryBuilder<loSkuContext> qb = dao.queryBuilder();
        List<loSkuContext> list = qb.where(qb.or(loSkuContextDao.Properties.Code.like(expr),loSkuContextDao.Properties.NameLong.like(expr))).list();
        return list;
    }
}
