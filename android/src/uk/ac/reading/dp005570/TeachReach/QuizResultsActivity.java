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

public class QuizResultsActivity extends ListActivity implements OnItemClickListener{
	
	private QuestionItemAdapter mAdapter;
	private ArrayList<Answer> mQuestions;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_results);
		
		mQuestions = new ArrayList<Answer>();
		mQuestions.add(new Answer(1, AnswerStatus.CORRECT));
		mQuestions.add(new Answer(2, AnswerStatus.INCORRECT));
		mQuestions.add(new Answer(2, AnswerStatus.NOT_APPLICABLE));
		
        this.mAdapter = new QuestionItemAdapter(this, R.layout.results_question_item, mQuestions);
        setListAdapter(mAdapter);
        
        getListView().setOnItemClickListener(this);
	}

	
	/**
	 * 
	 * @author ianfield
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
                		icon.setImageResource(android.R.drawable.ic_menu_agenda);
                	}
                	else if (a.getStatus() == AnswerStatus.INCORRECT){
                		//Display cross
                		icon.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                	}
                	else{
                		//Not applicable
                		icon.setImageResource(android.R.drawable.ic_menu_sort_by_size);
                	}
                }
                return v;
        }
	}


	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, FeedbackActivity.class);
		startActivity(i);
	}

}
