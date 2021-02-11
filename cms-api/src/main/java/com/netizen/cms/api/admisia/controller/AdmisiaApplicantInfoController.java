package com.netizen.cms.api.admisia.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.admisia.model.request.AdmisiaApplicantRegistrationRequest;
import com.netizen.cms.api.admisia.model.request.AdmisiaApplicantStatusUpdateRequest;
import com.netizen.cms.api.admisia.model.request.MarkInputRequest;
import com.netizen.cms.api.admisia.service.AdmisiaApplicantInfoService;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;

@RestController
@RequestMapping(value = "/admisia/applicant")
public class AdmisiaApplicantInfoController {
	
	@Autowired
	private AdmisiaApplicantInfoService admisiaApplicantInfoService;
	
	
	/**
	 * @author riaz_netizen
	 * @since 04-10-2020
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<?> saveApplicantInfo(@RequestBody @Valid AdmisiaApplicantRegistrationRequest request){		
		ItemResponse<?> itemResponse=admisiaApplicantInfoService.saveApplicantInfo(request);
		return new ResponseEntity<>(itemResponse,HttpStatus.CREATED);
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
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/assessment/list")
	public ResponseEntity<ItemResponse> searPendingOrRejectedOrApproveAssessmentList(@RequestParam Long cmsId, @RequestParam Long classId,
			@RequestParam Long groupId, @RequestParam String assessmentType) {
		ItemResponse itemResponse = admisiaApplicantInfoService.searPendingOrRejectedOrApprovedAssessmentList(cmsId,
				classId, groupId, assessmentType);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 10-11-2020
	 * @param cmsId
	 * @param classId
	 * @param groupId
	 * @param approvalType
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/approval/list")
	public ResponseEntity<ItemResponse> searPendingOrWaitingOrRejectedOrApprovedOrTransferredForAdmissionList(@RequestParam Long cmsId, @RequestParam Long classId,
			@RequestParam Long groupId, @RequestParam String approvalType) {
		ItemResponse itemResponse = admisiaApplicantInfoService.searPendingOrWaitingOrRejectedOrApprovedOrTransferredForAdmissionList(cmsId, classId, groupId, approvalType);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 28-10-2020
	 * @param cmsId
	 * @param classConfigId
	 * @param applicantStatus
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/status-wise/list")
	public ResponseEntity<ItemResponse> searchStatusWiseApplicantList(@RequestParam Long cmsId, @RequestParam Long classConfigId,@RequestParam int applicantStatus) {
		ItemResponse itemResponse = admisiaApplicantInfoService.searchApplicantListByStatus(cmsId, classConfigId, applicantStatus);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 18-10-2020
	 * @param statusUpdateRequest
	 * @return
	 */
	@PutMapping(value = "/update/applicants-status")
	public ResponseEntity<BaseResponse> updateServiceCharge(@RequestBody @Valid AdmisiaApplicantStatusUpdateRequest statusUpdateRequest) {
		BaseResponse baseResponse = admisiaApplicantInfoService.updateApplicantStatus(statusUpdateRequest);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 20-10-2020
	 * @param registrationId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/details-info/by-registrationId")
	public ResponseEntity<ItemResponse> applicantInfoByRegistrationId(@RequestParam Long cmsId,@RequestParam String registrationId) {		
		ItemResponse itemResponse = admisiaApplicantInfoService.applicantDetailsInfoByRegistrationIdForAdmin(cmsId, registrationId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 27-10-2020
	 * @param registrationId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/personal-info/by-registrationId")
	public ResponseEntity<ItemResponse> applicantPersonalInfoByRegistrationId(@RequestParam Long cmsId,@RequestParam String registrationId) {		
		ItemResponse itemResponse = admisiaApplicantInfoService.applicantPersonalInfoByRegistrationIdForAdmin(cmsId,registrationId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 21-10-2020
	 * @param academicYear
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "list/analytical/by-academicYear")
	public ResponseEntity<ItemResponse> findApplicantAnalyticsReport(@RequestParam Integer academicYear,@RequestParam Long cmsId) {		
		ItemResponse itemResponse = admisiaApplicantInfoService.findApplicantAnalyticsReport(academicYear, cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}

	
	/**
	 * @author riaz_netizen
	 * @since 21-10-2020
	 * @param academicYear
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "list/collection/analytics/by-academicYear")
	public ResponseEntity<ItemResponse> findcollectionanAlyticalReport(@RequestParam Integer academicYear,@RequestParam Long cmsId) {		
		ItemResponse itemResponse = admisiaApplicantInfoService.findCollectionAnalyticalReport(academicYear, cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 29-10-2020
	 * @param academicYear
	 * @param classConfigId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "paid/applicants/by-academicYear")
	public ResponseEntity<ItemResponse> findFeePaidApplicantsReport(@RequestParam Long cmsId,@RequestParam Integer academicYear,@RequestParam Long classConfigId) {		
		ItemResponse itemResponse = admisiaApplicantInfoService.findFeePaidApplicantList(cmsId,academicYear, classConfigId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 01-11-2020
	 * @param cmsId
	 * @param academicYear
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "ata/glance/by-academicYear")
	public ResponseEntity<ItemResponse> findAtaGlanceReportByAcademicYear(@RequestParam Long cmsId,@RequestParam Integer academicYear) {		
		ItemResponse itemResponse = admisiaApplicantInfoService.ataGlanceReportByAcademicYear(cmsId, academicYear);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = "/mark/input")
	public ResponseEntity<BaseResponse> inputMarkInfo(@RequestBody @Valid MarkInputRequest request){		
		BaseResponse baseResponse=admisiaApplicantInfoService.admisiaMarkInput(request);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 24-12-2020
	 * @param cmsId
	 * @param classConfigId
	 * @param academicYear
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/previous-exams-details")
	public ResponseEntity<ItemResponse> findApplicantPreviousExamDetails(@RequestParam Long cmsId,@RequestParam Long classConfigId,@RequestParam Integer academicYear) {		
		ItemResponse itemResponse = admisiaApplicantInfoService.applicantPeviousExamInfoDetails(cmsId,classConfigId, academicYear);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
}
