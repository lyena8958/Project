package model.post;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.common.DBCP;
import model.userInfo.UserInfoVO;

public class PostDAO {
	
	// 
	private static String sql_SELECT_ALL = "SELECT * FROM post ORDER BY pnum DESC";
	private static String sql_SELECT_ONE = "SELECT * FROM post WHERE pnum=?";
	
//	MySql sql
	private static String sql_INSERT = 
			"INSERT INTO post (category, title, content, writer, p_user, path)"
			+ " VALUES(?, ?, ?, ?, ?, ?)";
//	oracle sql
//	private static String sql_INSERT = 
//			"INSERT INTO post (pnum, category, title, content, writer, p_user, path)"
//			+ " VALUES((SELECT NVL(MAX(pnum),0) + 1 FROM post), ?, ?, ?, ?, ?, ?)";
	private static String sql_DELETE = "DELETE FROM post WHERE pnum=?";
	
	// MySql sql
	private static String sql_UPDATE = "UPDATE post SET category=?, title=?, content=?, writer=?, path=?, pdate=now() WHERE pnum=?";
	
	// oracle sql
//	private static String sql_UPDATE = "UPDATE post SET category=?, title=?, content=?, writer=?, path=?, pdate=sysdate WHERE pnum=?";
	
	
	// 
	// 
	private static String sql_ViewsUp = "UPDATE post SET views=views+1 WHERE pnum=?";
	private static String sql_LikesUp = "UPDATE post SET plike=plike+1 WHERE pnum=?";
	private static String sql_LikesDown = "UPDATE post SET plike=plike-1 WHERE pnum=?";
	// 

	//  
	private static String sql_SELECT_CATEGORY = "SELECT * FROM post WHERE category=? ORDER BY pnum DESC";
	
	// Mysql sql
	private static String sql_SELECT_VIEWS = "SELECT * FROM post ORDER BY VIEWS DESC LIMIT 10";
	
	// oracle sql
//	private static String sql_SELECT_VIEWS = "SELECT * FROM (SELECT * FROM post ORDER BY views DESC) WHERE ROWNUM <= 10";
	
	
	private static String sql_SELECT_CATEGORYFORVIEWS = "SELECT * FROM post WHERE category=? ORDER BY views DESC";
	
	// MySql sql
	private static String sql_getPnum = "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'post' AND table_schema = DATABASE()";
	
	// oracle sql 
//	private static String sql_getPnum = "SELECT NVL(MAX(pnum),0) + 1 AS pnum FROM post";
	
	// 
	private static String sql_SELECT_MYPOST = "SELECT * FROM post WHERE p_user=? ORDER BY pnum DESC";
	
	// 
	private static String sql_SELECT_LIKEPOST = "SELECT l_post FROM likeInfo WHERE l_user=? ORDER BY ldate DESC";
	private static String sql_SELECT_POSTINFO = "SELECT * FROM post WHERE pnum=?";
	
	// 
	public ArrayList<PostVO> SelectAll(){
		Connection conn = DBCP.connect();
		ArrayList<PostVO> datas = new ArrayList<PostVO>();
		PreparedStatement pstmt = null;
		SimpleDateFormat dateFix = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dateOrigin;
		String dateToStr;
		try {
			pstmt = conn.prepareStatement(sql_SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				PostVO vo = new PostVO();
				vo.setPnum(rs.getInt("pnum"));
				vo.setViews(rs.getInt("views"));
				vo.setPlike(rs.getInt("plike"));
				vo.setCategory(rs.getString("category"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setWriter(rs.getString("writer"));
				dateOrigin = rs.getDate("pdate");
				dateToStr = dateFix.format(dateOrigin);
				vo.setPdate(dateToStr);
				vo.setP_user(rs.getString("p_user"));
				vo.setPath(rs.getString("path"));
				vo.setComCnt(rs.getInt("comCnt"));
				datas.add(vo);
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("PostDAO SelectAll() printed!");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt, conn);
		}
		return datas;
	}

	// 
	public PostVO SelectOne(PostVO vo) {
	      Connection conn=DBCP.connect();
	      PostVO data=null;
	      PreparedStatement pstmt=null;
	      SimpleDateFormat dateFix = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	      Date dateOrigin;
	      String dateToStr;
	      try{
	         conn.setAutoCommit(false);		// 
	         // showPost
	         pstmt=conn.prepareStatement(sql_SELECT_ONE);
	         pstmt.setInt(1, vo.getPnum());
	         ResultSet rs=pstmt.executeQuery();
	         if(rs.next()){
	            data=new PostVO();
	            data.setPnum(rs.getInt("pnum"));
	            data.setViews(rs.getInt("views"));
	            data.setPlike(rs.getInt("plike"));
	            data.setCategory(rs.getString("category"));
	            data.setTitle(rs.getString("title"));
	            data.setContent(rs.getString("content"));
	            data.setWriter(rs.getString("writer"));
	            dateOrigin = rs.getDate("pdate");
	            dateToStr = dateFix.format(dateOrigin);
	            data.setPdate(dateToStr);
	            data.setP_user(rs.getString("p_user"));
	            data.setPath(rs.getString("path"));
	            data.setComCnt(rs.getInt("comCnt"));
	            System.out.println(data);
	         }   
	         rs.close();
	         
	         // ViewsUp
	         pstmt=conn.prepareStatement(sql_ViewsUp); //
	         pstmt.setInt(1, vo.getPnum());
	         pstmt.executeUpdate();
	         conn.commit(); // 
	      }
	      catch(Exception e){
	         System.out.println("PostDAO SelectOne() printed!");
	         e.printStackTrace();
	         try {
	            conn.rollback();
	         } catch (SQLException e1) {
	            e1.printStackTrace();
	         }
	      }
	      finally {
	         DBCP.disconnect(pstmt,conn);
	      }
	      return data;
	   }
	
	// InsertPost
	public boolean InsertDB(PostVO vo) {
		Connection conn=DBCP.connect();
		boolean res = false;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_INSERT);
			pstmt.setString(1, vo.getCategory());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setString(4, vo.getWriter());
			pstmt.setString(5, vo.getP_user());
			pstmt.setString(6, vo.getPath());
			pstmt.executeUpdate();
			res=true;
		}
		catch(Exception e){
			System.out.println("PostDAO InsertDB() printed!");
			e.printStackTrace();
			//res=false;
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return res;
	}
	
	// DeletePost
	public boolean DeleteDB(PostVO vo) {
		Connection conn=DBCP.connect();
		boolean res=false;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_DELETE);
			pstmt.setInt(1, vo.getPnum());
			pstmt.executeUpdate();
			res=true;
		}
		catch(Exception e){
			System.out.println("PostDAO DeleteDB() printed!");
			e.printStackTrace();
			//res=false;
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return res;
	}

	// EditPost
	public boolean UpdateDB(PostVO vo) {
		Connection conn=DBCP.connect();
		boolean res=false;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_UPDATE);
			pstmt.setString(1, vo.getCategory());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setString(4, vo.getWriter());
			pstmt.setString(5,  vo.getPath());
			pstmt.setInt(6, vo.getPnum());
			pstmt.executeUpdate();
			res=true;
		}
		catch(Exception e){
			System.out.println("PostDAO UpdateDB() printed!");
			e.printStackTrace();
			//res=false;
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return res;
	}
	
	//Condition
	public ArrayList<PostVO> searchPost(String condition,String text){
		String sql_SearchPost = "SELECT * FROM post WHERE "+condition+" LIKE ? ORDER BY pnum DESC";
		Connection conn = DBCP.connect();
		ArrayList<PostVO> datas = new ArrayList<PostVO>();
		PreparedStatement pstmt = null;
		SimpleDateFormat dateFix = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dateOrigin;
		String dateToStr;
		try {
			pstmt = conn.prepareStatement(sql_SearchPost);
			pstmt.setString(1, text);
			
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				PostVO vo = new PostVO();
				vo.setPnum(rs.getInt("pnum"));
				vo.setViews(rs.getInt("views"));
				vo.setPlike(rs.getInt("plike"));
				vo.setCategory(rs.getString("category"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setWriter(rs.getString("writer"));
				dateOrigin = rs.getDate("pdate");
				dateToStr = dateFix.format(dateOrigin);
				vo.setPdate(dateToStr);
				vo.setP_user(rs.getString("p_user"));
				vo.setPath(rs.getString("path"));
				vo.setComCnt(rs.getInt("comCnt"));
				System.out.println("DAO  : "+vo);
				datas.add(vo);
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("PostDAO SearchPost() printed!");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt, conn);
		}
		System.out.println("DAO datas : "+datas);
		return datas;
	}
	
	
	
	// 
    public ArrayList<PostVO> SelectCategory(PostVO vo){
       Connection conn = DBCP.connect();
       ArrayList<PostVO> datas = new ArrayList();
       PreparedStatement pstmt = null;
       SimpleDateFormat dateFix = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
       Date dateOrigin;
       String dateToStr;
       try {
          pstmt = conn.prepareStatement(sql_SELECT_CATEGORY);
          pstmt.setString(1, vo.getCategory());
          ResultSet rs = pstmt.executeQuery();
          while(rs.next()) {
             PostVO data = new PostVO();
             data.setPnum(rs.getInt("pnum"));
             data.setViews(rs.getInt("views"));
             data.setPlike(rs.getInt("plike"));
             data.setCategory(rs.getString("category"));
             data.setTitle(rs.getString("title"));
             data.setContent(rs.getString("content"));
             dateOrigin = rs.getDate("pdate");
             dateToStr = dateFix.format(dateOrigin);
             data.setPdate(dateToStr);
             data.setP_user(rs.getString("p_user"));
             data.setWriter(rs.getString("writer"));
             data.setComCnt(rs.getInt("comCnt"));
             data.setPath(rs.getString("path"));
             datas.add(data);
          }
          rs.close();
       }
       catch(Exception e) {
          System.out.println("PostDAO SelectCategory() printed!");
          e.printStackTrace();
       }
       finally {
          DBCP.disconnect(pstmt, conn);
       }
       return datas;
    }
    
    // 
    public ArrayList<PostVO> SelectViews(){
       Connection conn = DBCP.connect();
       ArrayList<PostVO> datas = new ArrayList();
       PreparedStatement pstmt = null;
       SimpleDateFormat dateFix = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
       Date dateOrigin;
       String dateToStr;
       try {
          pstmt = conn.prepareStatement(sql_SELECT_VIEWS);
          ResultSet rs = pstmt.executeQuery();
          while(rs.next()) {
             PostVO vo = new PostVO();
             vo.setPnum(rs.getInt("pnum"));
             vo.setViews(rs.getInt("views"));
             vo.setPlike(rs.getInt("plike"));
             vo.setCategory(rs.getString("category"));
             vo.setTitle(rs.getString("title"));
             vo.setContent(rs.getString("content"));
             dateOrigin = rs.getDate("pdate");
             dateToStr = dateFix.format(dateOrigin);
             vo.setPdate(dateToStr);
             vo.setP_user(rs.getString("p_user"));
             vo.setWriter(rs.getString("writer"));
             vo.setComCnt(rs.getInt("comCnt"));
             vo.setPath(rs.getString("path"));
             datas.add(vo);
          }
          rs.close();
       }
       catch(Exception e) {
          System.out.println("PostDAO SelectViews() printed!");
          e.printStackTrace();
       }
       finally {
          DBCP.disconnect(pstmt, conn);
       }
       return datas;
    }
    
    public ArrayList<PostVO> SelectCategoryForViews(PostVO vo){
        Connection conn = DBCP.connect();
        ArrayList<PostVO> datas = new ArrayList();
        PreparedStatement pstmt = null;
        SimpleDateFormat dateFix = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dateOrigin;
        String dateToStr;
        try {
           pstmt = conn.prepareStatement(sql_SELECT_CATEGORYFORVIEWS);
           pstmt.setString(1, vo.getCategory());
           ResultSet rs = pstmt.executeQuery();
           while(rs.next()) {
              PostVO data = new PostVO();
              data.setPnum(rs.getInt("pnum"));
              data.setViews(rs.getInt("views"));
              data.setPlike(rs.getInt("plike"));
              data.setCategory(rs.getString("category"));
              data.setTitle(rs.getString("title"));
              data.setContent(rs.getString("content"));
              dateOrigin = rs.getDate("pdate");
              dateToStr = dateFix.format(dateOrigin);
              data.setPdate(dateToStr);
              data.setP_user(rs.getString("p_user"));
              data.setWriter(rs.getString("writer"));
              data.setComCnt(rs.getInt("comCnt"));
              data.setPath(rs.getString("path"));
              datas.add(data);
           }
           rs.close();
        }
        catch(Exception e) {
           System.out.println("PostDAO SelectCategoryForViews() printed!");
           e.printStackTrace();
        }
        finally {
           DBCP.disconnect(pstmt, conn);
        }
        return datas;
     }
    
    // 
    public int expectPnum() {
		Connection conn = DBCP.connect();
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql_getPnum);
			ResultSet rs = pstmt.executeQuery();
		
			if(rs.next()) {
				result = rs.getInt("AUTO_INCREMENT");	
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("PostDAO expectPnum() printed!");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt, conn);
		}
		return result;
	}
    
 
 	public boolean ViewsUp(PostVO vo) {
 		Connection conn=DBCP.connect();
 		boolean res=false;
 		PreparedStatement pstmt=null;
 		try{
 			pstmt=conn.prepareStatement(sql_ViewsUp);
 			pstmt.setInt(1, vo.getPnum());
 			pstmt.executeUpdate();
 			res=true;
 		}
 		catch(Exception e){
 			System.out.println("PostDAO ViewsUp() printed!");
 			e.printStackTrace();
 			//res=false;
 		}
 		finally {
 			DBCP.disconnect(pstmt,conn);
 		}
 		return res;
 	}

 	public boolean LikesUp(PostVO vo) {
 		Connection conn=DBCP.connect();
 		boolean res=false;
 		PreparedStatement pstmt=null;
 		try{
 			pstmt=conn.prepareStatement(sql_LikesUp);
 			pstmt.setInt(1, vo.getPnum());
 			pstmt.executeUpdate();
 			res=true;
 		}
 		catch(Exception e){
 			System.out.println("PostDAO LikesUp() printed!");
 			e.printStackTrace();
 			//res=false;
 		}
 		finally {
 			DBCP.disconnect(pstmt,conn);
 		}
 		return res;
 	}
 
 	public boolean LikesDown(PostVO vo) {
 		
 		Connection conn=DBCP.connect();
 		boolean res=false;
 		PreparedStatement pstmt=null;
 		try{
 			pstmt=conn.prepareStatement(sql_LikesDown);
 			pstmt.setInt(1, vo.getPnum());
 			pstmt.executeUpdate();
 			res=true;
 		}
 		catch(Exception e){
 			System.out.println("PostDAO LikesDown() printed!");
 			e.printStackTrace();
 			//res=false;
 		}
 		finally {
 			DBCP.disconnect(pstmt,conn);
 		}
 		return res;
 	}
 	
 	public ArrayList<PostVO> SelectMyPost(UserInfoVO vo){
 		System.out.println(vo.getId());
 		Connection conn = DBCP.connect();
		ArrayList<PostVO> datas = new ArrayList<PostVO>();
		PreparedStatement pstmt = null;
		SimpleDateFormat dateFix = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dateOrigin;
		String dateToStr;
		
		try {
			pstmt = conn.prepareStatement(sql_SELECT_MYPOST);
			pstmt.setString(1, vo.getId());
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				PostVO data = new PostVO();
				data.setPnum(rs.getInt("pnum"));
				data.setViews(rs.getInt("views"));
				data.setPlike(rs.getInt("plike"));
				data.setCategory(rs.getString("category"));
				data.setTitle(rs.getString("title"));
				data.setContent(rs.getString("content"));
				data.setWriter(rs.getString("writer"));
				dateOrigin = rs.getDate("pdate");
				dateToStr = dateFix.format(dateOrigin);
				data.setPdate(dateToStr);
				data.setP_user(rs.getString("p_user"));
				data.setPath(rs.getString("path"));
				data.setComCnt(rs.getInt("comCnt"));
				datas.add(data);
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("PostDAO SelectMyPost() printed!");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt, conn);
		}
		return datas;
	}
 	
 	public ArrayList<PostVO> SelectLikePost(String id){
		Connection conn = DBCP.connect();
		ArrayList<PostVO> datas = new ArrayList<PostVO>();
		PreparedStatement pstmt = null;
		SimpleDateFormat dateFix = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dateOrigin;
		String dateToStr;
		try {
			pstmt = conn.prepareStatement(sql_SELECT_LIKEPOST);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				pstmt = conn.prepareStatement(sql_SELECT_POSTINFO);
				pstmt.setInt(1, rs.getInt("l_post"));
				ResultSet rrs = pstmt.executeQuery();
				if(rrs.next()) {
					PostVO data = new PostVO();
					data.setPnum(rrs.getInt("pnum"));
					data.setViews(rrs.getInt("views"));
					data.setPlike(rrs.getInt("plike"));
					data.setCategory(rrs.getString("category"));
					data.setTitle(rrs.getString("title"));
					data.setContent(rrs.getString("content"));
					data.setWriter(rrs.getString("writer"));
					dateOrigin = rrs.getDate("pdate");
					dateToStr = dateFix.format(dateOrigin);
					data.setPdate(dateToStr);
					data.setP_user(rrs.getString("p_user"));
					data.setPath(rrs.getString("path"));
					data.setComCnt(rrs.getInt("comCnt"));
					datas.add(data);
				}
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("PostDAO SelectLikePost() printed!");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt, conn);
		}
		return datas;
	}
}
