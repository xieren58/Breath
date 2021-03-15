package com.zkp.breath.database.greendao.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zkp.breath.jetpack.paging.StudentDao;

//import org.greenrobot.greendao.database.Database;

/**
 * 升级的话需要自定义，然后重写onUpgrade()方法
 */
public class CusDevOpenHelper /*extends DaoMaster.OpenHelper */{
//    private static final String TAG = "CusDevOpenHelper";
//
//    public CusDevOpenHelper(Context context, String name) {
//        super(context, name);
//    }
//
//    public CusDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
//        super(context, name, factory);
//    }
//
//    @Override
//    public void onUpgrade(Database db, int oldVersion, int newVersion) {
//        super.onUpgrade(db, oldVersion, newVersion);
//        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
//            @Override
//            public void onCreateAllTables(Database db, boolean ifNotExists) {
//                DaoMaster.createAllTables(db, ifNotExists);
//            }
//
//            @Override
//            public void onDropAllTables(Database db, boolean ifExists) {
//                DaoMaster.dropAllTables(db, ifExists);
//            }
//        }, StudentDao.class);
//        Log.i(TAG, "onUpgrade: " + oldVersion + " newVersion = " + newVersion);
//    }
}
