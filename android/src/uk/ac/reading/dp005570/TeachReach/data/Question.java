package uk.ac.reading.dp005570.TeachReach.data;

import java.util.ArrayList;

/**
 * 
 * @author ianfield
 * Class to store the information for a particular question
 */
public class Question {
	private String questionText;
	private QuestionType type;
	private ArrayList<String> options;
	private Boolean[] correctOptions; //TODO 2D update as matrix
	
	public enum QuestionType {
		MULTIPLE_CHOICE, BLANKS, SLIDER, ORDERING, MATCH_UP
	}
	
	public Question(String questionText, QuestionType type, ArrayList<String> options, Boolean[] correctOptions){
		this.questionText = questionText;
		this.type = type;
		this.options = options;
		this.setCorrectOptions(correctOptions);
	}
	
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
	public String getQuestionText() {
		return questionText;
	}
	
	public void setType(QuestionType type) {
		this.type = type;
	}
	
	public QuestionType getType() {
		return type;
	}
	
	public void setOptions(ArrayList<String> options) {
		this.options = options;
	}
	
	public ArrayList<String> getOptions() {
		return options;
	}

	public void setCorrectOptions(Boolean[] correctOptions) {
		this.correctOptions = correctOptions;
	}

	public Boolean[] getCorrectOptions() {
		return correctOptions;
	}
	
}
