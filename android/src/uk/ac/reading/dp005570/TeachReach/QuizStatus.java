package uk.ac.reading.dp005570.TeachReach;

public class QuizStatus {
	private String name;
	private boolean taken;
	
	QuizStatus(String name, boolean taken){
		this.name = name;
		this.taken = taken;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	public boolean isTaken() {
		return taken;
	}
}
