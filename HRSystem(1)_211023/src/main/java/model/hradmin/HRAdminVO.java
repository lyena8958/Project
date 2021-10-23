package model.hradmin;

public class HRAdminVO {
	
	private int hnum; // PK
	private String id; 
	private String pw;
	private int hmem; // FK
	
	public int getHnum() {
		return hnum;
	}
	public void setHnum(int hnum) {
		this.hnum = hnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getHmem() {
		return hmem;
	}
	public void setHmem(int hmem) {
		this.hmem = hmem;
	}
	
	@Override
	public String toString() {
		return "HRAdminVO [hnum=" + hnum + ", id=" + id + ", pw=" + pw + ", hmem=" + hmem + "]";
	}
	
	
}
