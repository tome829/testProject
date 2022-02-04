package egovframework.kf.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.kf.dao.top_newsDAO;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.service.top_newsService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("top_newsService")
public class top_newsServiceImpl extends EgovAbstractServiceImpl implements top_newsService {
	
	/** top_newsDAO */
	@Resource(name = "top_newsDAO")
	private top_newsDAO top_newsDAO;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	@Override
	public RestResultVO top_newsSearch(ParameterVO paramVO) throws Exception {
		RestResultVO resultVO = top_newsDAO.top_newsSearch(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}
}
