package model.post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

//SELETE Äõ¸®¹® MappeR Class
class PostRowMapper implements RowMapper<PostVO>{

	@Override
	public PostVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PostVO data = new PostVO();
		
		data.setPnum(rs.getInt("PNUM"));
		data.setPmem(rs.getInt("PMEM"));
		data.setWdate(rs.getString("WDATE"));
		data.setCategory(rs.getString("CATEGORY"));
		data.setContent(rs.getString("CONTENT"));
		data.setPassword(rs.getString("PASSWORD"));
		data.setResult(rs.getString("RESULT"));
		return data;
	}
	
}
@Repository
public class PostDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// 쿼리문
	private String GET_ONE = "SELECT * FROM post WHERE PNUM=?";
	private String GET_LIST = "SELECT * FROM post ORDER BY WDATE DESC";
	private String INSERT = "INSERT INTO post (PMEM, WDATE, CATEGORY, CONTENT, PASSWORD) VALUES (?, SYSDATE, ?, ?, ?)";
	private String UPDATE = "UPDATE post SET PMEM=?, CATEGORY=?, CONTENT=?, PASSWORD=?, RESULT=? WHERE PNUM=?";
	private String DELETE = "DELETE FROM post WHERE PNUM=?";
	
	//[ONE]
	public PostVO getData(PostVO vo) {
		Object[] args = {vo.getPnum()};
		return jdbcTemplate.queryForObject(GET_ONE, args, new PostRowMapper());
		
	}
	
	//[LIST]
	public List<PostVO> getList(PostVO vo) {
		//Object[] args = {};
		return jdbcTemplate.query(GET_LIST, new PostRowMapper());
		
	}
	
	//[INSERT]
	public boolean insertPost(PostVO vo) {
		Object[] args = {vo.getPmem(), vo.getCategory(), vo.getContent(), vo.getPassword()};
		jdbcTemplate.update(INSERT, args);
		
		return true;
	}
	
	//[UPDATE]
	public boolean updatePost(PostVO vo) {
		Object[] args = {vo.getPmem(), vo.getCategory(), vo.getContent(), vo.getPassword(),vo.getResult(), vo.getPnum()};
		jdbcTemplate.update(UPDATE, args);
		
		return true;
	}
	
	//[DELETE]
	public boolean deletePost(PostVO vo) {
		jdbcTemplate.update(DELETE, vo.getPnum());
		
		return true;
	}
}
