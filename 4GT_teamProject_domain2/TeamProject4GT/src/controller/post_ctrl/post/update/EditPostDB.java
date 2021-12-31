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
import model.likeInfo.LikeInfoDAO;
import model.likeInfo.LikeInfoVO;
import model.post.PostDAO;
import model.post.PostVO;
import model.userInfo.UserInfoVO;

public class EditPostDB implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();

		String realFolder = "";
		String filename1 = "";
		// 파일 크기 3MB로 제한
		int maxSize = 1024*1024*3;
		String encType = "utf-8";   

		// 이녀석이 업로드 될 폴더 이름
		String savefile = "img";

		String filename = "";

		// 파일이 저장될 서버의 경로
		ServletContext scontext = request.getSession().getServletContext();
		realFolder = scontext.getRealPath(savefile);
		// 
		PostDAO PDAO = new PostDAO();
		PostVO PVO = new PostVO();
		UserInfoVO UVO= new UserInfoVO();
		PVO.setPnum(PDAO.expectPnum());
		try{
			// 파일 업로드
			
			MultipartRequest multi=new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
			Enumeration<?> files = multi.getFileNames(); 
			String file1 = (String)files.nextElement();
			filename1 = multi.getFilesystemName(file1);
			HttpSession session = request.getSession();
			PVO.setPnum(Integer.parseInt(multi.getParameter("pnum")));
			PVO.setCategory(multi.getParameter("category"));
			PVO.setContent(multi.getParameter("content"));
			UVO = (UserInfoVO) session.getAttribute("userInfoData"); // 이름은 세션에서 VO로 저장된 UserInfoVO 사용!
			PVO.setWriter(UVO.getName()); 
			PVO.setP_user(UVO.getId()); // ID
			PVO.setTitle(multi.getParameter("title"));
			System.out.println("EditPostDB : "+filename1);
			
			
			// post 파일 경로 설정
			if(filename1 == null) { // 사용자가 사진을 변경하지 않았다면
				System.out.println("filename1이 null : "+multi.getParameter("path"));
				PVO.setPath(multi.getParameter("path"));

			}else {
				// 업로드 된 파일의 이름 변경하는 로직
				Path oldfile = Paths.get(realFolder +"/" +filename1);
				Path newfile = Paths.get(realFolder +"/" +PVO.getPnum()+"_PostImage.jpg"); 
				Files.move(oldfile, newfile, StandardCopyOption.REPLACE_EXISTING);
				realFolder = "img";
				String fullpath = realFolder + "/" + PVO.getPnum()+"_PostImage.jpg";

				// DB업데이트
				PVO.setPath(fullpath);
			}
	
			System.out.println(PVO);


		} catch(Exception e) {
			e.printStackTrace();
		}


		if(PDAO.UpdateDB(PVO)){
			// 업데이트가 완료됬을시 돌아가는 페이지 정보 세팅
			System.out.println("EditPostDB에서 넘어가는 PVO는 이거다! "+ PVO);
			request.setAttribute("singlePost", PVO);
			LikeInfoDAO LDAO = new LikeInfoDAO();
			LikeInfoVO LVO = new LikeInfoVO();
			String ID = UVO.getId();
			LVO.setL_post(PVO.getPnum());
			LVO.setL_user(ID);
			request.setAttribute("likeInfo", LDAO.SelectOne(LVO)); // 좋아요 정보

			forward.setRedirect(true);
			System.out.println(" ㅂㅇㅂㅈㄴㅇ ㅇ : "+PVO.getPnum());
			forward.setPath("selectOne.pdo?pnum="+PVO.getPnum());	
			return forward;
		}
		else{
			// 예외를 발생시켜 에러페이지로 이동
			try {
				throw new Exception("DB 변경 오류 발생!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

}
