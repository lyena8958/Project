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

	// [단일 조회]
	@RequestMapping("/getCareer.do")
	public String getCare(HttpServletRequest request, CareerInfoVO vo){

		CareerInfoVO data = vo; // vo 재활용(싱글톤 패턴 유지)
		data = careService.getData(vo);

		// 데이터 전달
		ModelAndView mav = new ModelAndView();
		mav.addObject("careerData ", data);

		// 경로
		return "show_Career";
	}

	// [경력 추가]
	@RequestMapping("/insertCare.do")
	public String insertCare(HttpServletRequest request, CareerInfoVO vo) {

		// 수행
		if(careService.insertCareer(vo)) {

			CareerInfoVO data = vo; // vo재활용
			data = careService.getData(vo); // 수행된 데이터 객체 전달받음

			// 데이터 전달
			ModelAndView mav = new ModelAndView();
			mav.addObject("careerData ", data);

			// 경로
			return "show_Mem";

		}
		// 수행오류
		else {
			try {
				throw new Exception("Career_insertCare 오류 발생!");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	// [경력 수정] --> 향후 SPA로 구현 예정
	@RequestMapping("/updateCare.do")
	public String updateCare(HttpServletRequest request, CareerInfoVO vo) {

		// 수행
		if(careService.updateCareer(vo)) {

			CareerInfoVO data = vo; // vo재활용
			data = careService.getData(vo); // 수행된 데이터 객체 전달받음


			// 경로
			return "show_Mem";

		}
		// 수행오류
		else {
			try {
				throw new Exception("Career_updateCare 오류 발생!");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	// [경력 삭제] --> 향후 SPA로 구현 예정
	@RequestMapping("/deleteCare.do")
	public String deleteCare(HttpServletRequest request, CareerInfoVO vo) {

		// 반영이 안되었다면
		if(!careService.deleteCareer(vo)) {
			try {
				throw new Exception("Career_deleteCare 오류 발생!");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		CareerInfoVO data = vo; // vo재활용
		data = careService.getData(vo); // 수행된 데이터 객체 전달받음

		// 경로
		return "show_Mem";
	}






}
