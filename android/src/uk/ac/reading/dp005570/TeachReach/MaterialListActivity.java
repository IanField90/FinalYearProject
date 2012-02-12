package uk.ac.reading.dp005570.TeachReach;

import uk.ac.reading.dp005570.TeachReach.util.TeachReachPopulater;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Display a list of materials for a part to the user
 * @author Ian Field
 *
 */
public class MaterialListActivity extends ListActivity implements OnItemClickListener {
	static final String[] materials = new String[] { "Introduction", "Material 2" };
	private TeachReachPopulater mTeachReachPopulater;
	private int mPartId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.material_list);
		mTeachReachPopulater = new TeachReachPopulater(getApplicationContext());
		//TODO handle more lifecycle events for DB.
		
//		mPartId = getIntent().getIntExtra("PART_ID", 0);// TODO handle properly
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.material_item, materials));
		ListView lv = getListView();
		//Allows user typing to navigate through list bad for backtracing
		//lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(this); 
		
	}

//	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// When a quiz item is clicked
		Intent intent = new Intent(this, MaterialActivity.class);
		//TODO intent.setData once content provider is set up
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refresh_lists:
			// utilise TeachReachPopulator
			String wait = getString(R.string.please_wait);
			String retrieve = getString(R.string.server_retrieval);
			final ProgressDialog progress = ProgressDialog.show(MaterialListActivity.this, wait, retrieve);
			final Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.server_error), Toast.LENGTH_LONG);
			
			Thread thread = new Thread(new Runnable(){
				public void run(){
					if(!mTeachReachPopulater.refreshPart(progress, mPartId)){
						toast.show();
					}
				}
			});
			thread.start();			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
