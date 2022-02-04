package egovframework.kf.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.SetParameter;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.service.BoardService;
import egovframework.kf.service.NewsService;
import egovframework.kf.service.PersonService;
import egovframework.kf.service.book_infoService;
import egovframework.kf.service.top_newsService;

/**
 * Class Name : SearchController.java
 * Description : 통합검색 조회를 위한 컨트롤러
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 * 2017년 12월  00일     김승희           최초 작성
 *
 * @since 2017년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Controller
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	/** BoardService */
//	@Resource(name = "boardService")
//	private BoardService boardService;	
	
	/**PersonService */
//	@Resource(name = "personService")
//	private PersonService personService;
	
	/**NewsService */
//	@Resource(name = "newsService")
//	private NewsService newsService;

	/**top_news */
	@Resource(name = "top_newsService")
	private top_newsService Top_newsService;
	
	/**book_info */
	@Resource(name = "book_infoService")
	private book_infoService Book_infoService;
	
	/** Parameter Setting */
	@Resource(name = "setParameter")
	private SetParameter setParameter;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
	
	
	@RequestMapping(value = "/search")
	public String search(@RequestParam Map<String, String> map, Model model) throws Exception {
		
		logger.info(" ========================== >>searchTest");

		// 파라미터 세팅
		ParameterVO paramVO = setParameter.setParameter(map);

		// 카테고리별 문서 호출
		setCategoryModel(model, paramVO);
		logger.debug(paramVO.toString());

		
		//파라미터
		model.addAttribute("params", paramVO);
		
		
		logger.info(" ========================== >>searchTestOut");
		return "search/search";
	}
	
	
	/**
	 * 모델 세팅 부분을 분리
	 * 카테고리 :	 게시판 조회 
	 * @return Model
	 * 
	 * @throws Exception
	 */
	private Model setCategoryModel(Model model, ParameterVO paramVO) throws Exception {
		RestResultVO result;
		RestResultVO result1;
		RestResultVO result2;
		int total = 0;
		
		String category = paramVO.getCategory();
		// 카테고리 여부
		if(paramVO.getKwd().length() > 0) {
		
			// 게시판
			if (commonUtil.easyChkEqual("TOTAL,BOARD,PERSON,NEWS,top_news", paramVO.getCategory(), ",", false)) {
				
System.out.println("category는 : "+paramVO.getCategory());
				
				if(paramVO.getCategory().equals("top_news")) {
					System.out.println("여기로 탈출 1");
					result = Top_newsService.top_newsSearch(paramVO);
					total += result.getTotal();
					System.out.println("여기까지 되는건가?");
					model.addAttribute("top_newsRow", result.getRows());
					model.addAttribute("top_newsList", result.getResult());
					model.addAttribute("top_newsTotal", result.getTotal());
				
				}else if(paramVO.getCategory().equals("book_info")) {
					System.out.println("여기로 탈출 2");
					result = Book_infoService.book_infoSearch(paramVO);
					total += result.getTotal();
					
					model.addAttribute("book_infoRow", result.getRows());
					model.addAttribute("book_infoList", result.getResult());
					model.addAttribute("book_infoTotal", result.getTotal());
				/*}else if(paramVO.getCategory().equals("NEWS")) {
					System.out.println("여기로 탈출 3");
					result = newsService.newsSearch(paramVO);
					System.out.println(result);
					total += result.getTotal();
					System.out.println("여기로 탈출 3_1");
					
					model.addAttribute("newsRow", result.getRows());
					model.addAttribute("newsList", result.getResult());
					model.addAttribute("newsTotal", result.getTotal());*/
				}else {
					System.out.println("SearchController 실행 안되고 있음.");
					result = Top_newsService.top_newsSearch(paramVO);
					result1 = Book_infoService.book_infoSearch(paramVO);
					/*result2 = newsService.newsSearch(paramVO);*/
					total += result.getTotal();
					
					model.addAttribute("top_newsRow", result.getRows());
					model.addAttribute("top_newsList", result.getResult());
					model.addAttribute("top_newsTotal", result.getTotal());
					model.addAttribute("book_infoRow", result1.getRows());
					model.addAttribute("book_infoList", result1.getResult());
					model.addAttribute("book_infoTotal", result1.getTotal());
					 /* model.addAttribute("newsRow", result2.getRows());
					 * model.addAttribute("newsList", result2.getResult());
					 * model.addAttribute("newsTotal", result2.getTotal());
					 */
					
				}
			}	
		}
		System.out.println("여기까지는 탈출");
		model.addAttribute("formatTotal", commonUtil.formatMoney(total));
		model.addAttribute("total", total);
		
		return model;
	}	
}
