package controller.post_ctrl.post.read;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.post.PostVO;

public class DateSlice {
	private ArrayList<PostVO> originData;
	private ArrayList<PostVO> newData;
	private Date today;
	private Date pdate;
	private int index;


	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public ArrayList<PostVO> getOriginData() {
		return originData;
	}
	public void setOriginData(ArrayList<PostVO> originData) {
		this.originData = originData;
	}
	public ArrayList<PostVO> getNewData() {
		return newData;
	}
	public void setNewData(ArrayList<PostVO> newData) {
		this.newData = newData;
	}
	public Date getToday() {
		return today;
	}
	public void setToday(Date today) {
		this.today = today;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public DateSlice(ArrayList<PostVO> originData, Date today, int index) {
		super();
		this.originData = originData;
		this.today = today;
		this.index = index;
		this.newData = new ArrayList<PostVO>();
	}

	public void excuteSlice() {
		for(int i=(this.index-1)*6; i<this.index*6; i++) { // 현재 인덱스에 -1*6~현재인덱스*6 까지의 데이터만 넘겨주기
			PostVO vo = this.originData.get(i);
			// vo에 있는 pdate
			String pdate = vo.getPdate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String now = sdf.format(this.today);

			// 포스팅 날짜와 오늘 날짜 같은 타입으로 변환
			Date datePdate = null;
			Date dateToday = null;
			try {
				datePdate = sdf.parse(pdate);
				dateToday = sdf.parse(now);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 시차 계산
			long diff = dateToday.getTime() - datePdate.getTime();
			long timeDif = diff / (60 * 60 * 1000); 

			vo.setNew(false);	// 디폴트 false
			if(timeDif<24) {	// 현재시간 - pdate 가 24시간 이하면 true
				vo.setNew(true);
			}
			// 넘겨줄 날짜 슬라이싱
			SimpleDateFormat sliceDate = new SimpleDateFormat("yyyy-MM-dd");
			pdate = sliceDate.format(datePdate);
			vo.setPdate(pdate);
			System.out.println("slicedata에 들어가는 data = "+vo);
			this.newData.add(vo);
			if(i==this.originData.size()-1) {// 6개 미만일 경우 break처리
				break;
			}
			
		}

	}
}
