package uk.ac.reading.dp005570.TeachReach.data;

/**
 * 
 * @author ianfield
 *
 */
public class Material {
	private String mEN, mFR, mES;
	private int mPartId, mId;
	
	/**
	 * 
	 * @param id ID of the material
	 * @param part_id ID of the part the material belongs to
	 * @param en English version of the material text
	 * @param fr French version of the material text
	 * @param es Spanish version of the material text
	 */
	public Material(int id, int part_id, String en, String fr, String es){
		setId(id);
		setPartId(part_id);
		setEN(en);
		setFR(fr);
		setES(es);
	}

	/**
	 * 
	 * @return Material contents in English
	 */
	public String getEN() {
		return mEN;
	}

	/**
	 * 
	 * @param mEN Material contents in English
	 */
	public void setEN(String mEN) {
		this.mEN = mEN;
	}

	/**
	 * 
	 * @return Material contents in French
	 */
	public String getFR() {
		return mFR;
	}

	/**
	 * 
	 * @param mFR Material contents in French
	 */
	public void setFR(String mFR) {
		this.mFR = mFR;
	}
	
	/**
	 * 
	 * @return Material contents in Spanish
	 */
	public String getES() {
		return mES;
	}
	
	/**
	 * 
	 * @param mES Material contents in Spanish
	 */
	public void setES(String mES) {
		this.mES = mES;
	}

	/**
	 * 
	 * @return ID of the Part the material belongs to
	 */
	public int getPartId() {
		return mPartId;
	}
	
	/**
	 * 
	 * @param mPartId ID of the Part the material belongs to
	 */
	public void setPartId(int mPartId) {
		this.mPartId = mPartId;
	}

	/**
	 * 
	 * @return Server's ID of the material
	 */
	public int getId() {
		return mId;
	}
	
	/**
	 * 
	 * @param mId Server's ID of the material
	 */
	public void setId(int mId) {
		this.mId = mId;
	}
}
