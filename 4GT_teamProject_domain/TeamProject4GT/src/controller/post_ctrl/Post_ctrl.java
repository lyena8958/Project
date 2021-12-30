package controller.post_ctrl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ActionForward;
import controller.post_ctrl.likeinfo.LikeDownAciton;
import controller.post_ctrl.likeinfo.LikeUpAction;
import controller.post_ctrl.post.create.InsertPostAction;
import controller.post_ctrl.post.delete.DeleteAction;
import controller.post_ctrl.post.read.FindPost;
import controller.post_ctrl.post.read.MainAction;
import controller.post_ctrl.post.read.PostAction;
import controller.post_ctrl.post.read.SelectOneAction;
import controller.post_ctrl.post.read.ShowMypage;
import controller.post_ctrl.post.update.EditPostAction;
import controller.post_ctrl.post.update.EditPostDB;

/**
 * Servlet implementation class Post_ctrl
 */
@WebServlet("/Post_ctrl")
public class Post_ctrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Post_ctrl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uri=request.getRequestURI();
		String cp=request.getContextPath();
		String action=uri.substring(cp.length());
		System.out.println("---------------------------");
		System.out.println("PC action : "+action);
		ActionForward forward = null;
		if(action.equals("/main.pdo")) {
			forward = new MainAction().execute(request, response);
		}else if(action.equals("/post.pdo")) {
			forward = new PostAction().execute(request, response);
		}else if(action.equals("/selectOne.pdo")) {
			forward = new SelectOneAction().execute(request, response);
		}else if(action.equals("/insertPostDB.pdo")) {
			forward = new InsertPostAction().execute(request, response);
		}else if(action.equals("/editPost.pdo")) {
			// System.out.println("/editPost.pdo 들어왔다");
			forward = new EditPostAction().execute(request, response);
			// System.out.println("EditPostAction 종료");
		}else if(action.equals("/editPostDB.pdo")) {
			forward = new EditPostDB().execute(request, response);
		}else if(action.equals("/deletePostDB.pdo")) {
			forward = new DeleteAction().execute(request, response);
		}else if(action.equals("/likeUp.pdo")) {
			forward = new LikeUpAction().execute(request, response);
		}else if(action.equals("/likeDown.pdo")) {
			forward = new LikeDownAciton().execute(request, response);
		}else if(action.equals("/findpost.pdo")){
			forward = new FindPost().execute(request, response);
		}
		else if(action.equals("/showMyPost.pdo")){
			forward = new ShowMypage().execute(request, response);
		}
		else {
			forward.setPath("error/error404.jsp");	// 404페이지로 보냄
			forward.setRedirect(false);
		}
		if(forward != null) {
			//System.out.println(forward);
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			}
			else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}

	}
}