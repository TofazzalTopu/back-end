package com.netizen.cms.api.admisia.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netizen.cms.api.admisia.model.entity.AdmisiaApplicantInfo;
import com.netizen.cms.api.admisia.model.entity.AdmisiaApplicantPrevexamInfo;
import com.netizen.cms.api.admisia.model.entity.AdmisiaClassConfig;
import com.netizen.cms.api.admisia.model.entity.AdmisiaCoreConfig;
import com.netizen.cms.api.admisia.model.entity.AdmisiaFeeTransactionLog;
import com.netizen.cms.api.admisia.model.request.AdmisiaApplicantRegistrationHelper;
import com.netizen.cms.api.admisia.model.request.AdmisiaApplicantRegistrationRequest;
import com.netizen.cms.api.admisia.model.request.AdmisiaApplicantStatusUpdateRequest;
import com.netizen.cms.api.admisia.model.request.MarkInputRequest;
import com.netizen.cms.api.admisia.model.request.MarkInputRequestHelper;
import com.netizen.cms.api.admisia.model.response.ApplicantAssesmentOrApprovalViewResponse;
import com.netizen.cms.api.admisia.model.response.ApplicantDetailsViewResponse;
import com.netizen.cms.api.admisia.model.response.ApplicantPersonalViewResponse;
import com.netizen.cms.api.admisia.model.response.ApplicantPreviousExamViewResponse;
import com.netizen.cms.api.admisia.repository.AdmisiaApplicantInfoRepository;
import com.netizen.cms.api.admisia.repository.AdmisiaApplicantPrevexamInfoRepository;
import com.netizen.cms.api.admisia.repository.AdmisiaClassConfigRepository;
import com.netizen.cms.api.admisia.repository.AdmisiaCoreConfigRepository;
import com.netizen.cms.api.admisia.repository.AdmisiaFeeTransactionLogRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.cmsinfo.repository.CmsInfoRepository;
import com.netizen.cms.api.common.enums.AdmisiaApplicantStatusEnum;
import com.netizen.cms.api.common.enums.AdmisiaAssessmentStatusEnum;
import com.netizen.cms.api.common.enums.AdmisiaAutoApprovedStatusEnum;
import com.netizen.cms.api.common.enums.AdmisiaFeeStatusEnum;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.FileUtilService;
import com.netizen.cms.api.location.model.entity.District;
import com.netizen.cms.api.location.repository.DistrictRepository;

@Service
public class AdmisiaApplicantInfoService {

	@Autowired
	public AdmisiaApplicantInfoRepository admisiaApplicantInfoRepository;

	@Autowired
	public AdmisiaApplicantPrevexamInfoRepository admisiaApplicantPrevexamInfoRepository;

	@Autowired
	public CmsInfoRepository cmsInfoRepository;

	@Autowired
	public AdmisiaCoreConfigRepository admisiaCoreConfigRepository;

	@Autowired
	public AdmisiaClassConfigRepository admisiaClassConfigRepository;

	@Autowired
	public AdmisiaFeeTransactionLogRepository feeTransactionLogRepository;

	@Autowired
	public AdmisiaApplicantPrevexamInfoRepository prevexamInfoRepository;

	@Autowired
	public DistrictRepository districtRepository;

	@Autowired
	public FileUtilService fileUtilService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public ItemResponse saveApplicantInfo(AdmisiaApplicantRegistrationRequest request) {

		ItemResponse itemResponse = new ItemResponse();

		// ----------------------- validation clause --------------------//

		CmsInfo cmsInfo = cmsInfoRepository.getOne(request.getCmsId());

		if (cmsInfo == null) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("No Cms Found");
			return itemResponse;
		}

		AdmisiaClassConfig admisiaClassConfig = admisiaClassConfigRepository
				.findByClassConfigIdAndCmsInfo(request.getAdmisiaClassConfigId(), cmsInfo);

		if (admisiaClassConfig == null) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("Admisia Class Configuration Not Found.");
			return itemResponse;
		}

		AdmisiaCoreConfig admisiaCoreConfig = admisiaCoreConfigRepository.findByCmsInfo_CmsId(cmsInfo.getCmsId());

		if (admisiaCoreConfig == null) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("Admisia is not configured.");
			return itemResponse;
		}

		District district = districtRepository.findByDistrictId(request.getDistrictId());

		if (district == null) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("No District Found.");
			return itemResponse;
		}

		AdmisiaApplicantInfo applicantInfo = admisiaApplicantInfoRepository
				.findByCmsInfoAndAdmisiaClassConfig_ClassConfigIdAndAcademicYearAndApplicantNameAndMobileNo(cmsInfo,
						admisiaClassConfig.getClassConfigId(), admisiaCoreConfig.getCurrentAdmissionYear(),
						request.getApplicantName(), request.getMobileNo());

		if (applicantInfo != null) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("Already Applied.");
			return itemResponse;

		}

		// ------------------------------------------------------------//

		String registrationId = (maxRegistrationId(admisiaCoreConfig.getCurrentAdmissionYear()) + 1) + "";
		Integer rollNo = (maxRollNo(cmsInfo.getCmsId(), admisiaCoreConfig.getCurrentAdmissionYear()) + 1);

		AdmisiaApplicantInfo admisiaApplicantInfo = new AdmisiaApplicantInfo();
		admisiaApplicantInfo.setAcademicYear(admisiaCoreConfig.getCurrentAdmissionYear());
		admisiaApplicantInfo.setAddressDetails(request.getAddressDetails());
		admisiaApplicantInfo.setAdmisiaClassConfig(admisiaClassConfig);
		admisiaApplicantInfo.setDistrict(district);

		admisiaApplicantInfo.setApplicantFeeStatus(0);
		admisiaApplicantInfo.setApplicantName(request.getApplicantName());

		admisiaApplicantInfo.setApplicantStatus(0);

		admisiaApplicantInfo.setApplicationDate(new Date());
		admisiaApplicantInfo.setBirthCertificateNo(request.getBirthCertificateNo());
		admisiaApplicantInfo.setDob(request.getDob());
		admisiaApplicantInfo.setCmsInfo(cmsInfo);
		admisiaApplicantInfo.setExamMarks(0.0);
		admisiaApplicantInfo.setExamResult("");
		admisiaApplicantInfo.setFatherName(request.getFatherName());
		admisiaApplicantInfo.setFatherNidNo(request.getFatherNidNo());
		admisiaApplicantInfo.setFatherOccupation(request.getFatherOccupation());
		admisiaApplicantInfo.setGender(request.getGender());
		admisiaApplicantInfo.setReligion(request.getReligion());
		admisiaApplicantInfo.setMobileNo(request.getMobileNo());
		admisiaApplicantInfo.setMotherName(request.getMotherName());
		admisiaApplicantInfo.setMotherNidNo(request.getMotherNidNo());
		admisiaApplicantInfo.setMotherOccupation(request.getMotherOccupation());
		admisiaApplicantInfo.setQuota(request.getQuota());
		admisiaApplicantInfo.setRegistrationId(registrationId);
		admisiaApplicantInfo.setRollNo(rollNo);
		admisiaApplicantInfo.setUserExecuted("public");

		if (request.isFileSave()) {

			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String imageName = cmsInfo.getCmsId() + "_" + registrationId + "_applicant." + fileExtension;
//			fileUtilService.uploadImgHeightWidth(Folder.ADMISIA_APPLICANT.name(), imageName, fileExtension,
//					request.getFileContent(), 200, 200);

			fileUtilService.uploadImgHeightWidth(Folder.MEMBER.name(), imageName, fileExtension,
					request.getFileContent(), 200, 200);
			admisiaApplicantInfo.setApplicantPhoto(imageName);

		}

		admisiaApplicantInfo = admisiaApplicantInfoRepository.save(admisiaApplicantInfo);

		if (admisiaClassConfig.getPrevExamInfoRequiredStatus() == 1 && request.getAdditionalInfos() != null
				&& request.getAdditionalInfos().size() > 0) {

			Set<AdmisiaApplicantPrevexamInfo> prevexamInfos = new LinkedHashSet<>();

			for (AdmisiaApplicantRegistrationHelper helper : request.getAdditionalInfos()) {

				AdmisiaApplicantPrevexamInfo prevexamInfo = new AdmisiaApplicantPrevexamInfo();

				prevexamInfo.setAdmisiaApplicantInfo(admisiaApplicantInfo);
				prevexamInfo.setBoardName(helper.getBoardName());
				prevexamInfo.setClassName(helper.getClassName());
				prevexamInfo.setCmsInfo(cmsInfo);
				prevexamInfo.setExamGpa(helper.getExamGpa());
				prevexamInfo.setExamGrade(helper.getExamGrade());
				prevexamInfo.setExamName(helper.getExamName());
				prevexamInfo.setInstituteName(helper.getInstituteName());
				prevexamInfo.setInstituteType(helper.getInstituteType());
				prevexamInfo.setPassingYear(helper.getPassingYear());
				prevexamInfo.setRollNo(helper.getRollNo());
				prevexamInfo.setRegistrationNo(helper.getRegistrationNo());

				prevexamInfos.add(prevexamInfo);

			}

			admisiaApplicantPrevexamInfoRepository.saveAll(prevexamInfos);

		}

		itemResponse.setMessage("Applicant Info Successfully Saved.");
		itemResponse.setItem(admisiaApplicantInfo.getRegistrationId());
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	public Long maxRegistrationId(Integer academicYear) {
		Long maxid;
		Long registrationId = admisiaApplicantInfoRepository.searchMaxRegistrationIdInAcYear(academicYear);
		if (registrationId == 0) {
			String strid = academicYear.toString().substring(2, 4);
			strid += "0000000";
			maxid = Long.parseLong(strid);
		} else {
			maxid = registrationId;
		}

		return maxid;
	}

	public Integer maxRollNo(Long cmsId, Integer academicYear) {
		Integer maxroll;
		Integer roll = admisiaApplicantInfoRepository.searchMaxRollInAcYear(cmsId, academicYear);
		if (roll == 0) {
			maxroll = 100000;
		} else {
			maxroll = roll;
		}

		return maxroll;
	}

	/**
	 * @author riaz_netizen
	 * @since 28-10-2020
	 * @param cmsId
	 * @param classConfigId
	 * @param applicantStatus
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searchApplicantListByStatus(Long cmsId, Long classConfigId, int applicantStatus) {

		ItemResponse itemResponse = new ItemResponse();

		List<AdmisiaApplicantInfo> applicantInfos = new ArrayList<>();

		if (applicantStatus == AdmisiaApplicantStatusEnum.PENDING_FOR_ASSESSMENT.getCode()
				|| applicantStatus == AdmisiaApplicantStatusEnum.REJECTED_FOR_ASSESSMENT.getCode()) {
			applicantInfos = admisiaApplicantInfoRepository
					.findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassConfigIdAndAdmisiaClassConfig_AdmissionExamStatusAndApplicantStatusAndApplicantFeeStatus(
							cmsId, classConfigId, AdmisiaAssessmentStatusEnum.ASSESSMENT_STATUS_YES.getCode(),
							applicantStatus, AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		} else if (applicantStatus == AdmisiaApplicantStatusEnum.PENDING_FOR_ADMISSION.getCode()) {
			applicantInfos = admisiaApplicantInfoRepository
					.findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassConfigIdAndAdmisiaClassConfig_AutoApproveStatusAndApplicantStatusAndApplicantFeeStatus(
							cmsId, classConfigId, AdmisiaAutoApprovedStatusEnum.AUTO_APPROVE_STATUS_NO.getCode(),
							AdmisiaApplicantStatusEnum.PENDING_FOR_ADMISSION.getCode(),
							AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		} else if (applicantStatus == AdmisiaApplicantStatusEnum.WAITING_FOR_ADMISSION.getCode()
				|| applicantStatus == AdmisiaApplicantStatusEnum.REJECTED_FOR_ADMISSION.getCode()
				|| applicantStatus == AdmisiaApplicantStatusEnum.APPROVED_FOR_ADMISSION.getCode()
				|| applicantStatus == AdmisiaApplicantStatusEnum.TRANSFERRED_FOR_ADMISSION.getCode()) {
			applicantInfos = admisiaApplicantInfoRepository
					.findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassConfigIdAndApplicantStatusAndApplicantFeeStatus(
							cmsId, classConfigId, applicantStatus, AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		}

		if (applicantInfos.isEmpty()) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("Desire applicants List is Empty.");
			return itemResponse;
		}

		List<ApplicantAssesmentOrApprovalViewResponse> responses = new ArrayList<>();

		for (AdmisiaApplicantInfo info : applicantInfos) {
			ApplicantAssesmentOrApprovalViewResponse response = new ApplicantAssesmentOrApprovalViewResponse();
			copyApplicantInfoToAssesmentViewResponse(info, response);
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 05-10-2020
	 * @param cmsId
	 * @param classId
	 * @param groupId
	 * @param assessmentType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searPendingOrRejectedOrApprovedAssessmentList(Long cmsId, Long classId, Long groupId,
			String assessmentType) {

		ItemResponse itemResponse = new ItemResponse();

		List<AdmisiaApplicantInfo> applicantInfos = new ArrayList<>();

		if (assessmentType.equalsIgnoreCase("Pending")) {
			applicantInfos = admisiaApplicantInfoRepository
					.findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassInfo_ClassIdAndAdmisiaClassConfig_GroupInfo_GroupIdAndAdmisiaClassConfig_AdmissionExamStatusAndApplicantStatusAndApplicantFeeStatus(
							cmsId, classId, groupId, AdmisiaAssessmentStatusEnum.ASSESSMENT_STATUS_YES.getCode(),
							AdmisiaApplicantStatusEnum.PENDING_FOR_ASSESSMENT.getCode(),
							AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		} else if (assessmentType.equalsIgnoreCase("Approved")) {
			applicantInfos = admisiaApplicantInfoRepository
					.findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassInfo_ClassIdAndAdmisiaClassConfig_GroupInfo_GroupIdAndAdmisiaClassConfig_AdmissionExamStatusAndApplicantStatusAndApplicantFeeStatus(
							cmsId, classId, groupId, AdmisiaAssessmentStatusEnum.ASSESSMENT_STATUS_YES.getCode(),
							AdmisiaApplicantStatusEnum.PENDING_FOR_ADMISSION.getCode(),
							AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		} else if (assessmentType.equalsIgnoreCase("Rejected")) {
			applicantInfos = admisiaApplicantInfoRepository
					.findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassInfo_ClassIdAndAdmisiaClassConfig_GroupInfo_GroupIdAndAdmisiaClassConfig_AdmissionExamStatusAndApplicantStatusAndApplicantFeeStatus(
							cmsId, classId, groupId, AdmisiaAssessmentStatusEnum.ASSESSMENT_STATUS_YES.getCode(),
							AdmisiaApplicantStatusEnum.REJECTED_FOR_ASSESSMENT.getCode(),
							AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		}

		List<ApplicantAssesmentOrApprovalViewResponse> responses = new ArrayList<>();

		for (AdmisiaApplicantInfo info : applicantInfos) {
			ApplicantAssesmentOrApprovalViewResponse response = new ApplicantAssesmentOrApprovalViewResponse();
			copyApplicantInfoToAssesmentViewResponse(info, response);
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 11-10-2020
	 * @param cmsId
	 * @param classId
	 * @param groupId
	 * @param approvalType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searPendingOrWaitingOrRejectedOrApprovedOrTransferredForAdmissionList(Long cmsId, Long classId,
			Long groupId, String approvalType) {

		ItemResponse itemResponse = new ItemResponse();

		List<AdmisiaApplicantInfo> applicantInfos = new ArrayList<>();

		if (approvalType.equalsIgnoreCase("Pending")) {

			applicantInfos = admisiaApplicantInfoRepository
					.findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassInfo_ClassIdAndAdmisiaClassConfig_GroupInfo_GroupIdAndAdmisiaClassConfig_AutoApproveStatusAndApplicantStatusAndApplicantFeeStatus(
							cmsId, classId, groupId, AdmisiaAutoApprovedStatusEnum.AUTO_APPROVE_STATUS_NO.getCode(),
							AdmisiaApplicantStatusEnum.PENDING_FOR_ADMISSION.getCode(),
							AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());
		} else if (approvalType.equalsIgnoreCase("Waiting")) {
			applicantInfos = admisiaApplicantInfoRepository
					.findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassInfo_ClassIdAndAdmisiaClassConfig_GroupInfo_GroupIdAndApplicantStatusAndApplicantFeeStatus(
							cmsId, classId, groupId, AdmisiaApplicantStatusEnum.WAITING_FOR_ADMISSION.getCode(),
							AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		} else if (approvalType.equalsIgnoreCase("Approved")) {
			applicantInfos = admisiaApplicantInfoRepository
					.findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassInfo_ClassIdAndAdmisiaClassConfig_GroupInfo_GroupIdAndApplicantStatusAndApplicantFeeStatus(
							cmsId, classId, groupId, AdmisiaApplicantStatusEnum.APPROVED_FOR_ADMISSION.getCode(),
							AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());
		} else if (approvalType.equalsIgnoreCase("Rejected")) {
			applicantInfos = admisiaApplicantInfoRepository
					.findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassInfo_ClassIdAndAdmisiaClassConfig_GroupInfo_GroupIdAndApplicantStatusAndApplicantFeeStatus(
							cmsId, classId, groupId, AdmisiaApplicantStatusEnum.REJECTED_FOR_ADMISSION.getCode(),
							AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());
		} else if (approvalType.equalsIgnoreCase("Transferred")) {
			applicantInfos = admisiaApplicantInfoRepository
					.findByCmsInfo_CmsIdAndAdmisiaClassConfig_ClassInfo_ClassIdAndAdmisiaClassConfig_GroupInfo_GroupIdAndApplicantStatusAndApplicantFeeStatus(
							cmsId, classId, groupId, AdmisiaApplicantStatusEnum.TRANSFERRED_FOR_ADMISSION.getCode(),
							AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());
		}

		List<ApplicantAssesmentOrApprovalViewResponse> responses = new ArrayList<>();

		for (AdmisiaApplicantInfo info : applicantInfos) {
			ApplicantAssesmentOrApprovalViewResponse response = new ApplicantAssesmentOrApprovalViewResponse();
			copyApplicantInfoToAssesmentViewResponse(info, response);
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	public void copyApplicantInfoToAssesmentViewResponse(AdmisiaApplicantInfo applicantInfo,
			ApplicantAssesmentOrApprovalViewResponse assesmentViewResponse) {

		assesmentViewResponse.setApplicantId(applicantInfo.getApplicantId());
		assesmentViewResponse.setClassConfigId(applicantInfo.getAdmisiaClassConfig().getClassConfigId());
		assesmentViewResponse.setRegistrationId(applicantInfo.getRegistrationId());
		assesmentViewResponse.setRollNo(applicantInfo.getRollNo());
		assesmentViewResponse.setApplicantName(applicantInfo.getApplicantName());
		assesmentViewResponse.setClasName(applicantInfo.getAdmisiaClassConfig().getClassInfo().getClassName());
		assesmentViewResponse.setGroupName(applicantInfo.getAdmisiaClassConfig().getGroupInfo().getGroupName());
		assesmentViewResponse.setAcademicYear(applicantInfo.getAcademicYear());
		assesmentViewResponse.setMobileNo(applicantInfo.getMobileNo());
		assesmentViewResponse.setGender(applicantInfo.getGender());
		assesmentViewResponse.setReligion(applicantInfo.getReligion());
		assesmentViewResponse.setDob(applicantInfo.getDob());
		assesmentViewResponse.setBirthCertificateNo(applicantInfo.getBirthCertificateNo());
		assesmentViewResponse.setApplicationStartDate(applicantInfo.getAdmisiaClassConfig().getApplicationStartDate());
		assesmentViewResponse.setApplicationEndDate(applicantInfo.getAdmisiaClassConfig().getApplicationEndDate());
		assesmentViewResponse.setApplicationDate(applicantInfo.getApplicationDate());
		assesmentViewResponse.setAddressDetails(applicantInfo.getAddressDetails());
		assesmentViewResponse.setFatherName(applicantInfo.getFatherName());
		assesmentViewResponse.setFatherNidNo(applicantInfo.getFatherNidNo());
		assesmentViewResponse.setFatherOccupation(applicantInfo.getFatherOccupation());
		assesmentViewResponse.setMotherName(applicantInfo.getMotherName());
		assesmentViewResponse.setMotherNidNo(applicantInfo.getMotherNidNo());
		assesmentViewResponse.setMotherOccupation(applicantInfo.getMotherOccupation());
		assesmentViewResponse.setQuota(applicantInfo.getQuota());
		assesmentViewResponse.setApplicantFeeStatus(applicantInfo.getApplicantFeeStatus());
		assesmentViewResponse.setApplicantStatus(applicantInfo.getApplicantStatus());
		assesmentViewResponse.setStatusUpdateDate(applicantInfo.getStatusUpdateDate());
		assesmentViewResponse.setExamMarks(applicantInfo.getExamMarks());
		assesmentViewResponse.setExamResult(applicantInfo.getExamResult());

		if (applicantInfo.getDistrict() != null) {
			assesmentViewResponse.setDistrictId(applicantInfo.getDistrict().getDistrictId());
			assesmentViewResponse.setDistrictName(applicantInfo.getDistrict().getDistrictName());
			assesmentViewResponse.setDivisionId(applicantInfo.getDistrict().getDivision().getDivisionId());
			assesmentViewResponse.setDivisionName(applicantInfo.getDistrict().getDivision().getDivisionName());
		}

		if (applicantInfo.getApplicantPhoto() != null) {
			try {
				assesmentViewResponse.setFileContent(
						fileUtilService.fetchFileInByte(Folder.MEMBER.name(), applicantInfo.getApplicantPhoto()));
				assesmentViewResponse.setFileName(applicantInfo.getApplicantPhoto());
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	/**
	 * @author riaz_netizen
	 * @since 18-10-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updateApplicantStatus(AdmisiaApplicantStatusUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		List<AdmisiaApplicantInfo> applicantInfos = admisiaApplicantInfoRepository
				.findByCmsInfo_CmsIdAndApplicantIdIn(request.getCmsId(), request.getApplicantIds());

		if (applicantInfos.isEmpty()) {
			baseResponse.setMessageType(0);
			baseResponse.setMessage("Desire applicants not found.");
			return baseResponse;
		}

		for (AdmisiaApplicantInfo applicantInfo : applicantInfos) {

			applicantInfo.setApplicantStatus(request.getApplicantStatus());
			applicantInfo.setStatusUpdateDate(new Date());

			admisiaApplicantInfoRepository.save(applicantInfo);
		}

		baseResponse.setMessage("Applicants Status Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 20-10-2020
	 * @param registrationId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse applicantDetailsInfoByRegistrationId(Long cmsId, String registrationId) {

		ApplicantDetailsViewResponse applicantDetailsViewResponse = new ApplicantDetailsViewResponse();

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		AdmisiaApplicantInfo admisiaApplicantInfo = admisiaApplicantInfoRepository
				.findByCmsInfo_CmsIdAndRegistrationId(cmsId, registrationId);

		if (admisiaApplicantInfo == null) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("No applicant found with this " + registrationId + " Id");
			return itemResponse;
		}

		ApplicantPersonalViewResponse applicantPersonalViewResponse = copyApplicantPersonalViewResponse(
				admisiaApplicantInfo);
		List<AdmisiaApplicantPrevexamInfo> admisiaApplicantPrevexamInfos = admisiaApplicantPrevexamInfoRepository
				.findByAdmisiaApplicantInfo_ApplicantId(admisiaApplicantInfo.getApplicantId());

		List<ApplicantPreviousExamViewResponse> applicantPreviousExamViewResponses = new ArrayList<>();

		for (AdmisiaApplicantPrevexamInfo prevexamInfo : admisiaApplicantPrevexamInfos) {
			ApplicantPreviousExamViewResponse previousExamViewResponse = copyApplicantPreviousExamViewResponse(
					prevexamInfo);
			applicantPreviousExamViewResponses.add(previousExamViewResponse);
		}

		applicantDetailsViewResponse.setApplicantPersonalViewResponse(applicantPersonalViewResponse);
		applicantDetailsViewResponse.setApplicantPreviousExamViewResponses(applicantPreviousExamViewResponses);

		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(applicantDetailsViewResponse);

		return itemResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 15-11-2020
	 * @param cmsId
	 * @param registrationId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse applicantDetailsInfoByRegistrationIdForAdmin(Long cmsId, String registrationId) {

		ApplicantDetailsViewResponse applicantDetailsViewResponse = new ApplicantDetailsViewResponse();

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		AdmisiaApplicantInfo admisiaApplicantInfo = admisiaApplicantInfoRepository
				.findByCmsInfo_CmsIdAndRegistrationIdAndApplicantFeeStatus(cmsId, registrationId,
						AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		if (admisiaApplicantInfo == null) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("No applicant found with this " + registrationId + " Id");
			return itemResponse;
		}

		ApplicantPersonalViewResponse applicantPersonalViewResponse = copyApplicantPersonalViewResponse(
				admisiaApplicantInfo);
		List<AdmisiaApplicantPrevexamInfo> admisiaApplicantPrevexamInfos = admisiaApplicantPrevexamInfoRepository
				.findByAdmisiaApplicantInfo_ApplicantId(admisiaApplicantInfo.getApplicantId());

		List<ApplicantPreviousExamViewResponse> applicantPreviousExamViewResponses = new ArrayList<>();

		for (AdmisiaApplicantPrevexamInfo prevexamInfo : admisiaApplicantPrevexamInfos) {
			ApplicantPreviousExamViewResponse previousExamViewResponse = copyApplicantPreviousExamViewResponse(
					prevexamInfo);
			applicantPreviousExamViewResponses.add(previousExamViewResponse);
		}

		applicantDetailsViewResponse.setApplicantPersonalViewResponse(applicantPersonalViewResponse);
		applicantDetailsViewResponse.setApplicantPreviousExamViewResponses(applicantPreviousExamViewResponses);

		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(applicantDetailsViewResponse);

		return itemResponse;
	}

	public ApplicantPersonalViewResponse copyApplicantPersonalViewResponse(AdmisiaApplicantInfo admisiaApplicantInfo) {

		ApplicantPersonalViewResponse applicantPersonalViewResponse = new ApplicantPersonalViewResponse();

		applicantPersonalViewResponse.setApplicantId(admisiaApplicantInfo.getApplicantId());
		applicantPersonalViewResponse.setRegistrationId(admisiaApplicantInfo.getRegistrationId());
		applicantPersonalViewResponse.setRollNo(admisiaApplicantInfo.getRollNo());
		applicantPersonalViewResponse
				.setClasName(admisiaApplicantInfo.getAdmisiaClassConfig().getClassInfo().getClassName());
		applicantPersonalViewResponse
				.setGroupName(admisiaApplicantInfo.getAdmisiaClassConfig().getGroupInfo().getGroupName());
		applicantPersonalViewResponse.setAcademicYear(admisiaApplicantInfo.getAcademicYear());
		applicantPersonalViewResponse
				.setApplicationStartDate(admisiaApplicantInfo.getAdmisiaClassConfig().getApplicationStartDate());
		applicantPersonalViewResponse
				.setApplicationEndDate(admisiaApplicantInfo.getAdmisiaClassConfig().getApplicationEndDate());
		applicantPersonalViewResponse
				.setApplicationFee(admisiaApplicantInfo.getAdmisiaClassConfig().getApplicationFee());
		applicantPersonalViewResponse.setServiceCharge(admisiaApplicantInfo.getAdmisiaClassConfig().getServiceCharge());
		applicantPersonalViewResponse.setTotalFee(admisiaApplicantInfo.getAdmisiaClassConfig().getTotalFee());
		applicantPersonalViewResponse
				.setAdmissionExamStatus(admisiaApplicantInfo.getAdmisiaClassConfig().getAdmissionExamStatus());
		applicantPersonalViewResponse
				.setAutoApproveStatus(admisiaApplicantInfo.getAdmisiaClassConfig().getAutoApproveStatus());
		applicantPersonalViewResponse.setApplicationDate(admisiaApplicantInfo.getApplicationDate());
		applicantPersonalViewResponse.setApplicantStatus(admisiaApplicantInfo.getApplicantStatus());
		applicantPersonalViewResponse.setApplicantFeeStatus(admisiaApplicantInfo.getApplicantFeeStatus());
		applicantPersonalViewResponse.setApplicantName(admisiaApplicantInfo.getApplicantName());
		applicantPersonalViewResponse.setMobileNo(admisiaApplicantInfo.getMobileNo());
		applicantPersonalViewResponse.setDob(admisiaApplicantInfo.getDob());
		applicantPersonalViewResponse.setBirthCertificateNo(admisiaApplicantInfo.getBirthCertificateNo());
		applicantPersonalViewResponse.setGender(admisiaApplicantInfo.getGender());
		applicantPersonalViewResponse.setReligion(admisiaApplicantInfo.getReligion());
		applicantPersonalViewResponse.setAddressDetails(admisiaApplicantInfo.getAddressDetails());
		applicantPersonalViewResponse.setQuota(admisiaApplicantInfo.getQuota());
		applicantPersonalViewResponse.setStatusUpdateDate(admisiaApplicantInfo.getStatusUpdateDate());
		applicantPersonalViewResponse.setFatherName(admisiaApplicantInfo.getFatherName());
		applicantPersonalViewResponse.setFatherNidNo(admisiaApplicantInfo.getFatherNidNo());
		applicantPersonalViewResponse.setFatherOccupation(admisiaApplicantInfo.getFatherOccupation());
		applicantPersonalViewResponse.setMotherName(admisiaApplicantInfo.getMotherName());
		applicantPersonalViewResponse.setMotherNidNo(admisiaApplicantInfo.getMotherNidNo());
		applicantPersonalViewResponse.setMotherOccupation(admisiaApplicantInfo.getMotherOccupation());
		if (admisiaApplicantInfo.getDistrict() != null) {
			applicantPersonalViewResponse.setDistrictId(admisiaApplicantInfo.getDistrict().getDistrictId());
			applicantPersonalViewResponse.setDistrictName(admisiaApplicantInfo.getDistrict().getDistrictName());
			applicantPersonalViewResponse
					.setDivisionId(admisiaApplicantInfo.getDistrict().getDivision().getDivisionId());
			applicantPersonalViewResponse
					.setDivisionName(admisiaApplicantInfo.getDistrict().getDivision().getDivisionName());
		}

		if (admisiaApplicantInfo.getAdmisiaClassConfig().getAdmissionExamStatus() == 1) {
			applicantPersonalViewResponse
					.setAdmissionExamDate(admisiaApplicantInfo.getAdmisiaClassConfig().getAdmissionExamDate());
			applicantPersonalViewResponse
					.setAdmissionExamTime(admisiaApplicantInfo.getAdmisiaClassConfig().getAdmissionExamTime());
			applicantPersonalViewResponse.setAdmissionExamInstruction(
					admisiaApplicantInfo.getAdmisiaClassConfig().getAdmissionExamInstruction());
			applicantPersonalViewResponse
					.setExamCenterName(admisiaApplicantInfo.getAdmisiaClassConfig().getExamCenterName());
		}

		if (admisiaApplicantInfo.getApplicantPhoto() != null) {
			try {
				applicantPersonalViewResponse.setFileContent(fileUtilService.fetchFileInByte(Folder.MEMBER.name(),
						admisiaApplicantInfo.getApplicantPhoto()));
				applicantPersonalViewResponse.setFileName(admisiaApplicantInfo.getApplicantPhoto());
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		return applicantPersonalViewResponse;
	}

	public ApplicantPreviousExamViewResponse copyApplicantPreviousExamViewResponse(
			AdmisiaApplicantPrevexamInfo prevexamInfo) {

		ApplicantPreviousExamViewResponse applicantPreviousExamViewResponse = new ApplicantPreviousExamViewResponse();
		applicantPreviousExamViewResponse.setPreviousExamId(prevexamInfo.getPreviousExamId());
		applicantPreviousExamViewResponse.setExamName(prevexamInfo.getExamName());
		applicantPreviousExamViewResponse.setRegistrationNo(prevexamInfo.getRegistrationNo());
		applicantPreviousExamViewResponse.setRollNo(prevexamInfo.getRollNo());
		applicantPreviousExamViewResponse.setClassName(prevexamInfo.getClassName());
		applicantPreviousExamViewResponse.setInstituteName(prevexamInfo.getInstituteName());
		applicantPreviousExamViewResponse.setInstituteType(prevexamInfo.getInstituteType());
		applicantPreviousExamViewResponse.setPassingYear(prevexamInfo.getPassingYear());
		applicantPreviousExamViewResponse.setExamGpa(prevexamInfo.getExamGpa());
		applicantPreviousExamViewResponse.setExamGrade(prevexamInfo.getExamGrade());
		applicantPreviousExamViewResponse.setBoardName(prevexamInfo.getBoardName());
		applicantPreviousExamViewResponse.setApplicantRegistrationId(prevexamInfo.getAdmisiaApplicantInfo().getRegistrationId());
		applicantPreviousExamViewResponse.setApplicantName(prevexamInfo.getAdmisiaApplicantInfo().getApplicantName());
		applicantPreviousExamViewResponse.setMobileNo(prevexamInfo.getAdmisiaApplicantInfo().getMobileNo());
		applicantPreviousExamViewResponse.setFatherName(prevexamInfo.getAdmisiaApplicantInfo().getFatherName());
		applicantPreviousExamViewResponse.setMotherName(prevexamInfo.getAdmisiaApplicantInfo().getMotherName());
		applicantPreviousExamViewResponse.setApplicantStatus(prevexamInfo.getAdmisiaApplicantInfo().getApplicantStatus());
		return applicantPreviousExamViewResponse;

	}
	

	/**
	 * @author riaz_netizen
	 * @since 24-12-2020
	 * @param cmsId
	 * @param classConfigId
	 * @param academicYear
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ItemResponse applicantPeviousExamInfoDetails(Long cmsId, Long classConfigId, Integer academicYear) {

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		List<AdmisiaApplicantPrevexamInfo> applicantPrevexamInfos = prevexamInfoRepository
				.findByCmsInfo_CmsIdAndAdmisiaApplicantInfo_AdmisiaClassConfig_ClassConfigIdAndAdmisiaApplicantInfo_AcademicYearAndAdmisiaApplicantInfo_ApplicantFeeStatus(
						cmsId, classConfigId, academicYear, AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		if (applicantPrevexamInfos.isEmpty()) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("Desire applicant list is empty");
			return itemResponse;
		}

		List<ApplicantPreviousExamViewResponse> responses = new ArrayList<>();

		for (AdmisiaApplicantPrevexamInfo info : applicantPrevexamInfos) {
			ApplicantPreviousExamViewResponse previousExamViewResponse = copyApplicantPreviousExamViewResponse(
					info);
			responses.add(previousExamViewResponse);
		}

		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(responses);

		return itemResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 27-10-2020
	 * @param registrationId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse applicantPersonalInfoByRegistrationId(Long cmsId, String registrationId) {

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		AdmisiaApplicantInfo admisiaApplicantInfo = admisiaApplicantInfoRepository
				.findByCmsInfo_CmsIdAndRegistrationId(cmsId, registrationId);

		if (admisiaApplicantInfo == null) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("No applicant found with this " + registrationId + " Id");
			return itemResponse;
		}

		ApplicantPersonalViewResponse applicantPersonalViewResponse = copyApplicantPersonalViewResponse(
				admisiaApplicantInfo);

		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(applicantPersonalViewResponse);

		return itemResponse;
	}

	public ApplicantPersonalViewResponse applicantPersonalInfoByRegistrationIdForJasper(Long cmsId,
			String registrationId) {

		AdmisiaApplicantInfo admisiaApplicantInfo = admisiaApplicantInfoRepository
				.findByCmsInfo_CmsIdAndRegistrationId(cmsId, registrationId);

		ApplicantPersonalViewResponse applicantPersonalViewResponse = copyApplicantPersonalViewResponse(
				admisiaApplicantInfo);

		return applicantPersonalViewResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 15-11-2020
	 * @param cmsId
	 * @param registrationId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse applicantPersonalInfoByRegistrationIdForAdmin(Long cmsId, String registrationId) {

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		AdmisiaApplicantInfo admisiaApplicantInfo = admisiaApplicantInfoRepository
				.findByCmsInfo_CmsIdAndRegistrationIdAndApplicantFeeStatus(cmsId, registrationId,
						AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		if (admisiaApplicantInfo == null) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("No applicant found with this " + registrationId + " Id");
			return itemResponse;
		}

		ApplicantPersonalViewResponse applicantPersonalViewResponse = copyApplicantPersonalViewResponse(
				admisiaApplicantInfo);

		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(applicantPersonalViewResponse);

		return itemResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 28-10-2020
	 * @param cmsId
	 * @param mobileNo
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searchApplicantListByMobileNo(Long cmsId, String mobileNo) {

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		AdmisiaCoreConfig coreConfig = admisiaCoreConfigRepository.findByCmsInfo_CmsId(cmsId);

		List<AdmisiaApplicantInfo> applicantInfos = admisiaApplicantInfoRepository
				.findByMobileNoAndAcademicYearOrderByApplicantIdAsc(mobileNo, coreConfig.getCurrentAdmissionYear());

		if (applicantInfos.isEmpty()) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage(
					"This admission year No applicant found with this Mobile Number " + "(" + mobileNo + ")");
			return itemResponse;
		}

		List<ApplicantPersonalViewResponse> responses = new ArrayList<>();

		for (AdmisiaApplicantInfo info : applicantInfos) {
			ApplicantPersonalViewResponse applicantPersonalViewResponse = copyApplicantPersonalViewResponse(info);
			responses.add(applicantPersonalViewResponse);
		}

		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(responses);

		return itemResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 21-10-2020
	 * @param academicYear
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findApplicantAnalyticsReport(Integer academicYear, Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		List<Map<String, Object>> analyticalReport = new ArrayList<>();

		List<Object[]> list = admisiaApplicantInfoRepository.getApplicantAnalyticsReport(academicYear, cmsId);

		for (Object[] obj : list) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("class", obj[0]);
			map.put("group", obj[1]);
			map.put("totalApplicant", obj[2]);
			map.put("pendingAssessment", obj[3]);
			map.put("rejectedAssessment", obj[4]);
			map.put("approvedAssessment", obj[5]);
			map.put("pendingAdmission", obj[6]);
			map.put("waitingAdmission", obj[7]);
			map.put("rejectedAdmission", obj[8]);
			map.put("approvedAdmission", obj[9]);
			map.put("transferredAdmission", obj[10]);

			analyticalReport.add(map);
		}
		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(analyticalReport);

		return itemResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 21-10-2020
	 * @param academicYear
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findCollectionAnalyticalReport(Integer academicYear, Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		List<Map<String, Object>> collectionReport = new ArrayList<>();

		List<Object[]> list = admisiaApplicantInfoRepository.getApplicantCollectionReport(academicYear, cmsId);

		for (Object[] obj : list) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("class", obj[0]);
			map.put("group", obj[1]);
			map.put("totalApplicant", obj[2]);
			map.put("collectionAmount", obj[3]);
			map.put("serviceCharge", obj[4]);
			map.put("receiveableAmount", obj[5]);

			collectionReport.add(map);
		}
		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(collectionReport);

		return itemResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 29-10-2020
	 * @param academicYear
	 * @param classConfigId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findFeePaidApplicantList(Long cmsId, Integer academicYear, Long classConfigId) {

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		List<Map<String, Object>> paymentReport = new ArrayList<>();

		List<AdmisiaFeeTransactionLog> list = feeTransactionLogRepository
				.findByCmsInfo_CmsIdAndAdmisiaApplicantInfo_AcademicYearAndAdmisiaApplicantInfo_AdmisiaClassConfig_ClassConfigIdAndAdmisiaApplicantInfo_ApplicantFeeStatus(
						cmsId, academicYear, classConfigId, AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		for (AdmisiaFeeTransactionLog obj : list) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("registrationNo", obj.getAdmisiaApplicantInfo().getRegistrationId());
			map.put("name", obj.getAdmisiaApplicantInfo().getApplicantName());
			map.put("contractNo", obj.getAdmisiaApplicantInfo().getMobileNo());
			map.put("applicationDate", obj.getAdmisiaApplicantInfo().getApplicationDate());
			map.put("paymentDate", obj.getTransactionDate());
			map.put("paymentType", obj.getCardType());
			map.put("paymentAmount", obj.getAmount());
			map.put("applicationFee", obj.getApplicationFee());
			map.put("serviceCharge", obj.getServiceCharge());

			paymentReport.add(map);
		}
		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(paymentReport);

		return itemResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 01-11-2020
	 * @param cmsId
	 * @param academicYear
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse ataGlanceReportByAcademicYear(Long cmsId, Integer academicYear) {

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		List<AdmisiaApplicantInfo> applicantInfos = admisiaApplicantInfoRepository
				.findByCmsInfo_CmsIdAndAcademicYearAndApplicantFeeStatusOrderByApplicantIdAsc(cmsId, academicYear,
						AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode());

		if (applicantInfos.isEmpty()) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("No applicant found with this Academic Year " + "(" + academicYear + ")");
			return itemResponse;
		}

		List<Map<String, Object>> responses = new ArrayList<>();

		for (AdmisiaApplicantInfo info : applicantInfos) {

			Map<String, Object> map = new LinkedHashMap<>();

			map.put("applicantId", info.getApplicantId());
			map.put("registrationId", info.getRegistrationId());
			map.put("applicantName", info.getApplicantName());
			map.put("mobile", info.getMobileNo());
			map.put("dob", info.getDob());
			map.put("applicantStatus", info.getApplicantStatus());
			map.put("className", info.getAdmisiaClassConfig().getClassInfo().getClassName());
			map.put("groupName", info.getAdmisiaClassConfig().getGroupInfo().getGroupName());
			if (info.getApplicantPhoto() != null) {
				try {
					map.put("fileName", info.getApplicantPhoto());
					map.put("fileContent",
							fileUtilService.fetchFileInByte(Folder.MEMBER.name(), info.getApplicantPhoto()));
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			responses.add(map);
		}

		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(responses);

		return itemResponse;
	}

	@Transactional
	public BaseResponse admisiaMarkInput(MarkInputRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		List<Long> applicantIds = new ArrayList<>();

		for (MarkInputRequestHelper helper : request.getDetails()) {
			applicantIds.add(helper.getApplicantId());
		}

		List<AdmisiaApplicantInfo> admisiaApplicantInfos = admisiaApplicantInfoRepository
				.findByCmsInfo_CmsIdAndApplicantIdIn(request.getCmsId(), applicantIds);

		for (AdmisiaApplicantInfo info : admisiaApplicantInfos) {

			for (MarkInputRequestHelper helper : request.getDetails()) {

				if (info.getApplicantId().equals(helper.getApplicantId())) {

					info.setExamMarks(helper.getExamMarks());
					info.setExamResult(helper.getExamResult());
					info.setStatusUpdateDate(new Date());

					if (info.getApplicantFeeStatus() == 1 && info.getAdmisiaClassConfig().getAdmissionExamStatus() == 1
							&& info.getAdmisiaClassConfig().getAutoApproveStatus() == 1) {
						info.setApplicantStatus(AdmisiaApplicantStatusEnum.APPROVED_FOR_ADMISSION.getCode());
					}

					break;
				}
			}

		}

		baseResponse.setMessageType(1);
		baseResponse.setMessage("Mark Successfully Inputted.");

		return baseResponse;
	}

}
