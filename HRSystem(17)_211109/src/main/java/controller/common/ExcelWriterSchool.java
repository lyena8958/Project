package controller.common;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;

import model.schoolInfo.SchoolInfoVO;

@Component("schoolWriter")
public class ExcelWriterSchool {
	
	private String[] schoolHeader = {"������", "������","�з�", "�б���", "�����迭", "������", "�����з¿���"};
	

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
