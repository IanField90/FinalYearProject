package uk.ac.reading.dp005570.TeachReach.data;

import java.util.ArrayList;

/**
 * 
 * @author ianfield
 * Class to store the information for a particular question
 */
public class Question {
	private String mQuestionText;
	private QuestionType mType;
	private ArrayList<String> mOptions;
	private Boolean[] mCorrectOptions;
	
	public enum QuestionType {
		MULTIPLE_CHOICE, BLANKS, SLIDER, ORDERING, MATCH_UP
	}
	
	
	public Question(String questionText, QuestionType type, ArrayList<String> options, Boolean[] correctOptions){
		this.mQuestionText = questionText;
		this.mType = type;
		this.mOptions = options;
		this.setCorrectOptions(correctOptions);
	}
	
	public void setQuestionText(String questionText) {
		this.mQuestionText = questionText;
	}
	
	public String getQuestionText() {
		return mQuestionText;
	}
	
	public void setType(QuestionType type) {
		this.mType = type;
	}
	
	public QuestionType getType() {
		return mType;
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
	
}
