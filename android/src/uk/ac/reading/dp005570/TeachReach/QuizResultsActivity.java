package uk.ac.reading.dp005570.TeachReach;

import java.util.ArrayList;

import uk.ac.reading.dp005570.TeachReach.data.Answer;
import uk.ac.reading.dp005570.TeachReach.data.AnswerStatus;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Allows the user to see the results of their quiz,
 * then optionally access feedback for an individual question
 * @author Ian Field
 *
 */
public class QuizResultsActivity extends ListActivity implements OnItemClickListener{
	private QuestionItemAdapter mAdapter;
	private ArrayList<Answer> mAnswers;
	private int mNumQuestions;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_results);

		mNumQuestions = getIntent().getIntExtra("Number_Questions", 0);
		mAnswers = new ArrayList<Answer>();
		for(int i = 1; i <= mNumQuestions; i++){
			switch(getIntent().getCharExtra("Answer_Status_" + i, 'X')){
			case 'X':
				mAnswers.add(new Answer(1, AnswerStatus.NOT_APPLICABLE));
				break;
			case 'C':
				mAnswers.add(new Answer(1, AnswerStatus.CORRECT));
				break;
			case 'I':
				mAnswers.add(new Answer(1, AnswerStatus.INCORRECT));
				break;
			}
			
		}

		this.mAdapter = new QuestionItemAdapter(this, R.layout.results_question_item, mAnswers);
		setListAdapter(mAdapter);

		getListView().setOnItemClickListener(this);
	}


	/**
	 * 
	 * @author Ian Field
	 * Class to handle custom list item display
	 */
	private class QuestionItemAdapter extends ArrayAdapter<Answer>{
		private ArrayList<Answer> items;

		public QuestionItemAdapter(Context context, int textViewResourceId, ArrayList<Answer> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.results_question_item, null);
			}
			Answer a = items.get(position);
			if (a != null) {
				TextView question_name = (TextView) v.findViewById(R.id.question_name);
				if(question_name != null){
					question_name.setText("Question " + (position+1));

				}

				ImageView icon = (ImageView) v.findViewById(R.id.question_status);
				// Display tick
				if(a.getStatus() == AnswerStatus.CORRECT){
					icon.setImageResource(R.drawable.tick);
				}
				else if (a.getStatus() == AnswerStatus.INCORRECT){
					//Display cross
					icon.setImageResource(R.drawable.cross);

				}
				else{
					//Not applicable
					icon.setImageResource(R.drawable.n_a);
				}
			}
			return v;
		}
	}


	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent i = new Intent(this, FeedbackActivity.class);
		startActivity(i);
	}

}
