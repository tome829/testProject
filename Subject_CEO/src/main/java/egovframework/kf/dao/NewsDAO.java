package egovframework.kf.dao;

import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.DCUtil;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.data.SearchVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : BoardDAO.java
 * Description : 검색대상 board 조회
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 * 2017년 12월  00일                       최초 작성
 *
 * @since 2017년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Repository
public class NewsDAO {
	private static final Logger logger = LoggerFactory.getLogger(NewsDAO.class);
	
	/** 엔진 공통 유틸 */
	@Resource(name = "dcUtil")
	private DCUtil dcUtil;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	/** REST 모듈 */
	@Resource(name = "restModule")
	private RestModule restModule;
		
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	

	/**
	 * 키워드에 맞는 문서관리 내용 리턴
	 * 
	 * @param kwd
	 * @throws IOException 
	 */
	public RestResultVO newsSearch(ParameterVO paramVO) throws Exception {
		SearchVO searchVO = new SearchVO();		
		// 쿼리 생성
		StringBuffer query = new StringBuffer();
		StringBuffer sbLog = new StringBuffer();
		String strNmFd = paramVO.getFields().isEmpty()? "text_idx": paramVO.getFields();

		// 쿼리 생성 부분
		query = dcUtil.makeQuery( strNmFd , paramVO.getKwd(), "allword", query, "AND");	
		System.out.println("query확인 필요1 "+query);
		
		//결과내재검색	true까지는 나옴.
		if( paramVO.getReSrchFlag() ){
			System.out.println("NEWSDAO의 데이터");
			System.out.println("쿼리스" + paramVO.getPreviousQueries());
			System.out.println("strNmFd = " + strNmFd);
			System.out.println("kwd는 = " + paramVO.getKwd());
			System.out.println("이전 쿼리 = " + paramVO.getPreviousQuery());
			System.out.println("이전 쿼리2 = " + paramVO.getOriginalQuery());
			/* getPreviousQueries 값이 null 값이여서 preCnt 값이 null*/
			int preCnt = paramVO.getPreviousQueries().length;
			System.out.println("preCnt = "+ preCnt);
			/* query = text_idx='박보검' allword */
			query.append(" AND  ")
					.append(dcUtil.makePreQuery(strNmFd , paramVO.getKwd(), paramVO.getPreviousQueries(), preCnt ,"allword") );		        
			System.out.println("NewsDAO 결과내재검색 부분");
		}


		/**
		 * 날짜검색기간 조회
		 * 기간/일/월/년도, 구간검색으로 조회시 자바스크립트에서 시작날짜와 종료날짜 조회하여 전달함.
		 */
		if (!paramVO.getStartDate().isEmpty() && !paramVO.getEndDate().isEmpty()){
			query = dcUtil.makeRangeQuery("created_time", paramVO.getStartDate().replace(".", "")+"000000", paramVO.getEndDate().replace(".", "")+"999999", query) ;
		}
		
		//정렬조건	(최신순)
		if ("d".equals(paramVO.getSort())){
			query.append(" order by created_time desc");
		}
		
		//로그기록 
		//SITE@인물+$|첫검색|1|정확도순^코난	
		sbLog.append(  dcUtil.getLogInfo(commonUtil.null2Str(paramVO.getSiteNm(),"SITE"),
				"게시판" ,
				commonUtil.null2Str( paramVO.getUserId(),""), paramVO.getKwd(),
				paramVO.getPageNum(),
				false,
				paramVO.getSiteNm(),
				commonUtil.null2Str( paramVO.getRecKwd(),"" )) );
	
		searchVO.setUrl(konanPropertiesService.getString("url"));
		searchVO.setCharset(konanPropertiesService.getString("charset"));
		searchVO.setFields(konanPropertiesService.getString("newsField"));
		searchVO.setFrom(konanPropertiesService.getString("newsFrom"));
		searchVO.setHilightTxt(konanPropertiesService.getString("newsHilight"));
		searchVO.setQuery(URLEncoder.encode(query.toString(), konanPropertiesService.getString("charset") ));
		searchVO.setLogInfo(URLEncoder.encode(sbLog.toString(), konanPropertiesService.getString("charset")));
	
		// URL 생성
		//String restUrl = dcUtil.getRestURL(paramVO, searchVO); //get방식 URL생성
		String postParamData = dcUtil.getParamPostData(paramVO, searchVO);
		logger.debug("RESTURL news : " + postParamData);
		
		RestResultVO restVO = new RestResultVO();
		//boolean success = restModule.restSearch(restUrl, restVO, searchVO.getFields());  //get방식 호출
		boolean success = restModule.restSearchPost(searchVO.getUrl() , postParamData, restVO, searchVO.getFields()); //post 방식 호출
		
		
		//초기화
		query.charAt(0);
		sbLog.charAt(0);
		
		if(!success) 
			return null;
				
		return restVO;		
	}
}
