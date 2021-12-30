package controller.userComment_Ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;

public class U_logOut_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ActionForward forward = new ActionForward();
		
		// 페이징처리 메서드 호출(uri 반환)
		String path = new Post_Action().paging(request.getParameter("pnum"));

		HttpSession session = request.getSession();
		session.removeAttribute("userInfoData"); // 회원 정보 session remove처리
		
		// 메인페이지 이동(나에게 보냄)
		forward.setPath(path);
		forward.setRedirect(true);
		
		
		return forward;
	}

}
