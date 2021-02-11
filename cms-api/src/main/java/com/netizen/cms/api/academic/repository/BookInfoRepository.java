package com.netizen.cms.api.academic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.academic.model.entity.BookInfo;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;

public interface BookInfoRepository extends JpaRepository<BookInfo, Long> {

	public BookInfo findByBookIdAndCmsInfo(Long bookId, CmsInfo cmsInfo);

	public List<BookInfo> findByCmsInfo_cmsIdOrderByClassInfo_ClassIdAsc(Long cmsId);
	
	public List<BookInfo> findByClassInfo_ClassIdAndCmsInfo_cmsId(Long classId,Long cmsId);

}
