package com.netizen.cms.api.official.controller;

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
import org.springframework.web.multipart.MultipartFile;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.official.model.request.NoticeInfoSaveRequest;
import com.netizen.cms.api.official.model.request.NoticeInfoUpdateRequest;
import com.netizen.cms.api.official.service.NoticeInfoService;

@RestController
@RequestMapping(value = "/notice/info")
public class NoticeInfoController {
	@Autowired
	public NoticeInfoService noticeInfoService;

	/**
	 * @author riaz_netizen
	 * @since 28-06-2020
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveNoticeInfo(@RequestBody @Valid NoticeInfoSaveRequest request) {
		BaseResponse baseResponse = noticeInfoService.saveNoticeInfo(request);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * @author riaz_netizen
	 * @since 28-06-2020
	 * @param updateRequest
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateNoticeInfo(@RequestBody @Valid NoticeInfoUpdateRequest updateRequest) {
		BaseResponse baseResponse = noticeInfoService.updateNoticeInfo(updateRequest);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}
	
	/**
	 * @author riaz_netizen 
	 * @since 05-07-2020
	 * @param noticeId
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/photo/update")
	public ResponseEntity<BaseResponse> updateSpeakerPhoto(@RequestParam Long noticeId,@RequestParam MultipartFile file){
		BaseResponse baseResponse=noticeInfoService.updateSingleNoticeFile(noticeId, file);
		return new ResponseEntity<>(baseResponse,HttpStatus.ACCEPTED);
	}

	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/enable/list")
	public ResponseEntity<ItemResponse> enableNoticeInfoList(@RequestParam Long cmsId) {
			
		ItemResponse itemResponse = noticeInfoService.findEnableNoticeInfoListForAdmin(cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/expired-disable-enable/list")
	public ResponseEntity<ItemResponse> findExpiredDisableEnableNoticeInfoList(@RequestParam Long cmsId) {
		ItemResponse itemResponse = noticeInfoService.findExpiredDisableOrEnableNoticeInfoList(cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 07-07-2020
	 * @param noticeId
	 * @return
	 */
	@GetMapping(value = "/file/find")
	public ResponseEntity<?> findNoticeFile(@RequestParam Long noticeId){
		return new ResponseEntity<>(noticeInfoService.findNoticeFile(noticeId),HttpStatus.FOUND);
	}

}
