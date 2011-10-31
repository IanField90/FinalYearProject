package uk.ac.reading.dp005570.TeachReach;

import java.util.ArrayList;

import uk.ac.reading.dp005570.TeachReach.data.Question;
import uk.ac.reading.dp005570.TeachReach.data.QuestionType;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class QuizActivity extends Activity implements OnSeekBarChangeListener {
	private Integer question_number, number_of_questions;
	private TextView question_progress, slider_label;
	private ArrayList<Question> quiz;
	private LinearLayout ll;
	private SeekBar slider;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		//Set up quiz
		populateQuiz();
		number_of_questions = quiz.size();
		//We start on Question 1
		question_number = 1;

		//Set up progress label
		question_progress = (TextView) findViewById(R.id.question_progress);
		question_progress.setText( question_number + " / " + number_of_questions);

		//Label question with "Question"
		TextView question_title = (TextView) findViewById(R.id.question_title);
		question_title.setText(R.string.question);

		// Actually prepare question
		ll = (LinearLayout) findViewById(R.id.question_options);
		loadQuestion(quiz.get(0));
		
	}

	public void loadQuestion(Question q){
		//Question text will not be different whatever type the question it is
		TextView question_text = (TextView) findViewById(R.id.question_text);
		question_text.setText(q.getQuestionText());

		switch(q.getType()){
		case MULTIPLE_CHOICE:
			//Get options
			ArrayList<String> options_list = q.getOptions();
			Spinner options = new Spinner(this);
			//Load options ready for spinner
			ArrayAdapter<String> options_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options_list);
			//Put options into spinner drop down
			options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			options.setAdapter(options_adapter);
			//TODO add OnItemSelectedListener
			//Load spinner at the location of R.id.question_options
			ll.addView(options);
			break;
		case SLIDER:
			//TODO Hashmap slider value + label value
			//TODO add slider value change listener to update label
			slider_label = new TextView(this);
			slider = new SeekBar(this);
			slider.setProgress(0);
			slider.setMax(q.getOptions().size()-1);
			slider.setOnSeekBarChangeListener(this); //Within this change listener set slider_label text to q.getOptions().get(position);
			slider_label.setText(q.getOptions().get(0));
			ll.addView(slider_label, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			ll.addView(slider, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
			break;
		case BLANKS:
		case ORDERING:
		case MATCH_UP:
			//Essentially the same rendering
			//Spinner control for each option.
			
			break;
		}

	}

	public void populateQuiz(){
		quiz = new ArrayList<Question>();
		//TODO Actual population later on
		//TODO Add blanks and slider(seekbar) question
		String questionText = "One thing a good leader should do is:" +
				"\n\nA) Tell others what should be done" +
				"\n\nB) Allow free exchange of ideas and support decision making" +
				"\n\nC) Not interfere with the group as they have specialist areas";
		QuestionType type = QuestionType.MULTIPLE_CHOICE;
		ArrayList<String> options = new ArrayList<String>();
		options.add("A");
		options.add("B");
		options.add("C");
		Boolean[] correctOptions = new Boolean[]{false, false, true};
		Question q = new Question(questionText, type, options, correctOptions);
		quiz.add(q);
		
		questionText = "Is a leader’s main responsibility to overcome the conflicts and challenges " +
				"that arise during the course of a normal day, project etc.";
		options = new ArrayList<String>();
		options.add("True");
		options.add("False");
		correctOptions = new Boolean[]{ true, false};
		type = QuestionType.MULTIPLE_CHOICE;
		q = new Question(questionText, type, options, correctOptions);
		quiz.add(q);
		
		questionText = "A bad leader is someone who always makes the decisions as they know best";
		options = new ArrayList<String>();
		options.add("True");
		options.add("False");
		correctOptions = new Boolean[]{ false, true };
		type = QuestionType.MULTIPLE_CHOICE;
		q = new Question(questionText, type, options, correctOptions);
		quiz.add(q);
		
//		questionText = "Order these qualities into the order that you feel are the most important for a leader to possess.\n\n" +
//				"A) Time management\n" + 
//				"B) Delegation\n" + 
//				"C) Authority\n" + 
//				"D) Communication\n" + 
//				"E) Patience\n";
//		options = new ArrayList<String>();
//		options.add("A");
//		options.add("B");
//		options.add("C");
//		options.add("D");
//		options.add("E");
//		correctOptions = new Boolean[]{ true, true, true, true, true }; //TODO Better representation
//		type = QuestionType.ORDERING;
//		q = new Question(questionText, type, options, correctOptions);
//		quiz.add(q);
//		
//		questionText = "Please match these up below:\n\n" +
//		"A) A reflector learns by\n" + 
//		"B) A theorist learns by\n" + 
//		"C) A Pragmatist learns by\n" + 
//		"D) An Activist learns by\n";
//		options = new ArrayList<String>();
//		options.add("Observing and reflecting");
//		options.add("Understanding the reasons behind it");
//		options.add("Active experimentation 'having a go'");
//		options.add("Doing and experimenting");
//		correctOptions = new Boolean[] { true, true, true, true }; //TODO Better representation
//		type = QuestionType.MATCH_UP;
//		q = new Question(questionText, type, options, correctOptions);
//		quiz.add(q);
//		
		questionText = "How likely?";
		options = new ArrayList<String>();
		options.add("Very unlikely");
		options.add("Unlikely");
		options.add("Unsure");
		options.add("Likely");
		options.add("Very likely");
		correctOptions = null; //Not applicable for this datatype
		type = QuestionType.SLIDER;
		q = new Question(questionText, type, options, correctOptions);
		quiz.add(q);
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
			if(question_number > 1){
				question_number--;
				question_progress.setText(question_number + " / " + number_of_questions);
				ll.removeAllViews();
				loadQuestion(quiz.get(question_number-1));
				return true;
			}
			else{
				return false;
			}
		case R.id.next:
			if(question_number < number_of_questions){
				question_number++;
				question_progress.setText(question_number + " / " + number_of_questions);
				ll.removeAllViews();
				loadQuestion(quiz.get(question_number-1));
				return true;
			}else {
				//TODO Load final results screen
				Intent intent = new Intent(this, QuizResultsActivity.class);
				startActivity(intent);
				return false;
			}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		Question current_question = quiz.get(question_number-1);
		slider_label.setText(current_question.getOptions().get(progress));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}
