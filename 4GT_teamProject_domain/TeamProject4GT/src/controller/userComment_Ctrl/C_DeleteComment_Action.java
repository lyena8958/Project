
package controller.userComment_Ctrl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.comments.CommentsDAO;
import model.comments.CommentsVO;

public class C_DeleteComment_Action implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		
		// VO DAO 인스턴스화
	    CommentsVO commentVO = new CommentsVO();
	    CommentsDAO commentDAO = new CommentsDAO();
	    
	    
	    // DAO수행 필요데이터 SET
	    commentVO.setCnum(Integer.parseInt(request.getParameter("cnum")));
	    commentVO.setC_post(Integer.parseInt(request.getParameter("c_post"))); // 트랜잭션 쿼리문 수행 위함
	    commentVO.setReplyCnt(Integer.parseInt(request.getParameter("replyCnt"))); // 트랜잭션 쿼리문 수행 위함
	    
		String path = null; // uri변수 초기화
		
	    //DAO 수행
	    // 댓글 삭제처리
	    if (commentDAO.DeleteDB(commentVO)) {
	    	
		    // 변경) ajax데이터 전달
	 	   response.setContentType("text/html; charset=UTF-8"); 
	 		PrintWriter out=response.getWriter();
	 	    /*commentVO = commentDAO.SelectOne(commentVO);*/
	 	    out.println("true"); // ajax 객체 전달
	 	    
	 	    
			// 이전) [페이징처리 메서드] 호출 (uri 반환)
	    	/*path = new Post_Action().paging(request.getParameter("c_post"));
			path += "#pcmsg"+request.getParameter("pcmsg");*/
	    }
	    // 반영실패 -> 오류수행
	    else {
	    	try {
				throw new Exception("C_DeleteComment_Action 오류발생!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    

	    
	    	    
	    // 전송 설정
	    //forward.setRedirect(false); // sendRedirect
	   // forward.setPath(path);
	    
	    return null;
	}
}
