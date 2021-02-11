package com.netizen.cms.api.official.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.official.model.entity.NoticeInfo;

public interface NoticeInfoRepository extends JpaRepository<NoticeInfo, Long> {

	/**
	 * @author riaz_netizen
	 * @since 28-06-2020
	 * @return
	 */
	public NoticeInfo findByNoticeIdAndCmsInfo(Long noticeId, CmsInfo cmsInfo);

	public List<NoticeInfo> findByCmsInfo_CmsIdAndNoticeStatusAndNoticeExpiryDateGreaterThanEqualOrderByNoticeSerialAsc(
			Long cmsId, int status, Date expiryDate);
	
	
	public List<NoticeInfo> findByCmsInfo_CmsIdAndNoticeStatusAndNoticeExpiryDateGreaterThanEqualAndNoticeIssueDateLessThanEqualOrderByNoticeSerialAsc(
			Long cmsId, int status, Date expiryDate, Date issueDate);

	/**
	 * @author riaz_netizen
	 * @since 28-06-2020
	 * @return
	 */
	@Query(value = "SELECT n.notice_id,n.notice_serial,n.notice_file_name,n.notice_title,n.notice_details,n.notice_issue_date,n.notice_expiry_date,n.notice_status,concat(datediff(n.notice_expiry_date,curdate()),' Days') as remainDays\r\n"
			+ "FROM notice_info as n\r\n"
			+ "where n.notice_expiry_date > current_date() and n.notice_status=1", nativeQuery = true)
	public Object[] findEnableNotice();

	/**
	 * @author riaz_netizen
	 * @since 28-06-2020
	 * @return
	 */
	@Query(value = "SELECT n.notice_id,n.notice_serial,n.notice_file_name,n.notice_title,n.notice_details,n.notice_issue_date,n.notice_expiry_date,n.notice_status,\r\n"
			+ "case when (n.notice_expiry_date < current_date()) then \"Expired\" else \"Not Expire\" end as expireStatus\r\n"
			+ "FROM notice_info as n\r\n" + "where n.notice_id not in(SELECT n.notice_id\r\n"
			+ "FROM notice_info as n\r\n"
			+ "where n.notice_expiry_date > current_date() and n.notice_status=1) and cms_id=:cmsId", nativeQuery = true)
	public List<Object[]> findExpiredDisableNotice(@Param("cmsId") Long cmsId);

}
