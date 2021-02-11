package com.netizen.cms.api.cmsinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsEdumanMapping;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;

public interface CmsEdumanMappingRepository extends JpaRepository<CmsEdumanMapping, Long>{

	
	public List<CmsEdumanMapping> findByCmsInfo(CmsInfo cmsInfo);
	
	
	public List<CmsEdumanMapping> findByCmsInfo_CmsId(Long cmsId);
	
	public List<CmsEdumanMapping> findByApprovedStatus(int approvedStatus);
	
	
	public CmsEdumanMapping findByInstituteIdAndCmsInfo_CmsId(String instituteId,Long cmsId);
	
	public CmsEdumanMapping findByCmsInfo_CustomCmsId_(Long customCmsId);
}
