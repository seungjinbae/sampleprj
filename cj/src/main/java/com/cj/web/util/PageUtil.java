package com.cj.web.util;

public class PageUtil {
	private static PageUtil singleton = null;
	
	public PageUtil() {}
	public static PageUtil getInstance() {
		if(singleton == null) singleton = new PageUtil();
		return singleton;
	}
	
	 /**
     *  bootstrap 용 페이지 리스트 : 페이지 이동 function 명을 받아 넘김
     *  @param functionName :페이지 클릭시 호출될 페이지 이동 메소드
     *  @param mTotalCount :   전체 데이터 수
     *  @param mPageCount : 하단에 표시할 페이지 수
     *  @param mListCount : 한페이지에 표시할 글 수
     *  @param page : 현재 페이지
     * */
    public String getPageList(String functionName, int mTotalCount, int mPageCount, int mListCount, String page)
    {
        int i=0;
        
        StringBuffer PageString = new StringBuffer();
        int mTotalSector = 0;		// 전체 섹터
        int mCurrentSector = 0;	// 현재 위치한 섹터
        int mTotalPageCount = 0;			// 전체 페이지 수
        
        if(mTotalCount >= mListCount)
        {
        	mTotalPageCount = mTotalCount/mListCount;
            if((mTotalCount % mListCount) > 0 )
            {
            	mTotalPageCount = mTotalPageCount + 1;
            }
        }        
       
        String mCurrentPage = "1";
        if(!page.equals("")) mCurrentPage = page;
        else mCurrentPage = "1";  
        
        mCurrentPage = "" + (Integer.parseInt(mCurrentPage) - 1); // pageable 은 0 부터 시작
        
        if(mTotalCount == 0) mTotalCount = 1;
        if(mPageCount == 0) mPageCount = 1;
        
        mTotalSector = (mTotalPageCount-1)/mPageCount; 
        //mCurrentSector= (new Integer(mCurrentPage).intValue()-1)/mPageCount;
       
        
        // 1. 이전 페이지 그룹 처리
        if(mCurrentSector > 0){
      	    // 이전 페이지 그룹이 있다면                          
            PageString.append("<li><a href=\"javascript:;\" class=\"direction\" onclick=\""+functionName+"("+ String.valueOf(((mCurrentSector*mPageCount)+i)) +" );\" alt='이전페이지 그룹'>&laquo;</a></li>");
        }else{      	  	        	  
			PageString.append("<li><a href='javascript:;' class='direction' alt='이전페이지'>&laquo;</a></li>");
        }
        
        // 2. 페이지 목록 처리
        
        for(i=1; i<= mPageCount; i++){
        	
            if(mTotalPageCount <(i+(mCurrentSector*mPageCount))){        	
            	  // 컨텐츠가 없는 경우. 첫페이지 인경우
                  if(i==1){ PageString.append("<li class='active'><a href='javascript:;'>" + String.valueOf((mCurrentSector*mPageCount)+i) + "</a></li>"); }
                  break;                  
            }else{
            	  // 페이지 표시
                  if(new Integer(mCurrentPage).intValue() != new Integer(i+(mCurrentSector*mPageCount)).intValue()){
                        PageString.append("<li><a href=\"javascript:;\" onclick=\""+functionName+"("+ String.valueOf((mCurrentSector*mPageCount)+i) +");\">" + ((mCurrentSector*mPageCount)+i) + "</a></li>");     
                  }else{
                	  	// 현재 페이지와 같다면
                    	PageString.append("<li class='active'><a href='javascript:;'>" + String.valueOf((mCurrentSector*mPageCount)+i) + "</a></li>");
                  }
            }  // end if   
        } // end for

        // 3. 다음 페이지 그룹 처리
        if(new Integer(mCurrentSector).intValue() < new Integer(mTotalSector).intValue()){
      	  
            PageString.append("<li><a href=\"javascript:;\" class=\"direction\" onclick=\""+functionName+"(" + String.valueOf(mCurrentSector*mPageCount + i )+ " );\" alt='다음페이지'>&raquo;</a></li>");            
        }else{
        	PageString.append("<li><a href='javascript:;' class='direction' alt='다음페이지'>&raquo;</a></li>");
        }
        return PageString.toString();
    }    
}
