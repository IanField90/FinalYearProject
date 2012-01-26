package uk.ac.reading.dp005570.TeachReach;

import java.sql.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author ianfield
 *	Handles updating and creating the database on the device.
 */
public class TeachReachDbAdapter {
	public static final String KEY_ROWID = "_id";
	
	private static final String TAG = "TeachReachDbAdapter";
	
	/**
	 * Response codes
	 */
	private final int OK = 0;
	private final int UPDATE_ERROR = -1;
	private final int INSERT_ERROR = -2;
	
	
	/**
	 * Database creation statements
	 */
	// TODO Update the databases to add 'changed_at' date field to each table
	// creation strings here	
	private static final String TABLE_COURSES = "CREATE TABLE Courses(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"	course_name_en VARCHAR(255) NOT NULL, \n" + 
			"	course_name_fr VARCHAR(255) NOT NULL, \n" + 
			"	course_name_es VARCHAR(255) NOT NULL,\n" + 
//			"	updated_at	DATETIME,\n" +
			"	PRIMARY KEY (_id)\n" + 
			");";
	private static final String TABLE_PROGRAMMES = "CREATE TABLE Programmes(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"	course_id INTEGER NOT NULL,\n" + 
			"	programme_name_en VARCHAR(255) NOT NULL, \n" + 
			"	programme_name_fr VARCHAR(255) NOT NULL, \n" + 
			"	programme_name_es VARCHAR(255) NOT NULL,\n" + 
			"	PRIMARY KEY (_id),\n" + 
			"	FOREIGN KEY (course_id) REFERENCES Courses(_id)\n" + 
			");";
	private static final String TABLE_PARTS ="CREATE TABLE Parts(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"	programme_id INTEGER NOT NULL,\n" + 
			"	part_name_en VARCHAR(255) NOT NULL, \n" + 
			"	part_name_fr VARCHAR(255) NOT NULL, \n" + 
			"	part_name_es VARCHAR(255) NOT NULL,\n" + 
			"	PRIMARY KEY (_id),\n" + 
			"	FOREIGN KEY (programme_id) REFERENCES Programmes(_id)\n" + 
			");";
	private static final String TABLE_QUIZES = "CREATE TABLE Quizes(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"	part_id INTEGER NOT NULL,\n" + 
			"	updated_at DATE,\n" + 
			"	PRIMARY KEY (_id),\n" + 
			"	FOREIGN KEY (part_id) REFERENCES Parts(_id)\n" + 
			");";
	private static final String TABLE_QUESTIONS = "CREATE TABLE Questions(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"	quiz_id INTEGER NOT NULL,\n" + 
			"	question_en VARCHAR(1000) NOT NULL,\n" + 
			"	question_fr VARCHAR(1000) NOT NULL,\n" + 
			"	question_es VARCHAR(1000) NOT NULL,\n" + 
			"	PRIMARY KEY (_id),\n" + 
			"	FOREIGN KEY (quiz_id) REFERENCES Quizes(_id)\n" + 
			");";
	private static final String TABLE_OPTIONS = "CREATE TABLE Options(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"	quiz_id INTEGER NOT NULL,\n" + 
			"	question_id INTEGER NOT NULL,\n" + 
			"	option_en VARCHAR(255) NOT NULL,\n" + 
			"	option_fr VARCHAR(255) NOT NULL,\n" + 
			"	option_es VARCHAR(255) NOT NULL,\n" + 
			"	answer BOOLEAN,\n" + 
			"	PRIMARY KEY (_id),\n" + 
			"	FOREIGN KEY (question_id) REFERENCES Questions(_id)\n" + 
			");";
	private static final String TABLE_FEEDBACKS = "CREATE TABLE Feedbacks(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"	quiz_id INTEGER NOT NULL,	\n" + 
			"	feedback_en VARCHAR(1000) NOT NULL,\n" + 
			"	feedback_fr VARCHAR(1000) NOT NULL,\n" + 
			"	feedback_es VARCHAR(1000) NOT NULL,\n" + 
			"	PRIMARY KEY (_id),\n" + 
			"	FOREIGN KEY (quiz_id) REFERENCES Quizes(_id)\n" + 
			");";
	private static final String DATABASE_NAME = "teachreachdb";
	private static final int DATABASE_VERSION = 4;
	
	private final Context mCtx;
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	/**
	 * @author ianfield
	 *	Handles updates and changes to the database schema
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper{
		
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TABLE_COURSES);
			db.execSQL(TABLE_PROGRAMMES);
			db.execSQL(TABLE_PARTS);
			db.execSQL(TABLE_QUIZES);
			db.execSQL(TABLE_QUESTIONS);
			db.execSQL(TABLE_OPTIONS);
			db.execSQL(TABLE_FEEDBACKS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + "to " + 
					newVersion + ", which will destroy old data" );
			// drop all tables
			db.execSQL("DROP TABLE IF EXISTS Feedbacks;");
			db.execSQL("DROP TABLE IF EXISTS Options;");
			db.execSQL("DROP TABLE IF EXISTS Questions;");
			db.execSQL("DROP TABLE IF EXISTS Quizes;");
			db.execSQL("DROP TABLE IF EXISTS Parts;");
			db.execSQL("DROP TABLE IF EXISTS Programmes;");
			db.execSQL("DROP TABLE IF EXISTS Courses;");
			onCreate(db);
		}
	}
	
	/**
	 * Allows the database to be created.
	 * @param ctx the context to work in.
	 */
	public TeachReachDbAdapter(Context ctx){
		this.mCtx = ctx;
	}
	
	/**
	 * Open database connection.
	 * @return class instance with opened connection.
	 * @throws SQLException
	 */
	public TeachReachDbAdapter open() throws SQLException{
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	/**
	 * close database connection.
	 */
	public void close(){
		mDbHelper.close();
	}
	
	
	/**
	 * Query the database for a list of courses
	 * @return All courses available from the database
	 */
	public Cursor fetchCourseList(){
		Cursor cursor = mDb.query(true, "Programmes", 
				new String[] {"programme_name_en", "programme_name_fr", "programme_name_es" }, 
				null, null, null, null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;

	}
	
	/**
	 * Utilises provides a string array of all courses from the database
	 */
	public void getCoursesList(){
		// TODO Utilise query helper and produce an array of courses but maintain IDs

	}
	
	public void createCourse(int id, String en, String fr, String es, Date date){
		
	}
	
	public void updateCourse(int id, String en, String fr, String es, Date date){
		
	}

	/**
	 * Retrieves the list of programmes for a given course
	 * @param course_id The ID of the course to list programmes for.
	 * @return list of programmes related to course
	 */
	public void fetchProgrammesList(int course_id){
		//TODO Use course_id
		Cursor mCursor = mDb.query(true, "Programmes", 
				new String[] {"programme_name_en", "programme_name_fr", "programme_name_es" }, 
				null, null, null, null, null, null);
		if(mCursor != null){
			mCursor.moveToFirst();
		}
		//TODO traverse cursor and construct array with IDs
//		return mCursor;
	}
	
	/**
	 * Retrieves the list of programmes
	 * @return list of all programmes
	 */
	private Cursor fetchProgrammesList(){
		Cursor mCursor = mDb.query(true, "Programmes", 
				new String[] {"programme_name_en", "programme_name_fr", "programme_name_es" }, 
				null, null, null, null, null, null);
		if(mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public void updateProgramme(int id, String en, String fr, String es, Date date){
		
	}
	
	public void createProgramme(int id, String en, String fr, String es, Date date){
		
	}
	
	/**
	 * Fetch all parts in the database
	 * @return Cursor for all parts
	 */
	public void fetchPartsList(){
		//TODO query logic
	}
	
	/**
	 * Fetch list of parts specific to the programme_id specified
	 * @param programme_id Corresponding programme id to retrieve list of parts for
	 * @return Cursor for selected parts
	 */
	public void fetchPartsList(int programme_id){
		//TODO query logic with programm_id
	}
	
	public void createPart(int id, String en, String fr, String es, Date date){
		
	}
	
	public void updatePart(int id, String en, String fr, String es, Date date){
		
	}
	
	/**
	 * Return the array object for all relative parts
	 */
	public void getPartsList(){
		// TODO Utilise query helper and produce an array of parts but maintain IDs
		
	}
	
	public void getQuizList(){
		
	}
	
	public void getMaterialsList(){
		
	}

	public void getQuiz(){
		
	}
	
}
