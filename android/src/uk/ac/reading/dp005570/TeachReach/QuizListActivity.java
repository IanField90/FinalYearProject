package uk.ac.reading.dp005570.TeachReach;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class QuizListActivity extends ListActivity implements OnItemClickListener{
	
//	private ArrayList<QuizStatus> mQuizzes = null;
	private String[] mQuizzes;
//	private QuizItemAdapter mAdapter;
//	private TeachReachPopulater mTeachReachPopulater;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mTeachReachPopulater = new TeachReachPopulater(getApplicationContext());
        
//        mQuizzes = mTeachReachPopulater.getCurrentQuizzes();
        setContentView(R.layout.quiz_list);
        getListView().setTextFilterEnabled(false);
        getListView().setOnItemClickListener(this);
//        mQuizzes = new ArrayList<QuizStatus>();
//		int part = getIntent().getIntExtra("Part", 0);
//		mTeachReachPopulater.get
//		getIntent().getExtras().getSerializable(key)
		
//		Toast.makeText(this.getApplicationContext(), "Selected ID: " + part, Toast.LENGTH_LONG).show(); //TODO Localised message text

        
        // TODO ProgressDialog here while retrieving from online if chosen
        populateQuizList();
        
//        this.mAdapter = new QuizItemAdapter(this, R.layout.quiz_item, mQuizzes);
//        setListAdapter(mAdapter);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.quiz_item, mQuizzes)); //TODO Change quiz_item to be suitable to put here

	}
	
	
	private void populateQuizList() {
		// TODO Future implementation would be here
//        mQuizzes.add(new QuizStatus("Test Quiz 1", false));
//        mQuizzes.add(new QuizStatus("Test Quiz 2", true));	
		mQuizzes = new String[]{ "Test Quiz 1", "Test Quiz 2" };
	}
//
//	/**
//	 * 
//	 * @author Ian Field
//	 * Class to handle custom list item display
//	 */
//	private class QuizItemAdapter extends ArrayAdapter<QuizStatus>{
//		private ArrayList<QuizStatus> items;
//        
//		public QuizItemAdapter(Context context, int textViewResourceId, ArrayList<QuizStatus> items) {
//            super(context, textViewResourceId, items);
//            this.items = items;
//        }
//		
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//                View v = convertView;
//                if (v == null) {
//                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    v = vi.inflate(R.layout.quiz_item, null);
//                }
//                QuizStatus q = items.get(position);
//                if (q != null) {
//                	TextView title = (TextView) v.findViewById(R.id.quiz_title);
//                	if(title != null){
//                		title.setText(q.getName());
//                	}
//                	
//                	ImageView icon = (ImageView) v.findViewById(R.id.quiz_status);
//                	// TODO tick and cross images instead
//                	if(q.isTaken()){
//                		icon.setImageResource(android.R.drawable.ic_menu_agenda);
//                	}
//                	else{
//                		icon.setImageResource(android.R.drawable.ic_menu_edit);
//                	}
//                }
//                return v;
//        }
//	}


//	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// When a quiz item is clicked
		Intent intent = new Intent(this, QuizActivity.class);
		//TODO intent.setData once content provider is set up
		startActivity(intent);
	}
	
}
