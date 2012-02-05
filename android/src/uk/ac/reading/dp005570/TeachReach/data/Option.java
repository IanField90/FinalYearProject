package uk.ac.reading.dp005570.TeachReach.data;
/**
 * 
 * @author ianfield
 *
 */
public class Option {
	private int mId, mQuestionId;
	private String mEN, mFR, mES;
	private Boolean mAnswer;
	
	/**
	 * 
	 * @param id ID of the option
	 * @param question_id ID of the question that the quiz belongs to
	 * @param en English version of the option
	 * @param fr French version of the option
	 * @param es Spanish version of the option
	 * @param answer Whether or not the option is a correct answer to the question
	 */
	public Option(int id, int question_id, String en, String fr, String es, Boolean answer){
		setId(id);
		setQuestionId(question_id);
		setEN(en);
		setFR(fr);
		setES(es);
		setAnswer(answer);
	}
	
	/**
	 * 
	 * @return ID of the option
	 */
	public int getId() {
		return mId;
	}
	
	/**
	 * 
	 * @param mId ID of the option
	 */
	public void setId(int mId) {
		this.mId = mId;
	}

	/**
	 * 
	 * @return ID of the question that the option belongs to
	 */
	public int getQuestionId() {
		return mQuestionId;
	}

	/**
	 * 
	 * @param mQuestionId ID of the quesiton that the option belongs to
	 */
	public void setQuestionId(int mQuestionId) {
		this.mQuestionId = mQuestionId;
	}

	/**
	 * 
	 * @return English version of the option
	 */
	public String getEN() {
		return mEN;
	}

	/**
	 * 
	 * @param mEN English version of the option
	 */
	public void setEN(String mEN) {
		this.mEN = mEN;
	}

	/**
	 * 
	 * @return French version of the option
	 */
	public String getFR() {
		return mFR;
	}
	
	/**
	 * 
	 * @param mFR French version of the option
	 */
	public void setFR(String mFR) {
		this.mFR = mFR;
	}

	/**
	 * 
	 * @return Spanish version of the option
	 */
	public String getES() {
		return mES;
	}

	/**
	 * 
	 * @param mES Spanish version of the option
	 */
	public void setES(String mES) {
		this.mES = mES;
	}

	/**
	 * 
	 * @return Whether or not the option is a correct answer to a question
	 */
	public Boolean isAnswer() {
		return mAnswer;
	}

	/**
	 * 
	 * @param mAnswer Whether or not the option is a correct answer to a question.
	 */
	public void setAnswer(Boolean mAnswer) {
		this.mAnswer = mAnswer;
	}
}
