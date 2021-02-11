package com.netizen.cms.api.academic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.netizen.cms.api.academic.model.entity.ClassInfo;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;

public interface ClassInfoRepository extends JpaRepository<ClassInfo, Long>{
	
	public ClassInfo findByClassNameAndCmsInfo(String className,CmsInfo cmsInfo);
	
	public ClassInfo findByClassIdAndCmsInfo(Long classId,CmsInfo cmsInfo);
	
	public List<ClassInfo> findByCmsInfoOrderByClassSerialAsc(CmsInfo cmsInfo);

}
