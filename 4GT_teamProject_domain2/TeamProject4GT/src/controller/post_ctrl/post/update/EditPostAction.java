package controller.post_ctrl.post.update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import controller.Action;
import controller.ActionForward;
import model.post.PostDAO;
import model.post.PostVO;
import model.userInfo.UserInfoVO;

public class EditPostAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//System.out.println("EditPostAction ¿Ô´ç!");
		ActionForward action = new ActionForward();
		PostDAO PDAO = new PostDAO();
		PostVO PVO = new PostVO();
		
		PVO.setPnum(Integer.parseInt(request.getParameter("pnum")));
		PVO = PDAO.SelectOne(PVO);
		System.out.println("EditPostAction : "+PVO);
		request.setAttribute("singlePost", PVO);
		action.setPath("EditPost.jsp");
		action.setRedirect(false);
		
		return action;
	}

}
