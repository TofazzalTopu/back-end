package com.netizen.cms.api.academic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.netizen.cms.api.academic.model.entity.FeeInfo;

public interface FeeInfoRepository extends JpaRepository<FeeInfo, Long>{

	List<FeeInfo> findByCmsInfo_CmsIdOrderByFeeSerial(Long cmsId);

	List<FeeInfo> findByClassInfo_ClassIdAndCmsInfo_CmsId(Long classId,Long cmsId);
}
