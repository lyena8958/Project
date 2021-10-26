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

	//[�α���]
	@RequestMapping("/loginHRAdmin.do")
	public String login(HttpServletRequest request, HRAdminVO vo) {
		HRAdminVO data = vo;
		data = hrService.getData(vo); 

		// �������� �ʴ� ����ڶ��, index������ �ǵ��ư�
		if(data==null) {
			return "index";
		}

		// �����Ѵٸ�, ���� ��� �� ���� ������ �̵�
		HttpSession session = request.getSession();
		session.setAttribute("userData", data);


		return "redirect:main.do";
	}

	//[�α׾ƿ�]
	@RequestMapping("/loginHRAdmin.do")
	public String logOut(HttpSession session) {
		// ���� ���� �� index �̵�
		session.invalidate();
		return "index";
	}

	//[HRAdmin �߰�] 
	@RequestMapping("/insertHRAdmin.do")
	public String insertHRAdmin(HRAdminVO vo) {
		
		// ������ �ȵǾ��ٸ�,
		if(!hrService.insertHRAdmin(vo)) {
			try {
				throw new Exception("HRAdmin_insertHRAdmin ���� �߻�!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return "index";
	}
	
	//[HRAdmin ����] 
	@RequestMapping("/deleteHRAdmin.do")
	public String deleteHRAdmin(HRAdminVO vo) {
		
		// ������ �ȵǾ��ٸ�,
		if(!hrService.deleteHRAdmin(vo)) {
			try {
				throw new Exception("HRAdmin_deleteHRAdmin ���� �߻�!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return "index";
	}
	
	//[HRAdmin ����]  -- �� ���������� �̻��
	@RequestMapping("/updateHRAdmin.do")
	public String updateHRAdmin(HRAdminVO vo) {
		
		// ������ �ȵǾ��ٸ�,
		if(!hrService.updateHRAdmin(vo)) {
			try {
				throw new Exception("HRAdmin_updateHRAdmin ���� �߻�!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		// �Ǿ��ٸ� index �̵�
		return "index";
	}
	
	//[ONE]  -- �� ���������� �̻��
	@RequestMapping("/getHRAdmin.do")
	public String getHRAdmin(HttpRequest request, HRAdminVO vo) {
		HRAdminVO data = vo; // vo��Ȱ��
		
		data = hrService.getData(vo);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("hrAdminData", data);
	
		return "main";
	}

	//[LIST] -- �� ���������� �̻��
	@RequestMapping("/getListHRAdmin.do")
	public String getListHRAdmin(HttpRequest request, HRAdminVO vo) {
		
		List<HRAdminVO> datas = hrService.getList(vo);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("hrAdminList", datas);
		
		return "main";
	}

}
