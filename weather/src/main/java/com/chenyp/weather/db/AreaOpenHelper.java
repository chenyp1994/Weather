package com.chenyp.weather.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenyp on 15-6-17.
 */
public class AreaOpenHelper extends SQLiteOpenHelper {

    /**
     * Create Area Table SQL
     */
    public static final String CREATE_AEAR = "create table Area ("
            + "id long primary key autoincrement, "
            + "areaid long,"
            + "namecn text,"
            + "nameen text,"
            + "districtcn text,"
            + "districten text,"
            + "provincecn text,"
            + "provinceen text,"
            + "nationcn text,"
            + "nationen text)";

    public AreaOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AreaOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_AEAR);  // 创建Area表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
