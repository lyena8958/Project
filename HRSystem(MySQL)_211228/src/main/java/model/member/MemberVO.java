package model.member;

import org.springframework.web.multipart.MultipartFile;

public class MemberVO {
	
	private int mnum; // PK 
	private String mName; // �̸�
	private String path; // ���ϰ�� 
	private String startDate; // �Ի���
	private String endDate; // �����
	private String birthDate; // �������
	private String teamName; // �μ���
	private String duty; // ����
	private String position; // ����
	private String work; // ��������
	private int mrank; // ����(����)
	private String mtype; // �Ի籸��(���/����)
	private MultipartFile FileUpload; 
	
	
	public int getMnum() {
		return mnum;
	}
	public void setMnum(int mnum) {
		this.mnum = mnum;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
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
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public int getMrank() {
		return mrank;
	}
	public void setMrank(int mrank) {
		this.mrank = mrank;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public MultipartFile getFileUpload() {
		return FileUpload;
	}
	public void setFileUpload(MultipartFile fileUpload) {
		FileUpload = fileUpload;
	}
	
	public String getMtype() {
		return mtype;
	}
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	@Override
	public String toString() {
		return "MemberVO [mnum=" + mnum + ", mName=" + mName + ", path=" + path + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", birthDate=" + birthDate + ", teamName=" + teamName + ", duty=" + duty
				+ ", position=" + position + ", work=" + work + ", mrank=" + mrank + ", mtype=" + mtype
				+ ", FileUpload=" + FileUpload + "]";
	}
	
	
	
	
}
