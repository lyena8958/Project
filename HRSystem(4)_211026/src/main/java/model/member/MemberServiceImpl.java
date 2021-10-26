package model.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO dao;
	
	@Override
	public MemberVO getDate(MemberVO vo) {
		return dao.getDate(vo);
	}

	@Override
	public List<MemberVO> getList(MemberVO vo) {
		return dao.getList(vo);
	}

	@Override
	public boolean insertMember(MemberVO vo) {
		return dao.insertMember(vo);
	}

	@Override
	public boolean updateMember(MemberVO vo) {
		return dao.updateMember(vo);
	}

	@Override
	public boolean deleteMember(MemberVO vo) {
		return dao.deleteMember(vo);
	}

}
