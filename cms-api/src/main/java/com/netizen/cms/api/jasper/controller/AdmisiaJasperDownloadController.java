package com.netizen.cms.api.jasper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.jasper.service.AdmisiaJasperDownloadService;



@RestController
@RequestMapping(value = "/admisia/download")
public class AdmisiaJasperDownloadController {
	
	@Autowired
	public AdmisiaJasperDownloadService admisiaJasperDownloadService;
	
	
	@GetMapping(value = "/admit-card")
	public ResponseEntity<BaseResponse> moneyReceiptDownload(@RequestParam Long cmsId,@RequestParam List<String> registrationIds) {
	    return new ResponseEntity<>(admisiaJasperDownloadService.downloadAdmitCard(cmsId, registrationIds), HttpStatus.OK);
	}

}
