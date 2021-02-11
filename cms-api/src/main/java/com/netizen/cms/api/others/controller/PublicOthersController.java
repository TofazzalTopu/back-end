package com.netizen.cms.api.others.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.others.service.AlumonusMemberListService;

@RestController
@RequestMapping("/public")
public class PublicOthersController {

	@Autowired
	public AlumonusMemberListService alumonusMemberListService;

	
	/**
	 * @author riaz_netizen
	 * @since 28-09-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/alumonus/members")
	public ResponseEntity<ItemResponse> alumonusMemberList(@RequestParam Long cmsId){
		ItemResponse itemResponse=alumonusMemberListService.searchAlumonusMembers(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
}
