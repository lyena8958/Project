package common;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import controller.common.ExcelWriterCareer;
import controller.common.ExcelWriterLicense;
import controller.common.ExcelWriterSchool;
import model.member.MemberDAO;
import model.member.MemberSet;

@Service
public class ExcelFileWriter {
	
	@Autowired
	public ExcelFileWriter(ExcelWriterSet ews) {
		this.ews = ews;
	}
	@Autowired
	private MemberDAO mdao;
	private ExcelWriterSet ews;


	public void writeFile(HttpServletResponse response,
			ArrayList<String[]> headers, XSSFWorkbook workbook, ArrayList<Integer> mdata,
			@ModelAttribute("career")boolean career, boolean license, boolean school) throws IOException {
		// System.out.println("��"+ System.getProperty("user.dir")); C:\eclipse-jee-photon-R-win32-x86_64\eclipse

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
		ExcelWriterCareer careerW = ews.isCareer() ? ews.getCareerWriter() : null;
		ExcelWriterLicense licenseW = ews.isLicense() ? ews.getLicenseWriter() : null;
		ExcelWriterSchool schoolW = ews.isSchool() ? ews.getSchoolWriter() : null;
		System.out.println();
		
		// [���� ����] (ù ��)
		  // headers list ����  (�з�,���,�ڰ����� null�� �ƴ϶�� ����Է�)
	
		headers.add(ews.getMemberWriter().getMemberHeader());
		
		if(careerW != null) {
			headers.add(ews.getCareerWriter().getCareerHeader());
		}
		if(schoolW != null) {
			headers.add(ews.getSchoolWriter().getSchoolHeader());
		}
		if(licenseW != null) {
			headers.add(ews.getLicenseWriter().getLicenseHeader());
		}

		// [������ ����]
		List<MemberSet> result = mdao.getSetList(); // ������ �ۼ��� ������ ����


		// [��� �� ���ϸ� ����]
		// �� �ð� ����, ���ϸ� ����
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

		// ������ ���� ���
		
		 /*1) ��� �����Ͽ� ���� �� 
		String filePath = System.getProperty("user.home")+"\\Downloads\\YHS_"+dateTime+".xlsx"; 
				//"C:\\Users\\user\\Desktop\\YHS_" ��
				// "C:\\Users\\ITSC\\Desktop\\YHS_" �п�
		  */ 
		
		//2) ��� ȭ�鿡�� �ٿ�ε� �� ��
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename="+"YHS_"+dateTime+".xlsx;");

		// [���� �����Է� ���� ��ü Set]
		//FileOutputStream fos = new FileOutputStream(filePath); // 1) ��� �����Ͽ� ��������	
		OutputStream fos =  response.getOutputStream(); // 2) ��� �ٿ�ε�
		
		//XSSFWorkbook workbook = ews.getWorkbook(); // [���� ���� ��� ������ ���ε����� ��ü - write, style...]

		//XSSFCellStyle style = workbook.createCellStyle(); // ��Ÿ�� ���� ��ü
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

			// ������ �ִ밪 ���
			for(int rm = 0; rm < mdata.size()-1; rm++) {
				rowMax = mdata.get(rm) > mdata.get(rm+1)    ?     mdata.get(rm) : mdata.get(rm+1);
			}
			
			if(rowMax==0) { // ����� ���/�з�/�ڰ����� ���ٸ�, ȸ�������� ����ϵ��� 1 ����
				rowMax=1;
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
					// rowMax�̸����� ������ �Է��Ұ� ���ٸ� �� �Է� nulló��
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
					
					// rowMax�̸����� ������ �Է��Ұ� ���ٸ� �� �Է� nulló��
					if(memdatas.getSchool()==null || memdatas.getSchool().size() <= m) {
						//System.out.println("m : "+m);
						int slength = ews.getSchoolWriter().getSchoolHeader().length;

						for(int ns = 0; ns < slength; ns++) { // �з� header ���̸�ŭ nulló��
							curRow.createCell(cellIndex++).setCellValue("");
						}
					}
					else {    // �ƴ϶�� ������ �Է�
						//System.out.println("getSchool size :"+ memdatas.getSchool().size());
						// �з� input�޼��� ���� (curRow, cellIndex, ���� VO)
						cellIndex = ews.getSchoolWriter().sCellInput(curRow, cellIndex, memdatas.getSchool().get(m));
					}					
				}

				//[license-�ڰݻ���]
				if(ews.isLicense()) {
					// rowMax�̸����� ������ �Է��Ұ� ���ٸ� �� �Է� nulló��
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
		
		// 1) ��� �����Ͽ� ���� �� -- window â�� ��� �� �� ��θ� ���� �ٿ�ε� �� �� window.close ó��  
		//response.setContentType("text/html; charset=UTF-8"); 
		//response.getWriter().println("<script>window.close(); alert('"+System.getProperty("user.home")+" ���� ����Ǿ����ϴ�.'); alert('"+System.getProperty("user.home")+" ���� ����Ǿ����ϴ�.');"+"</script>");		
		//response.getWriter().println("<script>window.close(); </script>");
	}//method ����
}





/*//�� ���κ� ������ �ݺ�     (career - school - license ��)      	
//[Career-��»���]
for(int c = 0; c < memdatas.getCareer().size(); c++) {
	CareerInfoVO cvo = memdatas.getCareer().get(c); //��� ����  ��ü
	System.out.println("excel cell : "+cell);
	curRow.createCell(cell++).setCellValue(cvo.getStartDate());
	curRow.createCell(cell++).setCellValue(cvo.getEndDate());
	curRow.createCell(cell++).setCellValue(cvo.getCompName());
	curRow.createCell(cell++).setCellValue(cvo.getDuty());
	curRow.createCell(cell++).setCellValue(cvo.getPosition());
	curRow.createCell(cell++).setCellValue(cvo.getRank());

}
//[school-�з»���]
for(int s = 0; s < memdatas.getSchool().size(); s++) {
	SchoolInfoVO svo = memdatas.getSchool().get(s); //�з� ����  ��ü
	curRow.createCell(cell++).setCellValue(svo.getStartDate());
	curRow.createCell(cell++).setCellValue(svo.getEndDate());
	curRow.createCell(cell++).setCellValue(svo.getStype());
	curRow.createCell(cell++).setCellValue(svo.getSname());
	curRow.createCell(cell++).setCellValue(svo.getField());
	curRow.createCell(cell++).setCellValue(svo.getMajor());
	curRow.createCell(cell++).setCellValue(svo.getFinish());

}

//[license-�ڰݻ���]
for(int l = 0; l < memdatas.getLicense().size(); l++) {
	LicenseInfoVO lvo = memdatas.getLicense().get(l); //�з� ����  ��ü
	curRow.createCell(cell++).setCellValue(lvo.getGetDate());
	curRow.createCell(cell++).setCellValue(lvo.getExpireDate());
	curRow.createCell(cell++).setCellValue(lvo.getLname());
	curRow.createCell(cell++).setCellValue(lvo.getGrade());
	curRow.createCell(cell++).setCellValue(lvo.getLmem());

}*/
