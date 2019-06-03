package ru.a5x5retail.frontproductmanagement.db_local.repository;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.db_local.DatabaseInit;
import ru.a5x5retail.frontproductmanagement.db_local.Settings;
import ru.a5x5retail.frontproductmanagement.db_local.SettingsDao;

public class SettingsRepository {
    private SettingsDao dao;
    public SettingsRepository() {
        dao = DatabaseInit.getDaoSession().getSettingsDao();
    }

    public Settings getValue(String key) {
        List<Settings> list = dao.loadAll();
        if (list != null) {
            for (Settings settings : list) {
                if (settings.getKey().equals(key)) {
                    return settings;
                }
            }
        }
        return null;
    }

    public void setValue(String key, String value) {
        Settings s = getValue(key);
        if (s != null) {
            s.setValue(value);
        } else {
            s = new Settings();
            s.setKey(key);
            s.setValue(value);
        }
        dao.insertOrReplace(s);
    }
}
