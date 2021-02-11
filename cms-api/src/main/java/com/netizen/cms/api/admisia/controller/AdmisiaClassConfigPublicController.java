package com.netizen.cms.api.admisia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.admisia.service.AdmisiaClassConfigService;
import com.netizen.cms.api.common.response.ItemResponse;

@RestController
@RequestMapping(value = "/public/admisia/class-config")
public class AdmisiaClassConfigPublicController {
	
	@Autowired
	private AdmisiaClassConfigService admisiaClassConfigService;
	
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> classConfigList(@RequestParam Long cmsId) {
		ItemResponse itemResponse = admisiaClassConfigService.admisiaClassConfigPublicView(cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}

}
