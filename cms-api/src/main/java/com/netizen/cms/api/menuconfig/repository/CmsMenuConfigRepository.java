package com.netizen.cms.api.menuconfig.repository;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.menuconfig.model.entity.CmsMenuConfig;

import java.util.List;

public interface CmsMenuConfigRepository extends JpaRepository<CmsMenuConfig, Long>{
	
	
	List<CmsMenuConfig> findAllByCmsInfo(CmsInfo cmsInfo);
	

}
