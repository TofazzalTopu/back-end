package com.netizen.cms.api.admisia.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.admisia.model.entity.AdmisiaClassConfig;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;

public interface AdmisiaClassConfigRepository extends JpaRepository<AdmisiaClassConfig, Long>{
	
	List<AdmisiaClassConfig> findByCmsInfo_CmsIdOrderByClassConfigSerial(Long cmsId);
	
	public AdmisiaClassConfig findByClassConfigIdAndCmsInfo(Long classConfigId,CmsInfo cmsInfo);
	
	public List<AdmisiaClassConfig> findByCmsInfo_CmsIdAndApplicationStartDateLessThanEqualAndApplicationEndDateGreaterThanEqualAndEnableStatusOrderByClassConfigSerial(Long cmsId,Date startDate,Date endDate,int enableStatus);
	
	public AdmisiaClassConfig findByCmsInfo_CmsIdAndClassInfo_ClassIdAndGroupInfo_groupId(Long cmsId,Long classId,Long groupId);
	
	public List<AdmisiaClassConfig> findByCmsInfo_CmsIdAndEnableStatusOrderByClassInfo_ClassIdAsc(Long cmsId,int enableStatus );
}
