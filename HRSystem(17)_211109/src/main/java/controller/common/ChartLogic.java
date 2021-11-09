package controller.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.careerInfo.CareerInfoVO;
import model.licenseInfo.LicenseInfoVO;
import model.member.MemberVO;
import model.schoolInfo.SchoolInfoVO;
import net.sf.json.JSONArray;

@Service
public class ChartLogic {

	@Autowired
	public ChartLogic(ChartData cd) {
		this.cd = cd;
		this.datas = new JSONArray();
		/*datas = new JSONArray();
		header = new JSONArray();*/
	}
	private ChartData cd; // 단일데이터
	private JSONArray datas; // 반환 리스트(js전달)
	private int nowyear = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"))); // 현재년도



	public Object[] infoData(List<MemberVO> mdatas){

		int in = mdatas.size(); // 채용
		int out = 0;			// 퇴직
		int work = 0;			// 재직
		double rate;			// 퇴직률
		
		for(int i = 0; i < mdatas.size(); i++) {
			
			// 퇴직일 기재가 되어있다면,
			if(!mdatas.get(i).getEndDate().equals("9999-12-31")) {
				out++; // 퇴직인원 증가
			}
		}
		work = in-out;
		rate = Math.round((out*1.0/(in+out))*100);
		Object[] infoData = {in, out, work, rate};
		
		return infoData;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// [재직현황]
	public JSONArray memYearDatas(List<MemberVO> mdatas, int dCnt){
		datas.clear();
		System.out.println("datas : "+datas);
		int in; // 입사
		int out; // 퇴직
		double rate; // 퇴직률

		int memIn; // member테이블의 년도
		int memOut; // member테이블의 년도
		int totalmem = 0; // 재직자현황
		// n년전 부터 ~ 현재년도까지
		for(int y = nowyear-dCnt+1; y <= nowyear; y++) {
			System.out.println("year : "+y);
			in = 0;
			out = 0;

			// 전체 데이터 년별 구분
			for(int i = 0; i < mdatas.size(); i++) {

				// 채용
				memIn = Integer.parseInt(mdatas.get(i).getStartDate().substring(0,4));

				if(memIn==y) { // 현재 년과 해당 입사년이 같다면
					in++;  //채용 수 증가
				}

				// 퇴직
				memOut = Integer.parseInt(mdatas.get(i).getEndDate().substring(0,4));

				if(memOut==y) {//  현재 년과 해당 퇴직년이 같다면
					out++; // 퇴직 수 증가
				}
				System.out.println("year : "+y);
				System.out.println("in : "+in);
				System.out.println("out : "+out);
				System.out.println("total : "+totalmem+"\n");
			}
			totalmem += in-out; // 연별 재직자 합산
			rate = Math.round((out*1.0/(in+out))*100); // 퇴사율
			cd.setDate(Integer.toString(y));
			cd.setData1(in);
			cd.setData2(out);
			cd.setData3(totalmem);
			cd.setData4((int)rate);

			System.out.println(cd);
			datas.add(cd);
		}

		return datas;
	}



	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// [최종학력]
	public JSONArray schYearDatas(List<SchoolInfoVO> sdatas, List<MemberVO> mdatas, int dCnt){
		datas.clear();
		System.out.println("datas : "+datas);
		int s1; // 고졸/전문대
		int s2; // 학사
		int s3; // 석사
		int memInYear;

		int schoolYear; // school테이블의 년도

		// n년전 부터 ~ 현재년도까지
		for(int y = nowyear-dCnt+1; y <= nowyear; y++) {
			System.out.println("year : "+y);

			s1 = 0;
			s2 = 0;
			s3 = 0;

			for(int m = 0; m < mdatas.size(); m++) {
				MemberVO memdata = mdatas.get(m);
				memInYear = Integer.parseInt(mdatas.get(m).getStartDate().substring(0,4));

				if(y != memInYear) {
					continue;
				}

				// 전체 데이터 년별 구분
				for(int i = 0; i < sdatas.size(); i++) {
					SchoolInfoVO data = sdatas.get(i); // 단일 데이터 저장

					if(memdata.getMnum()!=data.getSmem() || data.getFinish().equals("N")) { // 최종학력이 아니라면 PASS
						continue;
					}

					// 학력별 변수 증가처리
					if(data.getStype().equals("고등학교")||data.getStype().equals("전문대")) {
						s1++;
						break;
					}
					else if(data.getStype().equals("학사")) {
						s2++;
						break;
					}
					else if(data.getStype().equals("석사")) {
						s3++;
						break;
					}

				}
			}			


			cd.setDate(Integer.toString(y));
			cd.setData1(s1);
			cd.setData2(s2);
			cd.setData3(s3);

			datas.add(cd);
		}

		return datas;
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// [경력/신입]
	public JSONArray carYearDatas(List<CareerInfoVO> cdatas, List<MemberVO> mdatas, int dCnt){
		datas.clear();
		System.out.println("datas : "+datas);
		int c1; // 경력
		int c2; // 신입
		double rate; // 경력비율

		int memInYear; 


		// n년전 부터 ~ 현재년도까지
		for(int y = nowyear-dCnt+1; y <= nowyear; y++) {
			System.out.println("year : "+y);

			c1 = 0;
			c2 = 0;
			rate = 0.0;

			// ▶member 
			for(int m = 0; m < mdatas.size(); m++) {
				memInYear = Integer.parseInt(mdatas.get(m).getStartDate().substring(0,4));

				// 입사년도와 index 같지않거나
				// 		  이전 사원과 사번이 같다면 PASS 				
				if(y != memInYear) {
					continue;
				}

				// ▶career
				boolean flag = false; // 경력여부

				for(int i = 0; i < cdatas.size(); i++) {
					if(cdatas.get(i).getCmem()==mdatas.get(m).getMnum()) { // 데이터가 비어있지 않다면 == 경력대상 처리
						flag = true;
						break;
					}
				}

				if(flag) {
					c1++; // 경력증가
				}
				else {
					c2++; // 신입증가
				}
				System.out.println(mdatas.get(m).getMnum()+" : "+flag);

			}

			rate = Math.round((c1*1.0/(c1+c2))*100); // 경력률

			cd.setDate(Integer.toString(y));
			cd.setData1(c1);
			cd.setData2(c2);
			cd.setData3((int)rate);

			datas.add(cd);
		}
		return datas;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// [나이]
	public JSONArray ageYearDatas(List<MemberVO> mdatas, int dCnt){
		datas.clear();
		System.out.println("datas : "+datas);
		int a1; // ~30살
		int a2; // ~39살
		int a3; // 40살 이후

		int memYear; // member테이블의 년도

		// n년전 부터 ~ 현재년도까지
		for(int y = nowyear-dCnt+1; y <= nowyear; y++) {
			System.out.println("year : "+y);
			a1 = 0;
			a2 = 0;
			a3 = 0;

			// 전체 데이터 년별 구분
			for(int i = 0; i < mdatas.size(); i++) {

				// 입사일
				memYear = Integer.parseInt(mdatas.get(i).getStartDate().substring(0,4));

				// 기준연도와 같지 않다면 PASS
				if(memYear != y) {
					continue;
				}

				memYear = Integer.parseInt(mdatas.get(i).getBirthDate().substring(0,4));

				int age = (nowyear-memYear)+1; // 나이 추출

				// 나이 합산
				if(age <= 30) { // 30살 이하
					a1++;
				}
				else if(age <= 39) { // 39살 이하
					a2++;
				}
				else { // 그 이상
					a3++;
				}

				cd.setDate(Integer.toString(y));
				cd.setData1(a1);
				cd.setData2(a2);
				cd.setData3(a3);

			}


			datas.add(cd);
		}

		return datas;
	}
}
