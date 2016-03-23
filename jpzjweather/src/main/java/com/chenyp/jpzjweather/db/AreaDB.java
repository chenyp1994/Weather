package com.chenyp.jpzjweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;


import com.chenyp.jpzjweather.bean.Area;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyp on 15-6-17.
 */
public class AreaDB {

    /**
     * DataBase Name
     */
    public static final String DB_NAME = "DBArea";

    public static final String TAG = "AreaDB";

    /**
     * DataBase Version
     */
    public static final int VERSION = 1;

    private static AreaDB areaDB;

    private SQLiteDatabase db;
    private Context context;

    /**
     * 将构造方法私有化
     *
     * @param context
     */
    private AreaDB(Context context) {
        AreaOpenHelper dbHelper = new AreaOpenHelper(context,
                DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * 获取DataBaseDB的实例。
     *
     * @param context
     * @return
     */
    public synchronized static AreaDB getInstance(Context context) {
        if (areaDB == null) {
            areaDB = new AreaDB(context);
        }
        return areaDB;
    }

    /**
     * 将Area实例存储到数据库。
     *
     * @param area
     */
    public void saveArea(Area area) {
        if (area != null) {
            ContentValues values = new ContentValues();
            values.put("areaid", area.getAreaid());
            values.put("nameen", area.getNameen());
            values.put("namecn", area.getNamecn());
            values.put("districten", area.getDistricten());
            values.put("districtcn", area.getDistrictcn());
            values.put("provinceen", area.getProvinceen());
            values.put("provincecn", area.getProvincecn());
            values.put("nationen", area.getNameen());
            values.put("nationcn", area.getNationcn());
            db.insert("Area", null, values);
        }
    }

    /**
     * 根据areaid获取Area实例
     *
     * @param areaid
     * @return
     */
    public Area getAreaByAreaid(String areaid) {
        if (TextUtils.isEmpty(areaid))
            return null;
        Cursor cursor = db.query("Area", null, "areaid = ?",
                new String[]{areaid}, null, null, null);

        if (cursor.moveToFirst()) {
            return null;
        }
        Area area = new Area();
        area.setId(cursor.getInt(cursor.getColumnIndex("_id")));
        area.setAreaid(cursor.getLong(cursor.getColumnIndex("areaid")));
        area.setNamecn(cursor.getString(cursor
                .getColumnIndex("namecn")));
        area.setNameen(cursor.getString(cursor
                .getColumnIndex("nameen")));
        area.setDistrictcn(cursor.getString(cursor
                .getColumnIndex("districtcn")));
        area.setDistricten(cursor.getString(cursor
                .getColumnIndex("districten")));
        area.setProvincecn(cursor.getString(cursor
                .getColumnIndex("provincecn")));
        area.setProvinceen(cursor.getString(cursor.getColumnIndex("provinceen")));
        area.setNationcn(cursor.getString(cursor.getColumnIndex("nationcn")));
        area.setNationen(cursor.getString(cursor.getColumnIndex("nationen")));

        cursor.close();
        return area;
    }

    /**
     * 通过区域的名字获取到Area实例
     *
     * @param namecn
     * @return
     */
    public Area getAreaByName(String namecn) {
        if (TextUtils.isEmpty(namecn))
            return null;
        Cursor cursor = db.query("Area", null, "namecn = ?",
                new String[]{namecn}, null, null, null);

        if (!cursor.moveToFirst()) {
            return null;
        }
        Area area = new Area();
        area.setId(cursor.getInt(cursor.getColumnIndex("_id")));
        area.setAreaid(cursor.getLong(cursor.getColumnIndex("areaid")));
        area.setNamecn(cursor.getString(cursor
                .getColumnIndex("namecn")));
        area.setNameen(cursor.getString(cursor
                .getColumnIndex("nameen")));
        area.setDistrictcn(cursor.getString(cursor
                .getColumnIndex("districtcn")));
        area.setDistricten(cursor.getString(cursor
                .getColumnIndex("districten")));
        area.setProvincecn(cursor.getString(cursor
                .getColumnIndex("provincecn")));
        area.setProvinceen(cursor.getString(cursor.getColumnIndex("provinceen")));
        area.setNationcn(cursor.getString(cursor.getColumnIndex("nationcn")));
        area.setNationen(cursor.getString(cursor.getColumnIndex("nationen")));

        cursor.close();
        return area;
    }

    /**
     * 获取所有城市List
     *
     * @return
     */
    public List<Area> getAllArea() {
        List<Area> list = new ArrayList<Area>();
        Cursor cursor = db.query("Area", null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Area area = new Area();
                area.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                area.setAreaid(cursor.getLong(cursor.getColumnIndex("areaid")));
                area.setNamecn(cursor.getString(cursor
                        .getColumnIndex("namecn")));
                area.setNameen(cursor.getString(cursor
                        .getColumnIndex("nameen")));
                area.setDistrictcn(cursor.getString(cursor
                        .getColumnIndex("districtcn")));
                area.setDistricten(cursor.getString(cursor
                        .getColumnIndex("districten")));
                area.setProvincecn(cursor.getString(cursor
                        .getColumnIndex("provincecn")));
                area.setProvinceen(cursor.getString(cursor.getColumnIndex("provinceen")));
                area.setNationcn(cursor.getString(cursor.getColumnIndex("nationcn")));
                area.setNationen(cursor.getString(cursor.getColumnIndex("nationen")));
                list.add(area);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void execAreaSQL() {
        try {
            InputStream in = context.getAssets().open("area_list.sql");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String sqlUpdate = null;
            while ((sqlUpdate = bufferedReader.readLine()) != null) {
                if (!TextUtils.isEmpty(sqlUpdate)) {
                    db.execSQL(sqlUpdate);
                }
            }
            bufferedReader.close();
            in.close();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return db;
    }

    public Cursor getAreaByNameen(String nameen) {
        if (TextUtils.isEmpty(nameen))
            return null;
        Cursor cursor = db.query("Area", null, "nameen like ?",
                new String[]{nameen + "%"}, null, null, null);

        if (!cursor.moveToFirst()) {
            return null;
        }
        return cursor;

    }


    public Cursor getAreaByNamecn(String namecn) {
        if (TextUtils.isEmpty(namecn))
            return null;
        Cursor cursor = db.query("Area", null, "namecn like ?",
                new String[]{namecn + "%"}, null, null, null);

        if (!cursor.moveToFirst()) {
            return null;
        }

        return cursor;
    }

}
