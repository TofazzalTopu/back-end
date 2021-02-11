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
import com.netizen.cms.api.academic.model.request.GroupInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.GroupInfoUpdateRequest;
import com.netizen.cms.api.academic.service.GroupInfoService;
import com.netizen.cms.api.common.response.BaseResponse;


@RestController
@RequestMapping("/group-info")
public class GroupInfoController {
	
	@Autowired
	private GroupInfoService groupInfoService;
	
	
	@PostMapping("/save")
	public ResponseEntity<BaseResponse> saveGroupInfo(@RequestBody @Valid GroupInfoSaveRequest groupInfoSaveRequest){
		
		BaseResponse response =  groupInfoService.saveGroupInfo(groupInfoSaveRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<BaseResponse> findGroupInfos(@RequestParam Long cmsId){
		
		BaseResponse response =  groupInfoService.findGroupInfos(cmsId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<BaseResponse> updateGroupInfo(@RequestBody @Valid GroupInfoUpdateRequest groupInfoUpdateRequest){
		
		BaseResponse response =  groupInfoService.updateGroupInfo(groupInfoUpdateRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<BaseResponse> deleteGroupInfo(@RequestParam Long groupId){
		
		BaseResponse response =  groupInfoService.deleteGroupInfo(groupId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
}
