package com.netizen.cms.api.academic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.netizen.cms.api.academic.model.entity.SyllabusInfo;

public interface SyllabusInfoRepository extends JpaRepository<SyllabusInfo, Long>{

	List<SyllabusInfo> findByCmsInfo_CmsIdOrderBySyllabusSerial(Long cmsId);

}
