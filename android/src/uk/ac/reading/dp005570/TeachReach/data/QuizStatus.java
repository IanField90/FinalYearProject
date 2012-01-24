package uk.ac.reading.dp005570.TeachReach.data;

public class QuizStatus {
	private String mName;
	private boolean mTaken;
	
	public QuizStatus(String name, boolean taken){
		this.mName = name;
		this.mTaken = taken;
	}
	
	public void setName(String name) {
		this.mName = name;
	}
	public String getName() {
		return mName;
	}
	public void setTaken(boolean taken) {
		this.mTaken = taken;
	}
	public boolean isTaken() {
		return mTaken;
	}
}
