package common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.careerInfo.CareerInfoVO;
import model.licenseInfo.LicenseInfoVO;
import model.member.MemberDAO;
import model.member.MemberSet;
import model.member.MemberVO;
import model.schoolInfo.SchoolInfoVO;

@Service
public class ExcelFileWriter {

	@Autowired
	private MemberDAO mdao;
	
	private ArrayList<String[]> headers;

	public void writeFile() throws IOException {
		System.out.println("memService "+mdao); 
		
		//memService = new mdao
		headers =new ArrayList<String[]>();
		
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
		List<MemberSet> result = mdao.getSetList(); // ������ �ۼ��� ������ ����
		
		
		// ����ð�
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		System.out.println("����ð� "+dateTime);

		// ������ ���� ���
		String filePath = "C:\\Users\\user\\Desktop\\YHR_"+dateTime+".xlsx"; 
				//"C:\\Users\\user\\Desktop\\YHR_profileList.xlsx"; ��
				// "C:\\Users\\ITSC\\Desktop\\YHR_profileList.xlsx" �п� 
		
		filePath.replaceAll("-", "");
		filePath.replaceAll("T", "");
		
		// ��
		FileOutputStream fos = new FileOutputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("memList"); // sheet ����

		XSSFRow curRow;

		// �� ������ �Է�
		// ���� �Է�
		
		curRow=sheet.createRow(0); // ù��
		
		int hadersSize=0; // ��ü ���������

	/*	for (int i = 0; i < headers.size(); i++) { // �� ���̺��� ����size �ջ�
			 hadersSize += headers.get(i).length; 
		}*/
		
		                  //  ��
		
		for (int i = 0; i < headers.size(); i++) { // ���� �Է�
			
			for(int j = 0; j < headers.get(i).length; j++) {
				
				curRow.createCell(hadersSize).setCellValue(headers.get(i)[j]); // �ش� �迭�� index ex)������~..����
				hadersSize++;
			}

		}
	/*	for (int i = 0; i < headers.size(); i++) {
			 hadersSize += headers.get(i).length-1;
			for(int j = 0; i < headers.get(i).length-1; i++) {
				curRow.createCell(i).setCellValue(headers.get(i)[i]); // �ش� �迭�� index ex)������~..����
			}

		}*/
		
		int cell; // ��(���� ��)
		int row=1;
		System.out.println(result);

		// �������� ���� Write
		for (int i = 0; i < result.size(); i++) { // ��ü index 
			
			System.out.println("i : "+i+1+"\n");
			MemberSet memdatas = result.get(i); // ���κ� ������list      	
			
			ArrayList<Integer> mdata = new ArrayList<Integer>();
			mdata.add(memdatas.getCareer()==null? 0 : memdatas.getCareer().size());
			mdata.add(memdatas.getLicense()==null? 0 : memdatas.getLicense().size());
			mdata.add(memdatas.getSchool()==null? 0 : memdatas.getSchool().size());
			System.out.println("getCareer "+memdatas.getCareer());
			System.out.println("getLicense "+memdatas.getLicense());
			System.out.println("getSchool "+memdatas.getSchool()+"\n");
			int rowMax=0;
			
			for(int rm = 0; rm < mdata.size()-1; rm++) {
				System.out.println("rm : "+rm+"\n");
				
				rowMax = mdata.get(rm)>mdata.get(rm+1) ? mdata.get(rm) : mdata.get(rm+1);
				/*if(mdata.get(rm) > mdata.get(rm+1)) {
					rowMax = mdata.get(rm);
				}
				else {
					rowMax = mdata.get(rm+1);
				}*/
			}
			System.out.println(rowMax+"\n");
			// �� �ִ밪 (�̼����� �� ������ ���� �и�)
	/*		int rowMax = memdatas.getLicense().size() > memdatas.getCareer().size() ? 
					memdatas.getLicense().size() : memdatas.getCareer().size();

			rowMax = rowMax > memdatas.getSchool().size() ? rowMax : memdatas.getSchool().size();
*/

			// �� ���κ� ������
					for(int m = 0; m < rowMax; m++) {
						cell = 0; // �� cnt -- �� �࿡ ���� �� index 
						curRow=sheet.createRow(row++); // �� index
						System.out.println("m :"+m);
					// [member - ����������]
						MemberVO mvo = memdatas.getMember(); //���  ��ü
						curRow.createCell(cell++).setCellValue(mvo.getStartDate());
						curRow.createCell(cell++).setCellValue(mvo.getEndDate());
						curRow.createCell(cell++).setCellValue(mvo.getMnum());
						curRow.createCell(cell++).setCellValue(mvo.getmName());
						curRow.createCell(cell++).setCellValue(mvo.getWork());
						curRow.createCell(cell++).setCellValue(mvo.getBirthDate());
						curRow.createCell(cell++).setCellValue(mvo.getTeamName());
						curRow.createCell(cell++).setCellValue(mvo.getDuty());
						curRow.createCell(cell++).setCellValue(mvo.getPosition());
						curRow.createCell(cell++).setCellValue(mvo.getMrank());

					// [Career-��»���]
						// rowMax���� �۴ٸ� �� �Է� nulló��
						if(memdatas.getCareer().size() <= m) {
							for(int nu = 0; nu < careerHeader.length; nu++) { // �� header ���̸�ŭ nulló��
								curRow.createCell(cell++).setCellValue("");
							}
						}
						else { // �ƴ϶�� ������ �Է�
							CareerInfoVO cvo = memdatas.getCareer().get(m); //��� ����  ��ü
							System.out.println("excel cell : "+cell);
							curRow.createCell(cell++).setCellValue(cvo.getStartDate());
							curRow.createCell(cell++).setCellValue(cvo.getEndDate());
							curRow.createCell(cell++).setCellValue(cvo.getCompName());
							curRow.createCell(cell++).setCellValue(cvo.getDuty());
							curRow.createCell(cell++).setCellValue(cvo.getPosition());
							curRow.createCell(cell++).setCellValue(cvo.getRank());
						}


					// [school-�з»���]
						// rowMax���� �۴ٸ� �� �Է� nulló��
						if(memdatas.getSchool().size() <= m) {
							for(int ns = 0; ns < schoolHeader.length; ns++) { // �з� header ���̸�ŭ nulló��
								curRow.createCell(cell++).setCellValue("");
							}
						}
						else {    // �ƴ϶�� ������ �Է�
							SchoolInfoVO svo = memdatas.getSchool().get(m); //�з� ����  ��ü
							curRow.createCell(cell++).setCellValue(svo.getStartDate());
							curRow.createCell(cell++).setCellValue(svo.getEndDate());
							curRow.createCell(cell++).setCellValue(svo.getStype());
							curRow.createCell(cell++).setCellValue(svo.getSname());
							curRow.createCell(cell++).setCellValue(svo.getField());
							curRow.createCell(cell++).setCellValue(svo.getMajor());
							curRow.createCell(cell++).setCellValue(svo.getFinish());
						}					


					//[license-�ڰݻ���]
						// rowMax���� �۴ٸ� �� �Է� nulló��
						if(memdatas.getLicense().size() <= m) {
							for(int nl = 0; nl < licenseHeader.length; nl++) { // license header ���̸�ŭ nulló��
								curRow.createCell(cell++).setCellValue("");
							}
						}
						else {    // �ƴ϶�� ������ �Է�
							LicenseInfoVO lvo = memdatas.getLicense().get(m); //�з� ����  ��ü
							curRow.createCell(cell++).setCellValue(lvo.getGetDate());
							curRow.createCell(cell++).setCellValue(lvo.getExpireDate());
							curRow.createCell(cell++).setCellValue(lvo.getLname());
							curRow.createCell(cell++).setCellValue(lvo.getGrade());
						}	

					}
		}// write���� ����
			// �ݿ� �� ����
			workbook.write(fos);
			fos.close();
		
	}//method ����
}




/*
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

}*/
