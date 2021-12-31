package controller.userComment_Ctrl;

import java.io.IOException;
import java.io.PrintWriter;
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
import model.userInfo.UserInfoDAO;
import model.userInfo.UserInfoVO;

public class U_ProfileEdit_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		PrintWriter out=response.getWriter();

		String realFolder = "";
		String filename1 = "";
		// 파일 크기 3MB로 제한
		int maxSize = 1024*1024*3;
		String encType = "utf-8";   

		// 이녀석이 업로드 될 폴더 이름
		String savefile = "userProfile";

		String filename = "";

		// 파일이 저장될 서버의 경로
		ServletContext scontext = request.getSession().getServletContext();
		realFolder = scontext.getRealPath(savefile);
		System.out.println(realFolder);

		UserInfoDAO UDAO = new UserInfoDAO();
		UserInfoVO UVO = new UserInfoVO();

		try{
			// 파일 업로드
			MultipartRequest multi=new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
			Enumeration<?> files = multi.getFileNames(); 
			String file1 = (String)files.nextElement();
			filename1 = multi.getFilesystemName(file1);
			System.out.println(filename1);

			// 업로드 된 파일의 이름 변경하는 로직
			HttpSession session = request.getSession();
			UVO = (UserInfoVO)session.getAttribute("userInfoData");
			System.out.println("최초 UVO : "+UVO);
			/*File oldFile = new File(realFolder +"/" +filename1);
			System.out.println("올드파일 : "+oldFile);
			File newFile = new File(realFolder +"/" +UVO.getId()+"_profile.jpg");
			System.out.println("뉴파일 : "+newFile);
			oldFile.renameTo(newFile);
			System.out.println("바뀐 올드파일 : "+oldFile);*/
			Path oldfile = Paths.get(realFolder +"/" +filename1);
			Path newfile = Paths.get(realFolder +"/" +UVO.getId()+"_profile.jpg"); 
			Files.move(oldfile, newfile, StandardCopyOption.REPLACE_EXISTING);
			realFolder = "userProfile";
			String fullpath = realFolder + "/" + UVO.getId()+"_profile.jpg";

			// DB업데이트
			UVO.setProfile(fullpath);
			System.out.println("최종 UVO : "+UVO);
		} catch(Exception e) {
			e.printStackTrace();
		}


		if(UDAO.UpdateProfile(UVO)){
			// 부모 페이지 새로고침
			out.println("<script>opener.location.reload();</script>");
			out.println("<script>window.close();</script>");
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
