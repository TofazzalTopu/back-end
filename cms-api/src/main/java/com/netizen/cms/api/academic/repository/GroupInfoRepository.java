package com.netizen.cms.api.academic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.netizen.cms.api.academic.model.entity.GroupInfo;


public interface GroupInfoRepository extends JpaRepository<GroupInfo, Long>{

	List<GroupInfo> findByCmsInfo_CmsIdOrderByGroupSerial(Long cmsId);

	GroupInfo findByCmsInfo_CmsIdAndGroupName(Long cmsId, String groupName);

}
