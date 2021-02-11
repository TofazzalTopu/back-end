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
import org.springframework.web.multipart.MultipartFile;
import com.netizen.cms.api.academic.model.request.DressInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.DressInfoUpdateRequest;
import com.netizen.cms.api.academic.service.DressInfoService;
import com.netizen.cms.api.common.response.BaseResponse;


@RestController
@RequestMapping("/dress-info")
public class  DressInfoController {
	
	@Autowired
	private DressInfoService dressInfoService;
	
	
	@PostMapping("/save")
	public ResponseEntity<BaseResponse> savedressInfo(@RequestBody @Valid DressInfoSaveRequest dressInfoSaveRequest){
		
		BaseResponse response =  dressInfoService.saveDressInfo(dressInfoSaveRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<BaseResponse> finddressInfos(@RequestParam Long cmsId){

		BaseResponse response =  dressInfoService.findDressInfos(cmsId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("/list/with/image")
	public ResponseEntity<BaseResponse> finddressInfosWithImage(@RequestParam Long cmsId){
		
		BaseResponse response =  dressInfoService.findDressInfosWithImage(cmsId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<BaseResponse> updatedressInfo(@RequestBody @Valid DressInfoUpdateRequest dressInfoUpdateRequest){
		
		BaseResponse response =  dressInfoService.updateDressInfo(dressInfoUpdateRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<BaseResponse> deletedressInfo(@RequestParam Long dressId){
		
		BaseResponse response =  dressInfoService.deleteDressInfo(dressId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	@PutMapping(value = "/image/update")
	public ResponseEntity<BaseResponse> updateSyllabusFile(@RequestParam Long dressInfoId,@RequestParam MultipartFile file){
		BaseResponse baseResponse=dressInfoService.updateDressImage(dressInfoId, file);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	

	@GetMapping(value = "/image/find")
	public ResponseEntity<?> findSyllabusFile(@RequestParam Long dressInfoId){
		return new ResponseEntity<>(dressInfoService.findDressImage(dressInfoId),HttpStatus.FOUND);
	}
}
