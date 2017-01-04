package com.yto.suixingouuser.util.android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yto.suixingoustore.FrameApplication;
import com.yto.zhang.util.modle.PriceSearchResjo;

/**
 * 
 * @author Hank
 * @create_date 2014-4-23
 * @comment:外部数据数据库工具
 * 
 */
public class SuixingouDatabaseHelper extends SQLiteOpenHelper {

	private final static byte[] _DB_LOCK = new byte[0];

	// 数据库名
	private final static String DB_NAME = "SUIXINGOU.db";
	// 数据库版本
	private final static int DB_VERSION = 1;

	private static SuixingouDatabaseHelper instance = null;

	private static String CREATE_REGION = "CREATE TABLE IF NOT EXISTS region ("
			+ "regionId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "parentRegionId INTEGER DEFAULT NULL,"
			+ "regionName VARCHAR DEFAULT NULL,"
			+ "regionCode VARCHAR DEFAULT NULL," + "rank INTEGER DEFAULT NULL)";

	private static String CREATE_EXPRESSFEE = "CREATE TABLE IF NOT EXISTS expressfee ("
			+ " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "createTime VARCHAR NOT NULL DEFAULT '0000-00-00 00:00:00',"
			+ "updateTime DATE NOT NULL DEFAULT 'now',"
			+ "sourceCode VARCHAR NOT NULL,"
			+ "destCode VARCHAR NOT NULL,"
			+ "firstWeight DOUBLE NOT NULL," + "secondeWeight DOUBLE NOT NULL)";

	public static SuixingouDatabaseHelper getInstance() {
		if (instance == null) {
			instance = new SuixingouDatabaseHelper(FrameApplication.context);
		}
		return instance;
	}

	private SuixingouDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		dropTables(db);
		createTables(db);
	}

	private void createTables(SQLiteDatabase db) {
		db.execSQL(CREATE_REGION);
		db.execSQL(CREATE_EXPRESSFEE);
	}

	private void dropTables(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS region");
		db.execSQL("DROP TABLE IF EXISTS expressfee");
	}

	public void insertAllData(final String fileName) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				prepareData(fileName);
			}
		}).start();
	}

	int totalNums = -1;
	int currentNum = 0;

	// 写入数据库脚本
	public void prepareData(String fileName) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = null;
		int count = 1;
		try {
			InputStream in = FrameApplication.context.getAssets()
					.open(fileName);
			if (in != null) {
				// 计算脚本行数
				Scanner scanner = new Scanner(in);
				while (scanner.hasNextLine()) {
					scanner.nextLine();
					count++;
				}
				scanner.close();
				scanner = null;
				in.close();
				in = null;
				if (count > 0) {
					// 显示写入进度
					// Trace.i("SuixingouDB", "total nums is:" + count);
					totalNums = count;
					InputStream input = FrameApplication.context.getAssets()
							.open(fileName);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(input));
					int i = 0;
					db.beginTransaction();
					// Trace.i("SuixingouDB","insert data begin :" +
					// MyDate.getDateEN());
					while ((sql = reader.readLine()) != null) {
						i++;
						currentNum = i;
						if (sql != null && sql.length() > 0) {
							db.execSQL(sql);
						}
						// Trace.i("SuixingouDB", "insert data progess "+
						// currentNum);
					}
					db.setTransactionSuccessful();
					db.endTransaction();
					// Trace.i("SuixingouDB","insert data end :" +
					// MyDate.getDateEN());
					reader.close();
					input.close();
					input = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			db.endTransaction();
		}
	}

	/**
	 * 获取所有目标地址的运费价格，以列表形式返回
	 * 
	 * @param myAdress
	 * @return 目标城市运费价格列表
	 */
	public List<PriceSearchResjo> getDestPriceList(String myAddress) {
		List<PriceSearchResjo> result = new ArrayList<PriceSearchResjo>();
		if (myAddress != null) {
			String regionCode = getRegionCodeByCityName(myAddress);
			if (regionCode != null && regionCode.length() > 0) {
				result = getPriceListBySourceCode(regionCode);
			}
		}
		return result;
	}

	/**
	 * 获取地区列表
	 * 
	 * @return
	 */
	public List<String> getProvinceList() {
		List<String> result = new ArrayList<String>();
		Cursor cur = this.getReadableDatabase().query("region", null,
				"parentRegionId=? OR parentRegionId=?",
				new String[] { "0", "-1" }, null, null, "regionId ASC");
		if (cur != null) {
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				String name = cur.getString(2);
				result.add(name);
			}
			cur.close();
		}
		return result;
	}

	/**
	 * 获取城市regionCode
	 * 
	 * @param myAddress
	 *            城市名
	 * @return regionCode
	 */
	public String getRegionCodeByCityName(String myAddress) {
		String result = "";
		if (myAddress != null) {
			Cursor cur = this.getReadableDatabase().query("region", null,
					"regionName=?", new String[] { myAddress }, null, null,
					null);
			if (cur != null && cur.moveToFirst()) {
				result = cur.getString(3);
				cur.close();
			}
		}
		return result;
	}

	/**
	 * 根据RegionCode获取城市名称
	 * 
	 * @param regionCode
	 * @return
	 */
	public String getCityNameByRegionCode(String regionCode) {
		String result = "";
		if (regionCode != null) {
			Cursor cur = this.getReadableDatabase().query("region", null,
					"regionCode=?", new String[] { regionCode }, null, null,
					null);
			if (cur != null && cur.moveToFirst()) {
				result = cur.getString(2);
				cur.close();
			}
		}
		return result;
	}

	/**
	 * 根据regionCode获取运费列表
	 * 
	 * @param regionCode
	 * @return 运费列表
	 */
	public List<PriceSearchResjo> getPriceListBySourceCode(String regionCode) {
		List<PriceSearchResjo> result = new ArrayList<PriceSearchResjo>();
		if (regionCode != null) {
			String myAddress = getCityNameByRegionCode(regionCode);
			Cursor cur = this.getReadableDatabase().query("expressfee", null,
					"sourceCode=?", new String[] { regionCode }, null, null,
					"id ASC");
			if (cur != null) {
				for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
					PriceSearchResjo jo = new PriceSearchResjo();
					jo.setOrderAdress(getCityNameByRegionCode(cur.getString(4)));
					jo.setFirstPrice(cur.getDouble(5));
					jo.setAddPrice(cur.getDouble(6));
					jo.setMyAddress(myAddress);
					result.add(jo);
				}
				cur.close();
			}
		}
		return result;
	}

	/**
	 * 程序退出时调用
	 */
	public void onAppClosed() {
		this.getReadableDatabase().close();
		this.getWritableDatabase().close();
	}

}
