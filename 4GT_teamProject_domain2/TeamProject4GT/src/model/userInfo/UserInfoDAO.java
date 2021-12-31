package model.userInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.DBCP;
import model.userInfo.UserInfoVO;

public class UserInfoDAO {

	//  Basic CRUD form
	private static String sql_SELECT_ALL = "SELECT * FROM userInfo";
	private static String sql_SELECT_ONE = "SELECT * FROM userInfo WHERE id=? AND pw=?";
	private static String sql_INSERT = "INSERT INTO userInfo (id, pw, name) VALUES(?, ?, ?)";
	private static String sql_DELETE = "DELETE FROM userInfo WHERE id=?";
	private static String sql_UPDATE = "UPDATE userInfo SET name=?, pw=? WHERE id=?";

	// Various Functions form
	//private static String sql_FindID = "SELECT * FROM userInfo WHERE id=?";
	//private static String sql_FindPW = "SELECT * FROM userInfo WHERE id=?";
	//private static String sql_CheckID = "SELECT * FROM userInfo WHERE id=?";
	private static String sql_updateProfile = "UPDATE userInfo SET profile=? WHERE id=?";
	private static String sql_FindInfo = "SELECT * FROM userInfo WHERE id=?";


	// SELECT ALL
	public ArrayList<UserInfoVO> SelectAll(){
		Connection conn = DBCP.connect();
		ArrayList<UserInfoVO> datas = new ArrayList();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql_SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				UserInfoVO vo = new UserInfoVO();
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setName(rs.getString("name"));
				vo.setProfile(rs.getString("profile"));
				datas.add(vo);
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("UserDAO SelectAll() printed!");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt, conn);
		}
		return datas;
	}

	// SELECT ONE
	public UserInfoVO SelectOne(UserInfoVO vo) {
		Connection conn=DBCP.connect();
		UserInfoVO data=null;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_SELECT_ONE);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				data=new UserInfoVO();
				data.setId(rs.getString("id"));
				data.setPw(rs.getString("pw"));
				data.setName(rs.getString("name"));
				data.setProfile(rs.getString("profile"));
			}	
			rs.close();
		}
		catch(Exception e){
			System.out.println("UserDAO SelectOne() printed!");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return data;
	}

	// INSERT 
	public boolean InsertDB(UserInfoVO vo) {
		Connection conn=DBCP.connect();
		boolean res = false;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_INSERT);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.executeUpdate();
			res=true;
		}
		catch(Exception e){
			System.out.println("UserDAO InsertDB() printed!");
			e.printStackTrace();
			//res=false;
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return res;
	}

	// DELETE
	public boolean DeleteDB(UserInfoVO vo) {
		Connection conn=DBCP.connect();
		boolean res=false;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_DELETE);
			pstmt.setString(1, vo.getId());
			pstmt.executeUpdate();
			res=true;
		}
		catch(Exception e){
			System.out.println("UserDAO DeleteDB() printed!");
			e.printStackTrace();
			//res=false;
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return res;
	}

	// UPDATE
	public boolean UpdateDB(UserInfoVO vo) {
		Connection conn=DBCP.connect();
		boolean res=false;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_UPDATE);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getId());
			pstmt.executeUpdate();
			res=true;
		}
		catch(Exception e){
			System.out.println("UserDAO UpdateDB() printed!");
			e.printStackTrace();
			//res=false;
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return res;
	}


	// Method for find user ID and PW.
	public UserInfoVO Find(UserInfoVO vo) {
		Connection conn=DBCP.connect();
		UserInfoVO data=null;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_FindInfo);
			pstmt.setString(1, vo.getId());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				data=new UserInfoVO();
				data.setId(rs.getString("id"));
				data.setPw(rs.getString("pw"));
				data.setName(rs.getString("name"));
			}	
			rs.close();
		}
		catch(Exception e){
			System.out.println("UserDAO FindPW() printed!");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}
		return data;
	}


	public boolean UpdateProfile(UserInfoVO vo) {
		Connection conn=DBCP.connect();
		boolean res=false;
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql_updateProfile);
			pstmt.setString(1, vo.getProfile());
			pstmt.setString(2, vo.getId());
			pstmt.executeUpdate();
			res=true;
		}
		catch(Exception e){
			System.out.println("UserDAO UpdateProfile() printed!");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			//res=false;
		}
		return res;
	}
	// Method for find user's overlapped ID.
	public boolean CheckID(String id) {
		Connection conn=DBCP.connect();
		PreparedStatement pstmt=null;
		boolean exist=false;

		try{
			pstmt=conn.prepareStatement(sql_FindInfo);
			pstmt.setString(1, id);
			ResultSet rs=pstmt.executeQuery();

			while(rs.next()) {
				exist=true;
			}
			rs.close();
		}
		catch(Exception e){
			System.out.println("UserInfoDAO CheckID() printed!");
			e.printStackTrace();
		}
		finally {
			DBCP.disconnect(pstmt,conn);
		}		
		return exist;
	}
}
