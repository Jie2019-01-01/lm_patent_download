package org.patent.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.patent.entity.Patent_Info;

/**
 * 	数据库操作,包含CRUD
 * @author liuweijie
 *
 */
public class DBOperation {
	
	private static String sql = "select * from patent_info where flag=2";
	private static String updsql = "update patent_info set flag=1,REMARKS='下载成功' where IPC_CODE=? and DATE=?";
	
	/**
	 * 获取失败数据
	 */
	public List<Patent_Info> getFailData() {
		List<Patent_Info> list = new ArrayList<>();
		
		// 数据库连接
		Connection con = DBUtilLocal.getConnection();
		PreparedStatement statement = null;
		ResultSet query = null;
		
		try {
			statement = con.prepareStatement(sql);
			query = statement.executeQuery();
			while(query.next()) {
				Patent_Info p = new Patent_Info();
				p.setIpc_code(query.getString("IPC_CODE"));
				p.setDate(query.getString("DATE"));
				list.add(p);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 	更新数据库的标识
	 */
	public void update(Patent_Info p) {
		try {
			
			// 获取连接
			Connection con = DBUtilLocal.getConnection();
			
			PreparedStatement statement = null;
			
			statement = con.prepareStatement(updsql);
			statement.setString(1, p.getIpc_code());
			statement.setString(2, p.getDate());
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
