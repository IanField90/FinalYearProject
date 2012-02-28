package uk.ac.reading.dp005570.TeachReach;

import java.util.Locale;

import uk.ac.reading.dp005570.TeachReach.data.Question;
import uk.ac.reading.dp005570.TeachReach.util.TeachReachPopulater;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Allows the user to view a snippet of information related to a particular question
 * this is viewable whether they got the answer correct, or not - it is seen as
 * a self-improvement exercise.
 * @author Ian Field
 *
 */
public class FeedbackActivity extends Activity{
	private int mQuizId;
	private int mQuestionPosition;
	private TeachReachPopulater mTeachReachPopulater;
	private Question mQuestion;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		// actual implementation, retrieve from intent extras - quick job
		mQuizId = getIntent().getIntExtra("QUIZ_ID", 0);
		mQuestionPosition = getIntent().getIntExtra("QUESTION_POS", 0);
		
		mTeachReachPopulater = new TeachReachPopulater(getApplicationContext());
		mTeachReachPopulater.openDB();
		mQuestion = mTeachReachPopulater.getFeedbackForQuestion(mQuizId, mQuestionPosition);		
		mTeachReachPopulater.closeDB();

		//		TextView tv = 
		TextView tv = (TextView) findViewById(R.id.feedback_body);
		if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("français")){
			tv.setText(mQuestion.getFeedbackFR());
		}
		else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("español")){
			tv.setText(mQuestion.getFeedbackES());
		}
		else{
			tv.setText(mQuestion.getFeedbackEN());
		}
		
	}
}
