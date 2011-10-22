package uk.ac.reading.dp005570.TeachReach;

import java.util.ArrayList;

import uk.ac.reading.dp005570.TeachReach.data.Question;
import uk.ac.reading.dp005570.TeachReach.data.QuestionType;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizActivity extends Activity {
	private Integer questionNumber, number_of_questions;
	private TextView question_progress;
	private ArrayList<Question> quiz;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		quiz = new ArrayList<Question>();
		populateQuiz();

		questionNumber = 1; 
		number_of_questions = quiz.size();

		question_progress = (TextView) findViewById(R.id.question_progress);
		question_progress.setText( questionNumber + " / " + number_of_questions);

		TextView question_title = (TextView) findViewById(R.id.question_title);
		question_title.setText(R.string.question);

		TextView question_text = (TextView) findViewById(R.id.question_text);
		question_text.setText("blab ewa rewa  ewarewar ewa rewahiorpewa hiophfido ewaklcnakm eawocpmecdkmwe mkl pewa");

//		for(Question q : quiz){
//			loadQuestion(q);
//		}


//		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		RadioGroup rg = (RadioGroup) findViewById(R.layout.option_multiple);
//		RadioButton rb = (RadioButton) findViewById(R.layout.option_multiple_item);
//
//		for(String option : quiz.get(0).getOptions()){
//			rb.setText(option);
//			rg.addView(rb);
//		}
		// Like quiz list activity for each option = new radio button in group
		// or spinner control for blanks, 1 for each blank - 1 spinner for each
		// but if slider question, only add slider then - requires special logic based on number of options (on change listener)(setMax)
//		View v = inflater.inflate(R.layout.option_multiple, null);
//		LinearLayout insertPoint = (LinearLayout) findViewById(R.id.question_options);
//		insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

	}

	public void populateQuiz(){
		//TODO Actual population later on
		//TODO Add 1 question of each type
		String questionText = "What colour is the sky?";
		QuestionType type = QuestionType.MULTIPLE_CHOICE;
		ArrayList<String> options = new ArrayList<String>();
		options.add("Red");
		options.add("Green");
		options.add("Blue");
		Boolean[] correctOptions = {false, false, true};
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
