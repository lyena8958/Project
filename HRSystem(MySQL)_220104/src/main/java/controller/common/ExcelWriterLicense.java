	package controller.common;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;

import model.licenseInfo.LicenseInfoVO;

@Component("licenseWriter")
public class ExcelWriterLicense {

	private String[] licenseHeader = {"�����","������","�ڰ�����", "���"};
		
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
