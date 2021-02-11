package com.netizen.cms.api.admisia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.admisia.model.entity.AdmisiaApplicantPrevexamInfo;

public interface AdmisiaApplicantPrevexamInfoRepository extends JpaRepository<AdmisiaApplicantPrevexamInfo, Long> {

	public List<AdmisiaApplicantPrevexamInfo> findByAdmisiaApplicantInfo_ApplicantId(Long applicantId);

	public List<AdmisiaApplicantPrevexamInfo> findByCmsInfo_CmsIdAndAdmisiaApplicantInfo_AdmisiaClassConfig_ClassConfigIdAndAdmisiaApplicantInfo_AcademicYearAndAdmisiaApplicantInfo_ApplicantFeeStatus(
			Long cmsId, Long classConfigId, Integer academicYear, Integer applicationFeeStatus);

}
