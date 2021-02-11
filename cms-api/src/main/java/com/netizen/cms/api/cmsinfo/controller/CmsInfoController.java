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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.cmsinfo.model.request.CmsInfoCreateRequest;
import com.netizen.cms.api.cmsinfo.model.request.CmsInfoUpdateRequest;
import com.netizen.cms.api.cmsinfo.model.request.QueryRequest;
import com.netizen.cms.api.cmsinfo.service.CmsInfoService;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;

@RestController
@RequestMapping("/cms-info")
public class CmsInfoController {
	
	@Autowired
	private CmsInfoService cmsInfoService;
	
	/**
	 * @author riaz_netizen
	 * @since 12-08-2020
	 * @param urlName
	 * @return
	 */
	@GetMapping(value ="/check-url/existence")
	public ResponseEntity<?> checkUrlInfo( @RequestParam(value = "urlName") String urlName) {
	        Boolean status = cmsInfoService.checkIfExistUrlName(urlName);
	        return new ResponseEntity<>(status, status ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
	    }
	
	
    @RequestMapping(value = "/execute/query", method = RequestMethod.POST)
    public ResponseEntity<ItemResponse> queryExecute(@RequestBody QueryRequest queryRequest) {
        ItemResponse response =cmsInfoService.queryExecute(queryRequest.getQuery(), queryRequest.getType(),queryRequest.getSecrate());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
	
	/**
	 * @author riaz_netizen
	 * @since 12-08-2020
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveCmsInfo(@RequestBody @Valid CmsInfoCreateRequest request){
		BaseResponse baseResponse=cmsInfoService.saveCmsInfo(request);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 13-08-2020
	 * @param userRoleAssignID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/find/urlInfo")
	public ResponseEntity<ItemResponse> findCmsInfo(@RequestParam(value = "userRoleAssignId") Long userRoleAssignId) {
		ItemResponse itemResponse=cmsInfoService.showNewlyCreatedCmsInfo(userRoleAssignId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 13-08-2020
	 * @param updateRequest
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateCmsInfo(@RequestBody @Valid CmsInfoUpdateRequest updateRequest) {
		BaseResponse baseResponse = cmsInfoService.updateCmsInfo(updateRequest);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}
	
	/**
	 * @author Mohammad Riaz 
	 * @since 07-01-2020
	 * @param customCmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/find/by-customCmsId")
	public ResponseEntity<ItemResponse> findCmsInfoByCustomCmsId(@RequestParam(value = "customCmsId") Long customCmsId) {
		ItemResponse itemResponse=cmsInfoService.findByCustomCmsId(customCmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}

}
