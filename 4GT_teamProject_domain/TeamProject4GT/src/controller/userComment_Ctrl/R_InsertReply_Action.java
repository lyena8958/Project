package controller.userComment_Ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.reply.ReplyDAO;
import model.reply.ReplyVO;

public class R_InsertReply_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		
		// VO DAO 인스턴스화
		ReplyDAO replyDAO = new ReplyDAO();
		ReplyVO replyVO = new ReplyVO();
		
	    // DAO수행 필요데이터 SET
		replyVO.setRment(request.getParameter("rment")); // 리플내용
		replyVO.setRwriter(request.getParameter("rwriter")); // 작성자
		replyVO.setR_user(request.getParameter("r_user")); // id
		replyVO.setR_post(Integer.parseInt(request.getParameter("r_post"))); // postPK 	
		replyVO.setR_comments(Integer.parseInt(request.getParameter("r_comments")));  //commentPK
		
		
		String path = null; // uri변수 초기화
		
	    //DAO 수행
		if(replyDAO.InsertDB(replyVO)) {
			// [페이징처리 메서드] 호출 (uri 반환)
	    	path = new Post_Action().paging(request.getParameter("r_post"));
	    	
			path += "#prmsg"+request.getParameter("prmsg");
		}
		// 반영 실패 -> 오류 수행
		else {
		 	try {
				throw new Exception("R_InsertReply_Action 오류 발생!");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
	    // 전송 설정
	    forward.setRedirect(false); // sendRedirect
	    forward.setPath(path);
	    
	    
	    
		return forward;
	}

}
