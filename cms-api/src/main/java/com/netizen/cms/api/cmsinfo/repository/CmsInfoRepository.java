package com.netizen.cms.api.cmsinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;

public interface CmsInfoRepository extends JpaRepository<CmsInfo, Long>{

	public CmsInfo findByUrlName(String urlName);

	public CmsInfo findByUserRoleAssignIdAndUrlStatus(Long roleAssignId, Integer urlStatus);
	
	@Query(value = "SELECT cms_id,url_name FROM cms_info",nativeQuery = true)
	public List<Object[]> searchUrlList();
	
	/**
	 * @author riaz_netizen
	 * @since 26-11-2020
	 * @return
	 */
	@Query(value = "select  ifnull(max(custom_cms_id),0) from cms_info ", nativeQuery = true)
	public Long searchMaxCustomCmsId();
	
	
	
}
