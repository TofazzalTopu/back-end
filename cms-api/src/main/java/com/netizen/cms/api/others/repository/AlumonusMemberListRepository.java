package com.netizen.cms.api.others.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.others.model.entity.AlumonusMemberList;

public interface AlumonusMemberListRepository extends JpaRepository<AlumonusMemberList, Long> {
	
	public List<AlumonusMemberList> findByCmsInfoOrderBySerialAsc(CmsInfo cmsInfo);

}
