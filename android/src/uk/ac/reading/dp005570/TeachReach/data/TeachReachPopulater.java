package uk.ac.reading.dp005570.TeachReach.data;

import java.util.ArrayList;

import uk.ac.reading.dp005570.TeachReach.TeachReachDbAdapter;
import android.database.Cursor;
import android.util.Log;

public class TeachReachPopulater {
	private final String TAG = "POPULATER";
//	private TeachReachParser mTeachReachParser;
	private TeachReachDbAdapter mTeachReachDbAdapter;
	
	//TODO change list types to reflect actual type i.e. replace HierarchySelection with Course etc
	
	public ArrayList<HierarchySelection> getCourseList(){
		//return id with all 3 text fields - unless language can be determined here.
		// add array list here
		ArrayList<HierarchySelection> courses = new ArrayList<HierarchySelection>();
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
				
				HierarchySelection selection = 
						new HierarchySelection(id, en, fr, es, HierarchySelection.Type.COURSE);
				courses.add(selection);
			}while(cursor.moveToNext());
			
		}
		return (courses.size() > 0) ? courses : null;
	}
	
	public ArrayList<HierarchySelection> getPogrammeList(int course_id){
		ArrayList<HierarchySelection> programmes = new ArrayList<HierarchySelection>();
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
				
				HierarchySelection selection = 
						new HierarchySelection(id, en, fr, es, HierarchySelection.Type.PROGRAMME);
				programmes.add(selection);
			}while(cursor.moveToNext());
		}
		
		return (programmes.size() > 0) ? programmes : null;
	}
	
	public ArrayList<HierarchySelection> getPartsList(int programme_id){
		ArrayList<HierarchySelection> parts = new ArrayList<HierarchySelection>();
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
				
				HierarchySelection selection = 
						new HierarchySelection(id, en, fr, es, HierarchySelection.Type.PART);
				parts.add(selection);
			}while(cursor.moveToNext());
		}
		return (parts.size() > 0) ? parts : null;

	}
}
