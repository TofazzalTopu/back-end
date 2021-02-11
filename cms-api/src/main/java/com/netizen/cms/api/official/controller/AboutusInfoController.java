package com.netizen.cms.api.official.controller;

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

import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.official.model.request.AboutusInfoSaveRequest;
import com.netizen.cms.api.official.model.request.AboutusInfoUpdateRequest;
import com.netizen.cms.api.official.service.AboutusInfoService;

@RestController
@RequestMapping("/aboutusinfo")
public class AboutusInfoController {

	@Autowired
	private AboutusInfoService aboutusInfoService;
	
	
	@PostMapping("/save")
	public ResponseEntity<BaseResponse> saveAboutusInfo(@RequestBody @Valid AboutusInfoSaveRequest aboutusInfoSaveRequest){
		
		BaseResponse response =  aboutusInfoService.saveAboutusInfo(aboutusInfoSaveRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/list")
	public ResponseEntity<ItemResponse> findAboutusInfos(@RequestParam Long cmsId){
		ItemResponse response =  aboutusInfoService.findAboutusInfos(cmsId);		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<BaseResponse> updatedressInfo(@RequestBody @Valid AboutusInfoUpdateRequest aboutusInfoUpdateRequest){
		
		BaseResponse response =  aboutusInfoService.updateAboutusInfo(aboutusInfoUpdateRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<BaseResponse> deleteAboutusInfo(@RequestParam Long aboutusId){
		
		BaseResponse response =  aboutusInfoService.deleteAboutusInfo(aboutusId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	@PutMapping(value = "/image/update")
	public ResponseEntity<BaseResponse> updateSyllabusFile(@RequestParam Long dressInfoId,@RequestParam MultipartFile file){
		BaseResponse baseResponse=aboutusInfoService.updateAboutusImage(dressInfoId, file);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	
	@GetMapping("/aboutus/by/type")
	public ResponseEntity<BaseResponse> findAboutusInfoByType(@RequestParam Long cmsId, @RequestParam String aboutusType){
	
		BaseResponse response =  aboutusInfoService.findAboutusInfoByType(cmsId, aboutusType);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}

}
