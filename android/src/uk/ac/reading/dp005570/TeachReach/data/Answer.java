package uk.ac.reading.dp005570.TeachReach.data;

public class Answer {
	private Integer answer; //Number of the answer within the question
	private AnswerStatus status;
	
	public Answer(Integer answer, AnswerStatus status){
		this.answer = answer;
		this.status = status;
	}
	
	public void setAnswer(Integer answer) {
		this.answer = answer;
	}
	public Integer getAnswer() {
		return answer;
	}
	public void setStatus(AnswerStatus status) {
		this.status = status;
	}
	public AnswerStatus getStatus() {
		return status;
	}
	
	
	
}
