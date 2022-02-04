package egovframework.kf.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.kf.dao.book_infoDAO;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.service.book_infoService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("book_infoService")
public class book_infoServiceImpl extends EgovAbstractServiceImpl implements book_infoService {
	
	/** book_infoDAO */
	@Resource(name = "book_infoDAO")
	private book_infoDAO Book_infoDAO;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	@Override
	public RestResultVO book_infoSearch(ParameterVO paramVO) throws Exception {
		RestResultVO resultVO = Book_infoDAO.book_infoSearch(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}
}
