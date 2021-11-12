package model.member;

import java.util.List;

public interface MemberService {
	List<MemberSet> getSetList();
	MemberVO getData(MemberVO vo);
	int getData();
	List<MemberVO> getList();
	boolean insertMember(MemberVO vo);
	boolean updateMember(MemberVO vo);
	boolean deleteMember(MemberVO vo);
	
}
