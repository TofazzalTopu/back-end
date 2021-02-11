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

import com.netizen.cms.api.academic.model.request.ClassInfoCreateRequest;
import com.netizen.cms.api.academic.model.request.ClassInfoUpdateRequest;
import com.netizen.cms.api.academic.service.ClassInfoService;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;


@RestController
@RequestMapping(value = "/class-info")
public class ClassInfoController {
	
	
	@Autowired
	public ClassInfoService classInfoService;
	
	
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveClassInfo(@RequestBody @Valid ClassInfoCreateRequest request){
		BaseResponse baseResponse=classInfoService.saveClassInfo(request);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateClassInfo(@RequestBody @Valid ClassInfoUpdateRequest request){
		BaseResponse baseResponse=classInfoService.updateClassInfo(request);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BaseResponse> deleteClassInfo(@RequestParam Long classId){
		BaseResponse baseResponse=classInfoService.deleteClassInfo(classId);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> classInfoList(@RequestParam Long cmsId){
		ItemResponse itemResponse=classInfoService.classInfoList(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}

}
