	package controller.common;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;

import model.licenseInfo.LicenseInfoVO;

@Component("licenseWriter")
public class ExcelWriter_License {

	private String[] licenseHeader = {"취득일","만료일","자격증명", "등급"};
		
	public int lCellInput(XSSFRow curRow, int cellIndex, LicenseInfoVO vo) {
	 	curRow.createCell(cellIndex++).setCellValue(vo.getGetDate());
		curRow.createCell(cellIndex++).setCellValue(vo.getExpireDate());
		curRow.createCell(cellIndex++).setCellValue(vo.getLname());
		curRow.createCell(cellIndex++).setCellValue(vo.getGrade());
		
		return cellIndex;
	}

	public String[] getLicenseHeader() {
		return licenseHeader;
	}

	public void setLicenseHeader(String[] licenseHeader) {
		this.licenseHeader = licenseHeader;
	}
	
}
