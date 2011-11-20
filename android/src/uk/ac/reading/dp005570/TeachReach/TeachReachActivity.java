package uk.ac.reading.dp005570.TeachReach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * @author ianfield
 *
 */
public class TeachReachActivity extends Activity implements OnClickListener{
//	private JSONObject object;
	private TeachReachDbAdapter mDbHelper;
   
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mDbHelper = new TeachReachDbAdapter(this);
        mDbHelper.open();
        
        String programmes[] = { "1. Programme 1", "2. Programme 2", "3. Programme 3",
        		"4. Programme 4", "5. Programme 5", "6. Programme 6", "7. Programme 7",
        		"8. Programme 8", "9. Programme 9", "10. Programme 10"  };
        String courses[] = { "1. Course 1", "2. Course 2" };
        String modules[] = { "1. Module 1", "2. Modlue 2" };
        
        //Set up proramme spinner
        Spinner programme_spinner = (Spinner) findViewById(R.id.programme_spinner);
        ArrayAdapter<String> programme_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, programmes);
        programme_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programme_spinner.setAdapter(programme_adapter);
        
        //Set up course spinner
        Spinner course_spinner = (Spinner) findViewById(R.id.course_spinner);
        ArrayAdapter<String> course_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        course_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_spinner.setAdapter(course_adapter);
        
        //Set up module spinner
        Spinner module_spinner = (Spinner) findViewById(R.id.module_spinner);
        ArrayAdapter<String> module_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, modules);
        module_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        module_spinner.setAdapter(module_adapter);
        
        //Set up m
        Button view_quizzes_button = (Button) findViewById(R.id.view_quizzes_button);
        view_quizzes_button.setOnClickListener(this);
        
        Button view_materials_button = (Button) findViewById(R.id.view_materials_button);
        view_materials_button.setOnClickListener(this);
    }

//    @Override
    public void onClick(View v) {
    	Intent intent;
    	if(v == findViewById(R.id.view_quizzes_button)){
        	//Create an intent and start activity
        	intent = new Intent(this, QuizListActivity.class);
        	startActivity(intent);

    	}
    	else if(v == findViewById(R.id.view_materials_button)){
    		intent = new Intent(this, MaterialListActivity.class);
        	startActivity(intent);
    	}
    }
    
    
    
//    public void interpretJSON(){
//    	String json = "{\n" + 
//    			"   \"programme\":\"Test Programme 1\",\n" + 
//    			"   \"course\":\"Test Course 1\",\n" + 
//    			"   \"module\":\"Test Module 1\",\n" + 
//    			"   \"quizes\":[\n" + 
//    			"      {\n" + 
//    			"         \"questions\":[\n" + 
//    			"            {\n" + 
//    			"               \"id\":1,\n" + 
//    			"               \"en\":\"What is your name?\",\n" + 
//    			"               \"fr\":\"Quel est votre nom?\",\n" + 
//    			"               \"es\":\"ÀCu‡l es tu nombre?\",\n" + 
//    			"               \"options\":[\n" + 
//    			"                  {\n" + 
//    			"                     \"correct\":false,\n" + 
//    			"                     \"en\":\"Bob\",\n" + 
//    			"                     \"fr\":\"Deven\",\n" + 
//    			"                     \"es\":\"Juan\"\n" + 
//    			"                  },\n" + 
//    			"                  {\n" + 
//    			"                     \"correct\":true,\n" + 
//    			"                     \"en\":\"Amy\",\n" + 
//    			"                     \"fr\":\"Ami\",\n" + 
//    			"                     \"es\":\"Amelia\"\n" + 
//    			"                  }\n" + 
//    			"               ]\n" + 
//    			"            },\n" + 
//    			"            {\n" + 
//    			"               \"id\":2,\n" + 
//    			"               \"en\":\"What is your name 2?\",\n" + 
//    			"               \"fr\":\"Quel est votre nom 2?\",\n" + 
//    			"               \"es\":\"ÀCu‡l es tu nombre 2?\",\n" + 
//    			"               \"options\":[\n" + 
//    			"                  {\n" + 
//    			"                     \"correct\":true,\n" + 
//    			"                     \"en\":\"Bob 2\",\n" + 
//    			"                     \"fr\":\"Deven 2\",\n" + 
//    			"                     \"es\":\"Juan 2\"\n" + 
//    			"                  },\n" + 
//    			"                  {\n" + 
//    			"                     \"correct\":false,\n" + 
//    			"                     \"en\":\"Amy 2\",\n" + 
//    			"                     \"fr\":\"Ami 2\",\n" + 
//    			"                     \"es\":\"Amelia 2\"\n" + 
//    			"                  }\n" + 
//    			"               ]\n" + 
//    			"            }\n" + 
//    			"         ]\n" + 
//    			"      }\n" + 
//    			"   ]\n" + 
//    			"}";
//    	try {
//			object = (JSONObject) new JSONTokener(json).nextValue();
//		} catch (JSONException e) {
//			e.printStackTrace();
//			object = null;
//			Log.e("error", "Json exception");
//		}
//        Log.i("info", object.toString());
//
//    }
}