package com.netizen.cms.api.academic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.netizen.cms.api.academic.model.entity.DressInfo;

public interface DressInfoRepository extends JpaRepository<DressInfo, Long> {

	List<DressInfo> findByCmsInfo_CmsIdOrderByDressSerial(Long cmsId);

	List<DressInfo> findByGenderAndCmsInfo_CmsIdOrderByDressSerialAsc(String gender, Long cmsId);

}
