package com.netizen.cms.api.jasper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.jasper.service.NetiCmsJasperDownloadService;

@RestController
@RequestMapping(value = "/neti-cms/download")
public class NetiCmsJasperDownloadController {
	@Autowired
	public NetiCmsJasperDownloadService cmsJasperDownloadService;
	
	
	@GetMapping(value = "/individual-result")
	public ResponseEntity<BaseResponse> individualStudentResultDownload(@RequestParam Long cmsId, @RequestParam String registrationId) throws Exception {
	    return new ResponseEntity<>(cmsJasperDownloadService.findApplicantInfo(cmsId, registrationId), HttpStatus.OK);
	}

}