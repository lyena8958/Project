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
		
		// ����¡ó�� �޼��� ȣ��(uri ��ȯ)
		String path = new Post_Action().paging(request.getParameter("pnum"));

		HttpSession session = request.getSession();
		session.removeAttribute("userInfoData"); // ȸ�� ���� session removeó��
		
		// ���������� �̵�(������ ����)
		forward.setPath(path);
		forward.setRedirect(true);
		
		
		return forward;
	}

}