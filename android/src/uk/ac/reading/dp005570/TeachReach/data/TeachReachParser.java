package uk.ac.reading.dp005570.TeachReach.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class TeachReachParser {
	private final String TAG = "PARSER";
	private final String ID = "id";
	private final String UPDATED_AT = "updated_at";
	
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
	
	/**
	 * Extracts information for a Course list and updates/creates entry in
	 * database
	 * @param list The JSON array of courses returned by the server
	 */
	public void parseCourses(JSONArray list){
		JSONObject course;
		int id;
		String en, fr, es, updated_at;
		for (int i = 0; i < list.length()-1; i++){
			try {
				course = list.getJSONObject(i);
				id = (Integer) course.get(ID);				
				updated_at = course.get(UPDATED_AT).toString();
				en = course.getString(COURSE_NAME_EN);
				fr = course.getString(COURSE_NAME_FR);
				es = course.getString(COURSE_NAME_ES);
				
				Log.i(TAG, course.toString());
				Log.i(TAG, "ID: "+id);
				Log.i(TAG, "Date: " + updated_at.toString()); //NOTE: Maybe irrelevant update if exists else insert
				Log.i(TAG, "EN: " + en);
				Log.i(TAG, "FR: " + fr);
				Log.i(TAG, "ES: " + es);	
				
				//TODO Call DB helper function to insert course or update if exists
				//createCourse(id, en, fr, es, updated_at);
				
				
				JSONArray programmes = course.getJSONArray(PROGRAMMES);
				parseProgramme(programmes, id);
				
			} catch (JSONException e) {
				// Shouldn't happen unless empty response
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Extracts information for a programme's JSON and updates/creates entry in
	 * database.
	 * @param list The list of programmes to parse
	 * @param course The ID of the parent course.
	 */
	private void parseProgramme(JSONArray list, int course){
		JSONObject programme;
		int id, course_id = course;
		String en, fr, es, updated_at;
		for (int i = 0; i < list.length()-1; i++){
			try {
				programme = list.getJSONObject(i);
				id = (Integer) programme.get(ID);				
				updated_at = programme.get(UPDATED_AT).toString();
				en = programme.getString(PROGRAMME_NAME_EN);
				fr = programme.getString(PROGRAMME_NAME_FR);
				es = programme.getString(PROGRAMME_NAME_ES);
				Log.i(TAG, programme.toString());
				Log.i(TAG, "ID: "+id);
				Log.i(TAG, "Date: " + updated_at.toString());
				Log.i(TAG, "EN: " + en);
				Log.i(TAG, "FR: " + fr);
				Log.i(TAG, "ES: " + es);	
				Log.i(TAG, "Course ID: " + course_id);
				
				//TODO Call DB helper function to insert programme or update if exists
//				TeachReachDbAdapter.createProgramme(id, course_id, en, fr, es, updated_at);
				
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
		String en, fr, es, updated_at;
		for (int i = 0; i < list.length()-1; i++){
			try {
				part = list.getJSONObject(i);
				id = (Integer) part.get(ID);				
				updated_at = part.get(UPDATED_AT).toString();
				en = part.getString(PART_NAME_EN);
				fr = part.getString(PART_NAME_FR);
				es = part.getString(PART_NAME_ES);
				Log.i(TAG, part.toString());
				Log.i(TAG, "ID: "+id);
				Log.i(TAG, "Date: " + updated_at.toString());
				Log.i(TAG, "EN: " + en);
				Log.i(TAG, "FR: " + fr);
				Log.i(TAG, "ES: " + es);
				Log.i(TAG, "Prog ID: " + programme_id);
				
				//TODO Call DB helper function to insert part or update if exists
				//createPart(id, programme_id, en, fr, es, updated_at);
				
				
				
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
	 * @param part
	 */
	public void parsePartContent(JSONArray part){
		//TODO triple layered parse.
	}
}
