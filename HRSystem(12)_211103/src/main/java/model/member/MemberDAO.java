package model.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import model.careerInfo.CareerInfoDAO;
import model.licenseInfo.LicenseInfoDAO;
import model.schoolInfo.SchoolInfoDAO;


//SELETE Äõ¸®¹® MappeR Class
class MemberRowMapper implements RowMapper<MemberVO>{

	@Override
	public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberVO data = new MemberVO();

		data.setMnum(rs.getInt("MNUM"));
		data.setmName(rs.getString("MNAME"));
		data.setPath(rs.getString("PATH"));
		data.setStartDate(rs.getString("STARTDATE"));
		data.setEndDate(rs.getString("ENDDATE"));
		data.setBirthDate(rs.getString("BIRTHDATE"));
		data.setTeamName(rs.getString("TEAMNAME"));
		data.setDuty(rs.getString("DUTY"));
		data.setPosition(rs.getString("POSITION"));
		data.setWork(rs.getString("WORK"));
		data.setMrank(rs.getInt("MRANK"));


		return data;
	}
	public int mapRow(ResultSet rs) throws SQLException {


		return rs.getInt("MNUM");


	}

}
@Repository
public class MemberDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// MemberSet ¸â¹öº¯¼ö
	@Autowired
	private SchoolInfoDAO sdao;
	@Autowired
	private CareerInfoDAO cdao;
	@Autowired
	private LicenseInfoDAO ldao;
	@Autowired
	private MemberSet sResult;


	// Äõ¸®¹®
	private String GET_ONE = "SELECT * FROM MEMBER WHERE MNUM=?";
	private String GET_LIST = "SELECT * FROM MEMBER ORDER BY MNUM ASC";
	private String INSERT = "INSERT INTO MEMBER (MNUM, MNAME, PATH, STARTDATE, ENDDATE, BIRTHDATE, "
			+ "TEAMNAME, DUTY, POSITION, WORK, MRANK) "
			+ "VALUES ((SELECT NVL(MAX(MNUM),0)+1 FROM MEMBER), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private String UPDATE = "UPDATE MEMBER SET MNAME=?, STARTDATE=?, ENDDATE=?, BIRTHDATE=?, TEAMNAME=?, DUTY=?, POSITION=?, WORK=?, MRANK=? WHERE MNUM=?";
	private String DELETE = "DELETE FROM MEMBER WHERE MNUM=?";

	// ´ÜÀÏ PKÁ¶È¸
	private String GET_PNUM = "SELECT NVL(MAX(MNUM),0) +1 AS MNUM FROM MEMBER";

	// MemberSet

	//[SetList]
	public List<MemberSet> getSetList() {
		
		List<MemberVO> mdatas = getList();
		
		List<MemberSet> result = new ArrayList<MemberSet>();
		
		// MemberSet setter Àû¿ë
		for(int i = 0; i < mdatas.size(); i++) {
			MemberVO vo = mdatas.get(i);
			// member
			sResult.setMember(getData(vo));
			
			// career
			sResult.setCareer(cdao.getList(vo.getMnum()));
			
			// school
			sResult.setSchool(sdao.getList(vo.getMnum()));

			// license
			sResult.setLicense(ldao.getList(vo.getMnum()));
			
			result.add(sResult); // MemberSet°´Ã¼ »ðÀÔ
		}

		

		return result;
	}

	//[LIST]
	public List<MemberVO> getList() {
		//Object[] args = {};
		return jdbcTemplate.query(GET_LIST, new MemberRowMapper());

	}

	//[ONE]
	public MemberVO getData(MemberVO vo) {
		Object[] args = {vo.getMnum()};

		try{
			return jdbcTemplate.queryForObject(GET_ONE, args, new MemberRowMapper());
		}catch(Exception e) {
			return null;
		}

	}

	//[ONE_INSERT]
	public boolean getData(int mnum) {			
		try{
			Object[] args = {mnum}; 
			jdbcTemplate.queryForObject(GET_ONE, args, new MemberRowMapper());
			return true;
		}catch(Exception e) {

			System.out.println("getData " + e);
			return false;
		}

	}
	//[ONE_MNUM]
	public int getData() {			
		try{
			Object[] args = {}; 
			return jdbcTemplate.queryForObject(GET_PNUM, args, int.class);
		}catch(Exception e) {

			System.out.println("getData " + e);
			return 0;
		}

	}


	//[INSERT]
	public boolean insertMember(MemberVO vo) {
		Object[] args = {vo.getmName(), vo.getPath(), vo.getStartDate(), vo.getEndDate(), 
				vo.getBirthDate(), vo.getTeamName(), vo.getDuty(), vo.getPosition(),vo.getWork(), vo.getMrank()};
		jdbcTemplate.update(INSERT, args);

		return true;
	}

	//[UPDATE]
	public boolean updateMember(MemberVO vo) {
		Object[] args = {vo.getmName(), vo.getStartDate(), vo.getEndDate(), vo.getBirthDate(),
				vo.getTeamName(), vo.getDuty(), vo.getPosition(), vo.getWork(), vo.getMrank(), vo.getMnum()};
		jdbcTemplate.update(UPDATE, args);

		return true;
	}

	//[DELETE]
	public boolean deleteMember(MemberVO vo) {
		jdbcTemplate.update(DELETE, vo.getMnum());

		return true;
	}
}
