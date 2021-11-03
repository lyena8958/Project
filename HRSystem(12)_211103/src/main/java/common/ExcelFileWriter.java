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
		
	// row 제목 설정
		String[] memberHeader = {"시작일", "종료일", "사번", "성명", "재직여부", "생년월일", "부서", "직무", "직위", "직급"};
		String[] careerHeader = {"입사일", "퇴직일", "회사명", "직무", "직위", "직급"};
		String[] schoolHeader = {"입학일", "졸업일","학력", "학교명", "전공계열", "전공명", "최종학력여부"};
		String[] licenseHeader = {"취득일","만료일","자격증명", "등급"};
		
		headers.add(memberHeader);
		headers.add(careerHeader);
		headers.add(schoolHeader);
		headers.add(licenseHeader);
		
	// 파일 입력
		List<MemberSet> result = (List<MemberSet>) mdao.getSetList(); // 엑셀에 작성할 데이터 모음
				
		// 저장할 파일 경로
		String filePath = "YHR_profileList.xlsx";
		
		FileOutputStream fos = new FileOutputStream(filePath);
		
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("studentList"); // sheet 생성
        
        XSSFRow curRow;
        
    // 행 데이터 입력
        // 제목 입력
        curRow=sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
        	for(int j = 0; i < headers.get(i).length; i++) {
        		curRow.createCell(i).setCellValue(headers.get(i)[i]); // 해당 배열의 index ex)시작일~..직급
        	}
        	
        }
        int cell;
        
        
   // ▶데이터 엑셀 Write
        for (int i = 1; i < result.size(); i++) { // 전체 index 
        	
        	MemberSet memdatas = result.get(i); // 개인별 데이터list      	
        	curRow=sheet.createRow(i); // 행 index
        	
        	cell = 0; // 셀 cnt -- 한 행에 넣을 셀 index 
        	
        	// 행 최대값 (미설정시 행 데이터 값이 밀림)
        	int rowMax = memdatas.getLicense().size() > memdatas.getCareer().size() ? 
        			      memdatas.getLicense().size() : memdatas.getCareer().size();
        			      
        	rowMax = rowMax > memdatas.getSchool().size() ? rowMax : memdatas.getSchool().size();
        		
        	
      // ▶ 개인별 데이터
        	for(int m = 0; m < rowMax; m++) {
        		
        	}
        	
        //☆ 개인별 데이터 반복     (career - school - license 순)      	
        	//[Career-경력사항]
        	for(int c = 0; c < memdatas.getCareer().size(); c++) {
        		CareerInfoVO cvo = memdatas.getCareer().get(c); //경력 단일  객체
        		System.out.println("excel cell : "+cell);
        		curRow.createCell(cell++).setCellValue(cvo.getStartDate());
        		curRow.createCell(cell++).setCellValue(cvo.getEndDate());
        		curRow.createCell(cell++).setCellValue(cvo.getCompName());
        		curRow.createCell(cell++).setCellValue(cvo.getDuty());
        		curRow.createCell(cell++).setCellValue(cvo.getPosition());
        		curRow.createCell(cell++).setCellValue(cvo.getRank());
        		
        	}
        	//[school-학력사항]
        	for(int s = 0; s < memdatas.getSchool().size(); s++) {
        		SchoolInfoVO svo = memdatas.getSchool().get(s); //학력 단일  객체
        		curRow.createCell(cell++).setCellValue(svo.getStartDate());
        		curRow.createCell(cell++).setCellValue(svo.getEndDate());
        		curRow.createCell(cell++).setCellValue(svo.getStype());
        		curRow.createCell(cell++).setCellValue(svo.getSname());
        		curRow.createCell(cell++).setCellValue(svo.getField());
        		curRow.createCell(cell++).setCellValue(svo.getMajor());
        		curRow.createCell(cell++).setCellValue(svo.getFinish());

        	}
        	
          	//[license-자격사항]
        	for(int l = 0; l < memdatas.getLicense().size(); l++) {
        		LicenseInfoVO lvo = memdatas.getLicense().get(l); //학력 단일  객체
        		curRow.createCell(cell++).setCellValue(lvo.getGetDate());
        		curRow.createCell(cell++).setCellValue(lvo.getExpireDate());
        		curRow.createCell(cell++).setCellValue(lvo.getLname());
        		curRow.createCell(cell++).setCellValue(lvo.getGrade());
        		curRow.createCell(cell++).setCellValue(lvo.getLmem());

        	}
        }
	
	}
}
