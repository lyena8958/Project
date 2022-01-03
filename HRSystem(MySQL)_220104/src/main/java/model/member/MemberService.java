package model.member;

import java.util.List;

public interface MemberService {
	List<MemberSet> getSetList();
	MemberVO getData(MemberVO vo);
	boolean getData(int mnum);
	int getData();
	List<MemberVO> getList(String text);
	boolean insertMember(MemberVO vo);
	boolean updateMember(MemberVO vo);
	boolean deleteMember(MemberVO vo);
	
}
