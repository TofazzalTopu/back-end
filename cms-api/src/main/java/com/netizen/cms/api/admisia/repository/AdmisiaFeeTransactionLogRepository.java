package com.netizen.cms.api.admisia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.admisia.model.entity.AdmisiaFeeTransactionLog;

public interface AdmisiaFeeTransactionLogRepository extends JpaRepository<AdmisiaFeeTransactionLog, Long> {
	/**
	 * @author riaz_netizen
	 * @since 29-10-2020
	 * @param academicYear
	 * @param classConfigId
	 * @return
	 */
	List<AdmisiaFeeTransactionLog> findByCmsInfo_CmsIdAndAdmisiaApplicantInfo_AcademicYearAndAdmisiaApplicantInfo_AdmisiaClassConfig_ClassConfigIdAndAdmisiaApplicantInfo_ApplicantFeeStatus(
			Long cmsId, Integer academicYear, Long classConfigId, Integer feePaidStatus);

}
