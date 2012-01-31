package uk.ac.reading.dp005570.TeachReach.test;

import uk.ac.reading.dp005570.TeachReach.TeachReachActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class TeachReachActivityTest extends	ActivityInstrumentationTestCase2<TeachReachActivity> {
	private Activity mTeachReachActivity;
	private Button mQuizzesButton;
	private String quizzesButtonText;
	
	public TeachReachActivityTest(){
		super("uk.ac.reading.dp005570.TeachReach.TeachReachActivity", TeachReachActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		mTeachReachActivity = this.getActivity();
		mQuizzesButton = (Button) mTeachReachActivity.findViewById(uk.ac.reading.dp005570.TeachReach.R.id.view_quizzes_button);
		quizzesButtonText = mTeachReachActivity.getString(uk.ac.reading.dp005570.TeachReach.R.string.view_quizzes_button);
	}
	
	public void testPreconditions(){
		assertNotNull(mQuizzesButton);
	}
	
	public void testQuizzesText(){
		assertEquals(quizzesButtonText, (String) mQuizzesButton.getText());
	}
	
}
