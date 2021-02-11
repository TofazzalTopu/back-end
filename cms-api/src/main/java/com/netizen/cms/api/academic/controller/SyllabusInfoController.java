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
import com.netizen.cms.api.academic.model.request.SyllabusInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.SyllabusInfoUpdateRequest;
import com.netizen.cms.api.academic.service.SyllabusInfoService;
import com.netizen.cms.api.common.response.BaseResponse;


@RestController
@RequestMapping("/syllabus-info")
public class  SyllabusInfoController {
	
	@Autowired
	private SyllabusInfoService syllabusInfoService;
	
	
	@PostMapping("/save")
	public ResponseEntity<BaseResponse> saveSyllabusInfo(@RequestBody @Valid SyllabusInfoSaveRequest syllabusInfoSaveRequest){
		
		BaseResponse response =  syllabusInfoService.saveSyllabusInfo(syllabusInfoSaveRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<BaseResponse> findSyllabusInfos(@RequestParam Long cmsId){

		BaseResponse response =  syllabusInfoService.findSyllabusInfos(cmsId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<BaseResponse> updatedressInfo(@RequestBody @Valid SyllabusInfoUpdateRequest syllabusInfoUpdateRequest){
		
		BaseResponse response =  syllabusInfoService.updateSyllabusInfo(syllabusInfoUpdateRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<BaseResponse> deleteSyllabusInfo(@RequestParam Long syllabusId){
		
		BaseResponse response =  syllabusInfoService.deleteSyllabusInfo(syllabusId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/file/update")
	public ResponseEntity<BaseResponse> updateSyllabusFile(@RequestParam Long syllabusId,@RequestParam MultipartFile file){
		BaseResponse baseResponse=syllabusInfoService.updateSyllabusFile(syllabusId, file);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	

	@GetMapping(value = "/file/find")
	public ResponseEntity<?> findSyllabusFile(@RequestParam Long syllabusId){
		return new ResponseEntity<>(syllabusInfoService.findSyllabusFile(syllabusId),HttpStatus.FOUND);
	}



}
