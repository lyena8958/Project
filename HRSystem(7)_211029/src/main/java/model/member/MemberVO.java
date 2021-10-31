package model.member;

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
	private int mrank; // ����(����)
	
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	public int getMrank() {
		return mrank;
	}
	public void setMrank(int mrank) {
		this.mrank = mrank;
	}
	
	@Override
	public String toString() {
		return "MemberVO [mnum=" + mnum + ", mName=" + mName + ", path=" + path + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", birthDate=" + birthDate + ", teamName=" + teamName + ", duty=" + duty
				+ ", position=" + position + ", mrank=" + mrank + "]";
	}
	
	
	
}