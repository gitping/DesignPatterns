package com.yto.suixingouuser.util.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserProfileDatabaseHelper extends SQLiteOpenHelper {

	private static final int DB_VERSION = 1;

	private static final String DB_NAME = "SuixingouUser";

	/**
	 * Database table and column names This model is bad and with redundancy, 3
	 * table should be used for this purpose
	 */
	private static final String TABLE_UP_NAME = "USER_PROFILE";

	private static final String ID_COL_NAME = "ID";
	private static final String parentRegionId = "parentRegionId";
	private static final String regionName = "regionName";
	private static final String regionCode = "regionCode";
	

	private static final String PROFILE_NAME_COL_NAME = "PROFILE_NAME";

	private static final String PARAM_NAME_COL_NAME = "PARAM_NAME";

	public UserProfileDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableSQL = "CREATE TABLE " + TABLE_UP_NAME + "(" + ID_COL_NAME + " INTEGER PRIMARY KEY," + parentRegionId + " INTEGER PRIMARY KEY," + regionName + " TEXT," + regionCode + " TEXT" + ")";
		db.execSQL(createTableSQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_UP_NAME);

		// Create tables again
		onCreate(db);
	}
	
	public void  insertSQL(String sql){
		SQLiteDatabase db = this.getWritableDatabase();

	}
	
	
}
