package model.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO dao;

	
	@Override
	public List<MemberSet> getSetList() {
		return dao.getSetList();
	}
	
	@Override
	public MemberVO getData(MemberVO vo) {
		return dao.getData(vo);
	}

	@Override
	public int getData() {
		return dao.getData();
	}
	
	@Override
	public List<MemberVO> getList() {
		return dao.getList();
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
