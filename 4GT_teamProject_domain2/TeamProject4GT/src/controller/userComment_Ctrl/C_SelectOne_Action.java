package controller.userComment_Ctrl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.comments.CommentsDAO;
import model.comments.CommentsSet;
import model.comments.CommentsVO;
import model.post.PostVO;

public class C_SelectOne_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// view에게 3가지 데이터 모두 전달
		// ① 단일 post  ② 좋아요 수  ③ 1의 댓글목록  ---- ①, ②는 post컨트롤 에게서 받음

		ActionForward forward = new ActionForward();

		// ① singlePost
		request.setAttribute("singlePost", request.getAttribute("singlePost"));
		System.out.println(request.getAttribute("singlePost"));
		// ② like
		request.setAttribute("likeInfo", request.getAttribute("likeInfo"));
		System.out.println( request.getAttribute("likeInfo"));


		// ③ postOne_comments
		// post 컨트롤에게 단일 게시물 정보를 받아오고,
		// DAO를 통해 해당 게시물의 모든 댓글을 AL에 받아옴  

		// VO DAO 인스턴스화
		CommentsVO commentVO = new CommentsVO();
		CommentsDAO commentDAO = new CommentsDAO();

		// DAO수행 필요데이터 SET
		commentVO.setC_post(((PostVO)request.getAttribute("singlePost")).getPnum());

		// DAO수행
		ArrayList<CommentsSet> postOne_comments = commentDAO.getSetData(commentVO);
		int commentCnt = 5;			
		String commentCntt = request.getParameter("ccnt");
		if (commentCntt!=null&&commentCntt.length()!=0) {
			commentCnt = Integer.parseInt(commentCntt);
		}
		String url = "ShowPost.jsp?ccnt="+commentCnt;
		
		if(postOne_comments.size()!=0) {
			ArrayList<CommentsSet> sliceComSet = new ArrayList<CommentsSet>();
			for(int i=0;i<commentCnt;i++) {
				CommentsSet vo = new CommentsSet();
				vo = postOne_comments.get(i);
				sliceComSet.add(vo);
				if(i+1==postOne_comments.size()) {
					break;
				}
			}
			// 댓글이 1개라도 있을때는 잘라낸 댓글을 보여줌!
			request.setAttribute("postOne_comments", sliceComSet);
			request.setAttribute("ccnt", commentCnt);
			
			// 페이지 전송설정
			forward.setRedirect(false); // forward
			forward.setPath(url);
			return forward;
		}


		// ③ request 전달	// 댓글이 하나도 없을 때 댓글 정보 전달
		request.setAttribute("postOne_comments", postOne_comments);

		// 페이지 전송설정
		forward.setRedirect(false); // forward
		forward.setPath(url);


		return forward;
	}

}
