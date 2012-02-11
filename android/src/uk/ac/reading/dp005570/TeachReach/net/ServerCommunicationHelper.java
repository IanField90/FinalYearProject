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
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author ianfield
 *	Allows the communication and requests from the server.
 *	Leverages the TeachReachDbAdapter class to update the database, 
 *	which allows the population of the main Menu's selection ranges.
 */
public class ServerCommunicationHelper {
	private HttpClient mClient = new DefaultHttpClient();
	private HttpGet mRequest = new HttpGet();
	private HttpResponse mResponse;
	private BufferedReader mIn;
	
	//Constants in case these fields change at a later date
	
	//Universal JSON response fields
	private final String TAG = "SERVER_COMS";
	
	//TODO This does not work...
	//Resources.getSystem().getString(uk.ac.reading.dp005570.TeachReach.R.string.server_uri);
	private final String SERVER_ADDRESS = "http://10.0.2.2:3000/"; //Default emulator server address
	private final String REST_ENDING = ".json";
	private final String REST_COURSES = "courses" + REST_ENDING;
	private final String REST_PART = "part/";
	
	/**
	 * 
	 */
	public ServerCommunicationHelper(){

	}
	
	/**
	 * Get the course list from the server
	 * @param progress used to feedback to user on communication progress
	 * @return JSON response from the server
	 */
	public String getCourseList(ProgressDialog progress){
		// run on a different thread, and give user some feedback on progress
		String response_page = null;
		try {
			mRequest.setURI(new URI(SERVER_ADDRESS + REST_COURSES));
			mResponse = mClient.execute(mRequest);
			mIn = new BufferedReader(new InputStreamReader(mResponse.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");

			while((line = mIn.readLine()) != null){
				sb.append(line + NL);
			}

			mIn.close();
			if(sb != null){
				response_page = sb.toString();
			}

			Log.i("ServerCommunicationHelper", "Response: " + response_page);

		} catch (URISyntaxException e) {
			Log.e(TAG, "Malformed URL");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.e(TAG, "Client Protocol Exception");
			e.printStackTrace();
		}
		catch (HttpHostConnectException e){
			Log.e(TAG, "Couldn't connect to server.");
			Toast.makeText(progress.getContext(), "Can't connect to server.", Toast.LENGTH_LONG).show(); //TODO Localised message text
			e.printStackTrace();
		}
		catch (IOException e) {
			Log.e(TAG, "IOException");
			e.printStackTrace();
		}
		
//		progress.dismiss();
		return response_page != null ? response_page : null;
	}

	/**
	 * Get the details for the part from the server
	 * @param id ID of the part to retrieve items for
	 * @param progress Used to feedback to user on communication progress
	 * @return JSON response from the server
	 */
	public String getPartContent(int id, ProgressDialog progress){
		// run on a different thread, and give user some feedback on progress
		String response_page = null;
		try {
			mRequest.setURI(new URI(SERVER_ADDRESS + REST_PART + id + REST_ENDING));
			mResponse = mClient.execute(mRequest);
			mIn = new BufferedReader(new InputStreamReader(mResponse.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");

			while((line = mIn.readLine()) != null){
				sb.append(line + NL);
			}

			mIn.close();
			if(sb != null){
				response_page = sb.toString();
			}

			Log.d("SERVER", "Response: " + response_page);

		} catch (URISyntaxException e) {
			Log.e(TAG, "Erroneous URI");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.e(TAG, "Client Protocol Exception");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, "I/O Exception");
			e.printStackTrace();
		}
		return response_page != null ? response_page : null;

	}
}
