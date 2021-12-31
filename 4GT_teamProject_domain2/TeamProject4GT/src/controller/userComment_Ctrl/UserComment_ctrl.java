package controller.userComment_Ctrl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ActionForward;

/**
 * Servlet implementation class UserComment_ctrl
 */
@WebServlet("/UserComment_ctrl")
public class UserComment_ctrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserComment_ctrl() {
		super();
	}

	// [부모메서드] - get
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	// [부모메서드] - post
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}


	// [로직수행 메서드]
	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// [사용자 요청 구분]
		String uri=request.getRequestURI();
		String cp=request.getContextPath();
		String action=uri.substring(cp.length()+1); // +1 ==  "/"까지 sub하기 위함

		// [요청 수행]
		ActionForward forward = null;

		////////////////////////////////////comments전달 ////////////////////////////////////

		// [단일카테고리 목록] showList
		if(action.equals("post.ucdo")) {
			forward = new Post_Action().execute(request, response);
		}
		// [메인페이지] post_ctrl -> main
		else if(action.equals("main.ucdo")) {
			forward = new Main_Action().execute(request, response);
		}


		////////////////////////////////////userInfo////////////////////////////////////
		// [프로필 수정] 
		else if(action.equals("ProfileImage.ucdo")) {
			new U_ProfileImage_Action().execute(request, response);
		}
		// [ID중복체크] --- 회원가입시 ID중복여부 체크 
		else if(action.equals("checkID.ucdo")) {
			new U_CheckID_Action().execute(request, response);
			return; // 이동페이지 없으므로 종료
		}
		// [회원가입] --- view에서 → param (id,pw) 받아야 함 
		else if(action.equals("signUp.ucdo")) {
			new U_SignUp_Action().execute(request, response);
			return; //가입 이후 이동페이지 없으므로 종료
		}
		// [로그인] 로그인시 session설정 → userInfoData
		else if(action.equals("joinUs.ucdo")) {
			forward = new U_JoinUs_Action().execute(request, response);

		}
		// [로그아웃] session해제
		else if(action.equals("logOut.ucdo")) {
			forward = new U_logOut_Action().execute(request, response);

		}
		//[ID/PW찾기] ID=pw+name , PW=id
		// VIEW parameter 받아야할 것 
		//     type → "id" 또는 "pw"
		//     id = "pw" + "name"    ||   pw = "id"
		else if(action.equals("infoHelp.ucdo")) {
			forward = new U_InfoHelp_Action().execute(request, response);
		}

		// ps) 마이페이지 --- login시 session을 setAttribute해두니 
		//             즉각적으로 불러서 사용하시면 됩니다.
		//   ★ver2 때에는 UserSetClass 생성하여 객체 전달할 예정!


		//[마이페이지 → 정보수정] --- view에서 → param (id,pw,name) 모두 받아야 함
		else if(action.equals("updateUser.ucdo")) {
			forward = new U_UpdateUser_Action().execute(request, response);

		}
		//[마이페이지 → 회원탈퇴] session 초기화
		else if (action.equals("deleteUser.ucdo")) {
			forward = new U_DeleteUser_Action().execute(request, response);

		}
		//[이메일 인증] - 회원가입 및 ID/PW찾기
		//	필요 Parameter : 인증번호("codeNum"), 사용자 이메일("receiver")
		//	전달 response : 인증번호("codeNum")
		else if (action.equals("codeSend.ucdo")) {
			forward = new M_codeSend_Action().execute(request, response);
		}

		/////////////////////////////////////comment/////////////////////////////////////
		// [댓글 좋아요] 
		else if (action.equals("likeUpComment.ucdo")) {
			forward = new C_LikeUp_Action().execute(request, response);
		}
		// [댓글 읽기 R]
		// [단일 게시물---showOne] 구성 (게시물+좋아요+댓글) 
		else if (action.equals("selectOne.ucdo")) {
			forward = new C_SelectOne_Action().execute(request, response);
		}
		// [댓글  생성 C]
		else if (action.equals("insertComment.ucdo")) {
			forward = new C_InsertComment_Action().execute(request, response);

		}
		//[댓글  수정 U]
		else if (action.equals("editComment.ucdo")) {
			forward = new C_EditComment_Action().execute(request, response);

		}
		//[댓글 삭제 D]
		else if (action.equals("deleteComment.ucdo")) {
			forward = new C_DeleteComment_Action().execute(request, response);

		}
		
		/////////////////////////////////////reply/////////////////////////////////////
		//[리플 좋아요]
		else if (action.equals("likeUpReply.ucdo")) {
			forward = new R_LikeUp_Action().execute(request, response);

		}
		//[리플 생성 C]
		else if (action.equals("insertReply.ucdo")) {
			forward = new R_InsertReply_Action().execute(request, response);

		}
		//[리플 수정 U]
		else if (action.equals("editReply.ucdo")) {
			forward = new R_UpdateReply_Action().execute(request, response);

		}
		//[리플 삭제 D]
		else if (action.equals("deleteReply.ucdo")) {
			forward = new R_DeleteReply_Action().execute(request, response);

		}
		//[프로필 사진 변경]
		else if (action.equals("userProfileEdit.ucdo")) {
			forward = new U_ProfileEdit_Action().execute(request, response);
		}
		else if (action.equals("mypage.ucdo")) {
			forward = new U_Mypage_Action().execute(request, response);
		}
		else {// 미지정된 경로 -> 404
			forward.setPath("error/error404.jsp");
			forward.setRedirect(false);
		}




		// [response 전달]
		if(forward!=null) { // 반환받은 forward가 null이 아니라면
			
			// redirect가 true == sendredirect라면
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			}
			else{ //forward라면
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
			
		}//if
		
		
	}

}
