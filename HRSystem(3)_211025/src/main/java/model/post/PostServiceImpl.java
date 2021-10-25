package model.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class PostServiceImpl implements PostService{

	@Autowired
	private PostDAO dao;
	
	@Override
	public PostVO getDate(PostVO vo) {
		return dao.getDate(vo);
	}

	@Override
	public List<PostVO> getList(PostVO vo) {
		return dao.getList(vo);
	}

	@Override
	public boolean insertPost(PostVO vo) {
		return dao.insertPost(vo);
	}

	@Override
	public boolean updatePost(PostVO vo) {
		return dao.updatePost(vo);
	}

	@Override
	public boolean deletePost(PostVO vo) {
		return dao.deletePost(vo);
	}

}
