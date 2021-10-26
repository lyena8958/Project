package controller.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import model.hradmin.HRAdminServiceImpl;
import model.hradmin.HRAdminVO;

@Controller
public class HRAdmin_Controller {

	@Autowired
	private HRAdminServiceImpl hrService;

	//[로그인]
	@RequestMapping("/loginHRAdmin.do")
	public String login(HttpServletRequest request, HRAdminVO vo) {
		HRAdminVO data = vo;
		data = hrService.getData(vo); 

		// 존재하지 않는 담당자라면, index페이지 되돌아감
		if(data==null) {
			return "index";
		}

		// 존재한다면, 세션 등록 및 메인 페이지 이동
		HttpSession session = request.getSession();
		session.setAttribute("userData", data);


		return "redirect:main.do";
	}

	//[로그아웃]
	@RequestMapping("/loginHRAdmin.do")
	public String logOut(HttpSession session) {
		// 세션 종료 및 index 이동
		session.invalidate();
		return "index";
	}

	//[HRAdmin 추가] 
	@RequestMapping("/insertHRAdmin.do")
	public String insertHRAdmin(HRAdminVO vo) {
		
		// 수정이 안되었다면,
		if(!hrService.insertHRAdmin(vo)) {
			try {
				throw new Exception("HRAdmin_insertHRAdmin 오류 발생!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return "index";
	}
	
	//[HRAdmin 삭제] 
	@RequestMapping("/deleteHRAdmin.do")
	public String deleteHRAdmin(HRAdminVO vo) {
		
		// 수정이 안되었다면,
		if(!hrService.deleteHRAdmin(vo)) {
			try {
				throw new Exception("HRAdmin_deleteHRAdmin 오류 발생!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return "index";
	}
	
	//[HRAdmin 수정]  -- 현 버전에서는 미사용
	@RequestMapping("/updateHRAdmin.do")
	public String updateHRAdmin(HRAdminVO vo) {
		
		// 수정이 안되었다면,
		if(!hrService.updateHRAdmin(vo)) {
			try {
				throw new Exception("HRAdmin_updateHRAdmin 오류 발생!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		// 되었다면 index 이동
		return "index";
	}
	
	//[ONE]  -- 현 버전에서는 미사용
	@RequestMapping("/getHRAdmin.do")
	public String getHRAdmin(HttpRequest request, HRAdminVO vo) {
		HRAdminVO data = vo; // vo재활용
		
		data = hrService.getData(vo);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("hrAdminData", data);
	
		return "main";
	}

	//[LIST] -- 현 버전에서는 미사용
	@RequestMapping("/getListHRAdmin.do")
	public String getListHRAdmin(HttpRequest request, HRAdminVO vo) {
		
		List<HRAdminVO> datas = hrService.getList(vo);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("hrAdminList", datas);
		
		return "main";
	}

}
