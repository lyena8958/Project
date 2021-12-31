package model.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBCP {
	
	public static Connection connect() {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/fourgt";
		//String url = "jdbc:mysql://183.111.242.23:3306/fourgt";
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
		
		return null; // ���ϵ��� �ʾ� �Ʒ����� ���� �Ǿ��ٸ� null ����
	}
	
	public static void disconnect(PreparedStatement pstmt, Connection conn) {
		try {
			pstmt.close();
			conn.close(); // Ŀ�ؼ��� close�� �ϸ鼭 ���õ� ���� ��� �ݾ��ֱ� ������ rs, pstmt close �����־ �����ϴ�. 
		} catch (Exception e) {
			System.out.println("disconnect()���� ���");
			e.printStackTrace();
		}
	}
		
}
