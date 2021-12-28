package model.schoolInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

//SELETE ������ MappeR Class
class SchoolRowMapper implements RowMapper<SchoolInfoVO>{

	@Override
	public SchoolInfoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		SchoolInfoVO data = new SchoolInfoVO();

		data.setSnum(rs.getInt("SNUM"));
		data.setStype(rs.getString("STYPE"));
		data.setSname(rs.getString("SNAME"));
		data.setStartDate(rs.getString("STARTDATE").substring(0, 10));
		data.setEndDate(rs.getString("ENDDATE").substring(0, 10));
		data.setField(rs.getString("FIELD"));
		data.setMajor(rs.getString("MAJOR"));
		data.setFinish(rs.getString("FINISH"));
		data.setSmem(rs.getInt("SMEM"));

		return data;
	}

}
@Repository
public class SchoolInfoDAO {
	SchoolInfoDAO(){ //,,?
		jdbcTemplate = new JdbcTemplate();
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// ������
	private String GET_ONE = "SELECT * FROM SchoolInfo WHERE SNUM=?";
	private String GET_LIST = "SELECT * FROM SchoolInfo WHERE SMEM=? ORDER BY SMEM ASC";
	private String INSERT = "INSERT INTO SchoolInfo (STYPE, SNAME, STARTDATE, ENDDATE, FIELD, MAJOR, FINISH, SMEM) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private String UPDATE = "UPDATE SchoolInfo SET STYPE=?, SNAME=?, STARTDATE=?, ENDDATE=?, FIELD=?, MAJOR=?, FINISH=? WHERE SNUM=?";
	private String DELETE = "DELETE FROM SchoolInfo WHERE SNUM=?";

	private String GET_LIST_ALL = "SELECT * FROM SchoolInfo ORDER BY SMEM ASC";

	//[ONE]
	public SchoolInfoVO getData(SchoolInfoVO vo) {
		Object[] args = {vo.getSnum()};
		return jdbcTemplate.queryForObject(GET_ONE, args, new SchoolRowMapper());

	}

	//[LIST]
	public List<SchoolInfoVO> getList() {
		List<SchoolInfoVO> datas = jdbcTemplate.query(GET_LIST_ALL, new SchoolRowMapper());

		return datas = (datas.size()==0)? null : datas; // ���׿��� : ���̶�� null��ȯ, �ƴ϶�� ������ ��ȯ

	}

	//[LIST]
	public List<SchoolInfoVO> getList(SchoolInfoVO vo) {
		Object[] args = {vo.getSmem()};
		List<SchoolInfoVO> datas = jdbcTemplate.query(GET_LIST, args, new SchoolRowMapper());

		return datas = (datas.size()==0)? null : datas; // ���׿��� : ���̶�� null��ȯ, �ƴ϶�� ������ ��ȯ

	}
	//[LIST_WRITER]
	public List<SchoolInfoVO> getList(int mnum) {
		Object[] args = {mnum};
		List<SchoolInfoVO> datas = jdbcTemplate.query(GET_LIST, args, new SchoolRowMapper());

		return datas = (datas.size()==0)? null : datas; // ���׿��� : ���̶�� null��ȯ, �ƴ϶�� ������ ��ȯ

	}

	//[INSERT]
	public boolean insertSchool(SchoolInfoVO vo) {
		Object[] args = {vo.getStype(), vo.getSname(), vo.getStartDate(), vo.getEndDate(), vo.getField(), vo.getMajor(), vo.getFinish(), vo.getSmem()};
		jdbcTemplate.update(INSERT, args);

		return true;
	}

	//[UPDATE]
	public boolean updateSchool(SchoolInfoVO vo) {
		Object[] args = {vo.getStype(), vo.getSname(), vo.getStartDate(), vo.getEndDate(), vo.getField(), vo.getMajor(), vo.getFinish(), vo.getSnum()};
		jdbcTemplate.update(UPDATE, args);

		return true;
	}

	//[DELETE]
	public boolean deleteSchool(SchoolInfoVO vo) {
		jdbcTemplate.update(DELETE, vo.getSnum());

		return true;
	}
}
