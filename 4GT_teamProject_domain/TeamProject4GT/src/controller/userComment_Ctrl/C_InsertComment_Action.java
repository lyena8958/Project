package controller.userComment_Ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.comments.CommentsDAO;
import model.comments.CommentsVO;

public class C_InsertComment_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// view拭辞 督虞耕斗級聖 穿含背爽檎(c_user, c_post, cment)
	    // set吉 commentVO稽 奇越蓄亜
		
		
		ActionForward forward = new ActionForward();
		
		// VO DAO 昔什渡什鉢
	    CommentsVO commentVO = new CommentsVO();
	    CommentsDAO commentDAO = new CommentsDAO();
	    
	    
	    // DAO呪楳 琶推汽戚斗 SET
	    commentVO.setCment(request.getParameter("cment"));
	    commentVO.setCwriter(request.getParameter("cwriter"));
	    commentVO.setC_user(request.getParameter("c_user"));
	    commentVO.setC_post(Integer.parseInt(request.getParameter("c_post")));
	    commentVO.setSecretNum(0); // 奄沙 0生稽 set
	    
	    // secretNum汽戚斗亜 角嬢尽陥檎 痕井
	    if(request.getParameter("secretNum") != null) {
	    	System.out.println("けしいけしけ");
	    	commentVO.setSecretNum(Integer.parseInt(request.getParameter("secretNum")));
	    }
	    

		String path = null; // uri痕呪 段奄鉢
		
	    //DAO 呪楳
	    // 奇越 蓄亜 刃戟
	    if (commentDAO.InsertDB(commentVO)) {
			// [凪戚臓坦軒 五辞球] 硲窒 (uri 鋼発)
	    	path = new Post_Action().paging(request.getParameter("c_post"));
			path += "#pcmsg"+request.getParameter("pcmsg");
	    }
	    // 鋼慎 叔鳶 -> 神嫌 呪楳
	    else {
	    	try {
				throw new Exception("C_InsertComment_Action 神嫌 降持!");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	    }
	    
	    // 穿勺 竺舛
	    forward.setRedirect(false); // forward
	    forward.setPath(path);
	    
	    return forward;
	}

}
