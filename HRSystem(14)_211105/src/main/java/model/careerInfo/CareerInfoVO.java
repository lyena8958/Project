package model.careerInfo;

import java.sql.Date;

public class CareerInfoVO {

	private int cnum; // PK
	private String startDate; // �Ի���
	private String endDate; // ������
	private String compName; // ȸ���
	private String position; // ����
	private String duty; // ����
	private int rank; // ����(����)
	private int cmem; // FK
	
	public int getCnum() {
		return cnum;
	}
	public void setCnum(int cnum) {
		this.cnum = cnum;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}	
	public int getCmem() {
		return cmem;
	}
	public void setCmem(int cmem) {
		this.cmem = cmem;
	}
	@Override
	public String toString() {
		return "CareerInfoVO [cnum=" + cnum + ", startDate=" + startDate + ", endDate=" + endDate + ", compName="
				+ compName + ", position=" + position + ", duty=" + duty + ", rank=" + rank + ", cmem=" + cmem + "]";
	}
	
	
}
