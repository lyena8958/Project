package controller.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import model.careerInfo.CareerInfoServiceImpl;
import model.careerInfo.CareerInfoVO;

@Controller
public class Career_Controller {

	@Autowired
	private CareerInfoServiceImpl careService;

	// [���� ��ȸ]
	@RequestMapping("/getCareer.do")
	public String getCare(HttpServletRequest request, CareerInfoVO vo){

		CareerInfoVO data = vo; // vo ��Ȱ��(�̱��� ���� ����)
		data = careService.getData(vo);

		// ������ ����
		ModelAndView mav = new ModelAndView();
		mav.addObject("careerData ", data);

		// ���
		return "show_Career";
	}

	// [��� �߰�]
	@RequestMapping("/insertCare.do")
	public String insertCare(HttpServletRequest request, CareerInfoVO vo) {

		// ����
		if(careService.insertCareer(vo)) {

			CareerInfoVO data = vo; // vo��Ȱ��
			data = careService.getData(vo); // ����� ������ ��ü ���޹���

			// ������ ����
			ModelAndView mav = new ModelAndView();
			mav.addObject("careerData ", data);

			// ���
			return "show_Mem";

		}
		// �������
		else {
			try {
				throw new Exception("Career_insertCare ���� �߻�!");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	// [��� ����] --> ���� SPA�� ���� ����
	@RequestMapping("/updateCare.do")
	public String updateCare(HttpServletRequest request, CareerInfoVO vo) {

		// ����
		if(careService.updateCareer(vo)) {

			CareerInfoVO data = vo; // vo��Ȱ��
			data = careService.getData(vo); // ����� ������ ��ü ���޹���


			// ���
			return "show_Mem";

		}
		// �������
		else {
			try {
				throw new Exception("Career_updateCare ���� �߻�!");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	// [��� ����] --> ���� SPA�� ���� ����
	@RequestMapping("/deleteCare.do")
	public String deleteCare(HttpServletRequest request, CareerInfoVO vo) {

		// �ݿ��� �ȵǾ��ٸ�
		if(!careService.deleteCareer(vo)) {
			try {
				throw new Exception("Career_deleteCare ���� �߻�!");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		CareerInfoVO data = vo; // vo��Ȱ��
		data = careService.getData(vo); // ����� ������ ��ü ���޹���

		// ���
		return "show_Mem";
	}






}
