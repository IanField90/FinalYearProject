package uk.ac.reading.dp005570.TeachReach.data;

/**
 * 
 * @author Ian Field
 *
 */
public class Answer {
	private Integer mAnswer; //Number of the answer within the question
	private AnswerStatus mStatus;
	
	public Answer(Integer answer, AnswerStatus status){
		this.mAnswer = answer;
		this.mStatus = status;
	}
	
	public void setAnswer(Integer answer) {
		this.mAnswer = answer;
	}
	public Integer getAnswer() {
		return mAnswer;
	}
	public void setStatus(AnswerStatus status) {
		this.mStatus = status;
	}
	public AnswerStatus getStatus() {
		return mStatus;
	}
	
	
	
}
