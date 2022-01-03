package common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import controller.common.ExcelWriterCareer;
import controller.common.ExcelWriterLicense;
import controller.common.ExcelWriterMember;
import controller.common.ExcelWriterSchool;

@Component
public class ExcelWriterSet {
	
	@Autowired
	public ExcelWriterSet(ExcelWriterMember memberWriter, 
			ExcelWriterCareer careerWriter, ExcelWriterSchool schoolWriter, ExcelWriterLicense licenseWriter){
		//System.out.println("ExcelWriterSet µµÂø");
		this.memberWriter = memberWriter;
		this.careerWriter = careerWriter;
		this.schoolWriter = schoolWriter;
		this.licenseWriter = licenseWriter;
		//System.out.println("?");
		
	}
 
	private boolean career;
	private boolean school;
	private boolean	license;

	private ExcelWriterMember memberWriter;
	private ExcelWriterCareer careerWriter;
	private ExcelWriterSchool schoolWriter;
	private ExcelWriterLicense licenseWriter;
	
	
	public boolean isCareer() {
		return career;
	}


	public void setCareer(boolean career) {
		this.career = career;
	}


	public boolean isSchool() {
		return school;
	}

	
	public void setSchool(boolean school) {
		this.school = school;
	}


	public boolean isLicense() {
		return license;
	}

	
	public void setLicense(boolean license) {
		this.license = license;
	}


	public ExcelWriterMember getMemberWriter() {
		return memberWriter;
	}


	public void setMemberWriter(ExcelWriterMember memberWriter) {
		this.memberWriter = memberWriter;
	}


	public ExcelWriterCareer getCareerWriter() {
		return careerWriter;
	}
	

	public void setCareerWriter(ExcelWriterCareer careerWriter) {
		this.careerWriter = careerWriter;
	}


	public ExcelWriterSchool getSchoolWriter() {
		return schoolWriter;
	}

	public void setSchoolWriter(ExcelWriterSchool schoolWriter) {
		this.schoolWriter = schoolWriter;
	}


	public ExcelWriterLicense getLicenseWriter() {
		return licenseWriter;
	}


	public void setLicenseWriter(ExcelWriterLicense licenseWriter) {
		this.licenseWriter = licenseWriter;
	}


	@Override
	public String toString() {
		return "ExcelWriterSet [career=" + career + ", school=" + school + ", license=" + license + ", memberWriter="
				+ memberWriter + ", careerWriter=" + careerWriter + ", schoolWriter=" + schoolWriter
				+ ", licenseWriter=" + licenseWriter + "]";
	}
	
	
	
	
	
}
