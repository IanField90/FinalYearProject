package uk.ac.reading.dp005570.TeachReach;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MaterialListActivity extends ListActivity implements OnItemClickListener {
	static final String[] materials = new String[] { "Introduction", "Material 2" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.material_list);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.material_item, materials));
		ListView lv = getListView();
		//Allows user typing to navigate through list bad for backtracing
		//lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(this);
//		
//		lv.setOnItemClickListener(new OnItemClickListener(){
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Toast.makeText(getApplicationContext(),	((TextView) view).getText() + " selected index: " + position, 
//						Toast.LENGTH_SHORT).show();
//			}
//		});
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// When a quiz item is clicked
		Intent intent = new Intent(this, MaterialActivity.class);
		//TODO intent.setData once content provider is set up
		startActivity(intent);
	}
}
