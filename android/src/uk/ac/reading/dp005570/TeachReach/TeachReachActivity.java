package uk.ac.reading.dp005570.TeachReach;

import java.util.Locale;

import uk.ac.reading.dp005570.TeachReach.util.TeachReachDbAdapter;
import uk.ac.reading.dp005570.TeachReach.util.TeachReachPopulater;
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
	private TeachReachPopulater mTeachReachPopulater;
	private String[] mCourseItems;
	private String[] mProgrammeItems;
	private String[] mPartItems;
	
	private final String TAG = "TeachReachActivity";
	
	private int mSelectedCourseId = 0;
	private int mSelectedProgrammeId = 0;
	private int mSelectedPartId = 0;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTeachReachPopulater = new TeachReachPopulater(getApplicationContext());
        loadSettings();
        
        //TODO use this when getting contents for main menu items and throughout
        // application run
        Locale locale = getResources().getConfiguration().locale;
        
        mDbHelper = new TeachReachDbAdapter(this);
        mDbHelper.open();
        
        mCourseItems = new String[] { "Course 1", "Course 2" };
//      mCourseItems = mTeachReachPopulater.getCourseItems();

        mProgrammeItems = new String[]{};
        mPartItems = new String[] {};
        
        //Set up course spinner
        mCourseSpinner = (Spinner) findViewById(R.id.course_spinner);
        ArrayAdapter<String> course_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mCourseItems);
        course_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCourseSpinner.setAdapter(course_adapter);
        mCourseSpinner.setOnItemSelectedListener(this);
        
        //Set up proramme spinner
        mProgrammeSpinner = (Spinner) findViewById(R.id.programme_spinner);
        ArrayAdapter<String> programme_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mProgrammeItems);
        programme_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProgrammeSpinner.setAdapter(programme_adapter);
        
        //Set up module spinner
        mPartSpinner = (Spinner) findViewById(R.id.part_spinner);
        ArrayAdapter<String> module_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mPartItems);
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
//			e.printStackTrace();
//		} catch (IOException e) {
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
			// utilise TeachReachPopulator
			final ProgressDialog progress = ProgressDialog.show(TeachReachActivity.this, "Please wait...", "Retrieving data...");
			Thread thread = new Thread(new Runnable(){
				public void run(){
					mTeachReachPopulater.getMainMenu(progress);
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
			// update programme spinner to reflect this change
			mProgrammeItems = new String[] { "Programme 1", "Programme 2" };//TODO use: mTeachReachPopulater.getProgrammeItems(position + 1);
			
	        ArrayAdapter<String> programme_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mProgrammeItems);
	        programme_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        mProgrammeSpinner.setAdapter(programme_adapter);
			
		}
		else if(parent == mProgrammeSpinner){
			Log.i(TAG, "Programmes Spinner changed.");
			//TODO update part spinner to reflect this change
			//mSelectedProgrammeId =  mProgrammes.get(position).getID();

			//TODO redraw spinner?
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