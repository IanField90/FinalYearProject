package uk.ac.reading.dp005570.TeachReach;

import java.util.Locale;

import uk.ac.reading.dp005570.TeachReach.util.TeachReachPopulater;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class QuizListActivity extends ListActivity implements OnItemClickListener{

	//	private ArrayList<QuizStatus> mQuizzes = null;
	private int mPartId;
	private String[] mQuizzes;
	//	private QuizItemAdapter mAdapter;
	private TeachReachPopulater mTeachReachPopulater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.quiz_list);
		getListView().setTextFilterEnabled(false);
		getListView().setOnItemClickListener(this);
		mTeachReachPopulater = new TeachReachPopulater(getApplicationContext());
		populateQuizList();
		if(mTeachReachPopulater.getCurrentQuizzes().size() > 0){
			setListAdapter(new ArrayAdapter<String>(this, R.layout.quiz_item, mQuizzes)); 
		}

	}

	@Override
	protected void onStop(){
		mTeachReachPopulater.closeDB();
		super.onStop();
	}
	
	@Override
	protected void onPause(){
		mTeachReachPopulater.closeDB();
		super.onPause();
	}
	
//	@Override
//	protected void onResume(){
//		mTeachReachPopulater.openDB();
//		super.onResume();
//	}
	
	@Override
	protected void onRestart(){
		mTeachReachPopulater.openDB();
		super.onResume();
	}
	
	
	/**
	 * Get quiz list from the database, do checks and populate list with correct languages
	 */
	private void populateQuizList() {
		mPartId = getIntent().getIntExtra(TeachReachActivity.PART_ID, 0);
		mTeachReachPopulater.openDB();
		mTeachReachPopulater.retrieveQuizList(mPartId);
		mTeachReachPopulater.closeDB();
		if(mTeachReachPopulater.getCurrentQuizzes().size() > 0){
			mQuizzes = new String[mTeachReachPopulater.getCurrentQuizzes().size()];
			for(int i = 0; i < mTeachReachPopulater.getCurrentQuizzes().size(); i++){
				if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("franais")){
					mQuizzes[i] = mTeachReachPopulater.getCurrentQuizzes().get(i).getFR();
				}
				else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("espa–ol")){
					mQuizzes[i] = mTeachReachPopulater.getCurrentQuizzes().get(i).getES();
				}
				else{
					mQuizzes[i] = mTeachReachPopulater.getCurrentQuizzes().get(i).getEN();
				}
			}
		}
		else{
			Toast.makeText(this.getApplicationContext(), getString(R.string.quiz_apology), Toast.LENGTH_LONG).show();
		}
	}


	/**
	 * Handle the clicking of a quiz item
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// When a quiz item is clicked
		Intent intent = new Intent(this, QuizActivity.class);
		int value = mTeachReachPopulater.getCurrentQuizzes().get(position).getId();
		intent.putExtra(TeachReachActivity.QUIZ_ID, value);
		startActivity(intent);
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
			String wait = getString(R.string.please_wait);
			String retrieve = getString(R.string.server_retrieval);
			final ProgressDialog progress = ProgressDialog.show(QuizListActivity.this, wait, retrieve);
			final Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.server_error), Toast.LENGTH_LONG);
			final Handler handler = new Handler(){
				@Override
				public void handleMessage(Message msg){
					refreshList();
				}
			};
			mTeachReachPopulater.openDB();
			Thread thread = new Thread(new Runnable(){
				public void run(){
					if(!mTeachReachPopulater.refreshPart(progress, mPartId)){
						toast.show();
						handler.sendEmptyMessage(0);
					}
				}
			});
			thread.start();	
		default:
			return super.onOptionsItemSelected(item);
		}

	}
	
	public void refreshList(){
		populateQuizList();
		setListAdapter(new ArrayAdapter<String>(this, R.layout.quiz_item, mQuizzes)); 
		mTeachReachPopulater.closeDB();
	}

}
