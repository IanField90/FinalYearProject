package uk.ac.reading.dp005570.TeachReach;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import uk.ac.reading.dp005570.TeachReach.data.Option;
import uk.ac.reading.dp005570.TeachReach.data.Question;
import uk.ac.reading.dp005570.TeachReach.util.TeachReachPopulater;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

/*
 * If shuffling around store in the following way: int[spinner number] = correct value - this will hopefully make sense
 * Meaning that the first element, would be the correct position for the first spinner control
 * 
 * Here's where the brunt of logic happens!
 */

/**
 * Handles the taking of quizzes and displaying of the questions within the quiz.
 * Accessible through QuizListActivity - so can assume quiz is present in DB.
 * @author Ian Field
 *
 */
public class QuizActivity extends Activity implements OnSeekBarChangeListener, OnClickListener {
	private final String NUM_QUESTIONS = "Number_Questions";
	//Store the current number of the question that the user is on
	private Integer mQuestionNumber;
	private TextView mQuestionProgress;
	private TextView mSliderLabel;
	private ArrayList<Question> mQuestions;	
	private ArrayList<Option> mOptions;
	private LinearLayout mLl;
	private SeekBar mSlider;	
	private Button mNextQuestion;
	private TeachReachPopulater mTeachReachPopulater;
	char mLetter;
	public static final String ANSWER_STATUS_STRING = "Answer_Status_";
	private Intent mIntent;
	private int mQuizId;
	private Integer[] mOptionPositions;
	private Random generator = new Random(System.currentTimeMillis());


	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		mIntent = new Intent(this, QuizResultsActivity.class);
		//default to 0 - shouldn't be possible
		mQuizId = getIntent().getIntExtra(TeachReachActivity.QUIZ_ID, 0);	
		mTeachReachPopulater = new TeachReachPopulater(getApplicationContext());
		mTeachReachPopulater.openDB();
		mTeachReachPopulater.retrieveQuestionList(mQuizId);
		mQuestions = mTeachReachPopulater.getCurrentQuestions();
		if(mQuestions.size() > 0){
			mTeachReachPopulater.retrieveOptionList(mQuestions.get(0).getId());
			mOptions = mTeachReachPopulater.getCurrentOptions();
			//Set up quiz
			//We start on Question 1
			mQuestionNumber = 1;
			//Set up progress label
			mQuestionProgress = (TextView) findViewById(R.id.question_progress);
			mQuestionProgress.setText( mQuestionNumber + " / " + mQuestions.size());
			mNextQuestion = (Button) findViewById(R.id.next_question);
			mNextQuestion.setOnClickListener(this);

			//Label question with "Question"
			TextView question_title = (TextView) findViewById(R.id.question_title);
			question_title.setText(R.string.question);

			// Actually prepare question
			mLl = (LinearLayout) findViewById(R.id.question_options);
			loadQuestion(mQuestions.get(0));
		}
	}

	@Override
	protected void onStop(){
		super.onStop();
		mTeachReachPopulater.closeDB();
	}

	@Override
	protected void onRestart(){
		super.onRestart();
		mTeachReachPopulater.openDB();
	}

	/**
	 * In order to not show the same order in spinners as they are correct
	 * spinner 1 answer 1, spinner 2 answer two etc. The positions are shuffled
	 * Meaning that the 'correct' position for a given spinner is stored as follows:
	 * mOptionPositions[SpinnerNumber];
	 * This can possibly be optimised further, but for now it works.
	 */
	private void shuffleOptions(){
		int pos;
		mOptionPositions = new Integer[mOptions.size()];
		
		//Initialise shuffle to -1
		for(int i = 0; i < mOptions.size(); i++){
			mOptionPositions[i] = -1;
		}
		
		boolean flag;
		
		for(int i = 0; i < mOptions.size(); i++){
			do{
				pos = generator.nextInt(mOptions.size());
				flag = true;
				for(int j = 0; j < mOptions.size(); j++){
					if(mOptionPositions[j] == pos){
						flag = false;
					}
				}
			}while(!flag);
			mOptionPositions[i] = pos;
//			mOptionPositions[pos-1] = i;
		}
	}

	/**
	 * Adds whether the question answer was correct or not to the intent for the results activity
	 * Answer_status_1 = 'X' means question 1's answer was N/A
	 * Answer_Status_2 = 'C' means question 2's answer was Correct
	 * Answer_Status_1 = 'I' means question 1's answer was Incorrect
	 */
	private void addAnswerToIntent(){
		char value = 'X';
		switch(mQuestions.get(mQuestionNumber-1).getTypeId()){
		case 1:
			//Multiple choice
			RadioGroup rg = (RadioGroup) mLl.getChildAt(0);
			for(int i = 0; i < rg.getChildCount()-1; i++){
				RadioButton rb = (RadioButton) rg.getChildAt(i);
				if(rb.isChecked() && mOptions.get(i).isAnswer()){
					value = 'C';//Correct
				}
			}
			if(value == 'X'){
				value = 'I'; //Incorrect
			}
			break;
		case 2:
			//Fill-in-the-blanks
		case 3:
			//Match up
			//More complex need to check all answers are correct
			for(int i = 0; i < 2*mOptions.size(); i++){
				//if odd
				if((i % 2) > 0){
					Spinner sp = (Spinner) mLl.getChildAt(i);
					// If it's not the correct position at any stage change from X to I
					int x = -1; //Index to lookup based on i
					for(int j = 0; j<mOptions.size(); j++){
						if(mOptionPositions[j] == i/2){
							x = j;
						}
					}
//					Log.i("QuizActivity", "Spinner pos: " + sp.getSelectedItemPosition() + " OptionCorrect: " + x);
					//If index is the spinner position
					if(sp.getSelectedItemPosition() != x){//!= mOptionPositions[i/2]){ //optimised
						value = 'I';
					}
				}
			}
			//If all correct value remains 'X', so change to 'C' to label as correct
			if(value == 'X'){
				value = 'C';
			}
			break;
		case 4:
			//Slider/Opinion
			value = 'X';// X for N/A
			break;
		}
		// question correctness answer
		mIntent.putExtra(ANSWER_STATUS_STRING + mQuestionNumber, value);
	}

	public void loadQuestion(Question q){
		TextView question_text = (TextView) findViewById(R.id.question_text);
		if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("français")){
			question_text.setText(mTeachReachPopulater.getCurrentQuestions().get(mQuestionNumber-1).getFR());
		}
		else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("español")){
			question_text.setText(mTeachReachPopulater.getCurrentQuestions().get(mQuestionNumber-1).getES());
		}
		else{
			question_text.setText(mTeachReachPopulater.getCurrentQuestions().get(mQuestionNumber-1).getEN());
		}

		mTeachReachPopulater.retrieveOptionList(q.getId());
		if(mTeachReachPopulater.getCurrentOptions().size() > 0){
			switch(q.getTypeId()){
			case 1:
				//Multiple choice	
				RadioGroup rg = new RadioGroup(this);
				ArrayList<Option> options = mTeachReachPopulater.getCurrentOptions();
				for(Option option : options){
					RadioButton rb = new RadioButton(this);
					if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("français")){
						rb.setText(option.getFR());
					}
					else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("español")){
						rb.setText(option.getES());
					}
					else{
						rb.setText(option.getEN());
					}
					rg.addView(rb);
					((RadioButton) rg.getChildAt(0)).setChecked(true);
				}
				mLl.addView(rg);
				break;
			case 2:
				shuffleOptions();
				//Fill-in-the-blanks
				mLetter = 'A';
				for(int i = 0; i < mOptions.size(); i++){
					TextView label = new TextView(this);
					label.setText(mLetter+")");
					Spinner spinner = new Spinner(this);
					String[] optns = new String[mOptions.size()];
					for(int j = 0; j < mOptions.size(); j++){
						if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("français")){
							optns[j] = mOptions.get(mOptionPositions[j]).getFR();
						}
						else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("español")){
							optns[j] = mOptions.get(mOptionPositions[j]).getES();
						}
						else{
							optns[j] = mOptions.get(mOptionPositions[j]).getEN();
						}
					}
					ArrayAdapter<String> options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optns);
					options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner.setAdapter(options_adapter);
					spinner.setHorizontalScrollBarEnabled(true);
					mLl.addView(label);
					mLl.addView(spinner);
					mLetter++;
				}
				break;
			case 3:
				shuffleOptions();
				//Match up
				mLetter = 'A';
				for(int i = 0; i < mOptions.size(); i++){
					TextView label = new TextView(this);
					label.setText(mLetter+")");
					Spinner spinner = new Spinner(this);
					String[] optns = new String[mOptions.size()];
					for(int j = 0; j < mOptions.size(); j++){
						if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("français")){
							optns[j] = mOptions.get(mOptionPositions[j]).getFR();
						}
						else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("español")){
							optns[j] = mOptions.get(mOptionPositions[j]).getES();
						}
						else{
							optns[j] = mOptions.get(mOptionPositions[j]).getEN();
						}
					}
					ArrayAdapter<String> options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optns);
					options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner.setAdapter(options_adapter);
					spinner.setHorizontalScrollBarEnabled(true);
					mLl.addView(label);
					mLl.addView(spinner);
					mLetter++;
				}
				break;
			case 4:
				//Slider/Opinion
				mSliderLabel = new TextView(this);
				mSlider = new SeekBar(this);
				mSlider.setProgress(0);
				mSlider.setMax(mOptions.size()-1);
				mSlider.setOnSeekBarChangeListener(this); //Within this change listener set slider_label text to q.getOptions().get(position);
				if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("français")){
					mSliderLabel.setText(mTeachReachPopulater.getCurrentOptions().get(0).getFR());
				}
				else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("español")){
					mSliderLabel.setText(mTeachReachPopulater.getCurrentOptions().get(0).getES());
				}
				else{
					mSliderLabel.setText(mTeachReachPopulater.getCurrentOptions().get(0).getEN());
				}
				mLl.addView(mSliderLabel, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				mLl.addView(mSlider, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
				break;
			}
		}
	}

	public void onClick(View v){
		if(v == findViewById(R.id.next_question)){
			if(mQuestionNumber < mQuestions.size()){
				// Save answer status here
				addAnswerToIntent();
				mQuestionNumber++;
				mQuestionProgress.setText(mQuestionNumber + " / " + mQuestions.size());
				mLl.removeAllViews();
				mTeachReachPopulater.retrieveOptionList(mQuestions.get(mQuestionNumber-1).getId());
				mOptions = mTeachReachPopulater.getCurrentOptions();
				loadQuestion(mQuestions.get(mQuestionNumber-1));
			}else {
				// Load final results screen
				addAnswerToIntent();
				mIntent.putExtra("QUIZ_ID", mQuizId);
				mIntent.putExtra(NUM_QUESTIONS, mQuestions.size());
				startActivity(mIntent);
			}
		}
	}

	/**
	 * Update the label for the progress bar to reflect agreement/opinion strength
	 */
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("français")){
			mSliderLabel.setText(mOptions.get(progress).getFR());
		}
		else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("español")){
			mSliderLabel.setText(mOptions.get(progress).getES());
		}
		else{
			mSliderLabel.setText(mOptions.get(progress).getEN());
		}
	}

	/**
	 * Unused
	 */
	public void onStartTrackingTouch(SeekBar seekBar) {		
	}

	/**
	 * Unused
	 */
	public void onStopTrackingTouch(SeekBar seekBar) {		
	}
}
