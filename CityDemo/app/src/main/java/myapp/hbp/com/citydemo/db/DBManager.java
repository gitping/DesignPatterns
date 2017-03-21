package myapp.hbp.com.citydemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import myapp.hbp.com.citydemo.model.RegionBean;

/**
 * author Bro0cL on 2016/1/26.
 */
public class DBManager {

    private static final String ASSETS_NAME = "city.db";
    private static final String DB_NAME = "city.db";
    private static final String TABLE_NAME = "region";
    private static final String REGION_NAME = "regionName";
    private static final String REGION_CODE = "regionCode";
    private static final String PINYIN = "pinyin";
    private static final int BUFFER_SIZE = 1024;
    private String DB_PATH;
    private Context mContext;

    public DBManager(Context context) {
        this.mContext = context;
        DB_PATH = File.separator + "data"
                + Environment.getDataDirectory().getAbsolutePath() + File.separator
                + context.getPackageName() + File.separator + "databases" + File.separator;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void copyDBFile() {
        File dir = new File(DB_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()) {
            InputStream is;
            OutputStream os;
            try {
                is = mContext.getResources().getAssets().open(ASSETS_NAME);
                os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = is.read(buffer, 0, buffer.length)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取全国所有的省份
     *
     * @return 返回省份和直辖市
     */
    public List<RegionBean> getProvinces() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        String sql = "select * from " + TABLE_NAME + " where " + REGION_CODE + " like '%0000'";
        Cursor cursor = db.rawQuery(sql, null);
        List<RegionBean> result = new ArrayList<>();
        RegionBean city;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(REGION_NAME));
            String code = cursor.getString(cursor.getColumnIndex(REGION_CODE));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
            city = new RegionBean(name, code, pinyin);
            result.add(city);
        }
        cursor.close();
        db.close();
        Collections.sort(result, new CityComparator());
        return result;
    }

    /**
     * 获取全国所有的省份
     *
     * @param keyword 模糊筛选的条件（中文或简拼）
     * @return 返回省份和直辖市
     */
    public List<RegionBean> searchProvinces(final String keyword) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        String sql = "select * from " + TABLE_NAME +
                " where " + REGION_CODE + " like '%0000' " +
                "and " + REGION_NAME + " like '%" + keyword + "%' " +
                "or " + PINYIN + " like '%" + keyword + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        List<RegionBean> result = new ArrayList<>();
        RegionBean city;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(REGION_NAME));
            String code = cursor.getString(cursor.getColumnIndex(REGION_CODE));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
            city = new RegionBean(name, code, pinyin);
            result.add(city);
        }
        cursor.close();
        db.close();
        Collections.sort(result, new CityComparator());
        return result;
    }

    /**
     * 根据一级城市的code查询出它下面的市或区信息
     *
     * @param byCode 一级城市的code
     * @return 市或区信息
     */
    public List<RegionBean> findByCodeDistricts(final String byCode) {
        if (TextUtils.isEmpty(byCode)) {
            return null;
        }
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        String sql = "select * from " + TABLE_NAME +
                " where " + REGION_CODE + " like '" + byCode.substring(0, 2) + "%' " +
                "AND " + REGION_CODE + " like '%00'" +
                "AND " + REGION_CODE + " not like '%0000'";
        Cursor cursor = db.rawQuery(sql, null);
        List<RegionBean> districtList = new ArrayList<>();
        RegionBean city;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(REGION_NAME));
            String code = cursor.getString(cursor.getColumnIndex(REGION_CODE));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
            city = new RegionBean(name, code, pinyin);
            districtList.add(city);
        }
        cursor.close();
        db.close();
        return districtList;
    }

    /**
     * 根据二级城市的code查询出它下面的区或县信息
     *
     * @param byCode 二级城市的code
     * @return 区或县信息
     */
    public List<RegionBean> findByCodeCountys(final String byCode) {
        if (TextUtils.isEmpty(byCode)) {
            return null;
        }
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        String sql = "select * from " + TABLE_NAME +
                " where " + REGION_CODE + " like '" + byCode.substring(0, 4) + "%' " +
                "AND " + REGION_CODE + " not like '%00'";
        Cursor cursor = db.rawQuery(sql, null);
        List<RegionBean> countyList = new ArrayList<>();
        RegionBean city;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(REGION_NAME));
            String code = cursor.getString(cursor.getColumnIndex(REGION_CODE));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
            city = new RegionBean(name, code, pinyin);
            countyList.add(city);
        }
        cursor.close();
        db.close();
        return countyList;
    }

    /**
     * 根据二级城市的code查询出它对应的上级code和name信息
     * @param byCode
     * @return
     */
    public List<RegionBean> findByCodeRegionBean(final String byCode) {
        if (TextUtils.isEmpty(byCode)) {
            return null;
        }
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        String sql = "select * from " + TABLE_NAME +
                " where " + REGION_CODE + " like '" + byCode.substring(0, 2) + "%' " +
                "AND " + REGION_CODE + " like '%0000'";
        Cursor cursor = db.rawQuery(sql, null);
        List<RegionBean> countyList = new ArrayList<>();
        RegionBean city;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(REGION_NAME));
            String code = cursor.getString(cursor.getColumnIndex(REGION_CODE));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
            city = new RegionBean(name, code, pinyin);
            countyList.add(city);
        }
        cursor.close();
        db.close();
        return countyList;
    }

    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<RegionBean> {
        @Override
        public int compare(RegionBean lhs, RegionBean rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }
}
