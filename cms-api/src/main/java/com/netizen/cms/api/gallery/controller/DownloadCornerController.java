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
import org.springframework.web.multipart.MultipartFile;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.gallery.model.request.DownloadCornerSaveRequest;
import com.netizen.cms.api.gallery.model.request.DownloadCornerUpdateRequest;
import com.netizen.cms.api.gallery.service.DownloadCornerService;

@RestController
@RequestMapping(value = "/download/corner")
public class DownloadCornerController {

	@Autowired
	public DownloadCornerService downloadCornerService;

	/**
	 * @author riaz_netizen
	 * @since 01-07-2020
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveDownloadCorner(@RequestBody @Valid DownloadCornerSaveRequest request) {
		BaseResponse baseResponse = downloadCornerService.saveDownloadCorner(request);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * @author riaz_netizen
	 * @since 01-07-2020
	 * @param updateRequest
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateDownloadCorner(
			@RequestBody @Valid DownloadCornerUpdateRequest updateRequest) {
		BaseResponse baseResponse = downloadCornerService.updateDownloadCorner(updateRequest);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}

	/**
	 * @author riaz_netizen
	 * @since 06-07-2020
	 * @param fileId
	 * @param file
	 * @return
	 *//*
		 * @PostMapping(value = "/photo/update") public ResponseEntity<BaseResponse>
		 * updateDownloadCornerFile(@RequestParam Long fileId,@RequestParam
		 * MultipartFile file){ BaseResponse
		 * baseResponse=downloadCornerService.updateSingleDownloadCorner(fileId, file);
		 * return new ResponseEntity<>(baseResponse,HttpStatus.ACCEPTED); }
		 */

	/**
	 * @author riaz_netizen
	 * @since 01-07-2020
	 * @param fileId
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BaseResponse> deleteDownloadCorner(@RequestParam Long fileId) {
		BaseResponse baseResponse = downloadCornerService.deleteDownloadCorner(fileId);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}

	/**
	 * @author riaz_netizen
	 * @since 01-07-2020
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> downloadCornerList(@RequestParam Long cmsId) {
		ItemResponse itemResponse = downloadCornerService.searchDownloadCornerList(cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}

	/**
	 * @author riaz_netizen
	 * @since 07-07-2020
	 * @param fileId
	 * @return
	 */
	@GetMapping(value = "/file/find")
	public ResponseEntity<?> findDownloadCornerFile(@RequestParam Long fileId) {
		return new ResponseEntity<>(downloadCornerService.findDownloadCornerFile(fileId), HttpStatus.FOUND);
	}

}
