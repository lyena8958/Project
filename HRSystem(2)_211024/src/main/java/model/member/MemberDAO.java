package model.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


//SELETE Äõ¸®¹® MappeR Class
class MemberRowMapper implements RowMapper<MemberVO>{

	@Override
	public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberVO data = new MemberVO();
		
		data.setMnum(rs.getInt("MNUM"));
		data.setmName(rs.getString("MNAME"));
		data.setPath(rs.getString("PATH"));
		data.setStartDate(rs.getDate("STARTDATE"));
		data.setEndDate(rs.getDate("ENDDATE"));
		data.setBirthDate(rs.getDate("BIRTHDATE"));
		data.setTeamName(rs.getString("TEAMNAME"));
		data.setDuty(rs.getString("DUTY"));
		data.setPosition(rs.getString("POSITION"));
		data.setRank(rs.getInt("RANK"));
				
		
		return data;
	}
	
}

public class MemberDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*// Äõ¸®¹®
	private String GET_ONE = "SELECT * FROM LICENSEINFO WHERE LNUM=?";
	private String GET_LIST = "SELECT * FROM LICENSEINFO";
	private String INSERT = "INSERT INTO LICENSEINFO (LNUM, GETDATE, EXPIREDATE, LNAME, GRADE, LMEM) VALUES ((SELECT NVL(MAX(LNUM),0)+1 FROM LICENSEINFO), ?, ?, ?, ?, ?)";
	private String UPDATE = "UPDATE LICENSEINFO SET GETDATE=?, EXPIREDATE=?, LNAME=?, GRADE=? WHERE LNUM=?";
	private String DELETE = "DELETE FROM LICENSEINFO WHERE LNUM=?";
	
	//[ONE]
	public MemberVO getDate(MemberVO vo) {
		Object[] args = {vo.getLnum()};
		return jdbcTemplate.queryForObject(GET_ONE, args, new LicenseRowMapper());
		
	}
	
	//[LIST]
	public List<MemberVO> getList(MemberVO vo) {
		//Object[] args = {};
		return jdbcTemplate.query(GET_LIST, new LicenseRowMapper());
		
	}
	
	//[INSERT]
	public boolean insertLicense(MemberVO vo) {
		Object[] args = {vo.getGetDate(), vo.getExpireDate(), vo.getLname(), vo.getGrade(), vo.getlMem()};
		jdbcTemplate.update(INSERT, args);
		
		return true;
	}
	
	//[UPDATE]
	public boolean updateLicense(MemberVO vo) {
		Object[] args = {vo.getGetDate(), vo.getExpireDate(), vo.getLname(), vo.getGrade(), vo.getLnum()};
		jdbcTemplate.update(UPDATE, args);
		
		return true;
	}
	
	//[DELETE]
	public boolean deleteLicense(MemberVO vo) {
		jdbcTemplate.update(DELETE, vo.getLnum());
		
		return true;
	}*/
}
