package controller.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import common.ExcelFileWriter;
import controller.common.ChartLogic;
import model.careerInfo.CareerInfoService;
import model.hradmin.HRAdminVO;
import model.member.MemberService;
import model.member.MemberVO;
import model.schoolInfo.SchoolInfoService;
import net.sf.json.JSONArray;

@Controller
public class Member_Controller {


	@Autowired
	private MemberService memService;
	@Autowired
	private SchoolInfoService schService;
	@Autowired
	private CareerInfoService carService;
	
	// POJO CLASS
	@Autowired
	private ExcelFileWriter efw;
	@Autowired
	private ChartLogic cyLogic;

	// cafe24 ȣ���� 
	private String path = "tomcat/webapps/ROOT/mazer-main/dist/imgMem/";
	
	//path = "C:\\YN_0910\\workspace\\SoloProject_211020\\src\\main\\webapp\\mazer-main\\dist\\imgMem\\";	
	//"D:\\YN_0910_210929\\workspace\\SoloProject_211020\\src\\main\\webapp\\mazer-main\\dist\\imgMem\\";

	// ������ ��Ʈȭ
	@RequestMapping("/mazer-main/dist/main.do")
	public String mainDatas(Model model, @RequestParam(name="dCnt", defaultValue="12", required=false)int dCnt,
			@RequestParam(name="dataType", defaultValue="������Ȳ", required=false) String dataType) {
		
		JSONArray datas = null;
		Object[] infoData = cyLogic.infoData(memService.getList(""));
		
		System.out.println("dataType : "+dataType);
		
		if(dataType.equals("������Ȳ")) {
			datas = cyLogic.memYearDatas(memService.getList(""), dCnt);
		}
		else if(dataType.equals("�з�")) {
			datas = cyLogic.schYearDatas(schService.getList(),memService.getList(""), dCnt);
		}
		else if(dataType.equals("���/����")) {
			datas = cyLogic.carYearDatas(carService.getList(), memService.getList(""), dCnt);
		}
		else if(dataType.equals("����")) {
			datas = cyLogic.ageYearDatas(memService.getList(""), dCnt);
		}
		
		System.out.println("size  :"+ datas.size());
		
		model.addAttribute("infoData", infoData);
		model.addAttribute("datas", datas);
		model.addAttribute("size", datas.size());
		model.addAttribute("dataType", dataType);
		//model.addAttribute("dCnt", dCnt);
		
		return "main.jsp";


	}

	// �������� �Է�
	@RequestMapping("/mazer-main/dist/profile.do")
	public void profileLogic(HttpServletResponse response,
			ArrayList<String[]> headers, XSSFWorkbook workbook, ArrayList<Integer> mdata,
			@ModelAttribute("career")boolean career, @ModelAttribute("license")boolean license, @ModelAttribute("school")boolean school) throws IOException {

		
		// �����Է� ���� �޼��� ����
		efw.writeFile(response, headers, workbook, mdata, career, license, school);
		
		
	}

	@RequestMapping("/mazer-main/dist/showMem.do")
	public String showMember(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		String text = request.getParameter("text");
		System.out.println("showMem : "+text);
		
		response.setContentType("text/html; charset=UTF-8");
		
		// [�̸� ��ȸ]
		if(text.matches("^=[��-�R]*$")) { // ù ���ڰ� = �̰�, ���ķ� ���ڸ�  true
			text = text.substring(1, text.length()); // = ����
			
			List<MemberVO> datas = memService.getList(text);
			System.out.println("�̸� ��ȸ �鸲~ "+datas.size());
			if(datas.size()==0) {
				response.getWriter().println("<script>alert('��ȸ�Ǵ� ����� �����ϴ�.'); window.close();</script>");
			}else {
				// ��ȸ�Ǵ� ���
				model.addAttribute("datas", datas);
				return "searchMem.jsp";
			}

			return null;
			
		}// [��� ��ȸ]
		else if(text.matches("[0-9]*")) { // 0~9�� ���ڸ� �����Ѵٸ� true
			vo.setMnum(Integer.parseInt(text)); // ���� ����ȯ
			vo = memService.getData(vo);

			if(vo==null) { // ��ȸ���� �ʴ� ���
				response.getWriter().println("<script>alert('�������� �ʴ� ����Դϴ�.'); history.go(-1);</script>");
				return null;
			}
			// ��ȸ�Ǵ� ���
			model.addAttribute("data", vo);
			return "show_Mem.jsp";
			
		// [�߸��� �˻�]
		}else { // ��� ���ǿ� ��ġ���� ���� ���
			response.getWriter().println("<script>alert('�߸��� �˻��Դϴ�.'); history.go(-1);</script>");
			return null;
		}

	}

	@RequestMapping("/mazer-main/dist/insertMem.do")
	public String insertMember(@ModelAttribute("data")MemberVO vo, HttpServletResponse response) throws IOException {
		System.out.println(vo);
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
				vo.setPath("imgMem/"+filename2);
				
				//vo.setPath("imgMem\\"+filename2);
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
		response.getWriter().println("<script>alert('���������� ä�� �߷��� �Ϸ�Ǿ����ϴ�.'); location.href='main.do';</script>");
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
		response.getWriter().println("<script>alert('�ش� �������� ���� ó���� �Ϸ�Ǿ����ϴ�.'); location.href='main.do';</script>");


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
			out.println("[{\"name\":\"" + "�Ϸ�"+"\"}]");
			//out.println(vo.getmName());

		}
		return null;

	}

}






//=======================================================================================================================================================================
//=======================================================================================================================================================================
//=======================================================================================================================================================================

/* ver2 -> POJO Class �� �����ű� (ExcelFileWriter)
 * @RequestMapping("/mazer-main/dist/profile.do")
public void profileLogic(HttpServletResponse response, 
		ArrayList<String[]> headers, XSSFWorkbook workbook, ArrayList<Integer> mdata,
		@ModelAttribute("career")boolean career, @ModelAttribute("license")boolean license, @ModelAttribute("school")boolean school) throws IOException {//, @ModelAttribute("data")MemberSet result
	ews.setCareer(career);
	ews.setLicense(license);
	ews.setSchool(school);
	System.out.println(ews);
	System.out.println("isCareer : "+ews.isCareer());
	System.out.println("isLicense : "+ews.isLicense());
	System.out.println("isSchool : "+ews.isSchool());
	// [��ü ����] 
	// is~~ : ����ڰ� checkBox�� Ŭ���ߴٸ� ���ε� ��ü�� ����, true �Ǿ�����
	// writer ��ü ���� (Client�� Checkbox�� Ŭ���ߴٸ� ��ü�� ������, �ƴ϶�� null��ȯ)
	ExcelWriter_Career careerW = ews.isCareer() ? ews.getCareerWriter() : null;
	ExcelWriter_License licenseW = ews.isLicense() ? ews.getLicenseWriter() : null;
	ExcelWriter_School schoolW = ews.isSchool() ? ews.getSchoolWriter() : null;
	System.out.println();

	// [���� ����] (ù ��)
	  // headers list ����  (�з�,���,�ڰ����� null�� �ƴ϶�� ����Է�)

	if(careerW != null) {
		headers.add(ews.getCareerWriter().getCareerHeader());
	}
	if(licenseW != null) {
		headers.add(ews.getLicenseWriter().getLicenseHeader());
	}
	if(schoolW != null) {
		headers.add(ews.getSchoolWriter().getSchoolHeader());
	}
	headers.add(ews.getMemberWriter().getMemberHeader());
	for(int i = 0; i < headers.size(); i++) {
		System.out.println("headers"+i);
		System.out.println(headers);
	}

	// [������ ����]
	List<MemberSet> result = memService.getSetList(); // ������ �ۼ��� ������ ����


	// [��� �� ���ϸ� ����]
	// �� �ð� ����, ���ϸ� ����
	LocalDateTime now = LocalDateTime.now();
	String dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

	// ������ ���� ���
	String filePath = "C:\\Users\\ITSC\\Desktop\\YHS_"+dateTime+".xlsx"; 
	//"C:\\Users\\user\\Desktop\\YHS_" ��
	// "C:\\Users\\ITSC\\Desktop\\YHS_" �п�

	// [���� �����Է� ���� ��ü Set]
	FileOutputStream fos = new FileOutputStream(filePath); // ��� ����	
	//XSSFWorkbook workbook = ews.getWorkbook(); // [���� ���� ��� ������ ���ε����� ��ü - write, style...]

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
		// ����� checkBox Ŭ������ �Ǵ� ��, size ALadd
		if(ews.isCareer()) {  // ���
			mdata.add(memdatas.getCareer()==null? 0 : memdatas.getCareer().size());
		}
		if(ews.isLicense()) { // �ڰ���
			mdata.add(memdatas.getLicense()==null? 0 : memdatas.getLicense().size());
		}
		if(ews.isSchool()) { // �з�
			mdata.add(memdatas.getSchool()==null? 0 : memdatas.getSchool().size());
		}

		int rowMax=0; // �� �ִ밪 ����

		// �ִ밪 ���
		for(int rm = 0; rm < mdata.size()-1; rm++) {
			rowMax = mdata.get(rm) > mdata.get(rm+1)    ?     mdata.get(rm) : mdata.get(rm+1);
		}

		// [���� ������ input] - ���ε����ʹ� �з�,���,�ڰ����� list�� �̷���������Ƿ� "�� ���"�� �ݺ��� �� �ִ�.
		for(int m = 0; m < rowMax; m++) {

			cellIndex = 0; // �� cnt -- �� �࿡ ���� �� index(==headersize)
			curRow=sheet.createRow(row++); // �� index

			// [member - ����������] -------------------------------------
			// ������ input�޼��� ���� (curRow, cellIndex, ���� VO)
			cellIndex = ews.getMemberWriter().mCellInput(curRow, cellIndex, memdatas.getMember());


			// [Career-��»���] -------------------------------------
			if(ews.isCareer()) { // Client checkŬ������ �Ǵ�
				// rowMax���� �۴ٸ� �� �Է� nulló��
				if(memdatas.getCareer()==null || memdatas.getCareer().size() <= m) {
					int clength = ews.getCareerWriter().getCareerHeader().length;

					for(int nu = 0; nu < clength; nu++) { // �� header ���̸�ŭ nulló��
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else { // �ƴ϶�� ������ �Է�
					// ��� input�޼��� ���� (curRow, cellIndex, ���� VO)
					cellIndex = ews.getCareerWriter().cCellInput(curRow, cellIndex, memdatas.getCareer().get(m));
				}
			}

			// [school-�з»���] -------------------------------------
			if(ews.isSchool()) {

				// rowMax���� �۴ٸ� �� �Է� nulló��
				if(memdatas.getSchool()==null || memdatas.getSchool().size() <= m) {
					System.out.println("m : "+m);
					int slength = ews.getSchoolWriter().getSchoolHeader().length;

					for(int ns = 0; ns < slength; ns++) { // �з� header ���̸�ŭ nulló��
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else {    // �ƴ϶�� ������ �Է�
					System.out.println("getSchool size :"+ memdatas.getSchool().size());
					// �з� input�޼��� ���� (curRow, cellIndex, ���� VO)
					cellIndex = ews.getSchoolWriter().sCellInput(curRow, cellIndex, memdatas.getSchool().get(m));
				}					
			}

			//[license-�ڰݻ���]
			if(ews.isLicense()) {
				// rowMax���� �۴ٸ� �� �Է� nulló��
				if(memdatas.getLicense()==null || memdatas.getLicense().size() <= m) {
					int llenght = ews.getLicenseWriter().getLicenseHeader().length;

					for(int nl = 0; nl < llenght; nl++) { // license header ���̸�ŭ nulló��
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else {    // �ƴ϶�� ������ �Է�
					// �ڰ��� input�޼��� ���� (curRow, cellIndex, ���� VO)
					cellIndex = ews.getLicenseWriter().lCellInput(curRow, cellIndex, memdatas.getLicense().get(m));
				}	
			}
		}
	}// write���� ����


	// �ݿ� �� ����
	workbook.write(fos);
	fos.close();

	response.getWriter().println("<script>window.close();</script>");
} //method ����

 */

//=======================================================================================================================================================================
//=======================================================================================================================================================================
//=======================================================================================================================================================================

//ver1 @RequestMapping("/mazer-main/dist/profile.do") ����
/*	public void test(HttpServletResponse response, ArrayList<String[]> headers) throws IOException {//, @ModelAttribute("data")MemberSet result
		System.out.println("memService "+memService); 


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
		String filePath = "C:\\Users\\ITSC\\Desktop\\YHS_"+dateTime+".xlsx"; 
		//"C:\\Users\\user\\Desktop\\YHS_" ��
		// "C:\\Users\\ITSC\\Desktop\\YHS_" �п�

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
 */