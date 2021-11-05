package common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import controller.common.ExcelWriter_Career;
import controller.common.ExcelWriter_License;
import controller.common.ExcelWriter_School;
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
		ews.setCareer(career);
		ews.setLicense(license);
		ews.setSchool(school);
		System.out.println(ews);
		System.out.println("isCareer : "+ews.isCareer());
		System.out.println("isLicense : "+ews.isLicense());
		System.out.println("isSchool : "+ews.isSchool());
		// [객체 설정] 
		// is~~ : 사용자가 checkBox를 클릭했다면 바인드 객체로 인해, true 되어있음
		// writer 객체 설정 (Client가 Checkbox를 클릭했다면 객체를 가져옴, 아니라면 null반환)
		ExcelWriter_Career careerW = ews.isCareer() ? ews.getCareerWriter() : null;
		ExcelWriter_License licenseW = ews.isLicense() ? ews.getLicenseWriter() : null;
		ExcelWriter_School schoolW = ews.isSchool() ? ews.getSchoolWriter() : null;
		System.out.println();
		
		// [제목 설정] (첫 행)
		  // headers list 저장  (학력,경력,자격증은 null이 아니라면 헤더입력)
	
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
		
		// [데이터 취합]
		List<MemberSet> result = mdao.getSetList(); // 엑셀에 작성할 데이터 모음


		// [경로 및 파일명 지정]
		// 현 시간 기준, 파일명 지정
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

		// 저장할 파일 경로
		String filePath = "C:\\Users\\ITSC\\Desktop\\YHS_"+dateTime+".xlsx"; 
		//"C:\\Users\\user\\Desktop\\YHS_" 집
		// "C:\\Users\\ITSC\\Desktop\\YHS_" 학원

		// [엑셀 파일입력 관련 객체 Set]
		FileOutputStream fos = new FileOutputStream(filePath); // 경로 저장	
		//XSSFWorkbook workbook = ews.getWorkbook(); // [엑셀 관련 모든 설정을 업로드해줄 객체 - write, style...]

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
			// 사용자 checkBox 클릭여부 판단 후, size ALadd
			if(ews.isCareer()) {  // 경력
				mdata.add(memdatas.getCareer()==null? 0 : memdatas.getCareer().size());
			}
			if(ews.isLicense()) { // 자격증
				mdata.add(memdatas.getLicense()==null? 0 : memdatas.getLicense().size());
			}
			if(ews.isSchool()) { // 학력
				mdata.add(memdatas.getSchool()==null? 0 : memdatas.getSchool().size());
			}

			int rowMax=0; // 행 최대값 변수

			// 최대값 계산
			for(int rm = 0; rm < mdata.size()-1; rm++) {
				rowMax = mdata.get(rm) > mdata.get(rm+1)    ?     mdata.get(rm) : mdata.get(rm+1);
			}

			// [개인 데이터 input] - 개인데이터는 학력,경력,자격증이 list로 이루어져있으므로 "행 출력"이 반복될 수 있다.
			for(int m = 0; m < rowMax; m++) {

				cellIndex = 0; // 셀 cnt -- 한 행에 넣을 셀 index(==headersize)
				curRow=sheet.createRow(row++); // 행 index

				// [member - 구성원정보] -------------------------------------
				// 구성원 input메서드 수행 (curRow, cellIndex, 단일 VO)
				cellIndex = ews.getMemberWriter().mCellInput(curRow, cellIndex, memdatas.getMember());


				// [Career-경력사항] -------------------------------------
				if(ews.isCareer()) { // Client check클릭여부 판단
					// rowMax보다 작다면 → 입력 null처리
					if(memdatas.getCareer()==null || memdatas.getCareer().size() <= m) {
						int clength = ews.getCareerWriter().getCareerHeader().length;

						for(int nu = 0; nu < clength; nu++) { // 경 header 길이만큼 null처리
							curRow.createCell(cellIndex++).setCellValue("");
						}
					}
					else { // 아니라면 데이터 입력
						// 경력 input메서드 수행 (curRow, cellIndex, 단일 VO)
						cellIndex = ews.getCareerWriter().cCellInput(curRow, cellIndex, memdatas.getCareer().get(m));
					}
				}

				// [school-학력사항] -------------------------------------
				if(ews.isSchool()) {
					
					// rowMax보다 작다면 → 입력 null처리
					if(memdatas.getSchool()==null || memdatas.getSchool().size() <= m) {
						System.out.println("m : "+m);
						int slength = ews.getSchoolWriter().getSchoolHeader().length;

						for(int ns = 0; ns < slength; ns++) { // 학력 header 길이만큼 null처리
							curRow.createCell(cellIndex++).setCellValue("");
						}
					}
					else {    // 아니라면 데이터 입력
						System.out.println("getSchool size :"+ memdatas.getSchool().size());
						// 학력 input메서드 수행 (curRow, cellIndex, 단일 VO)
						cellIndex = ews.getSchoolWriter().sCellInput(curRow, cellIndex, memdatas.getSchool().get(m));
					}					
				}

				//[license-자격사항]
				if(ews.isLicense()) {
					// rowMax보다 작다면 → 입력 null처리
					if(memdatas.getLicense()==null || memdatas.getLicense().size() <= m) {
						int llenght = ews.getLicenseWriter().getLicenseHeader().length;
						
						for(int nl = 0; nl < llenght; nl++) { // license header 길이만큼 null처리
							curRow.createCell(cellIndex++).setCellValue("");
						}
					}
					else {    // 아니라면 데이터 입력
						// 자격증 input메서드 수행 (curRow, cellIndex, 단일 VO)
						cellIndex = ews.getLicenseWriter().lCellInput(curRow, cellIndex, memdatas.getLicense().get(m));
					}	
				}
			}
		}// write적용 종료


		// 반영 및 종료
		workbook.write(fos);
		fos.close();

		response.getWriter().println("<script>window.close();</script>");		
	}//method 종료
}





/*//☆ 개인별 데이터 반복     (career - school - license 순)      	
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
