package com.netizen.cms.api.admisia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.netizen.cms.api.admisia.model.entity.AdmisiaCoreConfig;

public interface AdmisiaCoreConfigRepository extends JpaRepository<AdmisiaCoreConfig, Long> {

	public AdmisiaCoreConfig findByCmsInfo_CmsId(Long cmsId);

	@Query(value = "SELECT c.service_charge FROM admisia_core_config as c "
			+ " where c.cms_id=:cmsId", nativeQuery = true)
	public Double getServiceCharge(Long cmsId);

	public AdmisiaCoreConfig findByCoreConfigIdAndCmsInfo_CmsId(Long coreConfigId, Long cmsId);

}
