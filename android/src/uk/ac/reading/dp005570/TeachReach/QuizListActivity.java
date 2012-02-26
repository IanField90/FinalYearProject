package uk.ac.reading.dp005570.TeachReach;

import java.util.Locale;

import uk.ac.reading.dp005570.TeachReach.util.TeachReachPopulater;
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
	private TeachReachPopulater mTeachReachPopulater;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		int part_id = getIntent().getIntExtra(TeachReachActivity.PART_ID, 0);
//        mTeachReachPopulater = new TeachReachPopulater(getApplicationContext());
//        mTeachReachPopulater.openDB();
//        mTeachReachPopulater.retrieveQuizList(part_id);
//        mQuizzes = new String[mTeachReachPopulater.getCurrentQuizzes().size()];
//        for(int i = 0; i < mTeachReachPopulater.getCurrentQuizzes().size()-1; i++){
//        	if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("franais")){
//            	mQuizzes[i] = mTeachReachPopulater.getCurrentQuizzes().get(i).getFR();
//    		}
//    		else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("espa–ol")){
//            	mQuizzes[i] = mTeachReachPopulater.getCurrentQuizzes().get(i).getES();
//    		}
//    		else{
//            	mQuizzes[i] = mTeachReachPopulater.getCurrentQuizzes().get(i).getEN();
//    		}
//        }
//        
        setContentView(R.layout.quiz_list);
        getListView().setTextFilterEnabled(false);
        getListView().setOnItemClickListener(this);
		
        
        // TODO ProgressDialog here while retrieving from online if chosen
        populateQuizList();
        setListAdapter(new ArrayAdapter<String>(this, R.layout.quiz_item, mQuizzes)); 
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
