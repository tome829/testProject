package egovframework.kf.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.kf.dao.PersonDAO;
import egovframework.kf.data.ParameterVO;
import egovframework.kf.data.RestResultVO;
import egovframework.kf.service.PersonService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("personService")
public class PersonServiceImpl extends EgovAbstractServiceImpl implements PersonService {
	
	/** BoardDAO */
	@Resource(name = "personDAO")
	private PersonDAO personDAO;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;
	
	@Override
	public RestResultVO PersonSearch(ParameterVO paramVO) throws Exception {
		RestResultVO resultVO = personDAO.personSearch(paramVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}
}
