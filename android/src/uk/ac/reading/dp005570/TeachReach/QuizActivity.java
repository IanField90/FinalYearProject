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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class QuizActivity extends Activity {
	private Integer quesiton_number, number_of_questions;
	private TextView question_progress;
	private ArrayList<Question> quiz;
	private LinearLayout ll;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		//Set up quiz
		populateQuiz();
		number_of_questions = quiz.size();
		//We start on Question 1
		quesiton_number = 1;

		//Set up progress label
		question_progress = (TextView) findViewById(R.id.question_progress);
		question_progress.setText( quesiton_number + " / " + number_of_questions);

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
		case BLANKS:
			//TODO 
			break;
		case SLIDER:
			//TODO Hashmap slider value + label value
			//TODO add slider value change listener to update label
//			LinearLayout option_slider = (LinearLayout) findViewById(R.layout.option_slider);
//			TextView slider_value = (TextView) findViewById(R.id.slider_value);
//			SeekBar bar = (SeekBar) findViewById(R.id.slider);			
			break;
		}

	}

	public void populateQuiz(){
		quiz = new ArrayList<Question>();
		//TODO Actual population later on
		//TODO Add blanks and slider(seekbar) question
		String questionText = "What colour is the sky?";
		QuestionType type = QuestionType.MULTIPLE_CHOICE;
		ArrayList<String> options = new ArrayList<String>();
		options.add("Red");
		options.add("Green");
		options.add("Blue");
		Boolean[] correctOptions = new Boolean[]{false, false, true};
		Question q = new Question(questionText, type, options, correctOptions);
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
			if(quesiton_number > 1){
				quesiton_number--;
				question_progress.setText(quesiton_number + " / " + number_of_questions);
				return true;
			}
			else{
				return false;
			}
		case R.id.next:
			if(quesiton_number < number_of_questions){
				quesiton_number++;
				question_progress.setText(quesiton_number + " / " + number_of_questions);
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
}
