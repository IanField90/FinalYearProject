package uk.ac.reading.dp005570.TeachReach.util;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;

import uk.ac.reading.dp005570.TeachReach.data.Course;
import uk.ac.reading.dp005570.TeachReach.data.Material;
import uk.ac.reading.dp005570.TeachReach.data.Option;
import uk.ac.reading.dp005570.TeachReach.data.Part;
import uk.ac.reading.dp005570.TeachReach.data.Programme;
import uk.ac.reading.dp005570.TeachReach.data.Question;
import uk.ac.reading.dp005570.TeachReach.data.Quiz;
import uk.ac.reading.dp005570.TeachReach.net.ServerCommunicationHelper;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;

/**
 * Controller for populating the database and providing information to
 * views for proper population. Cursor traversal etc.
 * @author Ian Field
 */
public class TeachReachPopulater {
	private TeachReachParser mTeachReachParser;
	private TeachReachDbAdapter mTeachReachDbAdapter;
	private ServerCommunicationHelper mServerCommunicationHelper;
	//Used to determine which string sets to return
	private String mLocale;

	private ArrayList<Course> mCourses;
	private ArrayList<Programme> mProgrammes, mCurrentProgrammes;
	private ArrayList<Part> mParts, mCurrentParts;
	
	private ArrayList<Material> mMaterials;
//	private static Boolean mOpen; // TODO Look into using this to reduce errors?
	private ArrayList<Quiz> mQuizzes;
	private ArrayList<Question> mQuestions;
	private ArrayList<Option> mOptions;

	/**
	 * Instatiates the populater with including setting up the database parser etc.
	 * @param context The context of the application in order to display/dismiss the dialog box
	 */
	public TeachReachPopulater(Context context) {
		mTeachReachParser = new TeachReachParser();
		mTeachReachDbAdapter = new TeachReachDbAdapter(context);
		mTeachReachDbAdapter.open();
		mServerCommunicationHelper = new ServerCommunicationHelper();
		mLocale = Locale.getDefault().getDisplayLanguage();
	}

	/**
	 * Close the database
	 */
	public void closeDB(){
		mTeachReachDbAdapter.close();
	}
	
	/**
	 * Open the database connection - may throw error if already open
	 */
	public void openDB(){
		mTeachReachDbAdapter.open();
	}
	
	/**
	 * Calls  the server to update the main menu system in the entry point activity.
	 * @param dialog The dialog box present to the user, only dismiss is called - this is to give the user Async feedback of progress
	 * @return Whether the server connection was successful.
	 */
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
	 * Calls the server to update the content for a particular part.
	 * @param dialog The dialog box present to the users, only dismiss is called upon completion
	 * @param part_id The Server's ID of the part to retrieve all the content for.
	 * @return Whether or not the server communications were successful
	 */
	public boolean refreshPart(ProgressDialog dialog, int part_id){
		String response = mServerCommunicationHelper.getPartContent(part_id, dialog);
		if(response == null){
			dialog.dismiss();
			return false;
		}
		
		if((response != null) && (response.length() > 0)){
			try{
				mTeachReachParser.parsePartContent(new JSONArray(response), part_id);
				mMaterials = mTeachReachParser.getMaterials();
				mQuizzes = mTeachReachParser.getQuizzes();
				mQuestions = mTeachReachParser.getQuesitons();
				mOptions = mTeachReachParser.getOptions();
				updateMaterials();
				updateQuizzes();
				updateQuestions();
				updateOptions();
			} catch (JSONException e){
				e.printStackTrace();
			}
		}
		dialog.dismiss();
		return true;
	}

	/**
	 * Gets the list of courses to display in the entry point activity for the application
	 * @return List of courses encapsulated - rather than cursor store in memory for faster runtime
	 */
	public ArrayList<Course> getCourseList(){
		//return id with all 3 text fields - unless language can be determined here.
		// add array list here
		ArrayList<Course> courses = new ArrayList<Course>();
		Cursor cursor = mTeachReachDbAdapter.fetchCourseList();
		if(cursor != null){
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
	 * Gets the list of programmes for relevant course. 
	 * For display in the entry point activity for the application
	 * @param course_id The id of the parent course
	 * @return The list of programmes corresponding to the parent course
	 */
	public ArrayList<Programme> getPogrammeList(int course_id){
		ArrayList<Programme> programmes = new ArrayList<Programme>();
		Cursor cursor = mTeachReachDbAdapter.fetchProgrammesList(course_id);
		if(cursor != null && cursor.getCount() != 0){
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
	 * Gets the list of parts for relevant programme. 
	 * For display in the entry point activity for the application
	 * @param programme_id The id of the parent programme
	 * @return The list of parts corresponding to the parent programme
	 */
	public ArrayList<Part> getPartsList(int programme_id){
		ArrayList<Part> parts = new ArrayList<Part>();
		Cursor cursor = mTeachReachDbAdapter.fetchPartsList(programme_id);
		if(cursor != null){
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
	 * Convert course list to array for spinner control
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
	 * Convert list of programmes for spinner control
	 * @param course_id The server ID for the course
	 * @return Each course item for the current application language
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
	 * Convert list of parts for spinner control
	 * @param programme_id The server ID for the programme
	 * @return Each part item for the current application language
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
	
	/**
	 * Get the course list from the database ready to use.
	 */
	public void retrieveCourseList(){
		mCourses = new ArrayList<Course>();
		Cursor cursor = mTeachReachDbAdapter.fetchCourseList();
		if(cursor != null && cursor.getCount() > 0 ){
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				mCourses.add(new Course(id, en, fr, es));
			}while(cursor.moveToNext());
		}
	}
	
	/**
	 * Get the programmes list from the database ready to use.
	 * @param course_id The server's ID of the course to select programmes for.
	 */
	public void retrieveProgrammesList(int course_id){
		mCurrentProgrammes = new ArrayList<Programme>();
		Cursor cursor = mTeachReachDbAdapter.fetchProgrammesList(course_id);
		if(cursor.getCount() > 0 ){
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				mCurrentProgrammes.add(new Programme(id, course_id, en, fr, es));
			}while(cursor.moveToNext());
		}
	}
	
	/**
	 * Get the parts list from the database ready to use.
	 * @param programme_id The server's ID of the programme to select parts for.
	 */
	public void retrievePartsList(int programme_id){
		mCurrentParts = new ArrayList<Part>();
		Cursor cursor = mTeachReachDbAdapter.fetchPartsList(programme_id);
		if(cursor != null && cursor.getCount() > 0 ){//CRASH
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				mCurrentParts.add(new Part(id, programme_id, en, fr, es));
			}while(cursor.moveToNext());
		}
	}
	
	/**
	 * Get the materials from the database ready to use.
	 * @param part_id The server's ID of the part to select materials for.
	 */
	public void retrieveMaterials(int part_id){
		mMaterials = new ArrayList<Material>();
		Cursor cursor = mTeachReachDbAdapter.fetchMaterials(part_id);
		if(cursor != null && cursor.getCount() > 0){
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				mMaterials.add(new Material(id, part_id, en, fr, es));
			}while(cursor.moveToNext());
		}
	}
	
	/**
	 * Get the quizzes list from the database ready to use.
	 * @param part_id The server's ID for the part to select quizzes for.
	 */
	public void retrieveQuizList(int part_id){
		mQuizzes = new ArrayList<Quiz>();
		Cursor cursor = mTeachReachDbAdapter.fetchQuizzes(part_id);

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
	
	/**
	 * Get the questions list from the database ready to use.
	 * @param quiz_id The server's ID for the quiz to select questions for.
	 */
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
	
	/**
	 * Get the options list from the database ready to use.
	 * @param question_id The server's ID for the question to select options for.
	 */
	public void retrieveOptionList(int question_id){
		mOptions = new ArrayList<Option>();
		Cursor cursor = mTeachReachDbAdapter.fetchOptions(question_id);
		if(cursor != null && cursor.getCount() > 0){
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				Boolean answer = Boolean.parseBoolean(cursor.getString(4));//(cursor.getInt(4) > 0); // SQLite does not directly do datatype so this is a fix
				mOptions.add(new Option(id, question_id, en, fr, es, answer));
			}while(cursor.moveToNext());
		}
	}

	/**
	 * Update the database with each Course retrieved from the parser
	 */
	private void updateCourses(){
//		Log.i(TAG, "Number of courses: " + mCourses.size());
		for(Course course : mCourses){
			mTeachReachDbAdapter.createCourse(course.getId(), course.getEN(), course.getFR(), course.getES());
		}
	}

	/**
	 * Update the database with each Programme retrieved from the parser
	 */
	private void updateProgrammes(){
//		Log.i(TAG, "Number of programmes: " + mProgrammes.size());
		for(Programme programme : mProgrammes){
			mTeachReachDbAdapter.createProgramme(programme.getId(), programme.getCourseID(), 
					programme.getEN(), programme.getFR(), programme.getES());
		}

	}

	/**
	 * Update the database with each Part retrieved from the parser
	 */
	private void updateParts(){
//		Log.i(TAG, "Number of parts: " + mParts.size());
		for(Part part : mParts){
			mTeachReachDbAdapter.createPart(part.getId(), part.getProgrammeID(), 
					part.getEN(), part.getFR(), part.getES());
		}
	}
	
	/**
	 * Update the database with each Material retrieved from the parser
	 */
	private void updateMaterials(){
//		Log.i(TAG, "Number of Materials: " + mMaterials.size());
		for(Material material : mMaterials){
			mTeachReachDbAdapter.createMaterial(material.getId(), material.getPartId(),
					material.getEN(), material.getFR(), material.getES());
		}
	}
	
	/**
	 * Update the database with each Quiz retrieved from the parser
	 */
	private void updateQuizzes(){
//		Log.i(TAG, "Number of Quizzes: " + mQuizzes.size());
		for(Quiz quiz : mQuizzes){
			mTeachReachDbAdapter.createQuiz(quiz.getId(), quiz.getPartId(), 
					quiz.getEN(), quiz.getFR(), quiz.getES());
		}
	}
	
	/**
	 * Update the database with each Question retrieved from the parser
	 */
	private void updateQuestions(){
		for(Question question : mQuestions){
			mTeachReachDbAdapter.createQuestion(question.getId(), question.getQuizId(), question.getTypeId(),
					question.getEN(), question.getFR(), question.getES(), 
					question.getFeedbackEN(), question.getFeedbackFR(), question.getFeedbackES());
		}
	}
	
	/**
	 * Update the database with each Option retrieved from the parser
	 */
	private void updateOptions(){
		for(Option option : mOptions){
			mTeachReachDbAdapter.createOption(option.getId(), option.getQuestionId(),
					option.getEN(), option.getFR(), option.getES(), option.isAnswer());
		}
	}
	
	/**
	 * Retrieve the question at a given position from the DB
	 * @param quiz_id Quiz ID of the quiz
	 * @param position Position of the question to get feedback for
	 * @return The question that the feedback will be for
	 */
	public Question getFeedbackForQuestion(int quiz_id, int position){
		Question question = null;
		Cursor cursor = mTeachReachDbAdapter.fetchQuestions(quiz_id);
		if(cursor != null && cursor.getCount() > 0){
			cursor.move(position);// TODO check this is correct
			int id = cursor.getInt(0);
			int type = cursor.getInt(1);
			String en = cursor.getString(2);
			String fr = cursor.getString(3);
			String es = cursor.getString(4);
			String feedback_en = cursor.getString(5);
			String feedback_fr = cursor.getString(6);
			String feedback_es = cursor.getString(7);
			question = new Question(id, quiz_id, type, en, fr, es, feedback_en, feedback_fr, feedback_es);
		}
		return question;
	}

	/**
	 * Get the actual list of Programmes currently selected.
	 * @return The list of currently selected Programmes (belonging to a Course).
	 */
	public ArrayList<Programme> getCurrentProgrammes() {
		return mCurrentProgrammes;
	}
	
	/**
	 * Get the actual list of parts currently selected.
	 * @return The list of currently selected Parts (belonging to a Programme).
	 */
	public ArrayList<Part> getCurrentParts() {
		return mCurrentParts;
	}
	
	/**
	 * Get the actual list of materials for current part.
	 * @return The list of currently selected Materials (belonging to a Part).
	 */
	public ArrayList<Material> getCurrentMaterials(){
		return mMaterials;
	}
	
	/**
	 * Get the actual list of quizzes for current part.
	 * @return The list of currently selected Quizzes (belonging to a Part).
	 */
	public ArrayList<Quiz> getCurrentQuizzes(){
		return mQuizzes;
	}
	
	/**
	 * Get the actual list of questions for current quiz
	 * @return The list of current quiz questions
	 */
	public ArrayList<Question> getCurrentQuestions(){
		return mQuestions;
	}
	
	/**
	 * Get the actual list of options for current question
	 * @return The list of current question options
	 */
	public ArrayList<Option> getCurrentOptions(){
		return mOptions;
	}

}
