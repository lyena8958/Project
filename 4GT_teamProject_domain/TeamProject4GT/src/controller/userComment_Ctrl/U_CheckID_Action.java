package controller.userComment_Ctrl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.reply.ReplyDAO;
import model.userInfo.UserInfoDAO;

public class U_CheckID_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		// DAO 인스턴스화
		UserInfoDAO userInfoDAO = new UserInfoDAO();
		
		
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		//System.out.println(request.getParameter("id")+"@"+request.getParameter("mail")+" asdasdadsa");
		//DAO 수행 ★JS로 구현예정
		if(userInfoDAO.CheckID(request.getParameter("id")+"@"+request.getParameter("mail"))){
			out.println("true"); // out.println으로 ajax data에게 데이터가 넘어가게됨
			
		}
		// 반영 실패 -> 오류 수행
		else { 
			out.println("false");
		}
		// 이동경로 없음
		return null;
	}

}
