package com.netizen.cms.api.academic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.netizen.cms.api.academic.model.entity.SeatInfo;

public interface SeatInfoRepository extends JpaRepository<SeatInfo, Long>{

	List<SeatInfo> findByCmsInfo_CmsId(Long cmsId);

}
