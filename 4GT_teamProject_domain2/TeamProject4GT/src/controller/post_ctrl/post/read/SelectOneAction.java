package controller.post_ctrl.post.read;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

public class SelectOneAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward action = new ActionForward();
		PostDAO PDAO = new PostDAO();
		PostVO PVO = new PostVO();
		UserInfoVO UVO = new UserInfoVO();
		LikeInfoVO LVO = new LikeInfoVO();
		LikeInfoDAO LDAO = new LikeInfoDAO();
		System.out.println("SelectOne123123 "+request.getParameter("pnum"));
		if(request.getParameter("pnum")!=null) {
			PVO.setPnum(Integer.parseInt(request.getParameter("pnum"))); // pnum값으로 찾는거니 Pnum만 세팅 후 넘겨줌
		}
		/*if (PDAO.ViewsUp(PVO)) { // 포스트를 볼때 view 업 -> Model 트랜잭션 처리
			request.setAttribute("singlePost", PDAO.SelectOne(PVO));
		} else {
			throw new Exception("ViewUp 오류 발생!");
		}*/
		System.out.println(PVO);
		// 넘겨줄 날짜 슬라이싱
		PVO = PDAO.SelectOne(PVO);
		String pdate = PVO.getPdate();
		Date datePdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			datePdate = sdf.parse(pdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sliceDate = new SimpleDateFormat("yyyy-MM-dd");
		pdate = sliceDate.format(datePdate);
		PVO.setPdate(pdate);

		// 내가 쓴 글 정보 로그인 시만 수행
		HttpSession session = request.getSession();

		request.setAttribute("singlePost", PVO);
		request.setAttribute("likeInfo", false); // 사용자가 지금 보는글에 좋아요를 눌렀는지 확인하는 값 디폴트 false
		if (session.getAttribute("userInfoData") != null) {
			UVO = (UserInfoVO) session.getAttribute("userInfoData"); // 로그인 정보 있으면 좋아요를 눌렀는지 체크
			String ID = UVO.getId();
			int pnum = Integer.parseInt(request.getParameter("pnum"));
			LVO.setL_post(pnum);
			LVO.setL_user(ID);
			request.setAttribute("likeInfo", LDAO.SelectOne(LVO)); // 좋아요 정보
		}
		// 카테고리 인기글
		ArrayList<PostVO> categoryDatas = PDAO.SelectCategoryForViews(PVO);
		if(categoryDatas.size()!=0) {
			ArrayList<PostVO> sliceCategoryDatas = new ArrayList<PostVO>();
			for(int i=0;i<3;i++) {
				PostVO vo =new PostVO();
				vo = categoryDatas.get(i);
				sliceCategoryDatas.add(vo);
				if(categoryDatas.size()-1==i) {
					break;
				}
			}
			request.setAttribute("categoryDatas", sliceCategoryDatas);
		}
		action.setPath("selectOne.ucdo");
		action.setRedirect(false);
		return action;
	}

}
