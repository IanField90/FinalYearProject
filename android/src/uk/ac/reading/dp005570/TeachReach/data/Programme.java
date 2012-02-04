package uk.ac.reading.dp005570.TeachReach.data;

public class Programme {
	private String mEN, mFR, mES;
	private int mId, mCourseId;
	
	/**
	 * 
	 * @param id The ID of the programme on server-side
	 * @param course_id The ID of the parent Course
	 * @param en The English title of the programme
	 * @param fr The French title of the programme
	 * @param es The Spanish title of the programme
	 * @param updated The date the programme was last updated on the server
	 */
	public Programme(int id, int course_id, String en, String fr, String es){
		setId(id);
		setEN(en);
		setFR(fr);
		setES(es);
		setCourseID(course_id);
	}

	/**
	 * 
	 * @return The English title of the programme
	 */
	public String getEN() {
		return mEN;
	}

	/**
	 * 
	 * @param mEN The English title of the programme
	 */
	public void setEN(String mEN) {
		this.mEN = mEN;
	}

	/**
	 * 
	 * @return The French title of the programme
	 */
	public String getFR() {
		return mFR;
	}

	/**
	 * 
	 * @param mFR The French title of the programme
	 */
	public void setFR(String mFR) {
		this.mFR = mFR;
	}

	/**
	 * 
	 * @return The Spanish title of the programme
	 */
	public String getES() {
		return mES;
	}
	
	/**
	 * 
	 * @param mES The Spanish title of the programme
	 */
	public void setES(String mES) {
		this.mES = mES;
	}
	
	/**
	 * 
	 * @return The server's ID of the programme
	 */
	public int getId() {
		return mId;
	}
	
	/**
	 * 
	 * @param mId The server's ID of the programme
	 */
	public void setId(int mId) {
		this.mId = mId;
	}

	/**
	 * 
	 * @return The parent programme's ID of the programme
	 */
	public int getCourseID() {
		return mCourseId;
	}

	/**
	 * 
	 * @param mProgrammeID The parent programme's ID of the programme
	 */
	public void setCourseID(int mCourseID) {
		this.mCourseId = mCourseID;
	}
}
