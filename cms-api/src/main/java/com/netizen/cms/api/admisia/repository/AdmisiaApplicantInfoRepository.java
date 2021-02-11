package com.netizen.cms.api.admisia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.netizen.cms.api.admisia.model.entity.AdmisiaApplicantInfo;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;

public interface AdmisiaApplicantInfoRepository extends JpaRepository<AdmisiaApplicantInfo, Long> {

	@Query(value = "select  ifnull(max(registration_id),0) from admisia_applicant_info where  academic_year=? ", nativeQuery = true)
	public Long searchMaxRegistrationIdInAcYear(Integer academicYear);

	@Query(value = "select  ifnull(max(roll_no),0) from admisia_applicant_info where cms_id=?1 and academic_year=?2 ", nativeQuery = true)
	public Integer searchMaxRollInAcYear(Long cmsId, Integer academicYear);

	public AdmisiaApplicantInfo findByCmsInfoAndAdmisiaClassConfig_ClassConfigIdAndAcademicYearAndApplicantNameAndMobileNo(
			CmsInfo cmsInfo, Long classConfigId, Integer academicYear, String applicantName, String mobileNo);

	/**
	 * @author riaz_netizen
	 * @since 05-10-2020
	 * @param cmsId
	 * @param classId
	 * @param groupId
	 * @param admissionExamStatus
	 * @param applicantStatus
	 * @param applicantFeeStatus
	 * @return
	 */
	List<AdmisiaApplicantInfo> findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassInfo_ClassIdAndAdmisiaClassConfig_GroupInfo_GroupIdAndAdmisiaClassConfig_AdmissionExamStatusAndApplicantStatusAndApplicantFeeStatus(
			Long cmsId, Long classId, Long groupId, int admissionExamStatus, int applicantStatus,
			int applicantFeeStatus);

	/**
	 * @author riaz_netizen
	 * @since 11-10-2020
	 * @param cmsId
	 * @param classId
	 * @param groupId
	 * @param autoApproveStatus
	 * @param applicantStatus
	 * @param applicantFeeStatus
	 * @return
	 */
	List<AdmisiaApplicantInfo> findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassInfo_ClassIdAndAdmisiaClassConfig_GroupInfo_GroupIdAndAdmisiaClassConfig_AutoApproveStatusAndApplicantStatusAndApplicantFeeStatus(
			Long cmsId, Long classId, Long groupId, int autoApproveStatus, int applicantStatus, int applicantFeeStatus);

	/**
	 * @author riaz_netizen
	 * @since 11-10-2020
	 * @param cmsId
	 * @param classId
	 * @param groupId
	 * @param applicantStatus
	 * @param applicantFeeStatus
	 * @return
	 */
	List<AdmisiaApplicantInfo> findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassInfo_ClassIdAndAdmisiaClassConfig_GroupInfo_GroupIdAndApplicantStatusAndApplicantFeeStatus(
			Long cmsId, Long classId, Long groupId, int applicantStatus, int applicantFeeStatus);

	/**
	 * @author riaz_netizen
	 * @since 18-10-2020
	 * @param cmsId
	 * @param applicantIds
	 * @return
	 */
	public List<AdmisiaApplicantInfo> findByCmsInfo_CmsIdAndApplicantIdIn(Long cmsId, List<Long> applicantIds);

	/**
	 * @author riaz_netizen
	 * @since 20-10-2020
	 * @param registrationId
	 * @return
	 */
	public AdmisiaApplicantInfo findByRegistrationId(String registrationId);

	public AdmisiaApplicantInfo findByCmsInfo_CmsIdAndRegistrationId(Long cmsId, String registrationId);

	public AdmisiaApplicantInfo findByCmsInfo_CmsIdAndRegistrationIdAndApplicantFeeStatus(Long cmsId,
			String registrationId, int feePaidStatus);

	@Query(value = "select c.class_name as class, g.group_name as 'group',\n"
			+ "count(t.applicant_id) as totalApplicant,\n" + "sum(t.amount) as collectionAmount,\n"
			+ "sum(t.service_charge) as serviceCharge,\n" + "sum(t.application_fee) as receiveableAmount\n"
			+ "from admisia_fee_transaction_log as t\n"
			+ "inner join admisia_applicant_info as a on a.applicant_id=t.applicant_id\n"
			+ "inner join admisia_class_config as cc on cc.class_config_id=a.class_config_id\n"
			+ "inner join class_info as c on c.class_id=cc.class_id\n"
			+ "inner join group_info as g on g.group_id=cc.group_id\n"
			+ "where a.academic_year=:academicYear and t.cms_id=:cmsId\n"
			+ "group by c.class_id,g.group_id;", nativeQuery = true)
	List<Object[]> getApplicantCollectionReport(@Param("academicYear") Integer academicYear,
			@Param("cmsId") Long cmsId);

	
/**
 * @author Mohammad Riaz
 * @modify 03-02-2021
 * @param academicYear
 * @param cmsId
 * @return
 */
	@Query(value = "select c.class_name as class,g.group_name as 'group',count(a.applicant_id) as totalApplicant,\n"
			+ "ifnull(count(case when a.applicant_status=0 then a.applicant_id end), 0) as assisPending,\n"
			+ "ifnull(count(case when a.applicant_status=2 then a.applicant_id end), 0) as assisRejected,\n"
			+ "ifnull(count(case when a.applicant_status=1 and cc.admission_exam_status=1 then a.applicant_id end), 0) as assisApproved,\n"
			+ "ifnull(count(case when a.applicant_status=1 and cc.admission_exam_status=0 then a.applicant_id end), 0) as adPending,\n"
			+ "ifnull(count(case when a.applicant_status=4 then a.applicant_id end), 0) as adWaiting,\n"
			+ "ifnull(count(case when a.applicant_status=3 then a.applicant_id end), 0) as adRejected,\n"
			+ "ifnull(count(case when a.applicant_status=5 then a.applicant_id end), 0) as adApproved,\n"
			+ "ifnull(count(case when a.applicant_status=10 then a.applicant_id end), 0) as transfered\n"
			+ "from admisia_fee_transaction_log as t\n"
			+ "inner join admisia_applicant_info as a on a.applicant_id=t.applicant_id\n"
			+ "inner join admisia_class_config as cc on cc.class_config_id=a.class_config_id\n"
			+ "inner join class_info as c on c.class_id=cc.class_id\n"
			+ "inner join group_info as g on g.group_id=cc.group_id\n"
			+ "where a.academic_year=:academicYear and t.cms_id=:cmsId\n"
			+ "group by c.class_id,g.group_id", nativeQuery = true)
	List<Object[]> getApplicantAnalyticsReport(@Param("academicYear") Integer academicYear, @Param("cmsId") Long cmsId);
	
	

	/**
	 * @author riaz_netizen
	 * @since 28-10-2020
	 * @param cmsId
	 * @param classConfigId
	 * @param admissionExamStatus
	 * @param applicantStatus
	 * @param applicantFeeStatus
	 * @return
	 */
	List<AdmisiaApplicantInfo> findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassConfigIdAndAdmisiaClassConfig_AdmissionExamStatusAndApplicantStatusAndApplicantFeeStatus(
			Long cmsId, Long classConfigId, int admissionExamStatus, int applicantStatus, int applicantFeeStatus);

	/**
	 * @author riaz_netizen
	 * @since 28-10-2020
	 * @param cmsId
	 * @param classConfigId
	 * @param autoApproveStatus
	 * @param applicantStatus
	 * @param applicantFeeStatus
	 * @return
	 */
	List<AdmisiaApplicantInfo> findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassConfigIdAndAdmisiaClassConfig_AutoApproveStatusAndApplicantStatusAndApplicantFeeStatus(
			Long cmsId, Long classConfigId, int autoApproveStatus, int applicantStatus, int applicantFeeStatus);

	/**
	 * @author riaz_netizen
	 * @since 28-10-2020
	 * @param cmsId
	 * @param classConfigId
	 * @param applicantStatus
	 * @param applicantFeeStatus
	 * @return
	 */
	List<AdmisiaApplicantInfo> findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassConfigIdAndApplicantStatusAndApplicantFeeStatus(
			Long cmsId, Long classConfigId, int applicantStatus, int applicantFeeStatus);

	/**
	 * @author riaz_netizen
	 * @since 28-10-2020
	 * @param mobileNo
	 * @param academicYear
	 * @return
	 */
	List<AdmisiaApplicantInfo> findByMobileNoAndAcademicYearOrderByApplicantIdAsc(String mobileNo,
			Integer academicYear);

	public List<AdmisiaApplicantInfo> findByCmsInfo_CmsIdAndAcademicYearAndApplicantFeeStatusOrderByApplicantIdAsc(
			Long cmsId, Integer academicYear, int feeStatus);

	/**
	 * @author riaz_netizen
	 * @since 09-11-2020
	 * @param cmsId
	 * @param classConfigId
	 * @param applicantIds
	 * @return
	 */
	public List<AdmisiaApplicantInfo> findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassConfigIdAndApplicantIdIn(Long cmsId,
			Long classConfigId, List<Long> applicantIds);

	public List<AdmisiaApplicantInfo> findByCmsInfo_CmsIdAndAdmisiaClassConfig_AdmissionExamStatusAndApplicantStatusAndApplicantFeeStatusAndAcademicYearAndRegistrationIdInOrderByRollNo(
			Long cmsId, int admissionExamStatus, int applicantStatus, int applicantFeeStatus, Integer academicYear,
			List<String> registrationIds);
}
