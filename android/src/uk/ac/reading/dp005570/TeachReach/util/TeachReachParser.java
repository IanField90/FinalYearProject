package uk.ac.reading.dp005570.TeachReach.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.ac.reading.dp005570.TeachReach.data.Course;
import uk.ac.reading.dp005570.TeachReach.data.Material;
import uk.ac.reading.dp005570.TeachReach.data.Part;
import uk.ac.reading.dp005570.TeachReach.data.Programme;
import android.util.Log;

/**
 * 
 * @author ianfield
 * Handles the parsing of the data retrieved from the server.
 */
public class TeachReachParser {
	private final String TAG = "PARSER";
	private final String ID = "id";
	
	//Courses JSON response fields
	private final String COURSE_NAME_EN = "course_name_en";
	private final String COURSE_NAME_FR = "course_name_fr";
	private final String COURSE_NAME_ES = "course_name_es";
	
	//Programmes JSON response fields
	private final String PROGRAMMES = "programmes";
	private final String PROGRAMME_NAME_EN = "programme_name_en";
	private final String PROGRAMME_NAME_FR = "programme_name_fr";
	private final String PROGRAMME_NAME_ES = "programme_name_es";
	
	//Parts JSON response fields
	private final String PARTS = "parts";
	private final String PART_NAME_EN = "part_name_en";
	private final String PART_NAME_FR = "part_name_fr";
	private final String PART_NAME_ES = "part_name_es";
	
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
	
	// 1 getter for each of these - non-sent ids can still be populated.
	private ArrayList<Course> mCourses = new ArrayList<Course>();
	private ArrayList<Programme> mProgrammes = new ArrayList<Programme>();
	private ArrayList<Part> mParts = new ArrayList<Part>();

	private ArrayList<Material> mMaterials = new ArrayList<Material>();
	
	
	/**
	 * Extracts information for a Course list and updates/creates entry in
	 * database
	 * @param list The JSON array of courses returned by the server
	 */
	public ArrayList<Course> parseCourses(JSONArray list){
		JSONObject course;
		int id;
		String en, fr, es;
		ArrayList<Course> courses = new ArrayList<Course>();
		for (int i = 0; i < list.length(); i++){
			try {
				course = list.getJSONObject(i);
				id = (Integer) course.get(ID);				
				en = course.getString(COURSE_NAME_EN);
				fr = course.getString(COURSE_NAME_FR);
				es = course.getString(COURSE_NAME_ES);
				
				Log.i(TAG, course.toString());
				Log.i(TAG, "ID: "+id);
				Log.i(TAG, "EN: " + en);
				Log.i(TAG, "FR: " + fr);
				Log.i(TAG, "ES: " + es);	
				//Add to courses list to retain information in memory
				mCourses.add(new Course(id, en, fr, es));
				
				//Set up next parsing operation and call it.
				JSONArray programmes = course.getJSONArray(PROGRAMMES);
				parseProgrammes(programmes, id);
				
			} catch (JSONException e) {
				// Shouldn't happen unless empty response
				e.printStackTrace();
			}
		}
		return (courses.size() > 0) ? courses : null;
	}
	
	
	/**
	 * Extracts information for a programme's JSON and updates/creates entry in
	 * database.
	 * @param list The list of programmes to parse
	 * @param course The ID of the parent course.
	 */
	private void parseProgrammes(JSONArray list, int course){
		JSONObject programme;
		int id, course_id = course;
		String en, fr, es;
		for (int i = 0; i < list.length(); i++){
			try {
				programme = list.getJSONObject(i);
				id = (Integer) programme.get(ID);				
				en = programme.getString(PROGRAMME_NAME_EN);
				fr = programme.getString(PROGRAMME_NAME_FR);
				es = programme.getString(PROGRAMME_NAME_ES);
				Log.i(TAG, programme.toString());
				Log.i(TAG, "ID: "+id);
				Log.i(TAG, "EN: " + en);
				Log.i(TAG, "FR: " + fr);
				Log.i(TAG, "ES: " + es);	
				Log.i(TAG, "Course ID: " + course_id);
				//Add to programmes list to retain information in memory
				mProgrammes.add(new Programme(id, course_id, en, fr, es));
				
				//Set up next parsing operation and call it
				JSONArray parts = programme.getJSONArray(PARTS);
				parseParts(parts, id);
				
			} catch (JSONException e) {
				// Shouldn't happen unless empty response
				e.printStackTrace();
			}
		}
	}

	/**
	 * Extracts the information from a list of parts and updates/creates entry in
	 * database
	 * @param list A list of Parts to parse
	 * @param programme The ID of the Part's parent Programme
	 */
	private void parseParts(JSONArray list, int programme){
		JSONObject part;
		int id, programme_id = programme;
		String en, fr, es;
		for (int i = 0; i < list.length(); i++){
			try {
				part = list.getJSONObject(i);
				id = (Integer) part.get(ID);				
				en = part.getString(PART_NAME_EN);
				fr = part.getString(PART_NAME_FR);
				es = part.getString(PART_NAME_ES);
				Log.i(TAG, part.toString());
				Log.i(TAG, "ID: "+id);
				Log.i(TAG, "EN: " + en);
				Log.i(TAG, "FR: " + fr);
				Log.i(TAG, "ES: " + es);
				Log.i(TAG, "Prog ID: " + programme_id);	
				//Add to parts list to retain information in memory
				mParts.add(new Part(id, programme_id, en, fr, es));
				
			} catch (JSONException e) {
				// Shouldn't happen unless empty response
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Actually get all the information from a part. That is to say that the
	 * full quiz(questions -> options -> feedback), material should be parsed and
	 * added locally to the database
	 * @param part The array containing all information for a particular part.
	 */
	public void parsePartContent(JSONArray part){
		//TODO triple layered parse.
	}
	
	private void parseMaterials(JSONArray materials, int part_id){
		
	}
	
	private void parseQuizzes(JSONArray quizzes){
		
	}
	
	private void parseQuestion(JSONArray question){
		
	}
	
	private void parseOptions(JSONArray options){
		
	}


	public ArrayList<Course> getCourses() {
		return mCourses;
	}


	public void setCourses(ArrayList<Course> mCourses) {
		this.mCourses = mCourses;
	}


	public ArrayList<Programme> getProgrammes() {
		return mProgrammes;
	}


	public void setProgrammes(ArrayList<Programme> mProgrammes) {
		this.mProgrammes = mProgrammes;
	}


	public ArrayList<Part> getParts() {
		return mParts;
	}


	public void setParts(ArrayList<Part> mParts) {
		this.mParts = mParts;
	}
}
