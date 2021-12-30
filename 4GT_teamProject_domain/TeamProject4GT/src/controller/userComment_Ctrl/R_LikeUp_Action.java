package controller.userComment_Ctrl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.comments.CommentsVO;
import model.reply.ReplyDAO;
import model.reply.ReplyVO;

public class R_LikeUp_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward action = new ActionForward();
		// likeCntUp에 필요한 정보 : rnum
		// 페이지에 돌아가기위해 필요한 정보 : pnum, index
		String pnum = request.getParameter("pnum");
		int rnum = Integer.parseInt(request.getParameter("rnum"));
		
		// DB업데이트를 위해 필요한 정보
		ReplyDAO RDAO = new ReplyDAO();
		ReplyVO RVO = new ReplyVO();
		RVO.setRnum(rnum);

		// DB업데이트, 페이징 처리
		String path = null;
		if (RDAO.likeCntUp(RVO)) {
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out=response.getWriter();
			
			ReplyVO newData = RDAO.SelectOne(RVO);
			String result = "[{\"rlikeCnt\":\"" + newData.getRlikeCnt()+"\"}]";
			out.println(result); // ajax 객체 전달

		}
		else {
			try {
				throw new Exception("C_LikeUp_Action 오류발생!");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		return null;
	}

	
}
