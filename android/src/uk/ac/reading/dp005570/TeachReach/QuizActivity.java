package uk.ac.reading.dp005570.TeachReach;

import java.util.ArrayList;

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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Handles the taking of quizzes and displaying of the questions within the quiz.
 * Accessible through QuizListActivity - so can assume quiz is present in DB.
 * @author Ian Field
 *
 */
public class QuizActivity extends Activity implements OnSeekBarChangeListener, OnClickListener {
	//Store the current number of the question that the user is on
	private Integer mQuestionNumber;
	private Integer mNumberOfQuestions;
	private TextView mQuestionProgress, mSliderLabel;
	private ArrayList<Question> mQuiz;
	private LinearLayout mLl;
	private SeekBar mSlider;	
	private int mNumberOptions;
	private Button mNextQuestion;
	private TeachReachPopulater mTeachReachPopulater;
	char letter;
	public static final String ANSWER_STATUS_STRING = "Answer_Status_";
	private final String NUM_QUESTIONS = "Number_Questions";
	private Intent mIntent; //Launches QuizResultsActivity
	private int mSelectedQuizId;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		mIntent = new Intent(this, QuizResultsActivity.class);
		//default to 0 - shouldn't be possible
		int part_id = getIntent().getIntExtra(TeachReachActivity.PART_ID, 0);
//		Toast.makeText(this.getApplicationContext(), "Selected ID: " + part, Toast.LENGTH_LONG).show(); //TODO Localised message text
		//TODO if made static use that instead.
		mTeachReachPopulater = new TeachReachPopulater(getApplicationContext());
		mTeachReachPopulater.openDB();
		mTeachReachPopulater.retrieveQuizList(part_id);
		
//		int selected_position = getIntent().getIntExtra("Quiz_Position", 0);
//
//		if(mTeachReachPopulater.getCurrentQuizzes().size() > 0){
//			mSelectedQuizId = mTeachReachPopulater.getCurrentQuizzes().get(selected_position).getId(); 
//			mTeachReachPopulater.retrieveQuestionList(mSelectedQuizId);
//		}
		
		//Set up quiz
		populateQuiz();
		mNumberOfQuestions = mQuiz.size();
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
		loadQuestion(mQuiz.get(0));
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
	
	private void addAnswerToIntent(int question, char value){
		mIntent.putExtra(ANSWER_STATUS_STRING + question, value);
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
			options = new Spinner(this);
			//Load options ready for spinner
			options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, q.getOptions());
			//Put options into spinner drop down
			options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			options.setAdapter(options_adapter);
			options.setHorizontalScrollBarEnabled(true);
			//TODO add OnItemSelectedListener
			//Load spinner at the location of R.id.question_options
			mLl.addView(options);
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
			letter = '1';
			for(int i = 0; i < mNumberOptions; i++){
				TextView label = new TextView(this);
				label.setText(letter+")");
				options = new Spinner(this);
				options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, q.getOptions());
				options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				options.setAdapter(options_adapter);
				mLl.addView(label);
				mLl.addView(options);
				letter++;
			}
			break;
		case BLANKS:
			mNumberOptions = q.getOptions().size();
			letter = 'A';
			for(int i = 0; i < mNumberOptions; i++){
				TextView label = new TextView(this);
				label.setText("*|"+letter+"|*)");
				options = new Spinner(this);
				options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, q.getOptions());
				options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				options.setAdapter(options_adapter);
				options.setHorizontalScrollBarEnabled(true);

				mLl.addView(label);
				mLl.addView(options);
				letter++;
			}
			break;
		case MATCH_UP:
			//Essentially the same rendering
			//Spinner control for each option.
			mNumberOptions = q.getOptions().size();
			letter = 'A';
			for(int i = 0; i < mNumberOptions; i++){
				TextView label = new TextView(this);
				label.setText(letter+")");
				options = new Spinner(this);
				options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, q.getOptions());
				options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Can customise
				options.setAdapter(options_adapter);
				options.setScrollContainer(true);
				mLl.addView(label);
				mLl.addView(options);
				letter++;
			}
			break;
		}

	}

	public void populateQuiz(){
		mQuiz = new ArrayList<Question>();
		//TODO Actual population later on - also check quesiton has options here - then display nothing
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
		mQuiz.add(q);
		
		questionText = "Is a leader’s main responsibility to overcome the conflicts and challenges " +
				"that arise during the course of a normal day, project etc.";
		options = new ArrayList<String>();
		options.add("True");
		options.add("False");
		correctOptions = new Boolean[]{ true, false};
		type = Question.QuestionType.MULTIPLE_CHOICE;
		q = new Question(questionText, type, options, correctOptions);
		mQuiz.add(q);
		
		questionText = "A bad leader is someone who always makes the decisions as they know best";
		options = new ArrayList<String>();
		options.add("True");
		options.add("False");
		correctOptions = new Boolean[]{ false, true };
		type = Question.QuestionType.MULTIPLE_CHOICE;
		q = new Question(questionText, type, options, correctOptions);
		mQuiz.add(q);
		
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
		mQuiz.add(q);
		
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
		mQuiz.add(q);
		
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
		mQuiz.add(q);
	}
	
	public void onClick(View v){
		if(v == findViewById(R.id.next_question)){
			if(mQuestionNumber < mNumberOfQuestions){
				mQuestionNumber++;
				mQuestionProgress.setText(mQuestionNumber + " / " + mNumberOfQuestions);
				mLl.removeAllViews();
				loadQuestion(mQuiz.get(mQuestionNumber-1));
			}else {
				// Load final results screen
//				Intent intent = new Intent(this, QuizResultsActivity.class);
//				startActivity(intent);
				mIntent.putExtra(NUM_QUESTIONS, mNumberOfQuestions);
				startActivity(mIntent);
			}
		}
	}

//	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		Question current_question = mQuiz.get(mQuestionNumber-1);
		mSliderLabel.setText(current_question.getOptions().get(progress));
	}

//	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {		
	}

//	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {		
	}
}
