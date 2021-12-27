package model.licenseInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import model.careerInfo.CareerInfoVO;


//SELETE 쿼리문 MappeR Class
class LicenseRowMapper implements RowMapper<LicenseInfoVO>{

	@Override
	public LicenseInfoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		LicenseInfoVO data = new LicenseInfoVO();

		data.setLnum(rs.getInt("LNUM"));
		data.setGetDate(rs.getString("GETDATE").substring(0, 10));
		data.setExpireDate(rs.getString("EXPIREDATE").substring(0, 10));
		data.setLname(rs.getString("LNAME"));
		data.setGrade(rs.getString("GRADE"));
		data.setLmem(rs.getInt("LMEM"));

		return data;
	}

}

@Repository
public class LicenseInfoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 쿼리문
	private String GET_ONE = "SELECT * FROM licenseInfo WHERE LNUM=?";
	private String GET_LIST = "SELECT * FROM licenseInfo WHERE LMEM=? ORDER BY LMEM ASC, GETDATE ASC";
	private String INSERT = "INSERT INTO licenseInfo (GETDATE, EXPIREDATE, LNAME, GRADE, LMEM) VALUES (?, ?, ?, ?, ?)";
	private String UPDATE = "UPDATE licenseInfo SET GETDATE=?, EXPIREDATE=?, LNAME=?, GRADE=? WHERE LNUM=?";
	private String DELETE = "DELETE FROM licenseInfo WHERE LNUM=?";

	private String GET_LIST_ALL = "SELECT * FROM licenseInfo ORDER BY LMEM ASC";
	
	//[ONE]
	public LicenseInfoVO getData(LicenseInfoVO vo) {
		Object[] args = {vo.getLnum()};
		return jdbcTemplate.queryForObject(GET_ONE, args, new LicenseRowMapper());

	}
	//[LIST]
	public List<LicenseInfoVO> getList() { // LicenseInfoVO vo
		List<LicenseInfoVO> datas = jdbcTemplate.query(GET_LIST_ALL, new LicenseRowMapper());
		return datas = (datas.size()==0)? null : datas; // 삼항연산 : 참이라면 null반환, 아니라면 데이터 반환
	}


	//[LIST]
	public List<LicenseInfoVO> getList(LicenseInfoVO vo) { // LicenseInfoVO vo
		Object[] args = {vo.getLmem()};
		List<LicenseInfoVO> datas = jdbcTemplate.query(GET_LIST, args, new LicenseRowMapper());
		return datas = (datas.size()==0)? null : datas; // 삼항연산 : 참이라면 null반환, 아니라면 데이터 반환
	}

	//[LIST]
	public List<LicenseInfoVO> getList(int mnum) { // LicenseInfoVO vo
		Object[] args = {mnum};
		List<LicenseInfoVO> datas = jdbcTemplate.query(GET_LIST, args, new LicenseRowMapper());
		return datas = (datas.size()==0)? null : datas; // 삼항연산 : 참이라면 null반환, 아니라면 데이터 반환
	}

	//[INSERT]
	public boolean insertLicense(LicenseInfoVO vo) {
		Object[] args = {vo.getGetDate(), vo.getExpireDate(), vo.getLname(), vo.getGrade(), vo.getLmem()};
		jdbcTemplate.update(INSERT, args);

		return true;
	}

	//[UPDATE]
	public boolean updateLicense(LicenseInfoVO vo) {
		Object[] args = {vo.getGetDate(), vo.getExpireDate(), vo.getLname(), vo.getGrade(), vo.getLnum()};
		jdbcTemplate.update(UPDATE, args);

		return true;
	}

	//[DELETE]
	public boolean deleteLicense(LicenseInfoVO vo) {
		jdbcTemplate.update(DELETE, vo.getLnum());

		return true;
	}
}
