package uk.ac.reading.dp005570.TeachReach;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizListActivity extends ListActivity {
	
	private ArrayList<QuizStatus> m_quizzes = null;
	private QuizItemAdapter m_adapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_list);
        
        m_quizzes = new ArrayList<QuizStatus>();
        
        // TODO ProgressDialog here while retreiving from online if chosen
        populateQuizList();
        
        this.m_adapter = new QuizItemAdapter(this, R.layout.quiz_item, m_quizzes);
        setListAdapter(m_adapter);
	}
	
	
	private void populateQuizList() {
		// TODO Future implementation would be here
        m_quizzes.add(new QuizStatus("Test Quiz 1", false));
        m_quizzes.add(new QuizStatus("Test Quiz 2", true));		
	}


	private class QuizItemAdapter extends ArrayAdapter<QuizStatus>{
		private ArrayList<QuizStatus> items;
        
		public QuizItemAdapter(Context context, int textViewResourceId, ArrayList<QuizStatus> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }
		
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.quiz_item, null);
                }
                QuizStatus q = items.get(position);
                if (q != null) {
                	TextView title = (TextView) v.findViewById(R.id.quiz_title);
                	if(title != null){
                		title.setText(q.getName());
                	}
                	
                	ImageView icon = (ImageView) v.findViewById(R.id.quiz_status);
                	// TODO tick and cross images instead
                	if(q.isTaken()){
                		icon.setImageResource(android.R.drawable.ic_menu_agenda);
                	}
                	else{
                		icon.setImageResource(android.R.drawable.ic_menu_edit);
                	}
                }
                return v;
        }
	}
	
}
