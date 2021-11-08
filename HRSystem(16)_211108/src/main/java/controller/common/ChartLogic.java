package controller.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		datas = new JSONArray();
		header = new JSONArray();
		/*datas = new JSONArray();
		header = new JSONArray();*/
	}
	private ChartData cd; // ���ϵ�����
	private JSONArray datas; // ��ȯ ����Ʈ(js����)
	private JSONArray header;
	private int nowyear = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"))); // ����⵵


	public JSONArray getHeader() {
		return header;
	}
	public void setHeader(JSONArray header) {
		this.header = header;
	}



	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// [������Ȳ]
	public JSONArray memYearDatas(List<MemberVO> mdatas, int dCnt){
		datas.clear();
		System.out.println("datas : "+datas);
		int in; // �Ի�
		int out; // ����
		double rate; // ������

		int memIn; // member���̺��� �⵵
		int memOut; // member���̺��� �⵵
		int totalmem = 0; // ��������Ȳ
		// n���� ���� ~ ����⵵����
		for(int y = nowyear-dCnt+1; y <= nowyear; y++) {
			System.out.println("year : "+y);
			in = 0;
			out = 0;

			// ��ü ������ �⺰ ����
			for(int i = 0; i < mdatas.size(); i++) {

				// ä��
				memIn = Integer.parseInt(mdatas.get(i).getStartDate().substring(0,4));

				if(memIn==y) { // ���� ��� �ش� �Ի���� ���ٸ�
					in++;  //ä�� �� ����
				}

				// ����
				memOut = Integer.parseInt(mdatas.get(i).getEndDate().substring(0,4));

				if(memOut==y) {//  ���� ��� �ش� �������� ���ٸ�
					out++; // ���� �� ����
				}
				System.out.println("year : "+y);
				System.out.println("in : "+in);
				System.out.println("out : "+out);
				System.out.println("total : "+totalmem+"\n");
			}
			totalmem += in-out; // ���� ������ �ջ�
			rate = Math.round((out*1.0/(in+out))*100); // �����
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

	// [�����з�]
	public JSONArray schYearDatas(List<SchoolInfoVO> sdatas, List<MemberVO> mdatas, int dCnt){
		datas.clear();
		System.out.println("datas : "+datas);
		int s1; // ����/������
		int s2; // �л�
		int s3; // ����
		int memInYear;

		int schoolYear; // school���̺��� �⵵

		// n���� ���� ~ ����⵵����
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

				// ��ü ������ �⺰ ����
				for(int i = 0; i < sdatas.size(); i++) {
					SchoolInfoVO data = sdatas.get(i); // ���� ������ ����

					if(memdata.getMnum()!=data.getSmem() || data.getFinish().equals("N")) { // �����з��� �ƴ϶�� PASS
						continue;
					}

					// �зº� ���� ����ó��
					if(data.getStype().equals("����б�")||data.getStype().equals("������")) {
						s1++;
						break;
					}
					else if(data.getStype().equals("�л�")) {
						s2++;
						break;
					}
					else if(data.getStype().equals("����")) {
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
	// [���/����]
	public JSONArray carYearDatas(List<CareerInfoVO> cdatas, List<MemberVO> mdatas, int dCnt){
		datas.clear();
		System.out.println("datas : "+datas);
		int c1; // ���
		int c2; // ����
		double rate; // ��º���

		int memInYear; 


		// n���� ���� ~ ����⵵����
		for(int y = nowyear-dCnt+1; y <= nowyear; y++) {
			System.out.println("year : "+y);

			c1 = 0;
			c2 = 0;
			rate = 0.0;

			// ��member 
			for(int m = 0; m < mdatas.size(); m++) {
				memInYear = Integer.parseInt(mdatas.get(m).getStartDate().substring(0,4));

				// �Ի�⵵�� index �����ʰų�
				// 		  ���� ����� ����� ���ٸ� PASS 				
				if(y != memInYear) {
					continue;
				}

				// ��career
				boolean flag = false; // ��¿���

				for(int i = 0; i < cdatas.size(); i++) {
					if(cdatas.get(i).getCmem()==mdatas.get(m).getMnum()) { // �����Ͱ� ������� �ʴٸ� == ��´�� ó��
						flag = true;
						break;
					}
				}

				if(flag) {
					c1++; // �������
				}
				else {
					c2++; // ��������
				}
				System.out.println(mdatas.get(m).getMnum()+" : "+flag);

			}

			rate = Math.round((c1*1.0/(c1+c2))*100); // ��·�

			cd.setDate(Integer.toString(y));
			cd.setData1(c1);
			cd.setData2(c2);
			cd.setData3((int)rate);

			datas.add(cd);
		}
		return datas;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// [����]
	public JSONArray ageYearDatas(List<MemberVO> mdatas, int dCnt){
		datas.clear();
		System.out.println("datas : "+datas);
		int a1; // ~30��
		int a2; // ~39��
		int a3; // 40�� ����

		int memYear; // member���̺��� �⵵

		// n���� ���� ~ ����⵵����
		for(int y = nowyear-dCnt+1; y <= nowyear; y++) {
			System.out.println("year : "+y);
			a1 = 0;
			a2 = 0;
			a3 = 0;

			// ��ü ������ �⺰ ����
			for(int i = 0; i < mdatas.size(); i++) {

				// �Ի���
				memYear = Integer.parseInt(mdatas.get(i).getStartDate().substring(0,4));

				// ���ؿ����� ���� �ʴٸ� PASS
				if(memYear != y) {
					continue;
				}

				memYear = Integer.parseInt(mdatas.get(i).getBirthDate().substring(0,4));

				int age = (nowyear-memYear)+1; // ���� ����

				// ���� �ջ�
				if(age <= 30) { // 30�� ����
					a1++;
				}
				else if(age <= 39) { // 39�� ����
					a2++;
				}
				else { // �� �̻�
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
