package com.netizen.cms.api.official.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.official.model.entity.AboutusInfo;

public interface AboutusInfoRepository extends JpaRepository<AboutusInfo, Long>{

	List<AboutusInfo> findByCmsInfo_CmsId(Long cmsId);

	AboutusInfo findByCmsInfo_CmsIdAndAboutusType(Long cmsId, String typeName);
	
	AboutusInfo findByCmsInfoAndAboutusId(CmsInfo cmsInfo, Long aboutUsId);

}
