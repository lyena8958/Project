package model.post;

import java.util.List;

public interface PostService {
	PostVO getDate(PostVO vo);
	List<PostVO> getList(PostVO vo);
	boolean insertPost(PostVO vo);
	boolean updatePost(PostVO vo);
	boolean deletePost(PostVO vo);
}
