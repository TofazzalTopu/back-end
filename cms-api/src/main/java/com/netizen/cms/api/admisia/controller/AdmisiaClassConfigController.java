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

import com.netizen.cms.api.admisia.model.request.AdmisiaClassConfigSaveRequest;
import com.netizen.cms.api.admisia.model.request.AdmisiaClassConfigUpdateRequest;
import com.netizen.cms.api.admisia.service.AdmisiaClassConfigService;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;

@RestController
@RequestMapping(value = "/admisia/class/config")
public class AdmisiaClassConfigController {
	@Autowired
	private AdmisiaClassConfigService admisiaClassConfigService;
	

	/**
	 * @author riaz_netizen
	 * @since 01-10-2020
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveAdmisiaClassConfig(@RequestBody @Valid AdmisiaClassConfigSaveRequest request) {
		BaseResponse baseResponse = admisiaClassConfigService.saveClassConfig(request);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}
	
	

	/**
	 * @author riaz_netizen
	 * @since 01-10-2020
	 * @param updateRequest
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateAdmisiaClassConfig(@RequestBody @Valid AdmisiaClassConfigUpdateRequest updateRequest) {
		BaseResponse baseResponse = admisiaClassConfigService.updateClassConfig(updateRequest);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 01-10-2020
	 * @param admisiaId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> classConfigList(@RequestParam Long cmsId) {
		ItemResponse itemResponse = admisiaClassConfigService.searClassConfigList(cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 10-06-2020
	 * @param cmsId
	 * @return
	 */
	@GetMapping(value = "/service-charge")
	public ResponseEntity<Double> getNetizenServiceChargeAmount(@RequestParam Long cmsId) {
		Double serviceAmount = admisiaClassConfigService.findServiceChargeAmount(cmsId);
		return new ResponseEntity<>(serviceAmount, HttpStatus.OK);
	}

	
	/**
	 * @author riaz_netizen
	 * @since 18-10-2020
	 * @param cmsId
	 * @param classId
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/info")
	public ResponseEntity<ItemResponse> classConfigInfo(@RequestParam Long cmsId,@RequestParam Long classId,@RequestParam Long groupId) {
		ItemResponse itemResponse = admisiaClassConfigService.findClassConfigInfo(cmsId, classId, groupId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 25-10-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/info/list")
	public ResponseEntity<ItemResponse> classConfigInfoList(@RequestParam Long cmsId) {
		ItemResponse itemResponse = admisiaClassConfigService.findClassConfigInfoList(cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
}
