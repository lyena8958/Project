package controller.userComment_Ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.userInfo.UserInfoDAO;
import model.userInfo.UserInfoVO;

public class U_UpdateUser_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();

		
		// 기존 세션 - 유저 데이터 업데이트(session set)
		HttpSession session = request.getSession();
		
		// VO DAO 인스턴스화
		UserInfoVO userInfoVO = (UserInfoVO)session.getAttribute("userInfoData");
		
		UserInfoDAO userInfoDAO = new UserInfoDAO();
		
		// DAO수행 필요데이터 SET
		userInfoVO.setId(request.getParameter("id"));
		userInfoVO.setPw(request.getParameter("pw"));
		userInfoVO.setName(request.getParameter("name"));
		
		// DAO수행
		// 실패시 - 오류 수행
		if(!userInfoDAO.UpdateDB(userInfoVO)) {

			try {
				throw new Exception("UpdateUser_Action DB 오류 발생!");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}
		
		// 성공시 - 페이지 이동
		
		// 페이지 전송설정
		forward.setRedirect(true); // forward
		forward.setPath("MyPage.jsp");


		return forward;
	}

}
