package uk.ac.reading.dp005570.TeachReach;

import android.app.Activity;
import android.os.Bundle;

/**
 * Allows the user to view a snippet of information related to a particular question
 * this is viewable whether they got the answer correct, or not - it is seen as
 * a self-improvement exercise.
 * @author Ian Field
 *
 */
public class FeedbackActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		//TODO actual implementation, retrieve from intent extras - quick job
	}
}
