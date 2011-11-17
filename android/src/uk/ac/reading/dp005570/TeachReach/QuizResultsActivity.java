package uk.ac.reading.dp005570.TeachReach;

import java.util.ArrayList;

import uk.ac.reading.dp005570.TeachReach.data.Answer;
import uk.ac.reading.dp005570.TeachReach.data.AnswerStatus;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizResultsActivity extends ListActivity{
	
	private QuestionItemAdapter m_adapter;
	private ArrayList<Answer> m_questions;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_results);
		
		m_questions = new ArrayList<Answer>();
		m_questions.add(new Answer(1, AnswerStatus.CORRECT));
		m_questions.add(new Answer(2, AnswerStatus.INCORRECT));
		m_questions.add(new Answer(2, AnswerStatus.NOT_APPLICABLE));
		
        this.m_adapter = new QuestionItemAdapter(this, R.layout.results_question_item, m_questions);
        setListAdapter(m_adapter);
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

}
