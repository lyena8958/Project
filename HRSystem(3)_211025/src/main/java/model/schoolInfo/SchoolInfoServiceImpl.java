package model.schoolInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("schoolInfoService")
public class SchoolInfoServiceImpl implements SchoolInfoService{

	@Autowired
	private SchoolInfoService dao;
	
	@Override
	public SchoolInfoVO getDate(SchoolInfoVO vo) {
		return dao.getDate(vo);
	}

	@Override
	public List<SchoolInfoVO> getList(SchoolInfoVO vo) {
		return dao.getList(vo);
	}

	@Override
	public boolean insertSchool(SchoolInfoVO vo) {
		return dao.insertSchool(vo);
	}

	@Override
	public boolean updateSchool(SchoolInfoVO vo) {
		return dao.updateSchool(vo);
	}

	@Override
	public boolean deleteSchool(SchoolInfoVO vo) {
		return dao.deleteSchool(vo);
	}
	
	
}
