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
import model.post.Paging;
import model.post.PostDAO;
import model.post.PostVO;

public class FindPost implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward action = new ActionForward();
		PostDAO PDAO = new PostDAO();
		// 검색 값 저장
		String word = (String)request.getParameter("findWord");
		String find = "%"+word+"%";
		String condition = (String)request.getParameter("condition");
		// 각 항목 페이징 처리
		String url="result.jsp";	
		String indexx=request.getParameter("index");
		int index=1;
		if(indexx!=null){
			index=Integer.parseInt(indexx);
		}
		//url 작업
		url= url+ "?index="+index;
		// 제목검색 결과
		ArrayList<PostVO> result = PDAO.searchPost(condition,find);
		Date now = new Date();
		DateSlice ds = new DateSlice(result, now, index);
		ArrayList<Integer> pagingIndex = new ArrayList<Integer>();
		Paging paging =null;
		// 한페이지에 들어가는 게시물 수
		int pagingSize = 6;
		// 생성자 순서대로 int pageSize(한페이지 게시물 수), int thisPageNum(현재 페이지 번호), int totalPostCnt(총 포스트 갯수)
		paging = new Paging(pagingSize,index,result.size());
		paging.makePaging();
		// 페이징 for문으로 표기 할 수 있도록 하기
		int page = paging.getStartPageNum();
		//					1							5
		for(int i = paging.getStartPageNum();i<=paging.getEndPageNum();i++) {
			pagingIndex.add(page);
			page++;
		}

		// 해당하는 페이지에 데이터만 주기 캡슐화 진행
		if(result.size()!=0) {
			ds.excuteSlice();
		}

		// condition 한글화
		if(condition.equals("title")) {
			condition = "제목";
		}else if(condition.equals("writer")) {
			condition = "작성자";
		}else if(condition.equals("content")) {
			condition = "내용";
		}
		request.setAttribute("isLast", paging.isLast());
		request.setAttribute("isFirst", paging.isFirst());
		request.setAttribute("pagingIndex", pagingIndex);
		request.setAttribute("index", index);
		request.setAttribute("PostList", ds.getNewData());
		request.setAttribute("condition", condition);
		request.setAttribute("word", word);
		action.setPath("result.jsp");
		action.setRedirect(false);
		return action;
	}

}
