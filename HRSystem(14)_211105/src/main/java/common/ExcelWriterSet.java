package common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import controller.common.ExcelWriter_Career;
import controller.common.ExcelWriter_License;
import controller.common.ExcelWriter_Member;
import controller.common.ExcelWriter_School;

@Component
public class ExcelWriterSet {
	
	@Autowired
	public ExcelWriterSet(ExcelWriter_Member memberWriter, 
			ExcelWriter_Career careerWriter, ExcelWriter_School schoolWriter, ExcelWriter_License licenseWriter){
		System.out.println("ExcelWriterSet µµÂø");
		this.memberWriter = memberWriter;
		this.careerWriter = careerWriter;
		this.schoolWriter = schoolWriter;
		this.licenseWriter = licenseWriter;
		System.out.println("?");
		
	}
 
	private boolean career;
	private boolean school;
	private boolean	license;

	private ExcelWriter_Member memberWriter;
	private ExcelWriter_Career careerWriter;
	private ExcelWriter_School schoolWriter;
	private ExcelWriter_License licenseWriter;
	
	
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


	public ExcelWriter_Member getMemberWriter() {
		return memberWriter;
	}


	public void setMemberWriter(ExcelWriter_Member memberWriter) {
		this.memberWriter = memberWriter;
	}


	public ExcelWriter_Career getCareerWriter() {
		return careerWriter;
	}
	

	public void setCareerWriter(ExcelWriter_Career careerWriter) {
		this.careerWriter = careerWriter;
	}


	public ExcelWriter_School getSchoolWriter() {
		return schoolWriter;
	}

	public void setSchoolWriter(ExcelWriter_School schoolWriter) {
		this.schoolWriter = schoolWriter;
	}


	public ExcelWriter_License getLicenseWriter() {
		return licenseWriter;
	}


	public void setLicenseWriter(ExcelWriter_License licenseWriter) {
		this.licenseWriter = licenseWriter;
	}


	@Override
	public String toString() {
		return "ExcelWriterSet [career=" + career + ", school=" + school + ", license=" + license + ", memberWriter="
				+ memberWriter + ", careerWriter=" + careerWriter + ", schoolWriter=" + schoolWriter
				+ ", licenseWriter=" + licenseWriter + "]";
	}
	
	
	
	
	
}
