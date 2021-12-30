package model.userInfo;

public class UserInfoVO {
	private String id;
	private String pw;
	private String name;
	private String profile;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	@Override
	public String toString() {
		return "UserInfoVO [id=" + id + ", pw=" + pw + ", name=" + name + ", profile=" + profile + "]";
	}
}
