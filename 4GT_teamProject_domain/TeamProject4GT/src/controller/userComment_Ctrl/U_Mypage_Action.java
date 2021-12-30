package controller.userComment_Ctrl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.post.PostDAO;
import model.post.PostVO;
import model.userInfo.UserInfoVO;

public class U_Mypage_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward action = new ActionForward();
		PostDAO PDAO = new PostDAO();
		HttpSession session = request.getSession();
		UserInfoVO UVO = (UserInfoVO)session.getAttribute("userInfoData");
		String id = UVO.getId();
		ArrayList<PostVO> datas = PDAO.SelectLikePost(id);
		ArrayList<PostVO> sliceDatas = new ArrayList<PostVO>();
		if(datas.size()!=0) {
			for(int i = 0; i < datas.size();i++) {
				sliceDatas.add(datas.get(i));
				if(i==2) {
					break;
				}
			}
		}

		request.setAttribute("UserLikePost", sliceDatas);
		action.setPath("MyPage.jsp");
		action.setRedirect(false);

		return action;
	}

}
