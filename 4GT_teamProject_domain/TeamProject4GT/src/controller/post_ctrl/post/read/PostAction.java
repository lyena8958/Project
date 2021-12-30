package controller.post_ctrl.post.read;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.comments.CommentsVO;
import model.post.Paging;
import model.post.PostDAO;
import model.post.PostVO;

public class PostAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward action = new ActionForward();
		PostDAO PDAO = new PostDAO();
		PostVO PVO = new PostVO();
		String initial = request.getParameter("category");
		String category = "";
		if(initial.equals("hot")) {
			category = "인기글";
		}else if(initial.equals("chi")) {
			category = "치킨";
		}else if(initial.equals("piz")) {
			category = "피자";
		}else if(initial.equals("ham")) {
			category = "햄버거";
		}else if(initial.equals("kor")) {
			category = "한식";
		}else if(initial.equals("cha")) {
			category = "중식";
		}else if(initial.equals("jap")) {
			category = "일식";
		}
		ArrayList<PostVO> datas = new ArrayList<PostVO>();
		if(category.equals("인기글")) {
			datas = PDAO.SelectViews();
		}else {
			PVO.setCategory(category);
			datas = PDAO.SelectCategory(PVO);
		}
		String url="ShowList.jsp";	
		String indexx=request.getParameter("index");
		int index=1;
		if(indexx!=null){
			index=Integer.parseInt(indexx);
		}
		url= url+ "?index="+index;

		int pagingSize = 6;
		// 생성자 순서대로 int pageSize(한페이지 게시물 수), int thisPageNum(현재 페이지 번호), int totalPostCnt(총 포스트 갯수)
		Paging paging = new Paging(pagingSize,index,datas.size());
		paging.makePaging();
		// 페이징 for문으로 표기 할 수 있도록 하기
		ArrayList<Integer> pagingIndex = new ArrayList<Integer>();
		int page = paging.getStartPageNum();
		//					1							5
		for(int i = paging.getStartPageNum();i<=paging.getEndPageNum();i++) {
			pagingIndex.add(page);
			page++;
		}

		// 해당하는 페이지에 데이터만 주기 캡슐화 진행
		Date now = new Date();
		DateSlice ds = new DateSlice(datas, now, index);
		if(datas.size()!=0) {
			ds.excuteSlice();
		}
		request.setAttribute("category", category);
		request.setAttribute("isLast", paging.isLast());
		request.setAttribute("isFirst", paging.isFirst());
		request.setAttribute("pagingIndex", pagingIndex);
		request.setAttribute("index", index);
		request.setAttribute("PostList", ds.getNewData()); // PostList로 SelectAll 데이터를 넘김
		//System.out.println("mainActio 에서 보내주는 datas == "+datas);
		// request.setAttribute("commentsCnt", commentsCnt); // 댓글 수 AL로 넘김 0번 인덱스에 1번 포스트의 댓글 갯수 담겨있음! 댓글수 로직 -> 0927 model column 추가로 삭제
		System.out.println("showList로 가자 : "+url);
		action.setPath(url);
		action.setRedirect(false);
		return action;
	}

}
