package model.licenseInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class LicenseInfoServiceImpl implements LicenseInfoService{

	@Autowired
	private LicenseInfoDAO dao;
	
	@Override
	public LicenseInfoVO getDate(LicenseInfoVO vo) {
		return dao.getDate(vo);
	}

	@Override
	public List<LicenseInfoVO> getList(LicenseInfoVO vo) {
		return dao.getList(vo);
	}

	@Override
	public boolean insertLicense(LicenseInfoVO vo) {
		return dao.insertLicense(vo);
	}

	@Override
	public boolean updateLicense(LicenseInfoVO vo) {
		return dao.updateLicense(vo);
	}

	@Override
	public boolean deleteLicense(LicenseInfoVO vo) {
		return dao.deleteLicense(vo);
	}

}
