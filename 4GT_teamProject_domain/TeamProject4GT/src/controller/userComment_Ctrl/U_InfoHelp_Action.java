package controller.userComment_Ctrl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.userInfo.UserInfoDAO;
import model.userInfo.UserInfoVO;

public class U_InfoHelp_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
System.out.println("예나 : " + request.getParameter("type"));
		ActionForward forward = new ActionForward();

		// VO DAO 인스턴스화
		UserInfoVO userInfoVO = new UserInfoVO();
		UserInfoDAO userInfoDAO = new UserInfoDAO();

		// DAO수행 필요데이터 SET
		userInfoVO.setId(request.getParameter("id")+"@"+request.getParameter("mail"));
		//System.out.println(request.getParameter("id")+"@"+request.getParameter("mail"));

		// DAO 수행
		userInfoVO = userInfoDAO.Find(userInfoVO);
		//System.out.println(userInfoVO+"11");
		
		// response 전달 --- findUser
		request.setAttribute("findUser", userInfoVO);

		// ID찾기 --> view 반환 -> 객체 userInfo
		if(request.getParameter("type").equals("id")) {
			// informID 페이지 전송 -> 기존꺼
			//forward.setPath("informID.jsp");
			
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();

			// 반환여부 비교
				// 정보조회 실패
			if(userInfoVO == null) {
				// 자바스크립트를 이용하여 알림창 뒤, Login.jsp 페이지 이동
				out.println("<script>alert('존재하지 않는 ID입니다.'); window.close(); </script>");
			}
			else { // 정보조회 성공
				// 자바스크립트를 이용하여 알림창 뒤, Login.jsp 페이지 이동
				out.println("<script>alert('로그인이 가능한 ID입니다.'); window.close(); </script>");
			}

			return null;
		}
		// PW찾기 --> view 반환 == 객체 userInfo
		else if (request.getParameter("type").equals("pw")) {

			// informPW 페이지 전송
			forward.setPath("informPW.jsp");
		}
		
		
		// 페이지 전송설정
		forward.setRedirect(false); // forward

		
		return forward;
	}

}
