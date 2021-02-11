package com.netizen.cms.api.official.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.official.model.entity.MemberInfo;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {

	public MemberInfo findByMemberIdAndCmsInfo(Long memberId, CmsInfo cmsInfo);

	public List<MemberInfo> findByCmsInfoOrderByMemberSerialAsc(CmsInfo cmsInfo);

	public List<MemberInfo> findByMemberTypeAndCmsInfo_CmsIdOrderByMemberSerialAsc(String memberType, Long cmsId);

}
