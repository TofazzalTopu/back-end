package com.netizen.cms.api.academic.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.netizen.cms.api.academic.model.request.FeeInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.FeeInfoUpdateRequest;
import com.netizen.cms.api.academic.service.FeeInfoService;
import com.netizen.cms.api.common.response.BaseResponse;


@RestController
@RequestMapping("/fee-info")
public class  FeeInfoController {
	
	@Autowired
	private FeeInfoService feeInfoService;
	
	
	@PostMapping("/save")
	public ResponseEntity<BaseResponse> saveFeeInfo(@RequestBody @Valid FeeInfoSaveRequest feeInfoSaveRequest){
		
		BaseResponse response =  feeInfoService.saveFeeInfo(feeInfoSaveRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<BaseResponse> findFeeInfos(@RequestParam Long cmsId){
		BaseResponse response =  feeInfoService.findFeeInfos(cmsId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<BaseResponse> updatedressInfo(@RequestBody @Valid FeeInfoUpdateRequest feeInfoUpdateRequest){
		
		BaseResponse response =  feeInfoService.updateFeeInfo(feeInfoUpdateRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<BaseResponse> deleteFeeInfo(@RequestParam Long feeId){
		
		BaseResponse response =  feeInfoService.deleteFeeInfo(feeId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}


}
