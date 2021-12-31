package controller.post_ctrl.post.create;

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
import model.post.PostDAO;
import model.post.PostVO;
import model.userInfo.UserInfoVO;

public class InsertPostAction implements Action{

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
			System.out.println(filename1);
			HttpSession session = request.getSession();
			PVO.setCategory(multi.getParameter("category"));
			PVO.setContent(multi.getParameter("content"));
			UVO = (UserInfoVO) session.getAttribute("userInfoData"); // 이름은 세션에서 VO로 저장된 UserInfoVO 사용!
			PVO.setWriter(UVO.getName()); // Name 현재 여기와 이 아래에서 nullpointer뜨는데 이유는 로그인이 안되서 세션에 userInfo가 없음!
			PVO.setP_user(UVO.getId()); // ID
			PVO.setTitle(multi.getParameter("title"));
			System.out.println(PVO);
			
			// 업로드 된 파일의 이름 변경하는 로직
			Path oldfile = Paths.get(realFolder +"/" +filename1);
			Path newfile = Paths.get(realFolder +"/" +PVO.getPnum()+"_PostImage.jpg"); 
			System.out.println("oldfile "+oldfile);
			System.out.println("newfile "+newfile);
			Files.move(oldfile, newfile, StandardCopyOption.REPLACE_EXISTING);
			realFolder = "img";
			String fullpath = realFolder + "/" + PVO.getPnum()+"_PostImage.jpg";

			// DB업데이트
			PVO.setPath(fullpath);
		} catch(Exception e) {
			e.printStackTrace();
		}


		if(PDAO.InsertDB(PVO)){
			// 업데이트가 완료됬을시 돌아가는 페이지
			forward.setRedirect(true);
			forward.setPath("main.pdo");	
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
