package controller.common;

import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;

@Component("cd")
public class ChartData {
		
	// 년/월
	private String date;
	
	// 전송할 데이터
	private int data1;
	private int data2;
	private int data3;	
	private int data4;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getData1() {
		return data1;
	}
	public void setData1(int data1) {
		this.data1 = data1;
	}
	public int getData2() {
		return data2;
	}
	public void setData2(int data2) {
		this.data2 = data2;
	}
	public int getData3() {
		return data3;
	}
	public void setData3(int data3) {
		this.data3 = data3;
	}
	public int getData4() {
		return data4;
	}
	public void setData4(int data4) {
		this.data4 = data4;
	}

	
	@Override
	public String toString() {
		return "ChartData [date=" + date + ", data1=" + data1 + ", data2=" + data2 + ", data3=" + data3 + ", data4="
				+ data4 + "]";
	}
	
	
	
	
}
