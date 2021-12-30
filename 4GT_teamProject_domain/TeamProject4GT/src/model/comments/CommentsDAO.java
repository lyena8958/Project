package model.comments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.DBCP;
import model.reply.ReplyVO;

public class CommentsDAO {

	// 기본 CRUD
	private static String sql_SELECT_ALL = "SELECT * FROM comments";
	private static String sql_SELECT_ONE = "SELECT * FROM comments WHERE cnum=?";
	// 이예나 secretNum 추가
	
//	MySql sql
	private static String sql_INSERT = "INSERT INTO comments (cment, cdate, cwriter, c_user, c_post, secretNum) VALUES(?, now(), ?, ?, ?, ?)";
	
//	oracle sql
//	private static String sql_INSERT = "INSERT INTO comments (cnum, cment, cdate, cwriter, c_user, c_post, secretNum) VALUES((SELECT NVL(MAX(cnum),0) + 1 FROM comments), ?, sysdate, ?, ?, ?, ?)";
	private static String sql_DELETE = "DELETE FROM comments WHERE cnum=?";
	
	// MySql sql
	private static String sql_UPDATE = "UPDATE comments SET cment=?, cdate=now() WHERE cnum=?";
	
	//oracle sql
//	private static String sql_UPDATE = "UPDATE comments SET cment=?, cdate=sysdate WHERE cnum=?";

	// 사용자 정의 함수
	private static String sql_SELECT_POST = "SELECT * FROM comments WHERE c_post=? ORDER BY cnum"; // c_post를 받아서 그 글의 댓글들을 리턴
	private static String sql_likeCntUp = "UPDATE comments SET clikeCnt=clikeCnt+1 WHERE cnum=?";
	// 다른 테이블 접근 쿼리
	private static String sql_SELECT_ALL_REPLY = "SELECT * FROM reply WHERE r_comments=? ORDER BY rnum";
	private static String sql_comCntUp = "UPDATE post SET comCnt=comCnt+1 WHERE pnum=?";
	private static String sql_comCntDown = "UPDATE post SET comCnt=comCnt-? WHERE pnum=?";
	private static String sql_deleteReply = "DELETE FROM reply WHERE r_comments=?";
	
	public ArrayList<CommentsSet> getSetData(CommentsVO vo) {
		Connection conn = DBCP.connect();
		ArrayList<CommentsSet> result = new ArrayList<CommentsSet>();
		PreparedStatement pstmt = null;

		try{
			// c_post(글 번호)에 맞는 댓글들을 출력
			pstmt = conn.prepareStatement(sql_SELECT_POST);
			pstmt.setInt(1, vo.getC_post());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				// 글 하나당 CommentsSet 한개이므로 새로 생성
				CommentsSet cs = new CommentsSet(); 
				CommentsVO cvo = new CommentsVO();
				cvo.setCnum(rs.getInt("cnum"));
				cvo.setCment(rs.getString("cment"));
				cvo.setCdate(rs.getDate("cdate"));
				cvo.setCwriter(rs.getString("cwriter"));
				cvo.setReplyCnt(rs.getInt("replyCnt"));
				cvo.setClikeCnt(rs.getInt("clikeCnt"));
				cvo.setC_user(rs.getString("c_user"));
				cvo.setC_post(rs.getInt("c_post"));
				cvo.setSecretNum(rs.getInt("secretNum")); 
				// CommentsSet에 댓글을 set
				cs.setComment(cvo);

				// 2번째 쿼리 -> 댓글에 있는 답글들을 출력
				pstmt = conn.prepareStatement(sql_SELECT_ALL_REPLY);
				pstmt.setInt(1, cvo.getCnum()); // 댓글 PK가져오기 
				ResultSet rrs = pstmt.executeQuery();
				// rlist에 쿼리 결과(답글) 추가
				ArrayList<ReplyVO> rlist = new ArrayList<ReplyVO>();
				while(rrs.next()) {
					ReplyVO rvo = new ReplyVO();
					rvo.setRnum(rrs.getInt("rnum"));
					rvo.setRment(rrs.getString("rment"));
					rvo.setRdate(rrs.getDate("rdate"));
					rvo.setRwriter(rrs.getString("rwriter"));
					rvo.setRlikeCnt(rrs.getInt("rlikeCnt"));
					rvo.setR_user(rrs.getString("r_user"));
					rvo.setR_post(rrs.getInt("r_post"));
					rvo.setR_comments(rrs.getInt("r_comments"));
					rlist.add(rvo);
				}
				rrs.close();
				// CommentsSet에 답글 리스트 set
				cs.setRlist(rlist);
				// result에 CommentsSet 추가
				result.add(cs);
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("CommentsDAO getSetData에서 출력");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt, conn);
		}
		return result;
	}


	// SELECT ALL -> 전체 DB정보 추출
	public ArrayList<CommentsVO> SelectAll(){
		Connection conn = DBCP.connect();
		ArrayList<CommentsVO> datas = new ArrayList();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql_SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				CommentsVO vo = new CommentsVO();
				vo.setCnum(rs.getInt("cnum"));
				vo.setCment(rs.getString("cment"));
				vo.setCdate(rs.getDate("cdate"));
				vo.setCwriter(rs.getString("cwriter"));
				vo.setReplyCnt(rs.getInt("replyCnt"));
				vo.setClikeCnt(rs.getInt("clikeCnt"));
				vo.setC_user(rs.getString("c_user"));
				vo.setC_post(rs.getInt("c_post"));
				datas.add(vo);
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("CommentsDAO SelectAll()에서 출력");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt, conn);
		}
		return datas;
	}

	// SELECT ONE 
	public CommentsVO SelectOne(CommentsVO vo) {
		Connection conn=DBCP.connect();
		CommentsVO data=null;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_SELECT_ONE);
			pstmt.setInt(1, vo.getCnum());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				data=new CommentsVO();
				data.setCnum(rs.getInt("cnum"));
				data.setCment(rs.getString("cment"));
				data.setCdate(rs.getDate("cdate"));
				data.setCwriter(rs.getString("cwriter"));
				data.setReplyCnt(rs.getInt("replyCnt"));
				data.setClikeCnt(rs.getInt("clikeCnt"));
				data.setC_user(rs.getString("c_user"));
				data.setC_post(rs.getInt("c_post"));
			}   
			rs.close();
		}
		catch(Exception e){
			System.out.println("CommentsDAO SelectOne()에서 출력");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return data;
	}

	// INSERT -> 댓글 DB 등록 --> POST 테이블 댓글 수 증가 트랜잭션
	public boolean InsertDB(CommentsVO vo) {
		Connection conn=DBCP.connect();
		boolean res = false;
		PreparedStatement pstmt=null;
		try{
			// 댓글 INSERT
			conn.setAutoCommit(false);
			pstmt=conn.prepareStatement(sql_INSERT);
			pstmt.setString(1, vo.getCment());
			pstmt.setString(2, vo.getCwriter());
			pstmt.setString(3, vo.getC_user());
			pstmt.setInt(4, vo.getC_post());
			pstmt.setInt(5, vo.getSecretNum());
			pstmt.executeUpdate();
			pstmt.close();

			// "UPDATE post SET comCnt=comCnt+1 WHERE pnum=?"
			// POST 댓글 수 ++
			pstmt=conn.prepareStatement(sql_comCntUp);
			pstmt.setInt(1, vo.getC_post());
			pstmt.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			res=true;
		}
		catch(Exception e){
			System.out.println("CommentsDAO InsertDB()에서 출력");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			//res=false;
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return res;
	}

	// DELETE -> 댓글 삭제 -> 답글도 삭제
	public boolean DeleteDB(CommentsVO vo) {
		Connection conn=DBCP.connect();
		boolean res=false;
		PreparedStatement pstmt=null;
		try{
			conn.setAutoCommit(false);
			
			// 댓글삭제
			pstmt=conn.prepareStatement(sql_DELETE);
			pstmt.setInt(1, vo.getCnum());
			pstmt.executeUpdate();
			
			// 후 게시물의 댓글cnt 다운!
			pstmt=conn.prepareStatement(sql_comCntDown);
			pstmt.setInt(1, vo.getReplyCnt() + 1);
			pstmt.setInt(2, vo.getC_post());
			pstmt.executeUpdate();
			
			// 후 해당 댓글의 답글 삭제
			pstmt=conn.prepareStatement(sql_deleteReply);
			pstmt.setInt(1, vo.getCnum());
			pstmt.executeUpdate();
			
			
			conn.commit();
			conn.setAutoCommit(true);
			res=true;
		}
		catch(Exception e){
			System.out.println("CommentsDAO DeleteDB()에서 출력");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//res=false;
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return res;
	}

	// UPDATE -> 댓글 cment 수정
	public boolean UpdateDB(CommentsVO vo) {
		Connection conn=DBCP.connect();
		boolean res=false;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_UPDATE);
			pstmt.setString(1, vo.getCment());
			pstmt.setInt(2, vo.getCnum());
			pstmt.executeUpdate();
			res=true;
		}
		catch(Exception e){
			System.out.println("CommentsDAO UpdateDB()에서 출력");
			e.printStackTrace();
			//res=false;
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return res;
	}

	// 좋아요 ++
	public boolean likeCntUp(CommentsVO vo) {
		Connection conn=DBCP.connect();
		boolean res=false;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_likeCntUp);
			pstmt.setInt(1, vo.getCnum());
			pstmt.executeUpdate();
			res=true;
		}
		catch(Exception e){
			System.out.println("CommentsDAO likeCntUp()에서 출력");
			e.printStackTrace();
			//res=false;
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return res;
	}

	// 입력 vo 안에는 c_post만 존재하면 됩니다. 리턴은 그 글에 달린 댓글들만 뽑아줍니다. 
	public ArrayList<CommentsVO> SelectPost(CommentsVO vo){
		Connection conn = DBCP.connect();
		ArrayList<CommentsVO> datas = new ArrayList();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql_SELECT_POST);
			pstmt.setInt(1,vo.getC_post());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				CommentsVO data = new CommentsVO();
				data.setCnum(rs.getInt("cnum"));
				data.setCment(rs.getString("cment"));
				data.setCdate(rs.getDate("cdate"));
				data.setCwriter(rs.getString("cwriter"));
				data.setClikeCnt(rs.getInt("clikeCnt"));
				data.setC_user(rs.getString("c_user"));
				data.setC_post(rs.getInt("c_post"));
				datas.add(data);
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("CommentsDAO SelectPost()에서 출력");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt, conn);
		}
		return datas;
	}
}