package model.post;

public class Paging {

	// 멤버변수 
	private int pageSize; // (한 페이지에 보이는)게시글수  
	private int firstPageNum; // 첫번째 페이지번호 - 개발자기준 (전체) 
	private int prevPageNum; // 이전페이지 번호 
	private int startPageNum; // 시작페이지 (페이징네비 기준) - 사용자 기준
	private int thisPageNum; // 페이지 번호 - 현재페이지(인덱스)
	private int endPageNum; //마지막 페이지 (페이징네비 기준)  - 사용자 기준
	private int nextPageNum; // 다음 페이지 번호 
	private int finalPageNum; //마지막 페이지 번호 - 개발자기준 (전체)
	private int totalPostCnt; // 게시글 전체수 
	private boolean isFirst; // 버튼 생성여부
	private boolean isLast; // 버튼 생성여부
	
	
	// Getter & Setter 
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstPageNum() {
		return firstPageNum;
	}

	public void setFirstPageNum(int firstPageNum) {
		this.firstPageNum = firstPageNum;
	}

	public int getPrevPageNum() {
		return prevPageNum;
	}

	public void setPrevPageNum(int prevPageNum) {
		this.prevPageNum = prevPageNum;
	}

	public int getStartPageNum() {
		return startPageNum;
	}

	public void setStartPageNum(int startPageNum) {
		this.startPageNum = startPageNum;
	}

	public int getThisPageNum() {
		return thisPageNum;
	}

	public void setThisPageNum(int thisPageNum) {
		this.thisPageNum = thisPageNum;
	}

	public int getEndPageNum() {
		return endPageNum;
	}

	public void setEndPageNum(int endPageNum) {
		this.endPageNum = endPageNum;
	}

	public int getNextPageNum() {
		return nextPageNum;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public int getFinalPageNum() {
		return finalPageNum;
	}

	public void setFinalPageNum(int finalPageNum) {
		this.finalPageNum = finalPageNum;
	}

	public int getTotalPost() {
		return totalPostCnt;
	}

	public void setTotalPost(int totalPostCnt) {
		this.totalPostCnt = totalPostCnt;
	}
	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public Paging(int pageSize, int thisPageNum, int totalPostCnt) { // 한 페이지에 들어가는 포스트개수, 현재 페이지 넘버, 전체 데이터(datas.size)
		this.pageSize = pageSize;
		if(thisPageNum==0) {
			this.thisPageNum = 1;
		}else {
			this.thisPageNum = thisPageNum;
		}
		if(totalPostCnt==0) {
			return;
		}else {
			this.totalPostCnt = totalPostCnt;
		}
	}

	// paging 메서드 
	public void makePaging() {
		// 최대 페이지 수 설정
		this.finalPageNum = (this.totalPostCnt-1)/this.pageSize+1; 

		// 현재페이지 유효성 체크 , 마지막 페이지보다 현재페이지가 크면, 마지막페이지로 설정, 0보다 작거나 같으면 1페이지로 설정
		if(this.thisPageNum>this.finalPageNum) {
			this.setThisPageNum(this.finalPageNum);
		}
		if(this.thisPageNum<=0) {
			this.thisPageNum=1;
		}

		this.isFirst = this.thisPageNum==1?true:false; //시작페이지 (전체)   
		this.isLast = this.thisPageNum==finalPageNum?true:false; //마지막페이지(전체)

		int blockLastPageNum = this.finalPageNum; // 블록의 마지막 페이지의 숫자를 가장 마지막 페이지의 넘버로 초기화
		this.setFirstPageNum(1); // 첫번째 페이지 번호 
		int blockStartPageNum = this.firstPageNum; // 블록의 첫번째 페이지의 숫자를 가장 처음 페이지의 넘버로 초기화
		int pagesPerBlock = 5;							// 한블럭에 있는 페이지 수
		int mod = this.finalPageNum % pagesPerBlock;	// mod == 페이지를 묶고 남은 페이지 수
		if(this.finalPageNum - mod >= this.thisPageNum) {	// 만약에 지금 페이지가 마지막 페이지 묶음에 속하지 않는다면
			blockLastPageNum = (int) (Math.ceil((float)this.thisPageNum / pagesPerBlock) * pagesPerBlock); // Math.ceil 
			blockStartPageNum = blockLastPageNum - (pagesPerBlock - 1);
		} else { 
			blockStartPageNum = (int) (Math.ceil((float)this.thisPageNum / pagesPerBlock) * pagesPerBlock)
					- (pagesPerBlock - 1);
		}

		if (blockLastPageNum>this.finalPageNum) { 
			blockLastPageNum=this.finalPageNum;
		}
		
		this.setStartPageNum(blockStartPageNum); // 시작페이지(페이징네비기준)
		this.setEndPageNum(blockLastPageNum);    // 끝 페이지(페이징네비기준)
		this.setFinalPageNum(this.finalPageNum);
	}
	// toString

	@Override
	public String toString() {
		return "Paging [pageSize=" + pageSize + ", firstPageNum=" + firstPageNum + ", prevPageNum=" + prevPageNum
				+ ", startPageNum=" + startPageNum + ", thisPageNum=" + thisPageNum + ", endPageNum=" + endPageNum
				+ ", nextPageNum=" + nextPageNum + ", finalPageNum=" + finalPageNum + ", totalPostCnt=" + totalPostCnt
				+ ", isFirst=" + isFirst + ", isLast=" + isLast + "]";
	}


	
}

