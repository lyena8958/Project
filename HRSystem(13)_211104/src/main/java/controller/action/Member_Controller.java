package controller.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import model.careerInfo.CareerInfoVO;
import model.hradmin.HRAdminVO;
import model.licenseInfo.LicenseInfoVO;
import model.member.MemberDAO;
import model.member.MemberService;
import model.member.MemberSet;
import model.member.MemberVO;
import model.schoolInfo.SchoolInfoVO;

@Controller
public class Member_Controller {
	public Member_Controller(){

		path = "D:\\YN_0910_210929\\workspace\\SoloProject_211020\\src\\main\\webapp\\mazer-main\\dist\\imgMem\\";
		
		// 파일 저장경로
		//path = "C:\\YN_0910\\workspace\\SoloProject_211020\\src\\main\\webapp\\mazer-main\\dist\\imgMem\\";	
		
	}
	@Autowired
	private MemberService memService;

	private String path; 
	
	@RequestMapping("/mazer-main/dist/profile.do")
	public void test(HttpServletResponse response, ArrayList<String[]> headers) throws IOException {//, @ModelAttribute("data")MemberSet result
		System.out.println("memService "+memService); 
	
		//headers =new ArrayList<String[]>();
		
	// [제목 설정] (첫 행)
		String[] memberHeader = {"시작일", "종료일", "사번", "성명", "재직여부", "생년월일", "부서", "직무", "직위", "직급"};
		String[] careerHeader = {"입사일", "퇴직일", "회사명", "직무", "직위", "직급"};
		String[] schoolHeader = {"입학일", "졸업일","학력", "학교명", "전공계열", "전공명", "최종학력여부"};
		String[] licenseHeader = {"취득일","만료일","자격증명", "등급"};
		
		// list 저장
		headers.add(memberHeader);
		headers.add(careerHeader);
		headers.add(schoolHeader);
		headers.add(licenseHeader);
		 

		
	// [데이터 취합]
		List<MemberSet> result = memService.getSetList(); // 엑셀에 작성할 데이터 모음
		
		
	// [경로 및 파일명 지정]
		// 현 시간 기준, 파일명 지정
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

		// 저장할 파일 경로
		String filePath = "C:\\Users\\user\\Desktop\\YHS_"+dateTime+".xlsx"; 
				//"C:\\Users\\user\\Desktop\\YHR_profileList.xlsx"; 집
				// "C:\\Users\\ITSC\\Desktop\\YHR_profileList.xlsx" 학원
		
	// [엑셀 파일입력 관련 객체 Set]
		FileOutputStream fos = new FileOutputStream(filePath); // 경로 저장	
		XSSFWorkbook workbook = new XSSFWorkbook(); // [엑셀 관련 모든 설정을 업로드해줄 객체 - write, style...]
		
		XSSFCellStyle style = workbook.createCellStyle(); // 스타일 설정 객체
		XSSFSheet sheet = workbook.createSheet("YHS_profile"); // 엑셀 sheet 생성
		XSSFRow curRow; // 행 입력 대상 객체
		HSSFCell cell; // 단일 셀 - 스타일 적용목적
		
	// [제목 input]		
		curRow=sheet.createRow(0); // 첫행 생성

		int hadersSize=0; // 전체 제목사이즈 저장변수
		
		// 전체 제목 사이즈 저장
		for (int i = 0; i < headers.size(); i++) { // 제목 입력
			
			for(int j = 0; j < headers.get(i).length; j++) {
				
				curRow.createCell(hadersSize).setCellValue(headers.get(i)[j]); // 해당 배열의 index ex)시작일~..직급
				hadersSize++;
			}

		}

		
	// [구성원 input]
		
		int cellIndex; // 셀(행의 열)
		int row=1; // 행 (위에서 제목입력 시 0행을 입력했으므로, 구성원 데이터는 1행부터 작성)
		
		System.out.println(result);

		
	    // 전체 데이터 반복
		for (int i = 0; i < result.size(); i++) { // 전체 index 
		
			
			
	  // ---------------------------------------------------------------------------
	  // 개인별 데이터 입력 ---------------------------------------------------------------
	  // ---------------------------------------------------------------------------

			MemberSet memdatas = result.get(i); // 개인데이터Set (개인정보, 자격증list, 학력list, 경력list)      	
			
		  // [행 최대값 계산] 
			// 각각의 size 비교 및 저장 (nullPoint 방지)
			ArrayList<Integer> mdata = new ArrayList<Integer>();
			mdata.add(memdatas.getCareer()==null? 0 : memdatas.getCareer().size());
			mdata.add(memdatas.getLicense()==null? 0 : memdatas.getLicense().size());
			mdata.add(memdatas.getSchool()==null? 0 : memdatas.getSchool().size());
			
			System.out.println("getCareer "+memdatas.getCareer());
			System.out.println("getLicense "+memdatas.getLicense());
			System.out.println("getSchool "+memdatas.getSchool()+"\n");
			

			int rowMax=0; // 행 최대값 변수
			
			// 최대값 계산
			for(int rm = 0; rm < mdata.size()-1; rm++) {
				System.out.println("rm : "+rm+"\n");
				
				rowMax = mdata.get(rm)>mdata.get(rm+1) ? mdata.get(rm) : mdata.get(rm+1);

			}
			System.out.println(rowMax+"\n");
			

		 // [개인 데이터 input] - 개인데이터는 학력,경력,자격증이 list로 이루어져있으므로 "행 출력"이 반복될 수 있다.
			for(int m = 0; m < rowMax; m++) {
						
				cellIndex = 0; // 셀 cnt -- 한 행에 넣을 셀 index(==headersize)
				curRow=sheet.createRow(row++); // 행 index

				System.out.println("m :"+m);
			// [member - 구성원정보]
				MemberVO mvo = memdatas.getMember(); //멤버  객체
				curRow.createCell(cellIndex++).setCellValue(mvo.getStartDate());
				curRow.createCell(cellIndex++).setCellValue(mvo.getEndDate());
				curRow.createCell(cellIndex++).setCellValue(mvo.getMnum());
				curRow.createCell(cellIndex++).setCellValue(mvo.getmName());
				curRow.createCell(cellIndex++).setCellValue(mvo.getWork());
				curRow.createCell(cellIndex++).setCellValue(mvo.getBirthDate());
				curRow.createCell(cellIndex++).setCellValue(mvo.getTeamName());
				curRow.createCell(cellIndex++).setCellValue(mvo.getDuty());
				curRow.createCell(cellIndex++).setCellValue(mvo.getPosition());
				curRow.createCell(cellIndex++).setCellValue(mvo.getMrank());

			// [Career-경력사항]
				// rowMax보다 작다면 → 입력 null처리
				
				   		//style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());// 스타일지정안됨;
				   		//style.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 스타일지정안됨;

				if(memdatas.getCareer().size() <= m) {
					for(int nu = 0; nu < careerHeader.length; nu++) { // 경 header 길이만큼 null처리
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else { // 아니라면 데이터 입력
					CareerInfoVO cvo = memdatas.getCareer().get(m); //경력 단일  객체
					System.out.println("excel cell : "+cellIndex);
					
						//curRow.createCell(cellIndex).setCellStyle(style); // 스타일지정안됨;
					curRow.createCell(cellIndex++).setCellValue(cvo.getStartDate());
					
					curRow.createCell(cellIndex++).setCellValue(cvo.getEndDate());
					curRow.createCell(cellIndex++).setCellValue(cvo.getCompName());
					curRow.createCell(cellIndex++).setCellValue(cvo.getDuty());
					curRow.createCell(cellIndex++).setCellValue(cvo.getPosition());
					curRow.createCell(cellIndex++).setCellValue(cvo.getRank());
				}


			// [school-학력사항]
				// rowMax보다 작다면 → 입력 null처리
				if(memdatas.getSchool().size() <= m) {
					for(int ns = 0; ns < schoolHeader.length; ns++) { // 학력 header 길이만큼 null처리
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else {    // 아니라면 데이터 입력
					SchoolInfoVO svo = memdatas.getSchool().get(m); //학력 단일  객체
					curRow.createCell(cellIndex++).setCellValue(svo.getStartDate());
					curRow.createCell(cellIndex++).setCellValue(svo.getEndDate());
					curRow.createCell(cellIndex++).setCellValue(svo.getStype());
					curRow.createCell(cellIndex++).setCellValue(svo.getSname());
					curRow.createCell(cellIndex++).setCellValue(svo.getField());
					curRow.createCell(cellIndex++).setCellValue(svo.getMajor());
					curRow.createCell(cellIndex++).setCellValue(svo.getFinish());
				}					


			//[license-자격사항]
				// rowMax보다 작다면 → 입력 null처리
				if(memdatas.getLicense().size() <= m) {
					for(int nl = 0; nl < licenseHeader.length; nl++) { // license header 길이만큼 null처리
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else {    // 아니라면 데이터 입력
					LicenseInfoVO lvo = memdatas.getLicense().get(m); //학력 단일  객체
					curRow.createCell(cellIndex++).setCellValue(lvo.getGetDate());
					curRow.createCell(cellIndex++).setCellValue(lvo.getExpireDate());
					curRow.createCell(cellIndex++).setCellValue(lvo.getLname());
					curRow.createCell(cellIndex++).setCellValue(lvo.getGrade());
				}	

			}
		}// write적용 종료
		
		
			// 반영 및 종료
		workbook.write(fos);
		fos.close();
		
		response.getWriter().println("<script>window.close();</script>");
	} //method 종료


	@RequestMapping("/mazer-main/dist/showMem.do")
	public String showMember(MemberVO vo, HttpServletResponse response, Model model) throws IOException {//, @ModelAttribute("data")MemberSet result
		System.out.println("showMem : "+vo);
		vo = memService.getData(vo);

		if(vo==null) {
			response.setContentType("text/html; charset=UTF-8"); 
			response.getWriter().println("<script>alert('존재하지 않는 사번입니다.'); history.go(-1);</script>");
			return null;
		}
		// UI시 date yyyy-mm-dd 나오도록 set
		/*vo.setBirthDate(vo.getBirthDate().substring(0, 10));
		vo.setStartDate(vo.getStartDate().substring(0, 10));*/

		model.addAttribute("data", vo);

		return "show_Mem.jsp";

	}

	@RequestMapping("/mazer-main/dist/insertMem.do")
	public String insertMember(@ModelAttribute("data")MemberVO vo, HttpServletResponse response) throws IOException {

		vo.setEndDate("9999-12-31");
		if(path!=null) {
			MultipartFile fileupload = vo.getFileUpload();
			
			if(!fileupload.isEmpty()) {
				String fileName = fileupload.getOriginalFilename();
				String filename2 = memService.getData()+fileName.substring(fileName.length()-4,fileName.length()); //확장자
				System.out.println("파일설정 "+filename2);
				System.out.println("파일이름 : "+fileName);
				fileupload.transferTo(new File(path+filename2));
				//vo.setPath("\\mazer-main\\dist\\imgMem\\"+filename2);
				vo.setPath("imgMem\\"+filename2);
			}
			System.out.println("showMem : "+vo);
			if(!memService.insertMember(vo)) {
				try {
					throw new Exception("Member insertMem");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}



		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println("<script>alert('정상적으로 채용 발령이 완료되었습니다.'); location.href='main.jsp';</script>");
		return null;
		// showMem을 통해 생성된 유저 화면 보여주기
		//return "main.jsp";
	}

	@RequestMapping("/mazer-main/dist/updateMem.do")
	public String updateMem(MemberVO vo, Model model) throws IOException {
		System.out.println("updateMem : "+vo);
		if(vo.getEndDate()==null) {
			vo.setEndDate("9999-12-31");
		}
		if(!memService.updateMember(vo)) {
			try {
				throw new Exception("Member updateMem");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		vo = memService.getData(vo);
		model.addAttribute("data", vo);
		return "showMem.do";
	}

	@RequestMapping("/mazer-main/dist/deleteMem.do")
	public String deleteMember(MemberVO vo, HttpServletResponse response, HttpSession session) throws IOException {
		if(!memService.deleteMember(vo)) {
			try {
				throw new Exception("Member deleteMem");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		HRAdminVO admin = (HRAdminVO)session.getAttribute("userData");
		// 삭제된 데이터와 같다면, 로그아웃처리
		if(admin.getHmem()==vo.getMnum()) {

			return "logOutHRAdmin.do";
		}

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println("<script>alert('해당 구성원의 삭제 처리가 완료되었습니다.'); location.href='main.jsp';</script>");


		//return "redirect:showMem.do";
		return null;
	}


	////////////////////////////////////////////////SPA////////////////////////////////////////////////

	// insertPost.jsp
	@RequestMapping("/mazer-main/dist/getMem.do")
	public String getMem(MemberVO vo, HttpServletResponse response, PrintWriter out) throws IOException {//, @ModelAttribute("data")MemberSet results

		System.out.println("getMem : "+vo);

		vo = memService.getData(vo);

		System.out.println("getMem1 "+vo);

		if(vo!=null) {
			System.out.println("들림");

			//response.setContentType("text/html; charset=euc-kr");
			response.setContentType("text/html; charset=UTF-8");
			out = response.getWriter();

			//out.println("루피"); 
			out.println("[{\"name\":\"" + vo.getmName()+"\"}]");
			//out.println(vo.getmName());

		}
		return null;

	}

}
