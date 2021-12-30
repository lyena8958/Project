package model.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBCP {
	
	public static Connection connect() {
		String driver = "com.mysql.cj.jdbc.Driver";
		//String url = "jdbc:mysql://localhost:3306/fourgt";
		String url = "jdbc:mysql://183.111.242.23:3306/fourgt";
		String user = "fourgt";
		String password = "yena0207!!";

		Connection conn = null;
		try {
			Class.forName(driver);
			return conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null; // 리턴되지 않아 아래까지 오게 되었다면 null 리턴
	}
	
	public static void disconnect(PreparedStatement pstmt, Connection conn) {
		try {
			pstmt.close();
			conn.close(); // 커넥션이 close를 하면서 관련된 것을 모두 닫아주기 때문에 rs, pstmt close 안해주어도 무방하다. 
		} catch (Exception e) {
			System.out.println("disconnect()에서 출력");
			e.printStackTrace();
		}
	}
		
}
