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


//SELETE 쿼리문 MappeR Class
class MemberRowMapper implements RowMapper<MemberVO>{

	@Override
	public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberVO data = new MemberVO();

		data.setMnum(rs.getInt("MNUM"));
		data.setmName(rs.getString("MNAME"));
		data.setPath(rs.getString("MPATH"));
		data.setStartDate(rs.getString("STARTDATE").substring(0, 10));
		data.setEndDate(rs.getString("ENDDATE").substring(0, 10));
		data.setBirthDate(rs.getString("BIRTHDATE").substring(0, 10));
		data.setTeamName(rs.getString("TEAMNAME"));
		data.setDuty(rs.getString("DUTY"));
		data.setPosition(rs.getString("POSITION"));
		data.setWork(rs.getString("WORK"));
		data.setMrank(rs.getInt("MRANK"));


		return data;
	}


}
@Repository
public class MemberDAO {


	@Autowired
	private JdbcTemplate jdbcTemplate;

	// MemberSet 멤버변수
	@Autowired
	private SchoolInfoDAO sdao;
	@Autowired
	private CareerInfoDAO cdao;
	@Autowired
	private LicenseInfoDAO ldao;
	
	private MemberSet sResult;


	// 쿼리문
	private String GET_ONE = "SELECT * FROM member WHERE MNUM=?";
	private String GET_LIST = "SELECT * FROM member ORDER BY MNUM ASC";
	private String INSERT = "INSERT INTO member (MNAME, MPATH, STARTDATE, ENDDATE, BIRTHDATE, "
			+ "TEAMNAME, DUTY, POSITION, WORK, MRANK, MTYPE) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private String UPDATE = "UPDATE member SET MNAME=?, STARTDATE=?, ENDDATE=?, BIRTHDATE=?, TEAMNAME=?, DUTY=?, POSITION=?, WORK=?, MRANK=? WHERE MNUM=?";
	private String DELETE = "DELETE FROM member WHERE MNUM=?";

	// 단일 PK조회
	private String GET_PNUM = "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'member' AND table_schema = DATABASE()";
		//private String GET_PNUM = "SELECT NVL(MAX(MNUM),0) +1 AS MNUM FROM MEMBER";

	// MemberSet

	//[SetList]
	public List<MemberSet> getSetList() {
		
		
		//member 
		List<MemberVO> mdatas = getList(); 
		
		//setList
		List<MemberSet> result = new ArrayList<MemberSet>(); // 반환할 객체List
		
		System.out.println("1: "+mdatas);
		// MemberSet setter 적용
		for(int i = 0; i < mdatas.size(); i++) {

			sResult = new MemberSet(); // setOne
			MemberVO vo = mdatas.get(i); // memOne
			
			// member
			sResult.setMember(getData(vo));
			
			// career
			sResult.setCareer(cdao.getList(vo.getMnum()));
			
			// school
			sResult.setSchool(sdao.getList(vo.getMnum()));

			// license
			sResult.setLicense(ldao.getList(vo.getMnum()));
			
			result.add(sResult); // MemberSet객체 삽입
			//System.out.println("2: "+sResult);
		}

		

		return result;
	}

	//[LIST]
	public List<MemberVO> getList() {

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
				vo.getBirthDate(), vo.getTeamName(), vo.getDuty(), vo.getPosition(),vo.getWork(), vo.getMrank(), vo.getTeamName()};
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
