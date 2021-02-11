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
import com.netizen.cms.api.official.model.request.MemberInfoSaveRequest;
import com.netizen.cms.api.official.model.request.MemberInfoUpdateRequest;
import com.netizen.cms.api.official.service.MemberInfoService;

@RestController
@RequestMapping(value = "/member/info")
public class MemberInfoController {
	
	@Autowired
	public MemberInfoService memberInfoService;
	
	
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveMemberInfo(@RequestBody @Valid MemberInfoSaveRequest request){
		BaseResponse baseResponse=memberInfoService.saveMemberInfo(request);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateMemberInfo(@RequestBody @Valid MemberInfoUpdateRequest request){
		BaseResponse baseResponse=memberInfoService.updateMemberInfo(request);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/photo/update")
	public ResponseEntity<BaseResponse> updateMemberPhoto(@RequestParam Long memberId,@RequestParam MultipartFile file){
		BaseResponse baseResponse=memberInfoService.updateSingleMemberPhoto(memberId, file);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BaseResponse> deleteMemberInfo(@RequestParam Long memberId){
		BaseResponse baseResponse=memberInfoService.deleteMemberInfo(memberId);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> memberInfoList(@RequestParam Long cmsId){
		ItemResponse itemResponse=memberInfoService.searchMemberInfoList(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list/with-photo")
	public ResponseEntity<ItemResponse> memberInfoListWithPhoto(@RequestParam Long cmsId){
		ItemResponse itemResponse=memberInfoService.searchMemberInfoListWithPhoto(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}

}
