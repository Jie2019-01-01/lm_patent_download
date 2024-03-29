package org.patent.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBUtilLocal {

	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	private static BasicDataSource dataSource;

	static {
		Properties p = new Properties();
		try {
			p.load(DBUtilLocal.class.getClassLoader().getResourceAsStream(
					"db.properties"));
			dataSource = (BasicDataSource) BasicDataSourceFactory
					.createDataSource(p);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("初始化连接池失败！");
		}
	}

	public static synchronized Connection getConnection() {
		Connection con = tl.get();
		if (con == null) {
			try {
				con = dataSource.getConnection();
				tl.set(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tl.get();
	}

	public static synchronized void closeConnection() {
		if (tl.get() != null) {
			try {
				tl.get().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			tl.set(null);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getConnection());
	}
}
