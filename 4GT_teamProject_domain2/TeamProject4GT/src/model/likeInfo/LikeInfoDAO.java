package model.likeInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.DBCP;
import model.likeInfo.LikeInfoVO;

public class LikeInfoDAO {
   
   // 기본 CRUD
   private static String sql_SELECT_ALL = "SELECT * FROM likeInfo"; // 추후 content, condition 나눠서 진행
   private static String sql_SELECT_ONE = "SELECT * FROM likeInfo WHERE l_user=? AND l_post=?";
   private static String sql_INSERT = "INSERT INTO likeInfo (l_user, l_post) VALUES(?, ?)";
   private static String sql_DELETE = "DELETE FROM likeInfo WHERE l_user=? AND l_post=?";
   
   // 사용자 정의 함수
   private static String sql_plikeUP = "UPDATE post SET plike=plike+1 WHERE pnum=?";
   private static String sql_plikeDown = "UPDATE post SET plike=plike-1 WHERE pnum=?";
   
   // private static String sql_UPDATE = "UPDATE likeInfo SET l_user=?, l_post=? WHERE l_post=?";
   // UPDATE 쓸 일 없을거같아서 일단 보류
   
   // SELECT ALL -> 전체 좋아요 정보 추출 
   public ArrayList<LikeInfoVO> SelectAll(){
      Connection conn = DBCP.connect();
      ArrayList<LikeInfoVO> datas = new ArrayList();
      PreparedStatement pstmt = null;

      try {
         pstmt = conn.prepareStatement(sql_SELECT_ALL);
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()) {
           LikeInfoVO vo = new LikeInfoVO();
            vo.setL_user(rs.getString("l_user"));
            vo.setL_post(rs.getInt("l_post"));
            datas.add(vo);
         }
         rs.close();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      finally {
         DBCP.disconnect(pstmt, conn);
      }
      return datas;
   }

   // SELECT ONE -> boolean 타입, 있으면 true 아니면 false 반환.
   public boolean SelectOne(LikeInfoVO vo) {
      Connection conn=DBCP.connect();
      PreparedStatement pstmt=null;
      boolean res = false;
      try{
         pstmt=conn.prepareStatement(sql_SELECT_ONE);
         pstmt.setString(1, vo.getL_user());
         pstmt.setInt(2, vo.getL_post());
         ResultSet rs=pstmt.executeQuery();
         if(rs.next()){
            res= true;
         }   
         rs.close();
      }
      catch(Exception e){
         e.printStackTrace();
      }
      finally {
         DBCP.disconnect(pstmt,conn);
      }
      return res;
   }
   
   // INSERT -> 좋아요 정보 저장 -> plike++ 트랜잭션 처리
   public boolean InsertDB(LikeInfoVO vo) {
      Connection conn=DBCP.connect();
      boolean res = false;
      PreparedStatement pstmt=null;
      try{
    	 // 좋아요 정보 저장
    	 conn.setAutoCommit(false);
         pstmt=conn.prepareStatement(sql_INSERT);
         pstmt.setString(1, vo.getL_user());
         pstmt.setInt(2, vo.getL_post());
         pstmt.executeUpdate();
         pstmt.close();
         
         // "UPDATE post SET plike=plike+1 WHERE pnum=?"
         // 해당 포스트의 좋아요(plike) ++
         pstmt=conn.prepareStatement(sql_plikeUP);
         pstmt.setInt(1, vo.getL_post());
         pstmt.executeUpdate();
         conn.commit();
         conn.setAutoCommit(true);
         res=true;
      }
      catch(Exception e){
    	  System.out.println("LikeInfoDAO InsertDB()에서 출력");
    	  try {
    		  conn.rollback();
    	  }
    	  catch (SQLException e1) {
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
   
   // DELETE -> 좋아요 취소 -> plike-- 트랜잭션 처리
   public boolean DeleteDB(LikeInfoVO vo) {
      Connection conn=DBCP.connect();
      boolean res=false;
      PreparedStatement pstmt=null;
      try{
         // 좋아요 정보 삭제
    	 conn.setAutoCommit(false);
         pstmt=conn.prepareStatement(sql_DELETE);
         pstmt.setString(1, vo.getL_user());
         pstmt.setInt(2, vo.getL_post());
         pstmt.executeUpdate();
         pstmt.close();
         
         // "UPDATE post SET plike=plike-1 WHERE pnum=?"
         // 해당 포스트의 좋아요(plike) --
         pstmt=conn.prepareStatement(sql_plikeDown);
         pstmt.setInt(1, vo.getL_post());
         pstmt.executeUpdate();
         conn.commit();
         conn.setAutoCommit(true);
         res=true;
      }
      catch(Exception e){
    	  System.out.println("LikeInfoDAO DeleteDB()에서 출력");
    	  try {
    		  conn.rollback();
    	  }
    	  catch (SQLException e1) {
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
   
   /*
   // UPDATE -> 카테고리, 제목, 내용 변경
   public boolean UpdateDB(LikeInfoVO vo) {
      Connection conn=DBCP.connect();
      boolean res=false;
      PreparedStatement pstmt=null;
      try{
         pstmt=conn.prepareStatement(sql_UPDATE);
         pstmt.setString(1, vo.getL_user());
         pstmt.setInt(2, vo.getL_post());
         pstmt.executeUpdate();
         res=true;
      }
      catch(Exception e){
         System.out.println("UpdateDB()에서 출력");
         e.printStackTrace();
         //res=false;
      }
      finally {
         DBCP.disconnect(pstmt,conn);
      }
      return res;
   }
   */
   
}