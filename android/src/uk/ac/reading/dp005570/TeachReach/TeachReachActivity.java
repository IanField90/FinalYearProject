package uk.ac.reading.dp005570.TeachReach;

import uk.ac.reading.dp005570.TeachReach.util.TeachReachPopulater;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

/**
 * @author Ian Field
 *
 */
public class TeachReachActivity extends Activity implements OnClickListener, OnItemSelectedListener{
	private Spinner mProgrammeSpinner, mCourseSpinner, mPartSpinner; 
	private TeachReachPopulater mTeachReachPopulater;
	private String[] mCourseItems;
	private String[] mProgrammeItems;
	private String[] mPartItems;

	//	private final String TAG = "TeachReachActivity";

	//Fields to be used in the settings file
	public static final String PREFS_NAME = "TeachReachSettings";
	public static final String COURSE_ID = "courseID";
	public static final String PROGRAMME_ID = "programmeID";
	public static final String PART_ID = "partID";
	public static final String QUIZ_ID = "quizID";
	public static final String MATERIAL_ID = "materialID";
	public static final String COURSE_SPINNER_POS = "courseSpinnerPos";
	public static final String PROGRAMME_SPINNER_POS = "programmeSpinnerPos";
	public static final String PART_SPINNER_POS = "partSpinnerPos";

	private int mSelectedCourseId = 0;
	private int mSelectedProgrammeId = 0;
	private int mSelectedPartId = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);        
		mTeachReachPopulater = new TeachReachPopulater(getApplicationContext());
		loadSettings(); //Can cause crash on re-open
		mCourseItems = mTeachReachPopulater.getCourses();
		mProgrammeItems = mTeachReachPopulater.getProgrammes(mSelectedCourseId);
		mPartItems = mTeachReachPopulater.getParts(mSelectedProgrammeId);

		//Set up course spinner
		mCourseSpinner = (Spinner) findViewById(R.id.course_spinner);
		ArrayAdapter<String> course_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mCourseItems);
		course_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
		mCourseSpinner.setAdapter(course_adapter);
		mCourseSpinner.setOnItemSelectedListener(this);

		//Set up proramme spinner
		mProgrammeSpinner = (Spinner) findViewById(R.id.programme_spinner);
		ArrayAdapter<String> programme_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mProgrammeItems);
		programme_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
		mProgrammeSpinner.setAdapter(programme_adapter);
		mProgrammeSpinner.setOnItemSelectedListener(this);

		//Set up module spinner
		mPartSpinner = (Spinner) findViewById(R.id.part_spinner);
		ArrayAdapter<String> part_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mPartItems);
		part_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
		mPartSpinner.setAdapter(part_adapter);
		mPartSpinner.setOnItemSelectedListener(this);

		//Set up m
		Button view_quizzes_button = (Button) findViewById(R.id.view_quizzes_button);
		view_quizzes_button.setOnClickListener(this);

		Button view_materials_button = (Button) findViewById(R.id.view_materials_button);
		view_materials_button.setOnClickListener(this);

		setUpSpinners(); 
	}

	private void setUpSpinners(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		mCourseSpinner.setSelection(settings.getInt(COURSE_SPINNER_POS, 0), true);
		mProgrammeSpinner.setSelection(settings.getInt(PROGRAMME_SPINNER_POS, 0), true);
		mPartSpinner.setSelection(settings.getInt(PART_SPINNER_POS, 0), true);
	}

	private void loadSettings(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		mSelectedCourseId = settings.getInt(COURSE_ID, 0);
		mSelectedProgrammeId = settings.getInt(PROGRAMME_ID, 0);
		mSelectedPartId = settings.getInt(PART_ID, 0);
	}

	/**
	 * Save the id for course, programme, and part
	 */
	private void saveSettings(){
		// Get corresponding ID from db
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		editor.putInt(COURSE_ID, mSelectedCourseId);
		editor.putInt(PROGRAMME_ID, mSelectedProgrammeId);
		editor.putInt(PART_ID, mSelectedPartId);
		editor.putInt(COURSE_SPINNER_POS, mCourseSpinner.getSelectedItemPosition());
		editor.putInt(PROGRAMME_SPINNER_POS, mProgrammeSpinner.getSelectedItemPosition());
		editor.putInt(PART_SPINNER_POS, mPartSpinner.getSelectedItemPosition());


		//Save changes
		editor.commit();
	}

	@Override
	protected void onStop(){
		mTeachReachPopulater.closeDB();
		saveSettings();
		super.onStop();
	}

	@Override
	protected void onPause(){
		mTeachReachPopulater.closeDB();
		super.onPause();
	}

	@Override
	protected void onResume(){
		mTeachReachPopulater.openDB();
		super.onResume();
	}

	@Override
	protected void onRestart(){
		mTeachReachPopulater.openDB();
		super.onResume();
	}

	//	@Override
	//	protected void onDestroy(){
	//		mTeachReachPopulater.closeDB();
	//		super.onDestroy();
	//	}

	/**
	 * Onclick of a button
	 */
	public void onClick(View v) {
		Intent i;
		if(mTeachReachPopulater.getCurrentParts().size() != 0){
			if(v == findViewById(R.id.view_quizzes_button)){
				saveSettings();
				//Create an intent and start activity
				i = new Intent(this, QuizListActivity.class);
				i.putExtra(PART_ID, mSelectedPartId); // use actual partID here
				startActivity(i);
			}
			else if(v == findViewById(R.id.view_materials_button)){
				saveSettings();
				i = new Intent(this, MaterialListActivity.class);
				i.putExtra(PART_ID, mSelectedPartId); // use actual partID here
				startActivity(i);
			}
		}
	}

	/**
	 * Create the options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.main_menu, menu);
		return true;
	}

	/**
	 * Handle an options menu item selection
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refresh_lists:
			// utilise TeachReachPopulator
			String wait = getString(R.string.please_wait);
			String retrieve = getString(R.string.server_retrieval);
			final ProgressDialog progress = ProgressDialog.show(TeachReachActivity.this, wait, retrieve);
			final Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.server_error), Toast.LENGTH_LONG);
			final Handler handler = new Handler(){
				@Override
				public void handleMessage(Message msg){
					refresh();
				}
			};
			Thread thread = new Thread(new Runnable(){
				public void run(){
					if(!mTeachReachPopulater.refreshMainMenu(progress)){
						toast.show();
						handler.sendEmptyMessage(0);
					}
				}
			});
			thread.start();	
			// redraw spinners - test this

		default:
			return super.onOptionsItemSelected(item);
		}

	}
	
	public void refresh(){
		mSelectedCourseId = 0;
		mSelectedProgrammeId = 0;
		mSelectedPartId = 0;
		updateCourseSpinner();
		updateProgrammeSpinner();
		updatePartSpinner();
	}

	/**
	 * Handle selection of a spinner item
	 */
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if(parent == mCourseSpinner){
			//			Log.i(TAG, "Courses Spinner changed.");
			mSelectedCourseId = mTeachReachPopulater.getCourseList().get(position).getId();
			updateProgrammeSpinner();
			if(mTeachReachPopulater.getCurrentProgrammes().size() > 0) {
				mSelectedProgrammeId = mTeachReachPopulater.getCurrentProgrammes().get(mProgrammeSpinner.getSelectedItemPosition()).getId();
			}
			else{
				mSelectedProgrammeId = 0;
			}
			updatePartSpinner();
			//			mSelectedPartId = mTeachReachPopulater.getCurrentParts().get(mPartSpinner.getSelectedItemPosition()).getId();

		}
		else if(parent == mProgrammeSpinner){
			mSelectedProgrammeId = mTeachReachPopulater.getCurrentProgrammes().get(position).getId();
			updatePartSpinner();
		}
		else if(parent == mPartSpinner){
			mSelectedPartId = mTeachReachPopulater.getCurrentParts().get(position).getId();
		}

	}

	/**
	 * Redraw the course spinner
	 */
	private void updateCourseSpinner(){
		mCourseItems = mTeachReachPopulater.getCourses();
		ArrayAdapter<String> course_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mCourseItems);
		course_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
		mCourseSpinner.setAdapter(course_adapter);
//		mCourseSpinner.setOnItemSelectedListener(this);
	}

	/**
	 * Redraw the Programme spinner based on selected course
	 */
	private void updateProgrammeSpinner(){
		// update programme spinner to reflect this change
		mProgrammeItems = mTeachReachPopulater.getProgrammes(mSelectedCourseId);
		ArrayAdapter<String> programme_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mProgrammeItems);
		programme_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
		mProgrammeSpinner.setAdapter(programme_adapter);
	}

	/**
	 * Redraw the part spinner based on selected programme
	 */
	private void updatePartSpinner(){
		// update part spinner to reflect this change
		mPartItems = mTeachReachPopulater.getParts(mSelectedProgrammeId);
		ArrayAdapter<String> part_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mPartItems);
		part_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
		mPartSpinner.setAdapter(part_adapter);
	}

	/**
	 * Unused
	 */
	public void onNothingSelected(AdapterView<?> parent) {

	}
}