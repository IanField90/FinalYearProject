package uk.ac.reading.dp005570.TeachReach.data;

public class Course {
	private String mEN, mFR, mES;
	private int mId;
	
	/**
	 * 
	 * @param id The ID of the part on server-side
	 * @param en The English title of the course
	 * @param fr The French title of the course
	 * @param es The Spanish title of the course
	 * @param updated The date the course was last updated on the server
	 */
	public Course(int id, String en, String fr, String es){
		setId(id);
		setEN(en);
		setFR(fr);
		setES(es);
	}

	/**
	 * 
	 * @return The English title of the course
	 */
	public String getEN() {
		return mEN;
	}

	/**
	 * 
	 * @param mEN The English title of the course
	 */
	public void setEN(String mEN) {
		this.mEN = mEN;
	}

	/**
	 * 
	 * @return The French title of the course
	 */
	public String getFR() {
		return mFR;
	}

	/**
	 * 
	 * @param mFR The French title of the course
	 */
	public void setFR(String mFR) {
		this.mFR = mFR;
	}

	/**
	 * 
	 * @return The Spanish title of the course
	 */
	public String getES() {
		return mES;
	}
	
	/**
	 * 
	 * @param mES The Spanish title of the course
	 */
	public void setES(String mES) {
		this.mES = mES;
	}
	
	/**
	 * 
	 * @return The server's ID of the course
	 */
	public int getId() {
		return mId;
	}
	
	/**
	 * 
	 * @param mId The server's ID of the course
	 */
	public void setId(int mId) {
		this.mId = mId;
	}
}
