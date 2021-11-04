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
		
		// ���� ������
		//path = "C:\\YN_0910\\workspace\\SoloProject_211020\\src\\main\\webapp\\mazer-main\\dist\\imgMem\\";	
		
	}
	@Autowired
	private MemberService memService;

	private String path; 
	
	@RequestMapping("/mazer-main/dist/profile.do")
	public void test(HttpServletResponse response, ArrayList<String[]> headers) throws IOException {//, @ModelAttribute("data")MemberSet result
		System.out.println("memService "+memService); 
	
		//headers =new ArrayList<String[]>();
		
	// [���� ����] (ù ��)
		String[] memberHeader = {"������", "������", "���", "����", "��������", "�������", "�μ�", "����", "����", "����"};
		String[] careerHeader = {"�Ի���", "������", "ȸ���", "����", "����", "����"};
		String[] schoolHeader = {"������", "������","�з�", "�б���", "�����迭", "������", "�����з¿���"};
		String[] licenseHeader = {"�����","������","�ڰ�����", "���"};
		
		// list ����
		headers.add(memberHeader);
		headers.add(careerHeader);
		headers.add(schoolHeader);
		headers.add(licenseHeader);
		 

		
	// [������ ����]
		List<MemberSet> result = memService.getSetList(); // ������ �ۼ��� ������ ����
		
		
	// [��� �� ���ϸ� ����]
		// �� �ð� ����, ���ϸ� ����
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

		// ������ ���� ���
		String filePath = "C:\\Users\\user\\Desktop\\YHS_"+dateTime+".xlsx"; 
				//"C:\\Users\\user\\Desktop\\YHR_profileList.xlsx"; ��
				// "C:\\Users\\ITSC\\Desktop\\YHR_profileList.xlsx" �п�
		
	// [���� �����Է� ���� ��ü Set]
		FileOutputStream fos = new FileOutputStream(filePath); // ��� ����	
		XSSFWorkbook workbook = new XSSFWorkbook(); // [���� ���� ��� ������ ���ε����� ��ü - write, style...]
		
		XSSFCellStyle style = workbook.createCellStyle(); // ��Ÿ�� ���� ��ü
		XSSFSheet sheet = workbook.createSheet("YHS_profile"); // ���� sheet ����
		XSSFRow curRow; // �� �Է� ��� ��ü
		HSSFCell cell; // ���� �� - ��Ÿ�� �������
		
	// [���� input]		
		curRow=sheet.createRow(0); // ù�� ����

		int hadersSize=0; // ��ü ��������� ���庯��
		
		// ��ü ���� ������ ����
		for (int i = 0; i < headers.size(); i++) { // ���� �Է�
			
			for(int j = 0; j < headers.get(i).length; j++) {
				
				curRow.createCell(hadersSize).setCellValue(headers.get(i)[j]); // �ش� �迭�� index ex)������~..����
				hadersSize++;
			}

		}

		
	// [������ input]
		
		int cellIndex; // ��(���� ��)
		int row=1; // �� (������ �����Է� �� 0���� �Է������Ƿ�, ������ �����ʹ� 1����� �ۼ�)
		
		System.out.println(result);

		
	    // ��ü ������ �ݺ�
		for (int i = 0; i < result.size(); i++) { // ��ü index 
		
			
			
	  // ---------------------------------------------------------------------------
	  // ���κ� ������ �Է� ---------------------------------------------------------------
	  // ---------------------------------------------------------------------------

			MemberSet memdatas = result.get(i); // ���ε�����Set (��������, �ڰ���list, �з�list, ���list)      	
			
		  // [�� �ִ밪 ���] 
			// ������ size �� �� ���� (nullPoint ����)
			ArrayList<Integer> mdata = new ArrayList<Integer>();
			mdata.add(memdatas.getCareer()==null? 0 : memdatas.getCareer().size());
			mdata.add(memdatas.getLicense()==null? 0 : memdatas.getLicense().size());
			mdata.add(memdatas.getSchool()==null? 0 : memdatas.getSchool().size());
			
			System.out.println("getCareer "+memdatas.getCareer());
			System.out.println("getLicense "+memdatas.getLicense());
			System.out.println("getSchool "+memdatas.getSchool()+"\n");
			

			int rowMax=0; // �� �ִ밪 ����
			
			// �ִ밪 ���
			for(int rm = 0; rm < mdata.size()-1; rm++) {
				System.out.println("rm : "+rm+"\n");
				
				rowMax = mdata.get(rm)>mdata.get(rm+1) ? mdata.get(rm) : mdata.get(rm+1);

			}
			System.out.println(rowMax+"\n");
			

		 // [���� ������ input] - ���ε����ʹ� �з�,���,�ڰ����� list�� �̷���������Ƿ� "�� ���"�� �ݺ��� �� �ִ�.
			for(int m = 0; m < rowMax; m++) {
						
				cellIndex = 0; // �� cnt -- �� �࿡ ���� �� index(==headersize)
				curRow=sheet.createRow(row++); // �� index

				System.out.println("m :"+m);
			// [member - ����������]
				MemberVO mvo = memdatas.getMember(); //���  ��ü
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

			// [Career-��»���]
				// rowMax���� �۴ٸ� �� �Է� nulló��
				
				   		//style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());// ��Ÿ�������ȵ�;
				   		//style.setFillPattern(FillPatternType.SOLID_FOREGROUND);// ��Ÿ�������ȵ�;

				if(memdatas.getCareer().size() <= m) {
					for(int nu = 0; nu < careerHeader.length; nu++) { // �� header ���̸�ŭ nulló��
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else { // �ƴ϶�� ������ �Է�
					CareerInfoVO cvo = memdatas.getCareer().get(m); //��� ����  ��ü
					System.out.println("excel cell : "+cellIndex);
					
						//curRow.createCell(cellIndex).setCellStyle(style); // ��Ÿ�������ȵ�;
					curRow.createCell(cellIndex++).setCellValue(cvo.getStartDate());
					
					curRow.createCell(cellIndex++).setCellValue(cvo.getEndDate());
					curRow.createCell(cellIndex++).setCellValue(cvo.getCompName());
					curRow.createCell(cellIndex++).setCellValue(cvo.getDuty());
					curRow.createCell(cellIndex++).setCellValue(cvo.getPosition());
					curRow.createCell(cellIndex++).setCellValue(cvo.getRank());
				}


			// [school-�з»���]
				// rowMax���� �۴ٸ� �� �Է� nulló��
				if(memdatas.getSchool().size() <= m) {
					for(int ns = 0; ns < schoolHeader.length; ns++) { // �з� header ���̸�ŭ nulló��
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else {    // �ƴ϶�� ������ �Է�
					SchoolInfoVO svo = memdatas.getSchool().get(m); //�з� ����  ��ü
					curRow.createCell(cellIndex++).setCellValue(svo.getStartDate());
					curRow.createCell(cellIndex++).setCellValue(svo.getEndDate());
					curRow.createCell(cellIndex++).setCellValue(svo.getStype());
					curRow.createCell(cellIndex++).setCellValue(svo.getSname());
					curRow.createCell(cellIndex++).setCellValue(svo.getField());
					curRow.createCell(cellIndex++).setCellValue(svo.getMajor());
					curRow.createCell(cellIndex++).setCellValue(svo.getFinish());
				}					


			//[license-�ڰݻ���]
				// rowMax���� �۴ٸ� �� �Է� nulló��
				if(memdatas.getLicense().size() <= m) {
					for(int nl = 0; nl < licenseHeader.length; nl++) { // license header ���̸�ŭ nulló��
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else {    // �ƴ϶�� ������ �Է�
					LicenseInfoVO lvo = memdatas.getLicense().get(m); //�з� ����  ��ü
					curRow.createCell(cellIndex++).setCellValue(lvo.getGetDate());
					curRow.createCell(cellIndex++).setCellValue(lvo.getExpireDate());
					curRow.createCell(cellIndex++).setCellValue(lvo.getLname());
					curRow.createCell(cellIndex++).setCellValue(lvo.getGrade());
				}	

			}
		}// write���� ����
		
		
			// �ݿ� �� ����
		workbook.write(fos);
		fos.close();
		
		response.getWriter().println("<script>window.close();</script>");
	} //method ����


	@RequestMapping("/mazer-main/dist/showMem.do")
	public String showMember(MemberVO vo, HttpServletResponse response, Model model) throws IOException {//, @ModelAttribute("data")MemberSet result
		System.out.println("showMem : "+vo);
		vo = memService.getData(vo);

		if(vo==null) {
			response.setContentType("text/html; charset=UTF-8"); 
			response.getWriter().println("<script>alert('�������� �ʴ� ����Դϴ�.'); history.go(-1);</script>");
			return null;
		}
		// UI�� date yyyy-mm-dd �������� set
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
				String filename2 = memService.getData()+fileName.substring(fileName.length()-4,fileName.length()); //Ȯ����
				System.out.println("���ϼ��� "+filename2);
				System.out.println("�����̸� : "+fileName);
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
		response.getWriter().println("<script>alert('���������� ä�� �߷��� �Ϸ�Ǿ����ϴ�.'); location.href='main.jsp';</script>");
		return null;
		// showMem�� ���� ������ ���� ȭ�� �����ֱ�
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
		// ������ �����Ϳ� ���ٸ�, �α׾ƿ�ó��
		if(admin.getHmem()==vo.getMnum()) {

			return "logOutHRAdmin.do";
		}

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println("<script>alert('�ش� �������� ���� ó���� �Ϸ�Ǿ����ϴ�.'); location.href='main.jsp';</script>");


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
			System.out.println("�鸲");

			//response.setContentType("text/html; charset=euc-kr");
			response.setContentType("text/html; charset=UTF-8");
			out = response.getWriter();

			//out.println("����"); 
			out.println("[{\"name\":\"" + vo.getmName()+"\"}]");
			//out.println(vo.getmName());

		}
		return null;

	}

}
