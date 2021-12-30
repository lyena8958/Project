package controller.userComment_Ctrl;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class M_Mail  extends Authenticator{
	// 각자의 구글 아이디를 입력
	private PasswordAuthentication pa;
	
	// 발신처 이메일 작성
	public M_Mail() {
		String mail_id = "4grouptuna@gmail.com"; // 이메일
		String mail_pw = "vtzyrhamiqdoasgq";	// 비밀번호
		pa = new PasswordAuthentication(mail_id, mail_pw);
	}
	public PasswordAuthentication getPasswordAuthentication() {
		return pa;
	}
}
