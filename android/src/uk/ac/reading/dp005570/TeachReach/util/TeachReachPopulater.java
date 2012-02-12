package uk.ac.reading.dp005570.TeachReach.util;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;

import uk.ac.reading.dp005570.TeachReach.data.Course;
import uk.ac.reading.dp005570.TeachReach.data.Option;
import uk.ac.reading.dp005570.TeachReach.data.Part;
import uk.ac.reading.dp005570.TeachReach.data.Programme;
import uk.ac.reading.dp005570.TeachReach.data.Question;
import uk.ac.reading.dp005570.TeachReach.data.Quiz;
import uk.ac.reading.dp005570.TeachReach.net.ServerCommunicationHelper;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Controller for populating the database and providing information to
 * views for proper population. Cursor traversal etc.
 * @author Ian Field
 *
 */
public class TeachReachPopulater {
	private final String TAG = "POPULATER";
	private TeachReachParser mTeachReachParser;
	private TeachReachDbAdapter mTeachReachDbAdapter;
	private ServerCommunicationHelper mServerCommunicationHelper;
	//Used to determine which string sets to return
	private String mLocale;

	private ArrayList<Course> mCourses;
	private ArrayList<Programme> mProgrammes, mCurrentProgrammes;
	private ArrayList<Part> mParts, mCurrentParts;

	private ArrayList<Quiz> mQuizzes;
	private ArrayList<Question> mQuestions;
	private ArrayList<Option> mOptions;

	public TeachReachPopulater(Context context, int slelectedCourseId, int selectedProgrammeId, int selectedPartId) {
		mTeachReachParser = new TeachReachParser();
		mTeachReachDbAdapter = new TeachReachDbAdapter(context);
		mTeachReachDbAdapter.open();
		mServerCommunicationHelper = new ServerCommunicationHelper();
		mLocale = Locale.getDefault().getDisplayLanguage();
	}

	public void closeDB(){
		mTeachReachDbAdapter.close();
	}

	public boolean refreshMainMenu(ProgressDialog dialog){
		String response = mServerCommunicationHelper.getCourseList();
		if(response == null){
			dialog.dismiss();
			return false;
		}
		//Null check first. If it is then no response from server
		if((response != null) && (response.length() > 0)){
			try {
				mTeachReachParser.parseCourses(new JSONArray(response));
				mCourses = mTeachReachParser.getCourses();
				mProgrammes = mTeachReachParser.getProgrammes();
				mParts = mTeachReachParser.getParts();
				updateCourses();
				updateProgrammes();
				updateParts();
				dialog.dismiss();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return true;

	}

	/**
	 * 
	 * @return List of courses encapsulated - rather than cursor store in memory for faster runtime
	 */
	public ArrayList<Course> getCourseList(){
		//return id with all 3 text fields - unless language can be determined here.
		// add array list here
		ArrayList<Course> courses = new ArrayList<Course>();
		Cursor cursor = mTeachReachDbAdapter.fetchCourseList();
		if(cursor == null){
			Log.i(TAG, "Courses cursor empty.");
		}
		else{
			//Can traverse through content
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);

				Course course = new Course(id, en, fr, es);
				courses.add(course);
			}while(cursor.moveToNext());

		}
		return (courses.size() > 0) ? courses : null;
	}

	/**
	 * 
	 * @param course_id The id of the parent course
	 * @return The list of programmes corresponding to the parent course
	 */
	public ArrayList<Programme> getPogrammeList(int course_id){
		ArrayList<Programme> programmes = new ArrayList<Programme>();
		Cursor cursor = mTeachReachDbAdapter.fetchProgrammesList(course_id);
		if (cursor == null){
			Log.i(TAG, "Programmes cursor empty.");
		}
		else if(cursor.getCount() == 0){
			Log.i(TAG, "Programme count is zero.");
		}
		else{
			//Can traverse through content
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);

				Programme programme = new Programme(id, course_id, en, fr, es);
				programmes.add(programme);
			}while(cursor.moveToNext());
		}

		return (programmes.size() > 0) ? programmes : null;
	}

	/**
	 * 
	 * @param programme_id The id of the parent programme
	 * @return The list of parts corresponding to the parent programme
	 */
	public ArrayList<Part> getPartsList(int programme_id){
		ArrayList<Part> parts = new ArrayList<Part>();
		Cursor cursor = mTeachReachDbAdapter.fetchPartsList(programme_id);
		if (cursor == null){
			Log.i(TAG, "Parts cursor empty.");
		}
		else{
			//Can traverse through content
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				Part part = new Part(id, programme_id, en, fr, es);
				parts.add(part);
			}while(cursor.moveToNext());
		}
		return (parts.size() > 0) ? parts : null;

	}

	/**
	 * 
	 * @return List of courses in given language
	 */
	public String[] getCourses(){
		retrieveCourseList();
		String[] courses;
		if(mCourses.size() > 0){
			courses = new String[mCourses.size()];
			int i = 0;
			for( Course course : mCourses ){
				if(mLocale.equals("Français")){
					courses[i] = course.getFR();
				}
				else if(mLocale.equals("español")){
					courses[i] = course.getES();
				}
				else{
					//Default to english
					courses[i] = course.getEN();
				}
				i++;
			}
		}
		else{
			courses = new String[]{};
		}
		return courses;
	}

	/**
	 * 
	 * @param language
	 * @param course_id
	 * @return
	 */
	public String[] getProgrammes(int course_id){
		retrieveProgrammesList(course_id);
		String[] programmes;
		if(mCurrentProgrammes.size() > 0){
			programmes = new String[mCurrentProgrammes.size()];
			int i = 0;
			for(Programme programme : mCurrentProgrammes){
				if(mLocale.equals("Français")){
					programmes[i] = programme.getFR();
				}
				else if(mLocale.equals("español")){
					programmes[i] = programme.getES();
				}
				else{
					//Default to english
					programmes[i] = programme.getEN();
				}
				i++;
			}
		}
		else{
			// Make sure UI building doesn't crash trying to populate with null
			programmes = new String[]{};
		}
		return programmes;
	}

	/**
	 * 
	 * @param programme_id
	 * @return
	 */
	public String[] getParts(int programme_id){
		// check locale language strings
		retrievePartsList(programme_id);
		String[] parts;
		if(mCurrentParts.size() > 0){
			parts = new String[mCurrentParts.size()];
			int i = 0;
			for(Part part : mCurrentParts){
				if(mLocale.equals("Français")){
					parts[i] = part.getFR();
				}
				else if(mLocale.equals("español")){
					parts[i] = part.getES();
				}
				else{
					//Default to english
					parts[i] = part.getEN();
				}
				i++;
			}
		}
		else{
			parts = new String[]{};
		}
		return parts;
	}

	/* DATABASE FUNCTIONS - traverse through cursor*/

	public void retrieveCourseList(){
		mCourses = new ArrayList<Course>();
		Cursor cursor = mTeachReachDbAdapter.fetchCourseList();
		if(cursor != null){
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				mCourses.add(new Course(id, en, fr, es));
			}while(cursor.moveToNext());
		}
	}

	public void retrieveProgrammesList(int course_id){
		mCurrentProgrammes = new ArrayList<Programme>();
		Cursor cursor = mTeachReachDbAdapter.fetchProgrammesList(course_id);
		if(cursor.getCount() > 0 ){//!= null){//CRASH
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				mCurrentProgrammes.add(new Programme(id, course_id, en, fr, es));
			}while(cursor.moveToNext());
		}
	}

	public void retrievePartsList(int programme_id){
		mCurrentParts = new ArrayList<Part>();
		Cursor cursor = mTeachReachDbAdapter.fetchPartsList(programme_id);
		if(cursor.getCount() > 0 ){//!= null){//CRASH
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				mCurrentParts.add(new Part(id, programme_id, en, fr, es));
			}while(cursor.moveToNext());
		}
	}

	public void retrieveQuizList(int part_id){
		mQuizzes = new ArrayList<Quiz>();
		Cursor cursor = mTeachReachDbAdapter.fetchQuiz(part_id);

		if(cursor != null && cursor.getCount() > 0){
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				mQuizzes.add(new Quiz(id, part_id, en, fr, es));
			}while(cursor.moveToNext());
		}
	}
	
	public void retrieveQuestionList(int quiz_id){
		mQuestions = new ArrayList<Question>();
		Cursor cursor = mTeachReachDbAdapter.fetchQuestions(quiz_id);
		if(cursor != null && cursor.getCount() > 0){
			do{
				int id = cursor.getInt(0);
				int type = cursor.getInt(1);
				String en = cursor.getString(2);
				String fr = cursor.getString(3);
				String es = cursor.getString(4);
				String feedback_en = cursor.getString(5);
				String feedback_fr = cursor.getString(6);
				String feedback_es = cursor.getString(7);
				mQuestions.add(new Question(id, quiz_id, type, en, fr, es, feedback_en, feedback_fr, feedback_es));
			}while(cursor.moveToNext());
		}
	}
	
	public void retrieveOptionList(int question_id){
		mOptions = new ArrayList<Option>();
		Cursor cursor = mTeachReachDbAdapter.fetchOptions(question_id);
		if(cursor != null && cursor.getCount() > 0){
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				Boolean answer = (cursor.getInt(4) > 0); // SQLite does not directly do datatype so this is a fix
				mOptions.add(new Option(id, question_id, en, fr, es, answer));
			}while(cursor.moveToNext());
		}
	}

	/**
	 * Update the database with each Course retrieved from the parser
	 */
	private void updateCourses(){
		Log.i(TAG, "Number of courses: " + mCourses.size());
		for(Course course : mCourses){
			mTeachReachDbAdapter.createCourse(course.getId(), course.getEN(), course.getFR(), course.getES());
		}
	}

	/**
	 * Update the database with each Programme retrieved from the parser
	 */
	private void updateProgrammes(){
		Log.i(TAG, "Number of programmes: " + mProgrammes.size());
		for(Programme programme : mProgrammes){
			mTeachReachDbAdapter.createProgramme(programme.getId(), programme.getCourseID(), 
					programme.getEN(), programme.getFR(), programme.getES());
		}

	}

	/**
	 * Update the database with each Part retrieved from the parser
	 */
	private void updateParts(){
		Log.i(TAG, "Number of parts: " + mParts.size());
		for(Part part : mParts){
			mTeachReachDbAdapter.createPart(part.getId(), part.getProgrammeID(), 
					part.getEN(), part.getFR(), part.getES());
		}
	}

	public ArrayList<Programme> getCurrentProgrammes() {
		return mCurrentProgrammes;
	}

	public ArrayList<Part> getCurrentParts() {
		return mCurrentParts;
	}

	public void setCurrentParts(ArrayList<Part> mCurrentParts) {
		this.mCurrentParts = mCurrentParts;
	}

	public void setCurrentProgrammes(ArrayList<Programme> mCurrentProgrammes) {
		this.mCurrentProgrammes = mCurrentProgrammes;
	}
}
