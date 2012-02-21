package uk.ac.reading.dp005570.TeachReach;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class QuizListActivity extends ListActivity implements OnItemClickListener{
	
//	private ArrayList<QuizStatus> mQuizzes = null;
	private String[] mQuizzes;
//	private QuizItemAdapter mAdapter;
//	private TeachReachPopulater mTeachReachPopulater;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mTeachReachPopulater = new TeachReachPopulater(getApplicationContext());
        
//        mQuizzes = mTeachReachPopulater.getCurrentQuizzes();
        setContentView(R.layout.quiz_list);
        getListView().setTextFilterEnabled(false);
        getListView().setOnItemClickListener(this);
//        mQuizzes = new ArrayList<QuizStatus>();
//		int part = getIntent().getIntExtra("Part", 0);
//		mTeachReachPopulater.get
//		getIntent().getExtras().getSerializable(key)
		
//		Toast.makeText(this.getApplicationContext(), "Selected ID: " + part, Toast.LENGTH_LONG).show(); //TODO Localised message text

        
        // TODO ProgressDialog here while retrieving from online if chosen
        populateQuizList();
        
//        this.mAdapter = new QuizItemAdapter(this, R.layout.quiz_item, mQuizzes);
//        setListAdapter(mAdapter);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.quiz_item, mQuizzes)); //TODO Change quiz_item to be suitable to put here

	}
	
	
	private void populateQuizList() {
		// TODO Future implementation would be here
//        mQuizzes.add(new QuizStatus("Test Quiz 1", false));
//        mQuizzes.add(new QuizStatus("Test Quiz 2", true));	
		mQuizzes = new String[]{ "Test Quiz 1", "Test Quiz 2" };
	}


//	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// When a quiz item is clicked
		Intent intent = new Intent(this, QuizActivity.class);
//		intent.putExtra(TeachReachActivity.QUIZ_ID, value);
		startActivity(intent);
	}
	
}
