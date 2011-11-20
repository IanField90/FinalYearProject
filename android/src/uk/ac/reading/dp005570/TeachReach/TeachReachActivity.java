package uk.ac.reading.dp005570.TeachReach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * @author ianfield
 *
 */
public class TeachReachActivity extends Activity implements OnClickListener{
//	private JSONObject object;
	private TeachReachDbAdapter mDbHelper;
	private Spinner mProgrammeSpinner, mCourseSpinner, mPartSpinner; 
   
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mDbHelper = new TeachReachDbAdapter(this);
        mDbHelper.open();
        
        String programmes[] = { "1. Programme 1", "2. Programme 2", "3. Programme 3",
        		"4. Programme 4", "5. Programme 5", "6. Programme 6", "7. Programme 7",
        		"8. Programme 8", "9. Programme 9", "10. Programme 10"  };
        String courses[] = { "1. Course 1", "2. Course 2" };
        String parts[] = { "1. Part 1", "2. Part 2" };
        
        //Set up proramme spinner
        mProgrammeSpinner = (Spinner) findViewById(R.id.programme_spinner);
        ArrayAdapter<String> programme_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, programmes);
        programme_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProgrammeSpinner.setAdapter(programme_adapter);
        
        //Set up course spinner
        mCourseSpinner = (Spinner) findViewById(R.id.course_spinner);
        ArrayAdapter<String> course_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        course_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCourseSpinner.setAdapter(course_adapter);
        
        //Set up module spinner
        mPartSpinner = (Spinner) findViewById(R.id.part_spinner);
        ArrayAdapter<String> module_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, parts);
        module_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPartSpinner.setAdapter(module_adapter);
        
        //Set up m
        Button view_quizzes_button = (Button) findViewById(R.id.view_quizzes_button);
        view_quizzes_button.setOnClickListener(this);
        
        Button view_materials_button = (Button) findViewById(R.id.view_materials_button);
        view_materials_button.setOnClickListener(this);
    }

//    @Override
    public void onClick(View v) {
    	Intent i;
    	if(v == findViewById(R.id.view_quizzes_button)){
        	//Create an intent and start activity
        	i = new Intent(this, QuizListActivity.class);
        	i.putExtra("Programme", mProgrammeSpinner.getSelectedItemPosition()+1);
        	i.putExtra("Course", mCourseSpinner.getSelectedItemPosition()+1);
        	i.putExtra("Part", mPartSpinner.getSelectedItemPosition()+1);
        	startActivity(i);
    	}
    	else if(v == findViewById(R.id.view_materials_button)){
    		i = new Intent(this, MaterialListActivity.class);
    		i.putExtra("Programme", mProgrammeSpinner.getSelectedItemPosition()+1);
        	i.putExtra("Course", mCourseSpinner.getSelectedItemPosition()+1);
        	i.putExtra("Part", mPartSpinner.getSelectedItemPosition()+1);
        	startActivity(i);
    	}
    	else if(v == findViewById(R.id.refresh_lists_button)){
    		//TODO Retrieve from Server & Update DB
    	}
    }
}