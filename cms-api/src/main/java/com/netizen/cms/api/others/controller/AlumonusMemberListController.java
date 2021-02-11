package com.netizen.cms.api.others.controller;

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

import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.others.model.request.AlumonusMemberListSaveRequest;
import com.netizen.cms.api.others.model.request.AlumonusMemberListUpdateRequest;
import com.netizen.cms.api.others.service.AlumonusMemberListService;

@RestController
@RequestMapping(value = "/alumonus/member")
public class AlumonusMemberListController {

	@Autowired
	public AlumonusMemberListService alumonusMemberListService;

	/**
	 * @author riaz_netizen
	 * @since 27-09-2020
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveAlumonusMember(@RequestBody @Valid AlumonusMemberListSaveRequest request) {
		BaseResponse baseResponse = alumonusMemberListService.saveAlumonusMembers(request);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * @author riaz_netizen 27-09-2020
	 * @param updateRequest
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateAlumonusMember(
			@RequestBody @Valid AlumonusMemberListUpdateRequest updateRequest) {
		BaseResponse baseResponse = alumonusMemberListService.updateAlumonusMembers(updateRequest);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}
	

	/**
	 * @author riaz_netizen
	 * @since 27-09-2020
	 * @param alumonusMemberId
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BaseResponse> deleteAlumonusMember(@RequestParam Long alumonusMemberId) {
		BaseResponse baseResponse = alumonusMemberListService.deleteAlumonusMembers(alumonusMemberId);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}
	

	/**
	 * @author riaz_netizen
	 * @since 27-09-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> AlumonusMemberList(@RequestParam Long cmsId) {
		ItemResponse itemResponse = alumonusMemberListService.searchAlumonusMembers(cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}

}
