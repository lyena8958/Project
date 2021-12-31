package controller.post_ctrl.likeinfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.likeInfo.LikeInfoDAO;
import model.likeInfo.LikeInfoVO;
import model.post.PostDAO;
import model.post.PostVO;
import model.userInfo.UserInfoVO;

public class LikeDownAciton implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward action = new ActionForward();
		PostDAO PDAO = new PostDAO();
		PostVO PVO = new PostVO();
		LikeInfoVO LVO = new LikeInfoVO();
		LikeInfoDAO LDAO = new LikeInfoDAO();

		// 좋아요 테이블 접근 정보 세팅
		HttpSession session = request.getSession();
		UserInfoVO UVO = (UserInfoVO)session.getAttribute("userInfoData");
		System.out.println("@@@@ LikeupUVO = "+UVO);
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		String id = UVO.getId();
		LVO.setL_post(pnum);
		LVO.setL_user(id);

		if(LDAO.DeleteDB(LVO)) {	// 업데이트 됬을시에만 Post 테이블 좋아요 수 추가
			// post 테이블 좋아요 수 + 1
			PVO.setPnum(pnum);
			action.setPath("selectOne.pdo?pnum="+pnum+"#title");
			action.setRedirect(true);
		}else {
			try {
				throw new Exception("insertLike 오류발생!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return action;
	}

}
