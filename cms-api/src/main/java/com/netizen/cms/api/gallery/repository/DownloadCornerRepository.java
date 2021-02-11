package com.netizen.cms.api.gallery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.gallery.model.entity.DownloadCorner;

public interface DownloadCornerRepository extends JpaRepository<DownloadCorner, Long>{
	

	public List<DownloadCorner> findByCmsInfo_cmsIdOrderByFileSerialAsc(Long cmsId);

}
