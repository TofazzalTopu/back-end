package com.netizen.cms.api.admisia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.admisia.model.response.ApplicantPersonalViewResponse;
import com.netizen.cms.api.admisia.service.AdmisiaApplicantInfoService;
import com.netizen.cms.api.common.response.ItemResponse;

@RestController
@RequestMapping(value = "/public/applicant")
public class AdmisiaApplicantInfoPublicController {
	@Autowired
	private AdmisiaApplicantInfoService admisiaApplicantInfoService;

	/**
	 * @author riaz_netizen
	 * @since 20-10-2020
	 * @param registrationId
	 * @return
	 */	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/details-info/by-registrationId")
	public ResponseEntity<ItemResponse> applicantInfoByRegistrationId(@RequestParam Long cmsId,@RequestParam String registrationId) {		
		ItemResponse itemResponse = admisiaApplicantInfoService.applicantDetailsInfoByRegistrationId(cmsId,registrationId);
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
		ItemResponse itemResponse = admisiaApplicantInfoService.applicantPersonalInfoByRegistrationId(cmsId,registrationId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	

	@GetMapping(value = "/personal-info/by-registrationId/jasper")
	public ResponseEntity<?> applicantPersonalInfoByRegistrationIdForJasper(@RequestParam Long cmsId,@RequestParam String registrationId) {		
		ApplicantPersonalViewResponse viewResponse = admisiaApplicantInfoService.applicantPersonalInfoByRegistrationIdForJasper(cmsId,registrationId);
		return new ResponseEntity<>(viewResponse, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 28-10-2020
	 * @param cmsId
	 * @param mobileNo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/info/by-mobileNo")
	public ResponseEntity<ItemResponse> applicantListByMobileNo(@RequestParam Long cmsId,@RequestParam String mobileNo) {		
		ItemResponse itemResponse = admisiaApplicantInfoService.searchApplicantListByMobileNo(cmsId, mobileNo);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
}
