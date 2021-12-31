package controller.userComment_Ctrl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.reply.ReplyDAO;
import model.reply.ReplyVO;

public class R_UpdateReply_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out=response.getWriter();
		// VO DAO 인스턴스화
		ReplyDAO replyDAO = new ReplyDAO();
		ReplyVO replyVO = new ReplyVO();
		ReplyVO newData = new ReplyVO();
		// DAO수행 필요데이터 SET
		replyVO.setRnum(Integer.parseInt(request.getParameter("rnum")));
		replyVO.setRment(request.getParameter("rment"));
		String path = null; // uri변수 초기화

		//DAO 수행
		if(replyDAO.UpdateDB(replyVO)) {
//			// [페이징처리 메서드] 호출 (uri 반환)
//			path = new Post_Action().paging(request.getParameter("r_post"));
//			path += "#prmsg"+request.getParameter("prmsg");
			newData = replyDAO.SelectOne(replyVO);
		}
		// 반영 실패 -> 오류 수행
		else {
			try {
				throw new Exception("R_UpdateReply_Action 오류 발생!");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		String msg = newData.getRment().replace("\n"," ");
	    String result = "[{\"rment\":\"" + msg+"\",\"cdate\":\"" + newData.getRdate() + "\"}]";
	    out.println(result);
	    return null;
	}

}
