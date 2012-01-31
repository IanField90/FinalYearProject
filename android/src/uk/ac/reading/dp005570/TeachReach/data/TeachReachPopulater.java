package uk.ac.reading.dp005570.TeachReach.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import uk.ac.reading.dp005570.TeachReach.TeachReachDbAdapter;
import uk.ac.reading.dp005570.TeachReach.net.ServerCommunicationHelper;
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
	//TODO Check the return columns from database queries to ensure correct retrieval in traversal step.
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
		mServerCommunicationHelper = new ServerCommunicationHelper();
	}
	
	public void getMainMenu(){
		String response = mServerCommunicationHelper.getCourseList(null);//TODO Decide upon correct progress bar feedback operation
		try {
				mTeachReachParser.parseCourses(new JSONArray(response));
				mCourses = mTeachReachParser.getCourses();
				mProgrammes = mTeachReachParser.getProgrammes();
				mParts = mTeachReachParser.getParts();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
