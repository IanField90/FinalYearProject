package uk.ac.reading.dp005570.TeachReach.data;

import java.util.ArrayList;

import uk.ac.reading.dp005570.TeachReach.TeachReachDbAdapter;
import android.database.Cursor;
import android.util.Log;

public class TeachReachPopulater {
	private final String TAG = "POPULATER";
	private TeachReachParser mTeachReachParser;
	private TeachReachDbAdapter mTeachReachDbAdapter;
	
	public ArrayList<HierarchySelection> getCourseList(){
		//return id with all 3 text fields - unless language can be determined here.
		// add array list here
		ArrayList<HierarchySelection> courses = new ArrayList<HierarchySelection>();
		Cursor cursor = mTeachReachDbAdapter.fetchCourseList();
		if(cursor == null){
			Log.i(TAG, "Course cursor empty.");
		}
		else{
			//Can traverse through content
			while(cursor.moveToNext()){
				int id = cursor.getInt(0);
				String en = cursor.getString(1);
				String fr = cursor.getString(2);
				String es = cursor.getString(3);
				
				HierarchySelection selection = 
						new HierarchySelection(id, en, fr, es, HierarchySelection.Type.COURSE);
				courses.add(selection);
			}
			
		}
		return (courses.size() > 0) ? courses : null;
	}
}
