package uk.ac.reading.dp005570.TeachReach;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizResultsActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_results);
	}

	
	/**
	 * 
	 * @author ianfield
	 * Class to handle custom list item display
	 */
	private class QuestionItemAdapter extends ArrayAdapter<QuizStatus>{
		
		//TODO Customise copy&paste
		private ArrayList<QuizStatus> items;
        
		public QuestionItemAdapter(Context context, int textViewResourceId, ArrayList<QuizStatus> items) {
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
