package uk.ac.reading.dp005570.TeachReach.data;

import java.util.ArrayList;

import uk.ac.reading.dp005570.TeachReach.TeachReachDbAdapter;
import android.database.Cursor;
import android.util.Log;

public class TeachReachPopulater {
	private final String TAG = "POPULATER";
//	private TeachReachParser mTeachReachParser;
	private TeachReachDbAdapter mTeachReachDbAdapter;
		
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
