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

	// cafe24 È£½ºÆÃ 
	private String path = "tomcat/webapps/ROOT/mazer-main/dist/imgMem/";
	
	//path = "C:\\YN_0910\\workspace\\SoloProject_211020\\src\\main\\webapp\\mazer-main\\dist\\imgMem\\";	
	//"D:\\YN_0910_210929\\workspace\\SoloProject_211020\\src\\main\\webapp\\mazer-main\\dist\\imgMem\\";

	// µ¥ÀÌÅÍ Â÷Æ®È­
	@RequestMapping("/mazer-main/dist/main.do")
	public String mainDatas(Model model, @RequestParam(name="dCnt", defaultValue="12", required=false)int dCnt,
			@RequestParam(name="dataType", defaultValue="ÀçÁ÷ÇöÈ²", required=false) String dataType) {
		
		JSONArray datas = null;
		Object[] infoData = cyLogic.infoData(memService.getList(""));
		
		System.out.println("dataType : "+dataType);
		
		if(dataType.equals("ÀçÁ÷ÇöÈ²")) {
			datas = cyLogic.memYearDatas(memService.getList(""), dCnt);
		}
		else if(dataType.equals("ÇĞ·Â")) {
			datas = cyLogic.schYearDatas(schService.getList(),memService.getList(""), dCnt);
		}
		else if(dataType.equals("°æ·Â/½ÅÀÔ")) {
			datas = cyLogic.carYearDatas(carService.getList(), memService.getList(""), dCnt);
		}
		else if(dataType.equals("³ªÀÌ")) {
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

	// ¿¢¼¿ÆÄÀÏ ÀÔ·Â
	@RequestMapping("/mazer-main/dist/profile.do")
	public void profileLogic(HttpServletResponse response,
			ArrayList<String[]> headers, XSSFWorkbook workbook, ArrayList<Integer> mdata,
			@ModelAttribute("career")boolean career, @ModelAttribute("license")boolean license, @ModelAttribute("school")boolean school) throws IOException {

		
		// ¿¢¼¿ÀÔ·Â ·ÎÁ÷ ¸Ş¼­µå ¼öÇà
		efw.writeFile(response, headers, workbook, mdata, career, license, school);
		
		
	}

	@RequestMapping("/mazer-main/dist/showMem.do")
	public String showMember(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		String text = request.getParameter("text");
		System.out.println("showMem : "+text);
		
		response.setContentType("text/html; charset=UTF-8");
		
		// [ÀÌ¸§ Á¶È¸]
		if(text.matches("^=[°¡-ÆR]*$")) { // Ã¹ ±ÛÀÚ°¡ = ÀÌ°í, ÀÌÈÄ·Î ¹®ÀÚ¸é  true
			text = text.substring(1, text.length()); // = Á¦°Å
			
			List<MemberVO> datas = memService.getList(text);
			System.out.println("ÀÌ¸§ Á¶È¸ µé¸²~ "+datas.size());
			if(datas.size()==0) {
				response.getWriter().println("<script>alert('Á¶È¸µÇ´Â ´ë»óÀÌ ¾ø½À´Ï´Ù.'); window.close();</script>");
			}else {
				// Á¶È¸µÇ´Â »ç¹ø
				model.addAttribute("datas", datas);
				return "searchMem.jsp";
			}

			return null;
			
		}// [»ç¹ø Á¶È¸]
		else if(text.matches("[0-9]*")) { // 0~9ÀÇ ¼ıÀÚ¸¸ Á¸ÀçÇÑ´Ù¸é true
			vo.setMnum(Integer.parseInt(text)); // Á¤¼ö Çüº¯È¯
			vo = memService.getData(vo);

			if(vo==null) { // Á¶È¸µÇÁö ¾Ê´Â »ç¹ø
				response.getWriter().println("<script>alert('Á¸ÀçÇÏÁö ¾Ê´Â »ç¹øÀÔ´Ï´Ù.'); history.go(-1);</script>");
				return null;
			}
			// Á¶È¸µÇ´Â »ç¹ø
			model.addAttribute("data", vo);
			return "show_Mem.jsp";
			
		// [Àß¸øµÈ °Ë»ö]
		}else { // ¸ğµç Á¶°Ç¿¡ ÀÏÄ¡ÇÏÁö ¾ÊÀº °æ¿ì
			response.getWriter().println("<script>alert('Àß¸øµÈ °Ë»öÀÔ´Ï´Ù.'); history.go(-1);</script>");
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

				String filename2 = memService.getData()+fileName.substring(fileName.length()-4,fileName.length()); //È®ÀåÀÚ
				System.out.println("ÆÄÀÏ¼³Á¤ "+filename2);
				System.out.println("ÆÄÀÏÀÌ¸§ : "+fileName);
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
		response.getWriter().println("<script>alert('Á¤»óÀûÀ¸·Î Ã¤¿ë ¹ß·ÉÀÌ ¿Ï·áµÇ¾ú½À´Ï´Ù.'); location.href='main.do';</script>");
		return null;
		// showMemÀ» ÅëÇØ »ı¼ºµÈ À¯Àú È­¸é º¸¿©ÁÖ±â
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
		// »èÁ¦µÈ µ¥ÀÌÅÍ¿Í °°´Ù¸é, ·Î±×¾Æ¿ôÃ³¸®
		if(admin.getHmem()==vo.getMnum()) {

			return "logOutHRAdmin.do";
		}

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println("<script>alert('ÇØ´ç ±¸¼º¿øÀÇ »èÁ¦ Ã³¸®°¡ ¿Ï·áµÇ¾ú½À´Ï´Ù.'); location.href='main.do';</script>");


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
			System.out.println("µé¸²");

			//response.setContentType("text/html; charset=euc-kr");
			response.setContentType("text/html; charset=UTF-8");
			out = response.getWriter();

			//out.println("·çÇÇ"); 
			out.println("[{\"name\":\"" + "¿Ï·á"+"\"}]");
			//out.println(vo.getmName());

		}
		return null;

	}

}






//=======================================================================================================================================================================
//=======================================================================================================================================================================
//=======================================================================================================================================================================

/* ver2 -> POJO Class ·Î ·ÎÁ÷¿Å±è (ExcelFileWriter)
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
	// [°´Ã¼ ¼³Á¤] 
	// is~~ : »ç¿ëÀÚ°¡ checkBox¸¦ Å¬¸¯Çß´Ù¸é ¹ÙÀÎµå °´Ã¼·Î ÀÎÇØ, true µÇ¾îÀÖÀ½
	// writer °´Ã¼ ¼³Á¤ (Client°¡ Checkbox¸¦ Å¬¸¯Çß´Ù¸é °´Ã¼¸¦ °¡Á®¿È, ¾Æ´Ï¶ó¸é null¹İÈ¯)
	ExcelWriter_Career careerW = ews.isCareer() ? ews.getCareerWriter() : null;
	ExcelWriter_License licenseW = ews.isLicense() ? ews.getLicenseWriter() : null;
	ExcelWriter_School schoolW = ews.isSchool() ? ews.getSchoolWriter() : null;
	System.out.println();

	// [Á¦¸ñ ¼³Á¤] (Ã¹ Çà)
	  // headers list ÀúÀå  (ÇĞ·Â,°æ·Â,ÀÚ°İÁõÀº nullÀÌ ¾Æ´Ï¶ó¸é Çì´õÀÔ·Â)

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

	// [µ¥ÀÌÅÍ ÃëÇÕ]
	List<MemberSet> result = memService.getSetList(); // ¿¢¼¿¿¡ ÀÛ¼ºÇÒ µ¥ÀÌÅÍ ¸ğÀ½


	// [°æ·Î ¹× ÆÄÀÏ¸í ÁöÁ¤]
	// Çö ½Ã°£ ±âÁØ, ÆÄÀÏ¸í ÁöÁ¤
	LocalDateTime now = LocalDateTime.now();
	String dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

	// ÀúÀåÇÒ ÆÄÀÏ °æ·Î
	String filePath = "C:\\Users\\ITSC\\Desktop\\YHS_"+dateTime+".xlsx"; 
	//"C:\\Users\\user\\Desktop\\YHS_" Áı
	// "C:\\Users\\ITSC\\Desktop\\YHS_" ÇĞ¿ø

	// [¿¢¼¿ ÆÄÀÏÀÔ·Â °ü·Ã °´Ã¼ Set]
	FileOutputStream fos = new FileOutputStream(filePath); // °æ·Î ÀúÀå	
	//XSSFWorkbook workbook = ews.getWorkbook(); // [¿¢¼¿ °ü·Ã ¸ğµç ¼³Á¤À» ¾÷·ÎµåÇØÁÙ °´Ã¼ - write, style...]

	XSSFCellStyle style = workbook.createCellStyle(); // ½ºÅ¸ÀÏ ¼³Á¤ °´Ã¼
	XSSFSheet sheet = workbook.createSheet("YHS_profile"); // ¿¢¼¿ sheet »ı¼º
	XSSFRow curRow; // Çà ÀÔ·Â ´ë»ó °´Ã¼
	HSSFCell cell; // ´ÜÀÏ ¼¿ - ½ºÅ¸ÀÏ Àû¿ë¸ñÀû

	// [Á¦¸ñ input]		
	curRow=sheet.createRow(0); // Ã¹Çà »ı¼º

	int hadersSize=0; // ÀüÃ¼ Á¦¸ñ»çÀÌÁî ÀúÀåº¯¼ö

	// ÀüÃ¼ Á¦¸ñ »çÀÌÁî ÀúÀå
	for (int i = 0; i < headers.size(); i++) { // Á¦¸ñ ÀÔ·Â
		for(int j = 0; j < headers.get(i).length; j++) {

			curRow.createCell(hadersSize).setCellValue(headers.get(i)[j]); // ÇØ´ç ¹è¿­ÀÇ index ex)½ÃÀÛÀÏ~..Á÷±Ş
			hadersSize++;
		}

	}

	// [±¸¼º¿ø input]

	int cellIndex; // ¼¿(ÇàÀÇ ¿­)
	int row=1; // Çà (À§¿¡¼­ Á¦¸ñÀÔ·Â ½Ã 0ÇàÀ» ÀÔ·ÂÇßÀ¸¹Ç·Î, ±¸¼º¿ø µ¥ÀÌÅÍ´Â 1ÇàºÎÅÍ ÀÛ¼º)

	System.out.println(result);


	// ÀüÃ¼ µ¥ÀÌÅÍ ¹İº¹
	for (int i = 0; i < result.size(); i++) { // ÀüÃ¼ index 			

		// ---------------------------------------------------------------------------
		// °³ÀÎº° µ¥ÀÌÅÍ ÀÔ·Â ---------------------------------------------------------------
		// ---------------------------------------------------------------------------

		MemberSet memdatas = result.get(i); // °³ÀÎµ¥ÀÌÅÍSet (°³ÀÎÁ¤º¸, ÀÚ°İÁõlist, ÇĞ·Âlist, °æ·Âlist)      	

		// [Çà ÃÖ´ë°ª °è»ê] 
		// °¢°¢ÀÇ size ºñ±³ ¹× ÀúÀå (nullPoint ¹æÁö)
		// »ç¿ëÀÚ checkBox Å¬¸¯¿©ºÎ ÆÇ´Ü ÈÄ, size ALadd
		if(ews.isCareer()) {  // °æ·Â
			mdata.add(memdatas.getCareer()==null? 0 : memdatas.getCareer().size());
		}
		if(ews.isLicense()) { // ÀÚ°İÁõ
			mdata.add(memdatas.getLicense()==null? 0 : memdatas.getLicense().size());
		}
		if(ews.isSchool()) { // ÇĞ·Â
			mdata.add(memdatas.getSchool()==null? 0 : memdatas.getSchool().size());
		}

		int rowMax=0; // Çà ÃÖ´ë°ª º¯¼ö

		// ÃÖ´ë°ª °è»ê
		for(int rm = 0; rm < mdata.size()-1; rm++) {
			rowMax = mdata.get(rm) > mdata.get(rm+1)    ?     mdata.get(rm) : mdata.get(rm+1);
		}

		// [°³ÀÎ µ¥ÀÌÅÍ input] - °³ÀÎµ¥ÀÌÅÍ´Â ÇĞ·Â,°æ·Â,ÀÚ°İÁõÀÌ list·Î ÀÌ·ç¾îÁ®ÀÖÀ¸¹Ç·Î "Çà Ãâ·Â"ÀÌ ¹İº¹µÉ ¼ö ÀÖ´Ù.
		for(int m = 0; m < rowMax; m++) {

			cellIndex = 0; // ¼¿ cnt -- ÇÑ Çà¿¡ ³ÖÀ» ¼¿ index(==headersize)
			curRow=sheet.createRow(row++); // Çà index

			// [member - ±¸¼º¿øÁ¤º¸] -------------------------------------
			// ±¸¼º¿ø input¸Ş¼­µå ¼öÇà (curRow, cellIndex, ´ÜÀÏ VO)
			cellIndex = ews.getMemberWriter().mCellInput(curRow, cellIndex, memdatas.getMember());


			// [Career-°æ·Â»çÇ×] -------------------------------------
			if(ews.isCareer()) { // Client checkÅ¬¸¯¿©ºÎ ÆÇ´Ü
				// rowMaxº¸´Ù ÀÛ´Ù¸é ¡æ ÀÔ·Â nullÃ³¸®
				if(memdatas.getCareer()==null || memdatas.getCareer().size() <= m) {
					int clength = ews.getCareerWriter().getCareerHeader().length;

					for(int nu = 0; nu < clength; nu++) { // °æ header ±æÀÌ¸¸Å­ nullÃ³¸®
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else { // ¾Æ´Ï¶ó¸é µ¥ÀÌÅÍ ÀÔ·Â
					// °æ·Â input¸Ş¼­µå ¼öÇà (curRow, cellIndex, ´ÜÀÏ VO)
					cellIndex = ews.getCareerWriter().cCellInput(curRow, cellIndex, memdatas.getCareer().get(m));
				}
			}

			// [school-ÇĞ·Â»çÇ×] -------------------------------------
			if(ews.isSchool()) {

				// rowMaxº¸´Ù ÀÛ´Ù¸é ¡æ ÀÔ·Â nullÃ³¸®
				if(memdatas.getSchool()==null || memdatas.getSchool().size() <= m) {
					System.out.println("m : "+m);
					int slength = ews.getSchoolWriter().getSchoolHeader().length;

					for(int ns = 0; ns < slength; ns++) { // ÇĞ·Â header ±æÀÌ¸¸Å­ nullÃ³¸®
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else {    // ¾Æ´Ï¶ó¸é µ¥ÀÌÅÍ ÀÔ·Â
					System.out.println("getSchool size :"+ memdatas.getSchool().size());
					// ÇĞ·Â input¸Ş¼­µå ¼öÇà (curRow, cellIndex, ´ÜÀÏ VO)
					cellIndex = ews.getSchoolWriter().sCellInput(curRow, cellIndex, memdatas.getSchool().get(m));
				}					
			}

			//[license-ÀÚ°İ»çÇ×]
			if(ews.isLicense()) {
				// rowMaxº¸´Ù ÀÛ´Ù¸é ¡æ ÀÔ·Â nullÃ³¸®
				if(memdatas.getLicense()==null || memdatas.getLicense().size() <= m) {
					int llenght = ews.getLicenseWriter().getLicenseHeader().length;

					for(int nl = 0; nl < llenght; nl++) { // license header ±æÀÌ¸¸Å­ nullÃ³¸®
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else {    // ¾Æ´Ï¶ó¸é µ¥ÀÌÅÍ ÀÔ·Â
					// ÀÚ°İÁõ input¸Ş¼­µå ¼öÇà (curRow, cellIndex, ´ÜÀÏ VO)
					cellIndex = ews.getLicenseWriter().lCellInput(curRow, cellIndex, memdatas.getLicense().get(m));
				}	
			}
		}
	}// writeÀû¿ë Á¾·á


	// ¹İ¿µ ¹× Á¾·á
	workbook.write(fos);
	fos.close();

	response.getWriter().println("<script>window.close();</script>");
} //method Á¾·á

 */

//=======================================================================================================================================================================
//=======================================================================================================================================================================
//=======================================================================================================================================================================

//ver1 @RequestMapping("/mazer-main/dist/profile.do") ¿øº»
/*	public void test(HttpServletResponse response, ArrayList<String[]> headers) throws IOException {//, @ModelAttribute("data")MemberSet result
		System.out.println("memService "+memService); 


		// [Á¦¸ñ ¼³Á¤] (Ã¹ Çà)
		String[] memberHeader = {"½ÃÀÛÀÏ", "Á¾·áÀÏ", "»ç¹ø", "¼º¸í", "ÀçÁ÷¿©ºÎ", "»ı³â¿ùÀÏ", "ºÎ¼­", "Á÷¹«", "Á÷À§", "Á÷±Ş"};
		String[] careerHeader = {"ÀÔ»çÀÏ", "ÅğÁ÷ÀÏ", "È¸»ç¸í", "Á÷¹«", "Á÷À§", "Á÷±Ş"};
		String[] schoolHeader = {"ÀÔÇĞÀÏ", "Á¹¾÷ÀÏ","ÇĞ·Â", "ÇĞ±³¸í", "Àü°ø°è¿­", "Àü°ø¸í", "ÃÖÁ¾ÇĞ·Â¿©ºÎ"};
		String[] licenseHeader = {"ÃëµæÀÏ","¸¸·áÀÏ","ÀÚ°İÁõ¸í", "µî±Ş"};

		// list ÀúÀå
		headers.add(memberHeader);
		headers.add(careerHeader);
		headers.add(schoolHeader);
		headers.add(licenseHeader);

		// [µ¥ÀÌÅÍ ÃëÇÕ]
		List<MemberSet> result = memService.getSetList(); // ¿¢¼¿¿¡ ÀÛ¼ºÇÒ µ¥ÀÌÅÍ ¸ğÀ½


		// [°æ·Î ¹× ÆÄÀÏ¸í ÁöÁ¤]
		// Çö ½Ã°£ ±âÁØ, ÆÄÀÏ¸í ÁöÁ¤
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

		// ÀúÀåÇÒ ÆÄÀÏ °æ·Î
		String filePath = "C:\\Users\\ITSC\\Desktop\\YHS_"+dateTime+".xlsx"; 
		//"C:\\Users\\user\\Desktop\\YHS_" Áı
		// "C:\\Users\\ITSC\\Desktop\\YHS_" ÇĞ¿ø

		// [¿¢¼¿ ÆÄÀÏÀÔ·Â °ü·Ã °´Ã¼ Set]
		FileOutputStream fos = new FileOutputStream(filePath); // °æ·Î ÀúÀå	
		XSSFWorkbook workbook = new XSSFWorkbook(); // [¿¢¼¿ °ü·Ã ¸ğµç ¼³Á¤À» ¾÷·ÎµåÇØÁÙ °´Ã¼ - write, style...]

		XSSFCellStyle style = workbook.createCellStyle(); // ½ºÅ¸ÀÏ ¼³Á¤ °´Ã¼
		XSSFSheet sheet = workbook.createSheet("YHS_profile"); // ¿¢¼¿ sheet »ı¼º
		XSSFRow curRow; // Çà ÀÔ·Â ´ë»ó °´Ã¼
		HSSFCell cell; // ´ÜÀÏ ¼¿ - ½ºÅ¸ÀÏ Àû¿ë¸ñÀû

		// [Á¦¸ñ input]		
		curRow=sheet.createRow(0); // Ã¹Çà »ı¼º

		int hadersSize=0; // ÀüÃ¼ Á¦¸ñ»çÀÌÁî ÀúÀåº¯¼ö

		// ÀüÃ¼ Á¦¸ñ »çÀÌÁî ÀúÀå
		for (int i = 0; i < headers.size(); i++) { // Á¦¸ñ ÀÔ·Â

			for(int j = 0; j < headers.get(i).length; j++) {

				curRow.createCell(hadersSize).setCellValue(headers.get(i)[j]); // ÇØ´ç ¹è¿­ÀÇ index ex)½ÃÀÛÀÏ~..Á÷±Ş
				hadersSize++;
			}

		}


		// [±¸¼º¿ø input]

		int cellIndex; // ¼¿(ÇàÀÇ ¿­)
		int row=1; // Çà (À§¿¡¼­ Á¦¸ñÀÔ·Â ½Ã 0ÇàÀ» ÀÔ·ÂÇßÀ¸¹Ç·Î, ±¸¼º¿ø µ¥ÀÌÅÍ´Â 1ÇàºÎÅÍ ÀÛ¼º)

		System.out.println(result);


		// ÀüÃ¼ µ¥ÀÌÅÍ ¹İº¹
		for (int i = 0; i < result.size(); i++) { // ÀüÃ¼ index 



			// ---------------------------------------------------------------------------
			// °³ÀÎº° µ¥ÀÌÅÍ ÀÔ·Â ---------------------------------------------------------------
			// ---------------------------------------------------------------------------

			MemberSet memdatas = result.get(i); // °³ÀÎµ¥ÀÌÅÍSet (°³ÀÎÁ¤º¸, ÀÚ°İÁõlist, ÇĞ·Âlist, °æ·Âlist)      	

			// [Çà ÃÖ´ë°ª °è»ê] 
			// °¢°¢ÀÇ size ºñ±³ ¹× ÀúÀå (nullPoint ¹æÁö)
			ArrayList<Integer> mdata = new ArrayList<Integer>();
			mdata.add(memdatas.getCareer()==null? 0 : memdatas.getCareer().size());
			mdata.add(memdatas.getLicense()==null? 0 : memdatas.getLicense().size());
			mdata.add(memdatas.getSchool()==null? 0 : memdatas.getSchool().size());

			System.out.println("getCareer "+memdatas.getCareer());
			System.out.println("getLicense "+memdatas.getLicense());
			System.out.println("getSchool "+memdatas.getSchool()+"\n");


			int rowMax=0; // Çà ÃÖ´ë°ª º¯¼ö

			// ÃÖ´ë°ª °è»ê
			for(int rm = 0; rm < mdata.size()-1; rm++) {
				System.out.println("rm : "+rm+"\n");

				rowMax = mdata.get(rm)>mdata.get(rm+1) ? mdata.get(rm) : mdata.get(rm+1);

			}
			System.out.println(rowMax+"\n");


			// [°³ÀÎ µ¥ÀÌÅÍ input] - °³ÀÎµ¥ÀÌÅÍ´Â ÇĞ·Â,°æ·Â,ÀÚ°İÁõÀÌ list·Î ÀÌ·ç¾îÁ®ÀÖÀ¸¹Ç·Î "Çà Ãâ·Â"ÀÌ ¹İº¹µÉ ¼ö ÀÖ´Ù.
			for(int m = 0; m < rowMax; m++) {

				cellIndex = 0; // ¼¿ cnt -- ÇÑ Çà¿¡ ³ÖÀ» ¼¿ index(==headersize)
				curRow=sheet.createRow(row++); // Çà index

				System.out.println("m :"+m);
				// [member - ±¸¼º¿øÁ¤º¸]
				MemberVO mvo = memdatas.getMember(); //¸â¹ö  °´Ã¼
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

				// [Career-°æ·Â»çÇ×]
				// rowMaxº¸´Ù ÀÛ´Ù¸é ¡æ ÀÔ·Â nullÃ³¸®

				//style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());// ½ºÅ¸ÀÏÁöÁ¤¾ÈµÊ;
				//style.setFillPattern(FillPatternType.SOLID_FOREGROUND);// ½ºÅ¸ÀÏÁöÁ¤¾ÈµÊ;

				if(memdatas.getCareer().size() <= m) {
					for(int nu = 0; nu < careerHeader.length; nu++) { // °æ header ±æÀÌ¸¸Å­ nullÃ³¸®
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else { // ¾Æ´Ï¶ó¸é µ¥ÀÌÅÍ ÀÔ·Â
					CareerInfoVO cvo = memdatas.getCareer().get(m); //°æ·Â ´ÜÀÏ  °´Ã¼
					System.out.println("excel cell : "+cellIndex);

					//curRow.createCell(cellIndex).setCellStyle(style); // ½ºÅ¸ÀÏÁöÁ¤¾ÈµÊ;
					curRow.createCell(cellIndex++).setCellValue(cvo.getStartDate());

					curRow.createCell(cellIndex++).setCellValue(cvo.getEndDate());
					curRow.createCell(cellIndex++).setCellValue(cvo.getCompName());
					curRow.createCell(cellIndex++).setCellValue(cvo.getDuty());
					curRow.createCell(cellIndex++).setCellValue(cvo.getPosition());
					curRow.createCell(cellIndex++).setCellValue(cvo.getRank());
				}


				// [school-ÇĞ·Â»çÇ×]
				// rowMaxº¸´Ù ÀÛ´Ù¸é ¡æ ÀÔ·Â nullÃ³¸®
				if(memdatas.getSchool().size() <= m) {
					for(int ns = 0; ns < schoolHeader.length; ns++) { // ÇĞ·Â header ±æÀÌ¸¸Å­ nullÃ³¸®
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else {    // ¾Æ´Ï¶ó¸é µ¥ÀÌÅÍ ÀÔ·Â
					SchoolInfoVO svo = memdatas.getSchool().get(m); //ÇĞ·Â ´ÜÀÏ  °´Ã¼
					curRow.createCell(cellIndex++).setCellValue(svo.getStartDate());
					curRow.createCell(cellIndex++).setCellValue(svo.getEndDate());
					curRow.createCell(cellIndex++).setCellValue(svo.getStype());
					curRow.createCell(cellIndex++).setCellValue(svo.getSname());
					curRow.createCell(cellIndex++).setCellValue(svo.getField());
					curRow.createCell(cellIndex++).setCellValue(svo.getMajor());
					curRow.createCell(cellIndex++).setCellValue(svo.getFinish());
				}					


				//[license-ÀÚ°İ»çÇ×]
				// rowMaxº¸´Ù ÀÛ´Ù¸é ¡æ ÀÔ·Â nullÃ³¸®
				if(memdatas.getLicense().size() <= m) {
					for(int nl = 0; nl < licenseHeader.length; nl++) { // license header ±æÀÌ¸¸Å­ nullÃ³¸®
						curRow.createCell(cellIndex++).setCellValue("");
					}
				}
				else {    // ¾Æ´Ï¶ó¸é µ¥ÀÌÅÍ ÀÔ·Â
					LicenseInfoVO lvo = memdatas.getLicense().get(m); //ÇĞ·Â ´ÜÀÏ  °´Ã¼
					curRow.createCell(cellIndex++).setCellValue(lvo.getGetDate());
					curRow.createCell(cellIndex++).setCellValue(lvo.getExpireDate());
					curRow.createCell(cellIndex++).setCellValue(lvo.getLname());
					curRow.createCell(cellIndex++).setCellValue(lvo.getGrade());
				}	

			}
		}// writeÀû¿ë Á¾·á


		// ¹İ¿µ ¹× Á¾·á
		workbook.write(fos);
		fos.close();

		response.getWriter().println("<script>window.close();</script>");
	} //method Á¾·á
 */