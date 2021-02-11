package com.netizen.cms.api.gallery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.gallery.model.entity.ImportantLinkInfo;

public interface ImportantLinkInfoRepository extends JpaRepository<ImportantLinkInfo, Long> {

	public ImportantLinkInfo findByLinkIdAndCmsInfo(Long linkId, CmsInfo cmsInfo);

	public List<ImportantLinkInfo> findByCmsInfo_cmsIdOrderByLinkSerialAsc(Long cmsId);

}
