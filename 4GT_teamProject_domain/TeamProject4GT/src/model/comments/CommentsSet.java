package model.comments;

import java.util.ArrayList;

import model.reply.ReplyVO;


public class CommentsSet {
	private CommentsVO comment;
	private ArrayList<ReplyVO> rlist = new ArrayList<ReplyVO>();
	public CommentsVO getComment() {
		return comment;
	}
	public void setComment(CommentsVO comment) {
		this.comment = comment;
	}
	public ArrayList<ReplyVO> getRlist() {
		return rlist;
	}
	public void setRlist(ArrayList<ReplyVO> rlist) {
		this.rlist = rlist;
	}
	@Override
	public String toString() {
		return "CommentsSet [comment=" + comment + ", rlist=" + rlist + "]";
	}
	
	
}
