package uk.ac.reading.dp005570.TeachReach;

import uk.ac.reading.dp005570.TeachReach.util.TeachReachPopulater;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	private String[] mMaterials;
//	private final String TAG = "MaterialListActivity";
	private TeachReachPopulater mTeachReachPopulater;
	private int mPartId;
	private ListView mLv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.material_list);
		mTeachReachPopulater = new TeachReachPopulater(getApplicationContext());
		mPartId = getIntent().getIntExtra(TeachReachActivity.PART_ID, 0);
		if(mPartId != 0){
			mTeachReachPopulater.openDB();
			mTeachReachPopulater.retrieveMaterials(mPartId);
			mTeachReachPopulater.closeDB();
			if(mTeachReachPopulater.getCurrentMaterials().size() > 0){
				mMaterials = new String[mTeachReachPopulater.getCurrentMaterials().size()];
				
				for(int i = 0; i < mTeachReachPopulater.getCurrentMaterials().size(); i++){
					mMaterials[i] = getString(R.string.material) + " " + (i+1);
				}
				
				setListAdapter(new ArrayAdapter<String>(this, R.layout.material_item, mMaterials));
				mLv = getListView();
				//Allows user typing to navigate through list bad for backtracing
				//lv.setTextFilterEnabled(true);
				mLv.setOnItemClickListener(this); 
			}
			else{
				Toast.makeText(this.getApplicationContext(), getString(R.string.material_apology), Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	@Override
	protected void onPause(){
		mTeachReachPopulater.closeDB();
		super.onPause();
	}
//	
//	@Override
//	protected void onResume(){
//		mTeachReachPopulater.openDB();
//		super.onResume();
//	}
//	
////	@Override
////	protected void onRestart(){
////		mTeachReachPopulater.openDB();
////		super.onResume();
////	}
//	
	@Override
	protected void onStop(){
		mTeachReachPopulater.closeDB();
		super.onStop();
	}
	
//	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// When a quiz item is clicked
		Intent intent = new Intent(this, MaterialActivity.class);
		intent.putExtra(TeachReachActivity.PART_ID, mPartId);
		intent.putExtra("Material_Content_EN", mTeachReachPopulater.getCurrentMaterials().get(position).getEN());
		intent.putExtra("Material_Content_FR", mTeachReachPopulater.getCurrentMaterials().get(position).getFR());
		intent.putExtra("Material_Content_ES", mTeachReachPopulater.getCurrentMaterials().get(position).getES());
		intent.putExtra("Material_title", mMaterials[position]);
		intent.putExtra(TeachReachActivity.MATERIAL_ID, mTeachReachPopulater.getCurrentMaterials().get(position).getId()); // Add material id
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
			final Handler handler = new Handler(){
				@Override
				public void handleMessage(Message msg){
					refreshList();
				}
			};
			mTeachReachPopulater.openDB();
			Thread thread = new Thread(new Runnable(){
				public void run(){
					if(!mTeachReachPopulater.refreshPart(progress, mPartId)){
						toast.show();
					}
					handler.sendEmptyMessage(0); //TODO move into if statement once working properly
				}
			});
			thread.start();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void refreshList(){
		//TODO fix
		if(mPartId != 0){
			mTeachReachPopulater.openDB();
			mTeachReachPopulater.retrieveMaterials(mPartId);
			mTeachReachPopulater.closeDB();
			if(mTeachReachPopulater.getCurrentMaterials().size() > 0){
				mMaterials = new String[mTeachReachPopulater.getCurrentMaterials().size()];

				for(int i = 0; i < mTeachReachPopulater.getCurrentMaterials().size(); i++){
					mMaterials[i] = getString(R.string.material) + " " + (i+1);
				}
				setListAdapter(new ArrayAdapter<String>(this, R.layout.material_item, mMaterials));
			}
			else{
				Toast.makeText(getApplicationContext(), getString(R.string.material_apology), Toast.LENGTH_LONG).show();
			}

		}
	}
}
