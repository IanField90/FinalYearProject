package uk.ac.reading.dp005570.TeachReach.data;

/**
 * Used to store information for the selection items on the main menu of the mobile app.
 * @author ianfield
 *
 */
public class HierarchySelection {
	public static enum Type { COURSE, PROGRAMME, PART };
	private int id;
	private String en, fr, es;
	private Type type;
	
	public HierarchySelection(int id, String en, String fr, String es, Type type){
		this.setId(id);
		this.setEn(en);
		this.setFr(fr);
		this.setEs(es);
		this.setType(type);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getFr() {
		return fr;
	}

	public void setFr(String fr) {
		this.fr = fr;
	}

	public String getEs() {
		return es;
	}

	public void setEs(String es) {
		this.es = es;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	
}
