package com.netizen.cms.api.gallery.controller;

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
import com.netizen.cms.api.gallery.model.request.ImportantLinkInfoSaveRequest;
import com.netizen.cms.api.gallery.model.request.ImportantLinkInfoUpdateRequest;
import com.netizen.cms.api.gallery.service.ImportantLinkInfoService;

@RestController
@RequestMapping(value = "/importantLink/info")
public class ImportantLinkInfoController {
	@Autowired
	public ImportantLinkInfoService importantLinkInfoService ;
	
	
	/**
	 * @author riaz_netizen
	 * @since 30-06-2020
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveImportantLinkInfo(@RequestBody @Valid ImportantLinkInfoSaveRequest request){
		BaseResponse baseResponse=importantLinkInfoService.saveImportantLinkInfo(request);
		return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 30-06-2020
	 * @param updateRequest
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateImportantLinkInfo(@RequestBody @Valid ImportantLinkInfoUpdateRequest updateRequest){
		BaseResponse baseResponse=importantLinkInfoService.updateImportantLinkInfo(updateRequest);
		return new ResponseEntity<>(baseResponse,HttpStatus.ACCEPTED);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 30-06-2020
	 * @param linkId
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BaseResponse> deleteImportantLinkInfo(@RequestParam Long linkId){
		BaseResponse baseResponse=importantLinkInfoService.deleteImportantLinkInfo(linkId);
		return new ResponseEntity<>(baseResponse,HttpStatus.ACCEPTED);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 30-06-2020
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> importantLinkInfoList(@RequestParam Long cmsId){
		ItemResponse itemResponse=importantLinkInfoService.searchImportantLinkInfoList(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
}
