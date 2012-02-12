package uk.ac.reading.dp005570.TeachReach.data;

/**
 * 
 * @author Ian Field
 *
 */
public class Quiz {
	int mId, mPartId;
	String mTitleEN, mTitleFR, mTitleES;
	
	public Quiz(int id, int part_id, String en, String fr, String es){
		mId = id;
		mPartId = part_id;
		mTitleEN = en;
		mTitleFR = fr;
		mTitleES = es;
	}
	
	public String getEN(){
		return mTitleEN;
	}
	
	public String getFR(){
		return mTitleFR;
	}
	
	public String getES(){
		return mTitleES;
	}
	
	public int getId(){
		return mId;
	}
	
	public int getPartId(){
		return mPartId;
	}

}
