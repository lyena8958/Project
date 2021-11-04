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
		List<MemberSet> result = mdao.getSetList(); // 엑셀에 작성할 데이터 모음
		
		
		// 현재시간
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		System.out.println("현재시간 "+dateTime);

		// 저장할 파일 경로
		String filePath = "C:\\Users\\user\\Desktop\\YHR_"+dateTime+".xlsx"; 
				//"C:\\Users\\user\\Desktop\\YHR_profileList.xlsx"; 집
				// "C:\\Users\\ITSC\\Desktop\\YHR_profileList.xlsx" 학원 
		
		filePath.replaceAll("-", "");
		filePath.replaceAll("T", "");
		
		// 에
		FileOutputStream fos = new FileOutputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("memList"); // sheet 생성

		XSSFRow curRow;

		// 행 데이터 입력
		// 제목 입력
		
		curRow=sheet.createRow(0); // 첫행
		
		int hadersSize=0; // 전체 제목사이즈

	/*	for (int i = 0; i < headers.size(); i++) { // 각 테이블의 제목size 합산
			 hadersSize += headers.get(i).length; 
		}*/
		
		                  //  ↓
		
		for (int i = 0; i < headers.size(); i++) { // 제목 입력
			
			for(int j = 0; j < headers.get(i).length; j++) {
				
				curRow.createCell(hadersSize).setCellValue(headers.get(i)[j]); // 해당 배열의 index ex)시작일~..직급
				hadersSize++;
			}

		}
	/*	for (int i = 0; i < headers.size(); i++) {
			 hadersSize += headers.get(i).length-1;
			for(int j = 0; i < headers.get(i).length-1; i++) {
				curRow.createCell(i).setCellValue(headers.get(i)[i]); // 해당 배열의 index ex)시작일~..직급
			}

		}*/
		
		int cell; // 셀(행의 열)
		int row=1;
		System.out.println(result);

		// ▶데이터 엑셀 Write
		for (int i = 0; i < result.size(); i++) { // 전체 index 
			
			System.out.println("i : "+i+1+"\n");
			MemberSet memdatas = result.get(i); // 개인별 데이터list      	
			
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
			// 행 최대값 (미설정시 행 데이터 값이 밀림)
	/*		int rowMax = memdatas.getLicense().size() > memdatas.getCareer().size() ? 
					memdatas.getLicense().size() : memdatas.getCareer().size();

			rowMax = rowMax > memdatas.getSchool().size() ? rowMax : memdatas.getSchool().size();
*/

			// ▶ 개인별 데이터
					for(int m = 0; m < rowMax; m++) {
						cell = 0; // 셀 cnt -- 한 행에 넣을 셀 index 
						curRow=sheet.createRow(row++); // 행 index
						System.out.println("m :"+m);
					// [member - 구성원정보]
						MemberVO mvo = memdatas.getMember(); //멤버  객체
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

					// [Career-경력사항]
						// rowMax보다 작다면 → 입력 null처리
						if(memdatas.getCareer().size() <= m) {
							for(int nu = 0; nu < careerHeader.length; nu++) { // 경 header 길이만큼 null처리
								curRow.createCell(cell++).setCellValue("");
							}
						}
						else { // 아니라면 데이터 입력
							CareerInfoVO cvo = memdatas.getCareer().get(m); //경력 단일  객체
							System.out.println("excel cell : "+cell);
							curRow.createCell(cell++).setCellValue(cvo.getStartDate());
							curRow.createCell(cell++).setCellValue(cvo.getEndDate());
							curRow.createCell(cell++).setCellValue(cvo.getCompName());
							curRow.createCell(cell++).setCellValue(cvo.getDuty());
							curRow.createCell(cell++).setCellValue(cvo.getPosition());
							curRow.createCell(cell++).setCellValue(cvo.getRank());
						}


					// [school-학력사항]
						// rowMax보다 작다면 → 입력 null처리
						if(memdatas.getSchool().size() <= m) {
							for(int ns = 0; ns < schoolHeader.length; ns++) { // 학력 header 길이만큼 null처리
								curRow.createCell(cell++).setCellValue("");
							}
						}
						else {    // 아니라면 데이터 입력
							SchoolInfoVO svo = memdatas.getSchool().get(m); //학력 단일  객체
							curRow.createCell(cell++).setCellValue(svo.getStartDate());
							curRow.createCell(cell++).setCellValue(svo.getEndDate());
							curRow.createCell(cell++).setCellValue(svo.getStype());
							curRow.createCell(cell++).setCellValue(svo.getSname());
							curRow.createCell(cell++).setCellValue(svo.getField());
							curRow.createCell(cell++).setCellValue(svo.getMajor());
							curRow.createCell(cell++).setCellValue(svo.getFinish());
						}					


					//[license-자격사항]
						// rowMax보다 작다면 → 입력 null처리
						if(memdatas.getLicense().size() <= m) {
							for(int nl = 0; nl < licenseHeader.length; nl++) { // license header 길이만큼 null처리
								curRow.createCell(cell++).setCellValue("");
							}
						}
						else {    // 아니라면 데이터 입력
							LicenseInfoVO lvo = memdatas.getLicense().get(m); //학력 단일  객체
							curRow.createCell(cell++).setCellValue(lvo.getGetDate());
							curRow.createCell(cell++).setCellValue(lvo.getExpireDate());
							curRow.createCell(cell++).setCellValue(lvo.getLname());
							curRow.createCell(cell++).setCellValue(lvo.getGrade());
						}	

					}
		}// write적용 종료
			// 반영 및 종료
			workbook.write(fos);
			fos.close();
		
	}//method 종료
}




/*
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

}*/
