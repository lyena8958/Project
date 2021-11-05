package controller.common;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;

import model.careerInfo.CareerInfoVO;

@Component("careerWriter")
public class ExcelWriter_Career {

	private String[] careerHeader = {"�Ի���", "������", "ȸ���", "����", "����", "����"}; 
	public String[] getCareerHeader() {
		return careerHeader;
	}

	public void setCareerHeader(String[] careerHeader) {
		this.careerHeader = careerHeader;
	}
	

	public int cCellInput(XSSFRow curRow, int cellIndex, CareerInfoVO vo) {
		curRow.createCell(cellIndex++).setCellValue(vo.getStartDate());
		curRow.createCell(cellIndex++).setCellValue(vo.getEndDate());
		curRow.createCell(cellIndex++).setCellValue(vo.getCompName());
		curRow.createCell(cellIndex++).setCellValue(vo.getDuty());
		curRow.createCell(cellIndex++).setCellValue(vo.getPosition());
		curRow.createCell(cellIndex++).setCellValue(vo.getRank()); 

		return cellIndex;
	}

	
	
}
