package uk.ac.reading.dp005570.TeachReach.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.ac.reading.dp005570.TeachReach.data.Course;
import uk.ac.reading.dp005570.TeachReach.data.Material;
import uk.ac.reading.dp005570.TeachReach.data.Option;
import uk.ac.reading.dp005570.TeachReach.data.Part;
import uk.ac.reading.dp005570.TeachReach.data.Programme;
import uk.ac.reading.dp005570.TeachReach.data.Question;
import uk.ac.reading.dp005570.TeachReach.data.Quiz;
import android.util.Log;

/**
 * 
 * @author Ian Field
 * Handles the parsing of the data retrieved from the server.
 */
public class TeachReachParser {
	private final String TAG = "PARSER";
	private final String ID = "id";

	//Courses JSON response fields
	private final String COURSE_NAME_EN = "course_name_en";
	private final String COURSE_NAME_FR = "course_name_fr";
	private final String COURSE_NAME_ES = "course_name_es";

	//Programmes JSON response fields
	private final String PROGRAMMES = "programmes";
	private final String PROGRAMME_NAME_EN = "programme_name_en";
	private final String PROGRAMME_NAME_FR = "programme_name_fr";
	private final String PROGRAMME_NAME_ES = "programme_name_es";

	//Parts JSON response fields
	private final String PARTS = "parts";
	private final String PART_NAME_EN = "part_name_en";
	private final String PART_NAME_FR = "part_name_fr";
	private final String PART_NAME_ES = "part_name_es";

	private final String QUIZZES = "quizzes";
	private final String QUIZ_TITLE_EN = "name_en";
	private final String QUIZ_TITLE_FR = "name_fr";
	private final String QUIZ_TITLE_ES = "name_es";

	private final String QUESTIONS = "questions";
	private final String QUESTION_EN = "content_en";
	private final String QUESTION_FR = "content_fr";
	private final String QUESTION_ES = "content_es";
	private final String FEEDBACK_EN = "feedback_en";
	private final String FEEDBACK_FR = "feedback_fr";
	private final String FEEDBACK_ES = "feedback_es";
	private final String QUESTION_TYPE = "type_id";

	private final String OPTIONS = "options";
	private final String OPTION_EN = "option_en";
	private final String OPTION_FR = "option_fr";
	private final String OPTION_ES = "option_es";
	private final String OPTION_ANSWER = "answer";

	private final String MATERIALS = "materials";
	private final String MATERIAL_EN = "material_en";
	private final String MATERIAL_FR = "material_fr";
	private final String MATERIAL_ES = "material_es";

	// 1 getter for each of these - non-sent ids can still be populated.
	private ArrayList<Course> mCourses = new ArrayList<Course>();
	private ArrayList<Programme> mProgrammes = new ArrayList<Programme>();
	private ArrayList<Part> mParts = new ArrayList<Part>();

	private ArrayList<Material> mMaterials = new ArrayList<Material>();
	private ArrayList<Question> mQuestions = new ArrayList<Question>();
	private ArrayList<Quiz> mQuizzes = new ArrayList<Quiz>();
	private ArrayList<Option> mOptions = new ArrayList<Option>();

	/**
	 * Extracts information for a Course list and updates/creates entry in
	 * database
	 * @param list The JSON array of courses returned by the server
	 */
	public ArrayList<Course> parseCourses(JSONArray list){
		JSONObject course;
		int id;
		String en, fr, es;
		ArrayList<Course> courses = new ArrayList<Course>();
		for (int i = 0; i < list.length(); i++){
			try {
				course = list.getJSONObject(i);
				id = (Integer) course.get(ID);				
				en = course.getString(COURSE_NAME_EN);
				fr = course.getString(COURSE_NAME_FR);
				es = course.getString(COURSE_NAME_ES);

				Log.i(TAG, course.toString());
				Log.i(TAG, "ID: "+id);
				Log.i(TAG, "EN: " + en);
				Log.i(TAG, "FR: " + fr);
				Log.i(TAG, "ES: " + es);	
				//Add to courses list to retain information in memory
				mCourses.add(new Course(id, en, fr, es));

				//Set up next parsing operation and call it.
				JSONArray programmes = course.getJSONArray(PROGRAMMES);
				parseProgrammes(programmes, id);

			} catch (JSONException e) {
				// Shouldn't happen unless empty response
				e.printStackTrace();
			}
		}
		return (courses.size() > 0) ? courses : null;
	}


	/**
	 * Extracts information for a programme's JSON and updates/creates entry in
	 * database.
	 * @param list The list of programmes to parse
	 * @param course The ID of the parent course.
	 */
	private void parseProgrammes(JSONArray list, int course){
		JSONObject programme;
		int id, course_id = course;
		String en, fr, es;
		for (int i = 0; i < list.length(); i++){
			try {
				programme = list.getJSONObject(i);
				id = (Integer) programme.get(ID);				
				en = programme.getString(PROGRAMME_NAME_EN);
				fr = programme.getString(PROGRAMME_NAME_FR);
				es = programme.getString(PROGRAMME_NAME_ES);
				Log.i(TAG, programme.toString());
				Log.i(TAG, "ID: "+id);
				Log.i(TAG, "EN: " + en);
				Log.i(TAG, "FR: " + fr);
				Log.i(TAG, "ES: " + es);	
				Log.i(TAG, "Course ID: " + course_id);
				//Add to programmes list to retain information in memory
				mProgrammes.add(new Programme(id, course_id, en, fr, es));

				//Set up next parsing operation and call it
				JSONArray parts = programme.getJSONArray(PARTS);
				parseParts(parts, id);

			} catch (JSONException e) {
				// Shouldn't happen unless empty response
				e.printStackTrace();
			}
		}
	}

	/**
	 * Extracts the information from a list of parts and updates/creates entry in
	 * database
	 * @param list A list of Parts to parse
	 * @param programme The ID of the Part's parent Programme
	 */
	private void parseParts(JSONArray list, int programme){
		JSONObject part;
		int id, programme_id = programme;
		String en, fr, es;
		for (int i = 0; i < list.length(); i++){
			try {
				part = list.getJSONObject(i);
				id = part.getInt(ID);				
				en = part.getString(PART_NAME_EN);
				fr = part.getString(PART_NAME_FR);
				es = part.getString(PART_NAME_ES);
				Log.i(TAG, part.toString());
				Log.i(TAG, "ID: "+id);
				Log.i(TAG, "EN: " + en);
				Log.i(TAG, "FR: " + fr);
				Log.i(TAG, "ES: " + es);
				Log.i(TAG, "Prog ID: " + programme_id);	
				//Add to parts list to retain information in memory
				mParts.add(new Part(id, programme_id, en, fr, es));

			} catch (JSONException e) {
				// Shouldn't happen unless empty response
				e.printStackTrace();
			}
		}
	}

	/**
	 * Actually get all the information from a part. That is to say that the
	 * full quiz(questions -> options -> feedback), material should be parsed and
	 * added locally to the database
	 * @param jsonArray The array containing all information for a particular part.
	 */
	public void parsePartContent(JSONArray jsonArray, int part_id){
		//TODO triple layered parse.	
		try {
			JSONObject object = jsonArray.getJSONObject(0);
			JSONArray materials = object.getJSONArray(MATERIALS);
			JSONArray quizzes = object.getJSONArray(QUIZZES);
			parseMaterials(materials, part_id);
			parseQuizzes(quizzes, part_id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Go through and parse all materials for a part. Add materials to the material list
	 * @param materials The array of materials from the server
	 * @param part_id The server's ID for the part being parsed
	 */
	private void parseMaterials(JSONArray materials, int part_id){
		JSONObject material;
		int id;
		String en, fr, es;
		Log.i(TAG, "Parsing materials.");
		for (int i = 0; i < materials.length(); i++){
			try{
				material = materials.getJSONObject(i);
				id = material.getInt(ID);
				en = material.getString(MATERIAL_EN);
				fr = material.getString(MATERIAL_FR);
				es = material.getString(MATERIAL_ES);
				Log.i(TAG, material.toString());
				Log.i(TAG, "Material ID: " + id);
				Log.i(TAG, "Material EN: " + en);
				Log.i(TAG, "Material FR: " + fr);
				Log.i(TAG, "Material ES: " + es);
				Log.i(TAG, "Material Part ID: " + part_id);
				mMaterials.add(new Material(id, part_id, en, fr, es));
				Log.i(TAG, "Number of materials: " + mMaterials.size());
			}
			catch(JSONException e){
				e.printStackTrace();
			}
		}
		Log.i(TAG, "Finished parsing materials.");

	}

	/**
	 * Go through and parse all quizzes for a part. Add details to quiz list.
	 * @param quizzes The Array of quizzes from the server
	 * @param part_id The server's ID for the part being parsed
	 */
	private void parseQuizzes(JSONArray quizzes, int part_id){
		JSONObject quiz;
		int id;
		String en, fr, es;
		Boolean published;
		for (int i = 0; i < quizzes.length(); i++){
			try {
				quiz = quizzes.getJSONObject(i);
				id = quiz.getInt(ID);
				en = quiz.getString(QUIZ_TITLE_EN);
				fr = quiz.getString(QUIZ_TITLE_FR);
				es = quiz.getString(QUIZ_TITLE_ES);
				published =  quiz.getBoolean("published");
				Log.i(TAG, "ID: " + id);
				Log.i(TAG, "EN: " + en);
				Log.i(TAG, "FR: " + fr);
				Log.i(TAG, "ES: " + es);
				Log.i(TAG, "Published: " + published);
				Log.i(TAG, "Part ID: " + part_id);

				mQuizzes.add(new Quiz(id, part_id, en, fr, es));
				JSONArray questions = quiz.getJSONArray(QUESTIONS);
				parseQuestions(questions, id);

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Go through and translate all questions relating to a quiz.
	 * Add to total question list
	 * @param questions Array of all question objects
	 * @param quiz_id The Server's ID of the quiz
	 */
	private void parseQuestions(JSONArray questions, int quiz_id){
		JSONObject question;
		int id = 0, type = 0;
		String en, fr, es, feedback_en, feedback_fr, feedback_es;
		for (int i = 0; i < questions.length(); i++){
			try{
				question = questions.getJSONObject(i);
				id = question.getInt(ID);
				en = question.getString(QUESTION_EN);
				fr = question.getString(QUESTION_FR);
				es = question.getString(QUESTION_ES);
				type = question.getInt(QUESTION_TYPE);
				feedback_en = question.getString(FEEDBACK_EN);
				feedback_fr = question.getString(FEEDBACK_FR);
				feedback_es = question.getString(FEEDBACK_ES);
				
				//May be skipped if a field is empty - Warning error in log
				mQuestions.add(new Question(id, quiz_id, type, en, fr, es, feedback_en, feedback_fr, feedback_es));
				JSONArray options = question.getJSONArray(OPTIONS);
				parseOptions(options, id);
			}
			catch(JSONException e){
				e.printStackTrace();
			}

		}
	}

	private void parseOptions(JSONArray options, int question_id){
		JSONObject option;
		int id;
		String en, fr, es;
		Boolean answer;
		for (int i = 0; i < options.length(); i++){
			try{
				option = options.getJSONObject(i);
				id = option.getInt(ID);
				en = option.getString(OPTION_EN);
				fr = option.getString(OPTION_FR);
				es = option.getString(OPTION_ES);
				answer = option.getBoolean(OPTION_ANSWER);
				mOptions.add(new Option(id, question_id, en, fr, es, answer));
			}
			catch(JSONException e){
				e.printStackTrace();
			}
		}
	}


	/**
	 * 
	 * @return List of all Courses
	 */
	public ArrayList<Course> getCourses() {
		return mCourses;
	}
	
	/**
	 * 
	 * @return List of all Programmes
	 */
	public ArrayList<Programme> getProgrammes() {
		return mProgrammes;
	}
	
	/**
	 * 
	 * @return List of all Parts
	 */
	public ArrayList<Part> getParts() {
		return mParts;
	}

	/**
	 * 
	 * @return List of all Materials
	 */
	public ArrayList<Material> getMaterials() {
		return mMaterials;
	}
	
	/**
	 * 
	 * @return List of all Materials
	 */
	public ArrayList<Quiz> getQuizzes() {
		return mQuizzes;
	}
	
	/**
	 * 
	 * @return List of all Questions
	 */
	public ArrayList<Question> getQuesitons(){
		return mQuestions;
	}
	
	/**
	 * 
	 * @return List of all Options
	 */
	public ArrayList<Option> getOptions() {
		return mOptions;
	}

}
