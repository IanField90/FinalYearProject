package uk.ac.reading.dp005570.TeachReach;

import java.util.ArrayList;
import java.util.Locale;

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
	private Integer mNumberOfQuestions;
	private TextView mQuestionProgress;
	private TextView mSliderLabel;
	private ArrayList<Question> mQuestions;	
	private ArrayList<Option> mOptions;
	private LinearLayout mLl;
	private SeekBar mSlider;	
	private int mNumberOptions;
	private Button mNextQuestion;
	private TeachReachPopulater mTeachReachPopulater;
	char mLetter;
	public static final String ANSWER_STATUS_STRING = "Answer_Status_";
	private Intent mIntent;
	private int mQuizId;

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
			
		}
		//Set up quiz
		populateQuiz(); //TODO remove this once implementation is properly complete (DB retrieval issue ATM)
		mNumberOfQuestions = mQuestions.size();
		//We start on Question 1
		mQuestionNumber = 1;

		//Set up progress label
		mQuestionProgress = (TextView) findViewById(R.id.question_progress);
		mQuestionProgress.setText( mQuestionNumber + " / " + mNumberOfQuestions);

		mNextQuestion = (Button) findViewById(R.id.next_question);
		mNextQuestion.setOnClickListener(this);

		//Label question with "Question"
		TextView question_title = (TextView) findViewById(R.id.question_title);
		question_title.setText(R.string.question);

		// Actually prepare question
		mLl = (LinearLayout) findViewById(R.id.question_options);
//		loadQuestion(mQuestions.get(0));
		loadQuestion2(mQuestions.get(0));
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
			//More complex need to check all answers are correct

			break;
		case 3:
			//Match up
			//More complex need to check all answers are correct
			break;
		case 4:
			//Slider/Opinion
			value = 'N';// N for N/A
			break;
		}
		//TODO question correctness logic here
		mIntent.putExtra(ANSWER_STATUS_STRING + mQuestionNumber, value);
	}

	public void loadQuestion(Question q){
		//Question text will not be different whatever type the question it is
		TextView question_text = (TextView) findViewById(R.id.question_text);
		question_text.setText(q.getQuestionText());
		ArrayAdapter<String> options_adapter;
		Spinner options;
		switch(q.getType()){
		case MULTIPLE_CHOICE:
			//Get options
			//			options = new Spinner(this);
			//			//Load options ready for spinner
			//			options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, q.getOptions());
			//			//Put options into spinner drop down
			//			options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			//			options.setAdapter(options_adapter);
			//			options.setHorizontalScrollBarEnabled(true);
			//			//TODO add OnItemSelectedListener
			//			//Load spinner at the location of R.id.question_options
			//			
			//			mLl.addView(options);

			RadioGroup rg = new RadioGroup(this);
			for(String option : q.getOptions()){
				RadioButton rb = new RadioButton(this);
				rb.setText(option);
				rg.addView(rb);
			}
			((RadioButton)rg.getChildAt(0)).setChecked(true);
			mLl.addView(rg);
			break;
		case SLIDER:
			mSliderLabel = new TextView(this);
			mSlider = new SeekBar(this);
			mSlider.setProgress(0);
			mSlider.setMax(q.getOptions().size()-1);
			mSlider.setOnSeekBarChangeListener(this); //Within this change listener set slider_label text to q.getOptions().get(position);
			mSliderLabel.setText(q.getOptions().get(0));
			mLl.addView(mSliderLabel, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			mLl.addView(mSlider, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
			break;
		case ORDERING:
			mNumberOptions = q.getOptions().size();
			mLetter = '1';
			for(int i = 0; i < mNumberOptions; i++){
				TextView label = new TextView(this);
				label.setText(mLetter+")");
				options = new Spinner(this);
				options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, q.getOptions());
				options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				options.setAdapter(options_adapter);
				mLl.addView(label);
				mLl.addView(options);
				mLetter++;
			}
			break;
		case BLANKS:
			mNumberOptions = q.getOptions().size();
			mLetter = 'A';
			for(int i = 0; i < mNumberOptions; i++){
				TextView label = new TextView(this);
				label.setText("*|"+mLetter+"|*)");
				options = new Spinner(this);
				options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, q.getOptions());
				options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				options.setAdapter(options_adapter);
				options.setHorizontalScrollBarEnabled(true);

				mLl.addView(label);
				mLl.addView(options);
				mLetter++;
			}
			break;
		case MATCH_UP:
			//Essentially the same rendering
			//Spinner control for each option.
			mNumberOptions = q.getOptions().size();
			mLetter = 'A';
			for(int i = 0; i < mNumberOptions; i++){
				TextView label = new TextView(this);
				label.setText(mLetter+")");
				options = new Spinner(this);
				options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, q.getOptions());
				options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Can customise
				options.setAdapter(options_adapter);
				options.setScrollContainer(true);
				mLl.addView(label);
				mLl.addView(options);
				mLetter++;
			}
			break;
		}

	}

	public void loadQuestion2(Question q){
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
			}
			((RadioButton) rg.getChildAt(0)).setChecked(true);
			mLl.addView(rg);
			break;
		case 2:
			//Fill-in-the-blanks
			mLetter = 'A';
			for(int i = 0; i < mNumberOptions; i++){
				TextView label = new TextView(this);
				label.setText(mLetter+")");
				Spinner spinner = new Spinner(this);
				String[] optns = new String[mOptions.size()];
				for(int j = 0; j < mOptions.size(); j++){
					if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("français")){
						optns[j] = mOptions.get(j).getFR();
					}
					else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("español")){
						optns[j] = mOptions.get(j).getES();
					}
					else{
						optns[j] = mOptions.get(j).getEN();
					}
				}
				ArrayAdapter<String> options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optns);
				options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(options_adapter);
				spinner.setHorizontalScrollBarEnabled(true);
				mLl.addView(label);
				mLl.addView(spinner);
			}
			break;
		case 3:
			//Match up
			mLetter = 'A';
			for(int i = 0; i < mNumberOptions; i++){
				TextView label = new TextView(this);
				label.setText(mLetter+")");
				Spinner spinner = new Spinner(this);
				String[] optns = new String[mOptions.size()];
				for(int j = 0; j < mOptions.size(); j++){
					if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("français")){
						optns[j] = mOptions.get(j).getFR();
					}
					else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("español")){
						optns[j] = mOptions.get(j).getES();
					}
					else{
						optns[j] = mOptions.get(j).getEN();
					}
				}
				ArrayAdapter<String> options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optns);
				options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(options_adapter);
				spinner.setHorizontalScrollBarEnabled(true);
				mLl.addView(label);
				mLl.addView(spinner);
			}
			break;
		case 4:
			//Slider/Opinion
			mSliderLabel = new TextView(this);
			mSlider = new SeekBar(this);
			mSlider.setProgress(0);
			mSlider.setMax(q.getOptions().size()-1);
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


	/**
	 * 
	 */
	public void populateQuiz(){
		mQuestions = new ArrayList<Question>();
		//TODO Actual population later on - also check question has options here - then display nothing
		String questionText = "One thing a good leader should do is:" +
				"\n\nA) Tell others what should be done" +
				"\n\nB) Allow free exchange of ideas and support decision making" +
				"\n\nC) Not interfere with the group as they have specialist areas";
		Question.QuestionType type = Question.QuestionType.MULTIPLE_CHOICE;
		ArrayList<String> options = new ArrayList<String>();
		options.add("A");
		options.add("B");
		options.add("C");
		Boolean[] correctOptions = new Boolean[]{false, false, true};
		Question q = new Question(questionText, type, options, correctOptions);
		mQuestions.add(q);

		questionText = "Is a leader’s main responsibility to overcome the conflicts and challenges " +
				"that arise during the course of a normal day, project etc.";
		options = new ArrayList<String>();
		options.add("True");
		options.add("False");
		correctOptions = new Boolean[]{ true, false};
		type = Question.QuestionType.MULTIPLE_CHOICE;
		q = new Question(questionText, type, options, correctOptions);
		mQuestions.add(q);

		questionText = "A bad leader is someone who always makes the decisions as they know best";
		options = new ArrayList<String>();
		options.add("True");
		options.add("False");
		correctOptions = new Boolean[]{ false, true };
		type = Question.QuestionType.MULTIPLE_CHOICE;
		q = new Question(questionText, type, options, correctOptions);
		mQuestions.add(q);

		questionText = "Order these qualities into the order that you feel are the most important for a leader to possess.\n\n" +
				"A) Time management\n" + 
				"B) Delegation\n" + 
				"C) Authority\n" + 
				"D) Communication\n" + 
				"E) Patience\n";
		options = new ArrayList<String>();
		options.add("A");
		options.add("B");
		options.add("C");
		options.add("D");
		options.add("E");
		correctOptions = new Boolean[]{ true, true, true, true, true }; //TODO Better representation
		type = Question.QuestionType.ORDERING;
		q = new Question(questionText, type, options, correctOptions);
		mQuestions.add(q);

		questionText = "Please match these up below:\n\n" +
				"A) A reflector learns by\n" + 
				"B) A theorist learns by\n" + 
				"C) A Pragmatist learns by\n" + 
				"D) An Activist learns by\n";
		options = new ArrayList<String>();
		options.add("Observing and reflecting");
		options.add("Understanding the reasons behind it");
		options.add("Active experimentation 'having a go'");
		options.add("Doing and experimenting");
		correctOptions = new Boolean[] { true, true, true, true }; //TODO Better representation
		type = Question.QuestionType.MATCH_UP;
		q = new Question(questionText, type, options, correctOptions);
		mQuestions.add(q);

		questionText = "How likely?";
		options = new ArrayList<String>();
		options.add("Very unlikely");
		options.add("Unlikely");
		options.add("Unsure");
		options.add("Likely");
		options.add("Very likely");
		correctOptions = null; //Not applicable for this datatype
		type = Question.QuestionType.SLIDER;
		q = new Question(questionText, type, options, correctOptions);
		mQuestions.add(q);
	}

	public void onClick(View v){
		if(v == findViewById(R.id.next_question)){
			if(mQuestionNumber < mNumberOfQuestions){
				// Save answer status here
				addAnswerToIntent();
				mQuestionNumber++;
				mQuestionProgress.setText(mQuestionNumber + " / " + mNumberOfQuestions);
				mLl.removeAllViews();
				loadQuestion(mQuestions.get(mQuestionNumber-1));
			}else {
				// Load final results screen
				addAnswerToIntent();
				mIntent.putExtra("QUIZ_ID", mQuizId);
				mIntent.putExtra(NUM_QUESTIONS, mNumberOfQuestions);
				startActivity(mIntent);
			}
		}
	}

	/**
	 * Update the label for the progress bar to reflect agreement/opinion strength
	 */
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		Question current_question = mQuestions.get(mQuestionNumber-1);
		mSliderLabel.setText(current_question.getOptions().get(progress));


		//		if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("français")){
		//			mSliderLabel.setText(mTeachReachPopulater.getCurrentOptions().get(progress).getFR());
		//		}
		//		else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("español")){
		//			mSliderLabel.setText(mTeachReachPopulater.getCurrentOptions().get(progress).getES());
		//		}
		//		else{
		//			mSliderLabel.setText(mTeachReachPopulater.getCurrentOptions().get(progress).getEN());
		//		}
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
