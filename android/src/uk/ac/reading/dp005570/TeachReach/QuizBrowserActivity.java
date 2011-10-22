package uk.ac.reading.dp005570.TeachReach;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class QuizBrowserActivity extends Activity {
	private Integer questionNumber, number_of_questions;
	private TextView question_progress;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		questionNumber = 1; number_of_questions = 10;
		
		question_progress = (TextView) findViewById(R.id.question_progress);
		question_progress.setText( questionNumber + " / " + number_of_questions);
		
		TextView question_title = (TextView) findViewById(R.id.question_title);
		question_title.setText(R.string.question);
		
		TextView question_text = (TextView) findViewById(R.id.question_text);
		question_text.setText("blab ewa rewa  ewarewar ewa rewahiorpewa hiophfido ewaklcnakm eawocpmecdkmwe mkl pewa");
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.quiz_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.previous:
	    	if(questionNumber > 1){
	    		questionNumber--;
	    		question_progress.setText(questionNumber + " / " + number_of_questions);
	    		return true;
	    	}
	    	else{
	    		return false;
	    	}
	    case R.id.next:
	    	if(questionNumber < number_of_questions){
	    		questionNumber++;
	    		question_progress.setText(questionNumber + " / " + number_of_questions);
	    		return true;
	    	}else {
	    		//TODO Load final results screen
	    		return false;
	    	}
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}
