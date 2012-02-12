package uk.ac.reading.dp005570.TeachReach.data;

import java.util.ArrayList;

/**
 * 
 * @author Ian Field
 * Class to store the information for a particular question
 */
public class Question {
	//TODO remove unused fields and alter quiz activity to reflect this
	private String mQuestionEN, mQuestionFR, mQuestionES;
	private String mFeedbackEN, mFeedbackFR, mFeedbackES;
	private int mId, mQuizId, mType;
	private QuestionType mQType;
	private ArrayList<String> mOptions;
	private Boolean[] mCorrectOptions;
	
	public enum QuestionType {
		MULTIPLE_CHOICE, BLANKS, SLIDER, ORDERING, MATCH_UP
	}
	
	public Question(int id, int quiz_id, int type, String en, String fr, String es, 
			String feedback_en, String feedback_fr, String feedback_es){
		mId = id;
		mQuizId = quiz_id;
		mType = type;
		mQuestionEN = en;
		mQuestionFR = fr;
		mQuestionES = es;
		mFeedbackEN = feedback_en;
		mFeedbackFR = feedback_fr;
		mFeedbackES = feedback_es;
	}
	
	public Question(String questionText, QuestionType type, ArrayList<String> options, Boolean[] correctOptions){
		this.mQuestionEN = questionText;
		this.mQType = type;
		this.mOptions = options;
		this.setCorrectOptions(correctOptions);
	}
	
	public void setQuestionText(String questionText) {
		this.mQuestionEN = questionText;
	}
	
	public String getQuestionText() {
		return mQuestionEN;
	}
	
	public void setType(QuestionType type) {
		this.mQType = type;
	}
	
	public QuestionType getType() {
		return mQType;
	}
	
	public void setOptions(ArrayList<String> options) {
		this.mOptions = options;
	}
	
	public ArrayList<String> getOptions() {
		return mOptions;
	}

	public void setCorrectOptions(Boolean[] correctOptions) {
		this.mCorrectOptions = correctOptions;
	}

	public Boolean[] getCorrectOptions() {
		return mCorrectOptions;
	}
	
	public int getId(){
		return mId;
	}
	
	public int getQuizId(){
		return mQuizId;
	}
	
	public int getTypeId(){
		return mType;
	}
	
	public String getEN(){
		return mQuestionEN;
	}
	
	public String getFR(){
		return mQuestionFR;
	}
	
	public String getES(){
		return mQuestionES;
	}
	
	public String getFeedbackEN(){
		return mFeedbackEN;
	}
	
	public String getFeedbackFR(){
		return mFeedbackFR;
	}
	
	public String getFeedbackES(){
		return mFeedbackES;
	}
	
}
