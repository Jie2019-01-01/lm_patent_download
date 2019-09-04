package org.patent.entity;

/**
 * 专利信息实体类
 * @author liuweijie
 *
 */
public class Patent_Info {

	private String ipc_code;
	private Integer row_count;
	private String date;
	private Integer flag;
	private String remarks;
	
	public String getIpc_code() {
		return ipc_code;
	}
	public void setIpc_code(String ipc_code) {
		this.ipc_code = ipc_code;
	}
	public Integer getRow_count() {
		return row_count;
	}
	public void setRow_count(Integer row_count) {
		this.row_count = row_count;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
