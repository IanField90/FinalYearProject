package uk.ac.reading.dp005570.TeachReach;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TeachReachDbAdapter {
	public static final String KEY_ROWID = "_id";
	
	private static final String TAG = "TeachReachDbAdapter";
	
	/**
	 * Database creation statement
	 */
	//TODO Add creation string here
	private static final String DATABASE_CREATE = "";
	
	private static final String DATABASE_NAME = "teachreachdb";
	private static final int DATABASE_VERSION = 1;
	
	private final Context mCtx;
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + "to " + 
					newVersion + ", which will destroy old data" );
			//TODO drop all tables
			db.execSQL("DROP TABLE IF EXISTS courses");
			onCreate(db);
		}
	}
	
	/**
	 * Allows the database to be created
	 * @param ctx the context to work in
	 */
	public TeachReachDbAdapter(Context ctx){
		this.mCtx = ctx;
	}
	
	public TeachReachDbAdapter open() throws SQLException{
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		mDbHelper.close();
	}
}
