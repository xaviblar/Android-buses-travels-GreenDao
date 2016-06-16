package com.example.xavier.lab_viajesbuses;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.xavier.lab_viajesbuses.dao.DaoMaster;
import com.example.xavier.lab_viajesbuses.dao.DaoSession;
import com.example.xavier.lab_viajesbuses.dao.busesDao;
import com.example.xavier.lab_viajesbuses.dao.viajesDao;

/**
 * Created by Xavier on 11/15/2015.
 */
public class DaoAPP extends Application {
    static busesDao busDao;
    static viajesDao viajeDao;

    @Override
    public void onCreate()
    {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"ViajesBuses-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        busDao = daoSession.getBusesDao();
        viajeDao = daoSession.getViajesDao();
    }

    public static busesDao getBusDao()
    {
        return busDao;
    }

    public static viajesDao getViajeDao()
    {
        return viajeDao;
    }
}
