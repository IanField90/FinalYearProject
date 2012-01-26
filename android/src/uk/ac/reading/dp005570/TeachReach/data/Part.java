package uk.ac.reading.dp005570.TeachReach.data;

/**
 * Stores the information for a Part's Database entry for
 * storage within parsing a Server response
 * @author ianfield
 *
 */
public class Part {
	private String mEN, mFR, mES, mUpdated;
	private int mId, mProgrammeID;
	
	public Part(int id, int prog_id, String en, String fr, String es, String updated){
		setId(id);
		setEN(en);
		setFR(fr);
		setES(es);
		setUpdated(updated);
		setProgrammeID(prog_id);
	}

	public String getEN() {
		return mEN;
	}

	public void setEN(String mEN) {
		this.mEN = mEN;
	}

	public String getFR() {
		return mFR;
	}

	public void setFR(String mFR) {
		this.mFR = mFR;
	}

	public String getES() {
		return mES;
	}

	public void setES(String mES) {
		this.mES = mES;
	}

	public String getUpdated() {
		return mUpdated;
	}

	public void setUpdated(String mUpdated) {
		this.mUpdated = mUpdated;
	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public int getProgrammeID() {
		return mProgrammeID;
	}

	public void setProgrammeID(int mProgrammeID) {
		this.mProgrammeID = mProgrammeID;
	}
	
	

}
