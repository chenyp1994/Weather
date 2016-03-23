package com.chenyp.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.chenyp.weather.bean.Area;

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

    /**
     * DataBase Version
     */
    public static final int VERSION = 1;

    private static AreaDB areaDB;

    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     *
     * @param context
     */
    private AreaDB(Context context) {
        AreaOpenHelper dbHelper = new AreaOpenHelper(context,
                DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
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
            values.put("namecn", area.getNamecn());
            values.put("nameen", area.getNameen());
            values.put("districtcn", area.getDistrictcn());
            values.put("districten", area.getDistricten());
            values.put("provincecn", area.getProvincecn());
            values.put("provinceen", area.getProvinceen());
            values.put("nationcn", area.getNationcn());
            values.put("nationen", area.getNameen());
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
        area.setId(cursor.getLong(cursor.getColumnIndex("id")));
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

        if (cursor.moveToFirst()) {
            return null;
        }
        Area area = new Area();
        area.setId(cursor.getLong(cursor.getColumnIndex("id")));
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
     * @return
     */
    public List<Area> getAllArea() {
        List<Area> list = new ArrayList<Area>();
        Cursor cursor = db.query("Area", null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Area area = new Area();
                area.setId(cursor.getLong(cursor.getColumnIndex("id")));
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

}
