package uk.ac.reading.dp005570.TeachReach;

import org.json.JSONArray;
import org.json.JSONException;

import uk.ac.reading.dp005570.TeachReach.data.TeachReachParser;
import uk.ac.reading.dp005570.TeachReach.net.ServerCommunicationHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * @author ianfield
 *
 */
public class TeachReachActivity extends Activity implements OnClickListener, OnItemSelectedListener{
	private TeachReachDbAdapter mDbHelper;
	private Spinner mProgrammeSpinner, mCourseSpinner, mPartSpinner; 
//	private final String SETTINGS_FILE = "TeachReachSettings.txt";
	private ServerCommunicationHelper mSCH = new ServerCommunicationHelper();
	private TeachReachParser mTeachReachParser = new TeachReachParser();
	
	private final String TAG = "TeachReachActivity";
	
	private int mSelectedCourseId = 0;
	private int mSelectedProgrammeId = 0;
	private int mSelectedPartId = 0;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        loadSettings();
        
        mDbHelper = new TeachReachDbAdapter(this);
        mDbHelper.open();
        String courses[] = { "1. Course 1", "2. Course 2" };

        String programmes[] = { "1. Programme 1", "2. Programme 2", "3. Programme 3",
        		"4. Programme 4", "5. Programme 5", "6. Programme 6", "7. Programme 7",
        		"8. Programme 8", "9. Programme 9", "10. Programme 10"  };
        String parts[] = { "1. Part 1", "2. Part 2" };
        
        //Set up course spinner
        mCourseSpinner = (Spinner) findViewById(R.id.course_spinner);
        ArrayAdapter<String> course_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        course_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCourseSpinner.setAdapter(course_adapter);
        mCourseSpinner.setOnItemSelectedListener(this);
        
        //Set up proramme spinner
        mProgrammeSpinner = (Spinner) findViewById(R.id.programme_spinner);
        ArrayAdapter<String> programme_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, programmes);
        programme_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProgrammeSpinner.setAdapter(programme_adapter);
        
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
        
        mDbHelper.close();
    }

    private void loadSettings(){
//        try {
//        	char[] line = new char[10];
//			FileReader fr = new FileReader(SETTINGS_FILE);
//			fr.read(line);
//			
//			String string_line = line.toString();
//			String[] options = string_line.split(",", 2);
//			selected_course = Integer.parseInt(options[0]);
//			selected_programme = Integer.parseInt(options[1]);
//			selected_part = Integer.parseInt(options[2]);
//			
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
    }
    
    /**
     * Save the id for course, programme, and part
     */
    private void saveSettings(){
    	//TODO Get corresponding ID from db
    }
    
	@Override
	protected void onStop(){
		super.onStop();
		saveSettings();
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
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refresh_lists:
			final ProgressDialog progress = ProgressDialog.show(TeachReachActivity.this, "Please wait...", "Retrieving data...");
			Thread thread = new Thread(new Runnable(){
				public void run(){
					try {
						mTeachReachParser.parseCourses(new JSONArray( mSCH.getCourseList(progress)));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			thread.run();
		default:
			return super.onOptionsItemSelected(item);
		}
	
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if(parent == mCourseSpinner){
			Log.i(TAG, "Courses Spinner changed.");
			mSelectedCourseId = position + 1;
			//TODO update programme spinner to reflect this change
			
		}
		else if(parent == mProgrammeSpinner){
			Log.i(TAG, "Programmes Spinner changed.");
			//TODO update part spinner to reflect this change
			//mSelectedProgrammeId =  mProgrammes.get(position).getID();
		}
		else if(parent == mPartSpinner){
			Log.i(TAG, "Parts Spinner changed.");
			
			//mSelectedPartId = mParts.get(position).getID();
		}
		
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}