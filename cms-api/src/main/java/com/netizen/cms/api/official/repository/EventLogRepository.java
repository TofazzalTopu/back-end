package com.netizen.cms.api.official.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.official.model.entity.EventLog;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
	
	public EventLog findByEventIdAndCmsInfo(Long eventId,CmsInfo cmsInfo);
	
	public List<EventLog> findByCmsInfo_cmsIdOrderByEventSerialAsc(Long cmsId);
}
