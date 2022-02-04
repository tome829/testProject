package egovframework.kf.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.kf.dao.NewsDAO;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.service.NewsService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("newsService")
public class NewsServiceImpl extends EgovAbstractServiceImpl implements NewsService {
	
	/** NewsDAO */
	@Resource(name = "newsDAO")
	private NewsDAO newsDAO;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	@Override
	public RestResultVO newsSearch(ParameterVO paramVO) throws Exception {
		System.out.println("NewsServiceImpl 의 paramVO " + paramVO);
		RestResultVO resultVO = newsDAO.newsSearch(paramVO);
		System.out.println("resultVO 값 : "+ resultVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		System.out.println("NewsServiceImpl 부분");
		return resultVO;
	}
}
