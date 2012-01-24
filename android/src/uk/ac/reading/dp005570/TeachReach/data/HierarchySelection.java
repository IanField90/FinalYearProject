package uk.ac.reading.dp005570.TeachReach.data;

/**
 * Used to store information for the selection items on the main menu of the mobile app.
 * @author ianfield
 *
 */
public class HierarchySelection {
	public static enum Type { COURSE, PROGRAMME, PART };
	private int mId;
	private String mEN, mFR, mES;
	private Type mType;
	
	public HierarchySelection(int id, String en, String fr, String es, Type type){
		this.setId(id);
		this.setEn(en);
		this.setFr(fr);
		this.setEs(es);
		this.setType(type);
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}

	public String getEn() {
		return mEN;
	}

	public void setEn(String en) {
		this.mEN = en;
	}

	public String getFr() {
		return mFR;
	}

	public void setFr(String fr) {
		this.mFR = fr;
	}

	public String getEs() {
		return mES;
	}

	public void setEs(String es) {
		this.mES = es;
	}

	public Type getType() {
		return mType;
	}

	public void setType(Type type) {
		this.mType = type;
	}
	
	
}
