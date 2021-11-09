package controller.common;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;

import model.member.MemberVO;

@Component("memberWriter")
public class ExcelWriterMember {
	private String[] memberHeader= {"시작일", "종료일", "사번", "성명", "재직여부", "생년월일", "부서", "직무", "직위", "직급"}; 
	
	
	public int mCellInput(XSSFRow curRow, int cellIndex, MemberVO vo) {
		curRow.createCell(cellIndex++).setCellValue(vo.getStartDate());
		curRow.createCell(cellIndex++).setCellValue(vo.getEndDate());
		curRow.createCell(cellIndex++).setCellValue(vo.getMnum());
		curRow.createCell(cellIndex++).setCellValue(vo.getmName());
		curRow.createCell(cellIndex++).setCellValue(vo.getWork());
		curRow.createCell(cellIndex++).setCellValue(vo.getBirthDate());
		curRow.createCell(cellIndex++).setCellValue(vo.getTeamName());
		curRow.createCell(cellIndex++).setCellValue(vo.getDuty());
		curRow.createCell(cellIndex++).setCellValue(vo.getPosition());
		curRow.createCell(cellIndex++).setCellValue(vo.getMrank());

		return cellIndex;
	}


	public String[] getMemberHeader() {
		return memberHeader;
	}


	public void setMemberHeader(String[] memberHeader) {
		this.memberHeader = memberHeader;
	}
	
	
}
