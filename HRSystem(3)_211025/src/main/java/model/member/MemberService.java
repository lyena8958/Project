package model.member;

import java.util.List;

public interface MemberService {
	MemberVO getDate(MemberVO vo);
	List<MemberVO> getList(MemberVO vo);
	boolean insertMember(MemberVO vo);
	boolean updateMember(MemberVO vo);
	boolean deleteMember(MemberVO vo);
}
