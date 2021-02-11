package com.netizen.cms.api.cmsinfo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.cmsinfo.model.request.CmsEdumanMappingSaveRequest;
import com.netizen.cms.api.cmsinfo.model.request.CmsEdumanMappingUpdateRequest;
import com.netizen.cms.api.cmsinfo.service.CmsEdumanMappingService;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;

@RestController
@RequestMapping("/cms-eduman-mapping")
public class CmsEdumanMappingController {
	@Autowired
	private CmsEdumanMappingService cmsEdumanMappingService;
	
	
	/**
	 * @author riaz_netizen
	 * @since 25-08-2020
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<?> saveCmsInfo(@RequestBody @Valid CmsEdumanMappingSaveRequest request){
		
		ItemResponse<?> itemResponse=cmsEdumanMappingService.saveCmsEdumanMapping(request);
		return new ResponseEntity<>(itemResponse,HttpStatus.CREATED);
	}
	
	
	
	/**
	 * @author riaz_netizen
	 * @since 25-08-2020
	 * @param instituteId
	 * @param cmsId
	 * @return
	 */
	@GetMapping(value ="/check-institute/existence")
	public ResponseEntity<Boolean> checkUrlInfo( @RequestParam(value = "instituteId") String instituteId,@RequestParam(value = "cmsId") Long cmsId) {
	        Boolean status = cmsEdumanMappingService.checkMappingInstituteExistence(instituteId, cmsId);
	        return new ResponseEntity<>(status, status ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
	    }
	
	/**
	 * @author riaz_netizen
	 * @since 24-08-2020
	 * @param request
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> approvedCmsEdumanMapping(@RequestBody @Valid CmsEdumanMappingUpdateRequest request){
		BaseResponse baseResponse=cmsEdumanMappingService.approvedCmsMapping(request);
		return new ResponseEntity<>(baseResponse,HttpStatus.ACCEPTED);	
	}
	
	/**
	 * @author riaz_netizen
	 * @since 24-08-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> approvedMappingInstituteList(@RequestParam Long cmsId){
		ItemResponse<?> itemResponse=cmsEdumanMappingService.approvedMappingInstituteList(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 24-08-2020
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "pending/cms-institute/list")
	public ResponseEntity<ItemResponse> pendingInstitutesForMapping(){
		ItemResponse<?> itemResponse=cmsEdumanMappingService.pendingCmsInstituteMappingList();
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
	
	
	
}
