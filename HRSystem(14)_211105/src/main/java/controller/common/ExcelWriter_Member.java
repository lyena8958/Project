package controller.common;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;

import model.member.MemberVO;

@Component("memberWriter")
public class ExcelWriter_Member {
	private String[] memberHeader= {"������", "������", "���", "����", "��������", "�������", "�μ�", "����", "����", "����"}; 
	
	
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
