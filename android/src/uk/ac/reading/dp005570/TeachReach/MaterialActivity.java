package uk.ac.reading.dp005570.TeachReach;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MaterialActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.material);
		
		TextView title = (TextView) findViewById(R.id.material_title);
		title.setText("Material 1");
		
		TextView body = (TextView) findViewById(R.id.material_body);
		body.setText("A leader always allows his team members to speak " +
				"up and share their ideas and then support them in decision " +
				"making to ensure that end goals are met. It is important that " +
				"a leader does not dictate to others but it is equally important " +
				"to spend time communicating. \n\nCommunication skills and willingness " +
				"and ability to coach and mentor others are essential characteristics " +
				"of a successful leader.");
	}
}
