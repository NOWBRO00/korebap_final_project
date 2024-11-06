package com.korebap.app.biz.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUtil {
	private static final String driverName="com.mysql.cj.jdbc.Driver";
	private static final String url="jdbc:mysql://localhost:3306/moogun";
	private static final String userName="root";
	private static final String password="1234";
	
	public static Connection connect() {
		Connection conn=null;
		System.out.println("여기1");
		try {
			Class.forName(driverName);
			System.out.println("여기2");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			System.out.println("여기3");
		}
		
		try {
			conn=DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			System.out.println("conn 연결 실패");
		}
		return conn;
	}
	
	public static void disconnect(PreparedStatement pstmt, Connection conn) {
		try {
			pstmt.close();
			conn.close();	
		} catch (SQLException e) {
			System.out.println("연결 해제 실패");
		}		
	}
}
