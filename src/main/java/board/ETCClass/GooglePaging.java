package board.ETCClass;

public class GooglePaging {
	private int backPageCnt;
	private int frontPageCnt;
	private int selectPage;
	private int getPageCnt;
	private int getPageOrigin;
	private int contentCnt;
	public GooglePaging(int selectPage,int maxPageCnt,int getPageCnt,int contentCnt) {
		getPageOrigin=getPageCnt;
		
		selectPage = selectPage < 1 ? 1 : selectPage; //인자 값이 0이하면 오류 1로 잡아주기
		maxPageCnt = (int)Math.ceil((double)maxPageCnt/contentCnt); //최대페이지로 최대 개수 잡아주기
		if(maxPageCnt < getPageCnt)getPageCnt = maxPageCnt;
		
		boolean isEvenNumber = getPageCnt % 2 == 0;
		int getPageSplite = getPageCnt/2;
		
		int backPageCnt  = getPageSplite; 	
		int frontPageCnt = getPageSplite; 
		if(isEvenNumber) frontPageCnt -= 1;
		
		if(maxPageCnt < selectPage + frontPageCnt) {
			int evenSum = 0;
			if(isEvenNumber)evenSum = 1;
			frontPageCnt -= getPageSplite - (maxPageCnt - selectPage + evenSum);  
			backPageCnt +=  getPageSplite - (maxPageCnt - selectPage + evenSum);
		}
		else if(1 > selectPage - backPageCnt) {
			frontPageCnt += getPageSplite - (selectPage-1);
			backPageCnt  -= getPageSplite - (selectPage-1); 
		}
		
		this.backPageCnt = backPageCnt;
		this.frontPageCnt = frontPageCnt;
		this.selectPage = selectPage;
		this.getPageCnt = getPageCnt;
		this.contentCnt = contentCnt;
	}
	
	public int[] getPages() {
		if(getPageCnt == 0)return null;
		
		int [] returnArr = new int[getPageCnt];
		int arrIndex = 0;
		for(int i = backPageCnt; i > 0; i--,arrIndex++) {
			returnArr[arrIndex] = selectPage - (i)  ; 
		}
		
		returnArr[arrIndex++] = selectPage;
		
		for(int i = 0; i < frontPageCnt; i++,arrIndex++) {
			returnArr[arrIndex] = selectPage + (i+1);
		}
		
		return returnArr; 
	}
	
	public int getPageCnt() {
		return getPageOrigin;
	}
	public int getContentCnt() {
		return contentCnt;
	}
}
