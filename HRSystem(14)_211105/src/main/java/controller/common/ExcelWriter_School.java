package controller.common;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;

import model.schoolInfo.SchoolInfoVO;

@Component("schoolWriter")
public class ExcelWriter_School {
	
	private String[] schoolHeader = {"입학일", "졸업일","학력", "학교명", "전공계열", "전공명", "최종학력여부"};
	

	public int sCellInput(XSSFRow curRow, int cellIndex, SchoolInfoVO vo) {
		curRow.createCell(cellIndex++).setCellValue(vo.getStartDate());
		curRow.createCell(cellIndex++).setCellValue(vo.getEndDate());
		curRow.createCell(cellIndex++).setCellValue(vo.getStype());
		curRow.createCell(cellIndex++).setCellValue(vo.getSname());
		curRow.createCell(cellIndex++).setCellValue(vo.getField());
		curRow.createCell(cellIndex++).setCellValue(vo.getMajor());
		curRow.createCell(cellIndex++).setCellValue(vo.getFinish());

		return cellIndex;
	}

	public String[] getSchoolHeader() {
		return schoolHeader;
	}

	public void setSchoolHeader(String[] schoolHeader) {
		this.schoolHeader = schoolHeader;
	}
	
}
