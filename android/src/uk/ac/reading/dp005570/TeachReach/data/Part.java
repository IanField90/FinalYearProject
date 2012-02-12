package uk.ac.reading.dp005570.TeachReach.data;

/**
 * Stores the information for a Part's Database entry for
 * storage within parsing a Server response
 * @author Ian Field
 *
 */
public class Part {
	private String mEN, mFR, mES;
	private int mId, mProgrammeID;
	
	/**
	 * 
	 * @param id The ID of the part on server-side
	 * @param prog_id The ID of the parent Programme
	 * @param en The English title of the part
	 * @param fr The French title of the part
	 * @param es The Spanish title of the part
	 * @param updated The date the part was last updated on the server
	 */
	public Part(int id, int prog_id, String en, String fr, String es){
		setId(id);
		setEN(en);
		setFR(fr);
		setES(es);
		setProgrammeID(prog_id);
	}

	/**
	 * 
	 * @return The English title of the part
	 */
	public String getEN() {
		return mEN;
	}

	/**
	 * 
	 * @param mEN The English title of the part
	 */
	public void setEN(String mEN) {
		this.mEN = mEN;
	}

	/**
	 * 
	 * @return The French title of the part
	 */
	public String getFR() {
		return mFR;
	}

	/**
	 * 
	 * @param mFR The French title of the part
	 */
	public void setFR(String mFR) {
		this.mFR = mFR;
	}

	/**
	 * 
	 * @return The Spanish title of the part
	 */
	public String getES() {
		return mES;
	}
	
	/**
	 * 
	 * @param mES The Spanish title of the part
	 */
	public void setES(String mES) {
		this.mES = mES;
	}

	/**
	 * 
	 * @return The server's ID of the part
	 */
	public int getId() {
		return mId;
	}
	
	/**
	 * 
	 * @param mId The server's ID of the part
	 */
	public void setId(int mId) {
		this.mId = mId;
	}

	/**
	 * 
	 * @return The parent programme's ID of the part
	 */
	public int getProgrammeID() {
		return mProgrammeID;
	}

	/**
	 * 
	 * @param mProgrammeID The parent programme's ID of the part
	 */
	public void setProgrammeID(int mProgrammeID) {
		this.mProgrammeID = mProgrammeID;
	}

}
