package uk.ac.reading.dp005570.TeachReach;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 
 * @author Ian Field
 * Displays the material - very basic text-only materials at this stage.
 *
 */
public class MaterialActivity extends Activity{
	private String mTitle;
	private String mBody;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.material);
		
		TextView title = (TextView) findViewById(R.id.material_title);
		if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("français")){
			mBody = getIntent().getExtras().getString("Material_Content_FR");
		}
		else if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("español")){
			mBody = getIntent().getExtras().getString("Material_Content_ES");
		}
		else{
			mBody = getIntent().getExtras().getString("Material_Content_EN");
		}
		
		mTitle = getIntent().getExtras().getString("Material_title");
		title.setText(mTitle);
		
		TextView body = (TextView) findViewById(R.id.material_body);
		body.setText(mBody);
//		body.setText("A leader always allows his team members to speak " +
//				"up and share their ideas and then support them in decision " +
//				"making to ensure that end goals are met. It is important that " +
//				"a leader does not dictate to others but it is equally important " +
//				"to spend time communicating. \n\nCommunication skills and willingness " +
//				"and ability to coach and mentor others are essential characteristics " +
//				"of a successful leader.");
	}
}
