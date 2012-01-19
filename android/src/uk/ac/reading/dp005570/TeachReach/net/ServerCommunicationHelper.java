package uk.ac.reading.dp005570.TeachReach.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class ServerCommunicationHelper {
	private HttpClient client = new DefaultHttpClient();
	private HttpGet request = new HttpGet();
	private HttpResponse response;
	private BufferedReader in;
	//TODO This does not work...
	//Resources.getSystem().getString(uk.ac.reading.dp005570.TeachReach.R.string.server_uri);
	private final String SERVER_ADDRESS = "http://10.0.2.2:3000/"; //Default emulator server address
	private final String REST_COURSES = "courses.json"; 
	public ServerCommunicationHelper(){
		
	}
	
	private void parseCourses(JSONArray list){
		list.toString();
	}
	
	public void getCourseList(){
		//TODO run on a different thread, and give user some feedback on progress
		//TODO timeout handling if the server cannot be found
		// (see list tutorial Karsten told me about)
		try {
			request.setURI(new URI(SERVER_ADDRESS + REST_COURSES));
			response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			
			while((line = in.readLine()) != null){
				sb.append(line + NL);
			}
			
			in.close();
			String response_page = sb.toString();
			
			Log.d("SERVER", "Response: " + response_page);
			
			try {
				JSONArray course_list = new JSONArray(response_page);
				parseCourses(course_list);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
