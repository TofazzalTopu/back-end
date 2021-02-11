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

import com.netizen.cms.api.admisia.model.request.AdmisiaCoreConfigSaveRequest;
import com.netizen.cms.api.admisia.model.request.AdmisiaCoreConfigUpdateRequest;
import com.netizen.cms.api.admisia.model.request.ServiceChargeUpdateRequest;
import com.netizen.cms.api.admisia.service.AdmisiaCoreConfigService;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;

@RestController
@RequestMapping(value = "/admisia/core/config")
public class AdmisiaCoreConfigController {

	@Autowired
	private AdmisiaCoreConfigService admisiaCoreConfigService;
	
	/**
	 * @author riaz_netizen
	 * @since 28-09-2020
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveAdmisiaCoreConfig(@RequestBody @Valid AdmisiaCoreConfigSaveRequest request) {
		BaseResponse baseResponse = admisiaCoreConfigService.saveCoreConfig(request);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 28-09-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/find")
	public ResponseEntity<ItemResponse> findCoreConfigByCmsId(@RequestParam Long cmsId){
		return new ResponseEntity<>(admisiaCoreConfigService.findCoreConfigByCmsId(cmsId), HttpStatus.OK);
	}
	

	/**
	 * @author riaz_netizen
	 * @since 28-09-2020
	 * @param updateRequest
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateAdmisiaCoreConfig(@RequestBody @Valid AdmisiaCoreConfigUpdateRequest updateRequest) {
		BaseResponse baseResponse = admisiaCoreConfigService.updateCoreConfig(updateRequest);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 10-07-2020
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> coreConfigListForNetiworldAdmin() {
		ItemResponse itemResponse = admisiaCoreConfigService.findAllConfigList();
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @param chargeUpdateRequest
	 * @return
	 */
	@PutMapping(value = "/update/service-charge")
	public ResponseEntity<BaseResponse> updateServiceCharge(@RequestBody @Valid ServiceChargeUpdateRequest chargeUpdateRequest) {
		BaseResponse baseResponse = admisiaCoreConfigService.updateServiceCharge(chargeUpdateRequest);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}
}
