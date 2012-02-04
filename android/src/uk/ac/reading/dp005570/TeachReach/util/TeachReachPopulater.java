package uk.ac.reading.dp005570.TeachReach.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import uk.ac.reading.dp005570.TeachReach.data.Course;
import uk.ac.reading.dp005570.TeachReach.data.Language;
import uk.ac.reading.dp005570.TeachReach.data.Part;
import uk.ac.reading.dp005570.TeachReach.data.Programme;
import uk.ac.reading.dp005570.TeachReach.net.ServerCommunicationHelper;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Controller for populating the database and providing information to
 * views for proper population. Cursor traversal etc.
 * @author ianfield
 *
 */
public class TeachReachPopulater {
	private final String TAG = "POPULATER";
	private TeachReachParser mTeachReachParser;
	private TeachReachDbAdapter mTeachReachDbAdapter;
	private ServerCommunicationHelper mServerCommunicationHelper;

	private ArrayList<Course> mCourses;
	private ArrayList<Programme> mProgrammes;
	private ArrayList<Part> mParts;

	public TeachReachPopulater(Context context) {
		mTeachReachParser = new TeachReachParser();
		mTeachReachDbAdapter = new TeachReachDbAdapter(context);
		mTeachReachDbAdapter.open();
		mServerCommunicationHelper = new ServerCommunicationHelper();
	}

	//TODO determine between update main menu list and retrieving from the database instead
	public void getMainMenu(ProgressDialog dialog){
		String response = mServerCommunicationHelper.getCourseList(dialog);
		//Null check first. If it is then no response from server
		if((response != null) && (response.length() > 0)){
			try {
				mTeachReachParser.parseCourses(new JSONArray(response));
				mCourses = mTeachReachParser.getCourses();
				mProgrammes = mTeachReachParser.getProgrammes();
				mParts = mTeachReachParser.getParts();
				//TODO NOTED spinner populater control
				//			String[] parts;
				//			for(Part part : mParts){
				//				if(part.getProgrammeID() == programme_id){
				//					parts[i] = part.getEN();
				//				}
				//			}
				updateCourses();
				updateProgrammes();
				updateParts();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
				String updated = cursor.getString(4);

				Course course = new Course(id, en, fr, es, updated);
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
		else{
			//Can traverse through content
			do{
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				String updated = cursor.getString(4);

				Programme programme = new Programme(id, course_id, en, fr, es, updated);
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
				String updated = cursor.getString(4);

				Part part = new Part(id, programme_id, en, fr, es, updated);
				parts.add(part);
			}while(cursor.moveToNext());
		}
		return (parts.size() > 0) ? parts : null;

	}

	/**
	 * 
	 * @param language The Language enumerator of the expected return
	 * @return List of courses in given language
	 */
	public String[] getCourses(Language language){
		//TODO return key pair: (id, value) instead so that data can be retrieved
		String[] courses = new String[mCourses.size()];
		int i = 0;
		for( Course course : mCourses ){
			switch (language){
			case EN:
				courses[i] = course.getEN();
				break;
			case FR:
				courses[i] = course.getFR();
				break;
			case ES:
				courses[i] = course.getES();
				break;
			}
			i++;
		}
		return courses;
	}

	/**
	 * 
	 * @param language
	 * @param course_id
	 * @return
	 */
	public String[] getProgrammes(Language language, int course_id){
		//TODO return key pair: (id, value) instead so that data can be retrieved
		String[] programmes = new String[mProgrammes.size()];
		int i = 0;
		for(Programme programme : mProgrammes){
			if (programme.getId() == course_id){
				switch(language){
				case EN:
					programmes[i] = programme.getEN();
					break;
				case FR:
					programmes[i] = programme.getFR();
					break;
				case ES:
					programmes[i] = programme.getES();
					break;

				}
				i++;
			}
		}
		return programmes;
	}

	/**
	 * 
	 * @param language
	 * @param programme_id
	 * @return
	 */
	public String[] getParts(Language language, int programme_id){
		//TODO return key pair: (id, value) instead so that data can be retrieved
		String[] parts = new String[mParts.size()];
		int i = 0;
		for(Part part : mParts){
			if (part.getId() == programme_id){
				switch(language){
				case EN:
					parts[i] = part.getEN();
					break;
				case FR:
					parts[i] = part.getFR();
					break;
				case ES:
					parts[i] = part.getES();
					break;

				}
				i++;
			}
		}
		return parts;
	}

	/* DATABASE FUNCTIONS - traverse through cursor*/

	public void retriveCourseList(){

	}

	public void retrieveProgrammesList(int course_id){

	}

	public void retrievePartsList(int programme_id){

	}

	/* Utilise parser's results and for each element call DB helper function to update/create */
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
	
	//TODO Actually use cursor to create these arrays for the spinner adapter
	public String[] getCourseItems(){
		return null;
	}
	
	public String[] getProgrammeItems(int course_id){
		return null;
	}
	
	public String[] getPartItems(int programme_id){
		return null;
	}
}
