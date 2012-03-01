package uk.ac.reading.dp005570.TeachReach.data;


/**
 * 
 * @author Ian Field
 * Class to store the information for a particular question
 */
public class Question {
	private String mQuestionEN, mQuestionFR, mQuestionES;
	private String mFeedbackEN, mFeedbackFR, mFeedbackES;
	private int mId, mQuizId, mType;
	
//	public enum QuestionType {
//		MULTIPLE_CHOICE, BLANKS, SLIDER, ORDERING, MATCH_UP
//	}
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
	
	public void setQuestionText(String questionText) {
		this.mQuestionEN = questionText;
	}
	
	public String getQuestionText() {
		return mQuestionEN;
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
