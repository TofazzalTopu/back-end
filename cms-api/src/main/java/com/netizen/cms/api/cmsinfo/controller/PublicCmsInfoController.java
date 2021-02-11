package com.netizen.cms.api.cmsinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.cmsinfo.service.CmsInfoService;
import com.netizen.cms.api.common.response.ItemResponse;

@RestController
@RequestMapping(value = "/public/cms-info")
public class PublicCmsInfoController {
	
	@Autowired
	public CmsInfoService cmsInfoService;
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/find/{url}")
	public ResponseEntity<ItemResponse> findCmsInfo(@PathVariable("url") String url) {
		ItemResponse itemResponse=cmsInfoService.findCmsInfoByUrl(url);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/url/list")
	public ResponseEntity<ItemResponse> searchUrlList() {
		ItemResponse itemResponse=cmsInfoService.searchUrlList();
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}

}
