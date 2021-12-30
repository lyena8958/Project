package model.reply;

import java.sql.Date;

public class ReplyVO {
	private int rnum;
	private String rment;
	private Date rdate;
	private String rwriter;
	private int rlikeCnt;
	private String r_user;
	private int r_post;
	private int r_comments;
	
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public String getRment() {
		return rment;
	}
	public void setRment(String rment) {
		this.rment = rment;
	}
	public Date getRdate() {
		return rdate;
	}
	public void setRdate(Date rdate) {
		this.rdate = rdate;
	}
	public String getRwriter() {
		return rwriter;
	}
	public void setRwriter(String rwriter) {
		this.rwriter = rwriter;
	}
	public String getR_user() {
		return r_user;
	}
	public void setR_user(String r_user) {
		this.r_user = r_user;
	}
	public int getR_post() {
		return r_post;
	}
	public void setR_post(int r_post) {
		this.r_post = r_post;
	}
	public int getR_comments() {
		return r_comments;
	}
	public void setR_comments(int r_comments) {
		this.r_comments = r_comments;
	}
	public int getRlikeCnt() {
		return rlikeCnt;
	}
	public void setRlikeCnt(int rlikeCnt) {
		this.rlikeCnt = rlikeCnt;
	}
	@Override
	public String toString() {
		return "ReplyVO [rnum=" + rnum + ", rment=" + rment + ", rdate=" + rdate + ", rwriter=" + rwriter
				+ ", rlikeCnt=" + rlikeCnt + ", r_user=" + r_user + ", r_post=" + r_post + ", r_comments=" + r_comments
				+ "]";
	}
	
	
}
