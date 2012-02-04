package uk.ac.reading.dp005570.TeachReach.util;


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
	public static final String KEY_ROWID = "server_id";
	
	private static final String TAG = "TeachReachDbAdapter";
	private final String ID = "_id";
	
	private final String SERVER_ID = "server_id";
	
	//Courses fields
	private final String COURSES = "courses";
	private final String COURSE_NAME_EN = "course_name_en";
	private final String COURSE_NAME_FR = "course_name_fr";
	private final String COURSE_NAME_ES = "course_name_es";
	private final String COURSE_ID = "course_id";
	
	//Programmes fields
	private final String PROGRAMMES = "programmes";
	private final String PROGRAMME_NAME_EN = "programme_name_en";
	private final String PROGRAMME_NAME_FR = "programme_name_fr";
	private final String PROGRAMME_NAME_ES = "programme_name_es";
	private final String PROGRAMME_ID = "programme_id";
	
	//Parts fields
	private final String PARTS = "parts";
	private final String PART_NAME_EN = "part_name_en";
	private final String PART_NAME_FR = "part_name_fr";
	private final String PART_NAME_ES = "part_name_es";
	private final String PART_ID = "part_id";
	
	private final String QUIZZES = "quizzes";
	private final String QUIZ_TITLE_EN = "quiz_name_en";
	private final String QUIZ_TITLE_FR = "quiz_name_fr";
	private final String QUIZ_TITLE_ES = "quiz_name_es";
	
	private final String QUESTIONS = "questions";
	private final String QUESTION_EN = "content_en";
	private final String QUESTION_FR = "content_fr";
	private final String QUESTION_ES = "content_es";
	private final String FEEDBACK_EN = "feedback_en";
	private final String FEEDBACK_FR = "feedback_fr";
	private final String FEEDBACK_ES = "feedback_es";
	
	private final String OPTIONS = "options";
	private final String OPTION_EN = "option_en";
	private final String OPTION_FR = "option_fr";
	private final String OPTION_ES = "option_es";
	private final String OPTION_ANSWER = "answer";
	
	private final String MATERIALS = "materials";
	private final String MATERIAL_EN = "material_en";
	private final String MATERIAL_FR = "material_fr";
	private final String MATERIAL_ES = "material_es";
	
	/**
	 * Database creation statements
	 */
	// TODO funky logic to maintain server_id in query
	// creation strings here	
	private static final String TABLE_COURSES = "CREATE TABLE Courses(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			" 	server_id INTEGER, \n" +
			"	course_name_en VARCHAR(255) NOT NULL, \n" + 
			"	course_name_fr VARCHAR(255) NOT NULL, \n" + 
			"	course_name_es VARCHAR(255) NOT NULL,\n" + 
			"	PRIMARY KEY (_id)\n" + 
			");";
	private static final String TABLE_PROGRAMMES = "CREATE TABLE Programmes(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"   server_id INTEGER, \n" +
			"	course_id INTEGER NOT NULL,\n" + 
			"	programme_name_en VARCHAR(255) NOT NULL, \n" + 
			"	programme_name_fr VARCHAR(255) NOT NULL, \n" + 
			"	programme_name_es VARCHAR(255) NOT NULL,\n" + 
			"	PRIMARY KEY (_id)\n" + 
			");";
	private static final String TABLE_PARTS ="CREATE TABLE Parts(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"   server_id INTEGER NOT NULL, \n" +
			"	programme_id INTEGER NOT NULL,\n" + 
			"	part_name_en VARCHAR(255) NOT NULL, \n" + 
			"	part_name_fr VARCHAR(255) NOT NULL, \n" + 
			"	part_name_es VARCHAR(255) NOT NULL,\n" + 
			"	PRIMARY KEY (_id)\n" + 
			");";
	private static final String TABLE_QUIZZES = "CREATE TABLE Quizzes(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"   server_id INTEGER,\n" +
			"	part_id INTEGER NOT NULL,\n" +
			"   name_en VARCHAR(255),\n" +
			"   name_fr VARCHAR(255),\n" +
			"   name_es VARCHAR(255),\n" +
			"	PRIMARY KEY (_id)\n" + 
			");";
	private static final String TABLE_QUESTIONS = "CREATE TABLE Questions(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"	quiz_id INTEGER NOT NULL,\n" + 
			"   server_id INTEGER, \n" +
			"	question_en VARCHAR(1000) NOT NULL,\n" + 
			"	question_fr VARCHAR(1000) NOT NULL,\n" + 
			"	question_es VARCHAR(1000) NOT NULL,\n" + 
			"   feedback_en VARCHAR(1000),\n" +
			"   feedback_fr VARCHAR(1000),\n" +
			"   feedback_es VARCHAR(1000),\n" +
			"	PRIMARY KEY (_id)\n" + 
			");";
	private static final String TABLE_OPTIONS = "CREATE TABLE Options(\n" + 
			"	_id INTEGER NOT NULL,\n" + 
			"   server_id INTEGER, \n" +
			"	quiz_id INTEGER NOT NULL,\n" + 
			"	question_id INTEGER NOT NULL,\n" + 
			"	option_en VARCHAR(255) NOT NULL,\n" + 
			"	option_fr VARCHAR(255) NOT NULL,\n" + 
			"	option_es VARCHAR(255) NOT NULL,\n" + 
			"	answer BOOLEAN,\n" + 
			"	PRIMARY KEY (_id)\n" + 
			");";
	private static final String DATABASE_NAME = "teachreachdb";
	private static final int DATABASE_VERSION = 7;
	
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
			db.execSQL(TABLE_QUIZZES);
			db.execSQL(TABLE_QUESTIONS);
			db.execSQL(TABLE_OPTIONS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + "to " + 
					newVersion + ", which will destroy old data" );
			// drop all tables
			db.execSQL("DROP TABLE IF EXISTS Options;");
			db.execSQL("DROP TABLE IF EXISTS Questions;");
			db.execSQL("DROP TABLE IF EXISTS Quizzes;");
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
		Cursor cursor = mDb.query(true, COURSES, 
				new String[] {COURSE_NAME_EN, COURSE_NAME_FR, COURSE_NAME_ES }, 
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
	
	/**
	 * Creates or updates the database entry for a course
	 * @param id Server's table ID
	 * @param en English course title
	 * @param fr French course title
	 * @param es Spanish course title
	 */
	public void createCourse(int id, String en, String fr, String es){
		Cursor cursor = mDb.rawQuery("SELECT * FROM courses WHERE server_id=?", new String[] { ""+id });
		String statement;
		if(cursor.getCount() == 0){
			//insert
			statement = "INSERT INTO " + COURSES + " VALUES( null, " + id + 
					", '" + en + "', '" + fr + "', '" + es + "')";
		}else{
			//update
			statement = "UPDATE " + COURSES + " SET " + COURSE_NAME_EN + "='" + en + "', " +
						COURSE_NAME_FR + "='" + fr + "', " + COURSE_NAME_ES + "='" + es + "'" +
						" WHERE " + SERVER_ID +"=" + id;
		}
		Log.i(TAG, "Statement: " + statement);
		mDb.execSQL(statement);
	}

	/**
	 * Retrieves the list of programmes for a given course
	 * @param course_id The ID of the course to list programmes for.
	 * @return list of programmes related to course
	 */
	public Cursor fetchProgrammesList(int course_id){
		// Use course_id
		Cursor cursor = mDb.query(true, PROGRAMMES, 
				new String[] { PROGRAMME_NAME_EN, PROGRAMME_NAME_FR, PROGRAMME_NAME_ES }, 
				null, null, null, SERVER_ID + "=" + course_id, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	/**
	 * Creates or updates the database entry for a programme
	 * @param id Server's table ID
	 * @param en English programme title
	 * @param fr French programme title
	 * @param es Spanish programme title
	 */
	public void createProgramme(int id, int course_id, String en, String fr, String es){
		Cursor cursor = mDb.rawQuery("SELECT * FROM programmes WHERE server_id=?", new String[] { ""+id });
		String statement;
		if(cursor.getCount() == 0){
			//insert
			statement = "INSERT INTO " + PROGRAMMES + " VALUES( null, " + id + ", " + course_id + 
					", '" + en + "', '" + fr + "', '" + es + "')";
		}else{
			//update
			statement = "UPDATE " + PROGRAMMES + " SET " + PROGRAMME_NAME_EN + "='" + en + "', " +
						PROGRAMME_NAME_FR + "='" + fr + "', " + PROGRAMME_NAME_ES + "='" + es + "'" +
						" WHERE " + SERVER_ID +"=" + id;
		}
		Log.i(TAG, "Statement: " + statement);
		mDb.execSQL(statement);
	}
	
	/**
	 * Fetch list of parts specific to the programme_id specified
	 * @param programme_id Corresponding programme id to retrieve list of parts for
	 * @return Cursor for selected parts
	 */
	public Cursor fetchPartsList(int programme_id){
		// query logic with programme_id
		// Use programme_id
		Cursor cursor = mDb.query(true, PARTS, 
				new String[] {PART_NAME_EN, PART_NAME_FR, PART_NAME_ES }, 
				null, null, null, PROGRAMME_ID + "=" + programme_id, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	/**
	 * Creates or updates the database entry for a part
	 * @param id Server's table ID
	 * @param en English part title
	 * @param fr French part title
	 * @param es Spanish part title
	 */
	public void createPart(int id, int programme_id, String en, String fr, String es){
		Cursor cursor = mDb.rawQuery("SELECT * FROM parts WHERE server_id=?", new String[] { ""+id });
		String statement;
		if(cursor.getCount() == 0){
			//insert
			statement = "INSERT INTO " + PARTS + " VALUES( null, " + id + ", " + programme_id +
					", '" + en + "', '" + fr + "', '" + es + "')";
		}else{
			//update
			statement = "UPDATE " + PARTS + " SET " + PART_NAME_EN + "='" + en + "', " +
						PART_NAME_FR + "='" + fr + "', " + PART_NAME_ES + "='" + es + "'" +
						" WHERE " + SERVER_ID +"=" + id;
		}
		Log.i(TAG, "Statement: " + statement);
		mDb.execSQL(statement);
	}

	
	/**
	 * Return the array object for all relative parts
	 */
	public void getPartsList(){
		// TODO Utilise query helper and produce an array of parts but maintain IDs
		
	}
	
	public Cursor getQuizList(int part_id){
		Cursor cursor = mDb.query(true, QUIZZES,
				new String[] { ID, QUIZ_TITLE_EN, QUIZ_TITLE_FR, QUIZ_TITLE_ES},
				null, null, null, PART_ID + "=" + part_id, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public void getMaterialsList(){
		
	}

	public void getQuiz(){
		
	}
	
}
