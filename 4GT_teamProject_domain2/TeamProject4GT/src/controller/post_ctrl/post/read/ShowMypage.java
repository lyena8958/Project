package controller.post_ctrl.post.read;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.post.Paging;
import model.post.PostDAO;
import model.post.PostVO;
import model.userInfo.UserInfoVO;

public class ShowMypage implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward action = new ActionForward();
		PostDAO PDAO = new PostDAO();
		HttpSession session = request.getSession();
		UserInfoVO UVO = (UserInfoVO)session.getAttribute("userInfoData");
		ArrayList<PostVO> datas = PDAO.SelectMyPost(UVO);
		// 占쏙옙占쏙옙징 처占쏙옙 占쏙옙占쏙옙
		String url="ShowMyPost.jsp";	
		String indexx=request.getParameter("index");
		int index=1;
		if(indexx!=null){
			index=Integer.parseInt(indexx);
		}
		url= url+ "?index="+index;
		int pagingSize = 6;
		// 占쏙옙占쏙옙 占쏙옙占쏙옙 int pageSize(占쏙옙占쏙옙占쏙옙占쏙옙 占쌉시뱄옙 占쏙옙), int thisPageNum(占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙호), int totalPostCnt(占쏙옙 占쏙옙트 占쏙옙占쏙옙)
		Paging paging = new Paging(pagingSize,index,datas.size());
		paging.makePaging();
		// 占쏙옙占쏙옙징 for占쏙옙占쏙옙占� 표占쏙옙 占쏙옙 占쏙옙 占쌍듸옙占쏙옙 占싹깍옙
		ArrayList<Integer> pagingIndex = new ArrayList<Integer>();
		int page = paging.getStartPageNum();
		//					1							5
		for(int i = paging.getStartPageNum();i<=paging.getEndPageNum();i++) {
			pagingIndex.add(page);
			page++;
		}
		// 占쏙옙占쏙옙占쏙옙 6占쏙옙占쏙옙
		if(datas.size()!=0) {
			Date today = new Date();
			DateSlice ds = new DateSlice(datas, today, index);
			ds.excuteSlice();
			request.setAttribute("MyPost", ds.getNewData());
		}
		

		request.setAttribute("isLast", paging.isLast());
		request.setAttribute("isFirst", paging.isFirst());
		request.setAttribute("pagingIndex", pagingIndex);
		request.setAttribute("index", index);
		action.setPath(url);

		action.setRedirect(false);
		return action;
	}

}
