package common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import model.careerInfo.CareerInfoVO;
import model.licenseInfo.LicenseInfoVO;
import model.member.MemberDAO;
import model.member.MemberSet;
import model.schoolInfo.SchoolInfoVO;

public class ExcelFileWriter {

	@Autowired
	private MemberDAO mdao;
	@Autowired
	private ArrayList<String[]> headers;
	
	public void writeFile(List<MemberSet> sdatas) throws FileNotFoundException {
		
	// row ���� ����
		String[] memberHeader = {"������", "������", "���", "����", "��������", "�������", "�μ�", "����", "����", "����"};
		String[] careerHeader = {"�Ի���", "������", "ȸ���", "����", "����", "����"};
		String[] schoolHeader = {"������", "������","�з�", "�б���", "�����迭", "������", "�����з¿���"};
		String[] licenseHeader = {"�����","������","�ڰ�����", "���"};
		
		headers.add(memberHeader);
		headers.add(careerHeader);
		headers.add(schoolHeader);
		headers.add(licenseHeader);
		
	// ���� �Է�
		List<MemberSet> result = (List<MemberSet>) mdao.getSetList(); // ������ �ۼ��� ������ ����
				
		// ������ ���� ���
		String filePath = "YHR_profileList.xlsx";
		
		FileOutputStream fos = new FileOutputStream(filePath);
		
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("studentList"); // sheet ����
        
        XSSFRow curRow;
        
    // �� ������ �Է�
        // ���� �Է�
        curRow=sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
        	for(int j = 0; i < headers.get(i).length; i++) {
        		curRow.createCell(i).setCellValue(headers.get(i)[i]); // �ش� �迭�� index ex)������~..����
        	}
        	
        }
        int cell;
        
        
   // �������� ���� Write
        for (int i = 1; i < result.size(); i++) { // ��ü index 
        	
        	MemberSet memdatas = result.get(i); // ���κ� ������list      	
        	curRow=sheet.createRow(i); // �� index
        	
        	cell = 0; // �� cnt -- �� �࿡ ���� �� index 
        	
        	// �� �ִ밪 (�̼����� �� ������ ���� �и�)
        	int rowMax = memdatas.getLicense().size() > memdatas.getCareer().size() ? 
        			      memdatas.getLicense().size() : memdatas.getCareer().size();
        			      
        	rowMax = rowMax > memdatas.getSchool().size() ? rowMax : memdatas.getSchool().size();
        		
        	
      // �� ���κ� ������
        	for(int m = 0; m < rowMax; m++) {
        		
        	}
        	
        //�� ���κ� ������ �ݺ�     (career - school - license ��)      	
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

        	}
        }
	
	}
}
