package com.netizen.cms.api.official.controller;

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
import com.netizen.cms.api.official.model.request.SpeechInfoSaveRequest;
import com.netizen.cms.api.official.model.request.SpeechInfoUpdateRequest;
import com.netizen.cms.api.official.service.SpeechInfoService;

@RestController
@RequestMapping(value = "/speech/info")
public class SpeechInfoController {
	@Autowired
	public SpeechInfoService speechInfoService;

	/**
	 * @author riaz_netizen
	 * @since 25-06-2020
	 * @param requesSave
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveSpeechInfo(@RequestBody @Valid SpeechInfoSaveRequest requesSave) {
		BaseResponse baseResponse = speechInfoService.saveSpeechInfo(requesSave);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * @author riaz_netizen
	 * @since 25-06-2020
	 * @param requestUpdate
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateSpeechInfo(@RequestBody @Valid SpeechInfoUpdateRequest requestUpdate) {
		BaseResponse baseResponse = speechInfoService.updateSpeechInfo(requestUpdate);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * @author riaz_netizen
	 * @since 05-07-2020
	 * @param speechId
	 * @param file
	 * @return
	 *//*
		 * @PostMapping(value = "/photo/update") public ResponseEntity<BaseResponse>
		 * updateSpeakerPhoto(@RequestParam Long speechId,@RequestParam MultipartFile
		 * file){ BaseResponse
		 * baseResponse=speechInfoService.updateSingleSpeakerPhoto(speechId, file);
		 * return new ResponseEntity<>(baseResponse,HttpStatus.ACCEPTED); }
		 */
	/**
	 * @author riaz_netizen
	 * @since 25-06-2020
	 * @param speechId
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BaseResponse> deleteSpeechInfo(@RequestParam Long speechId) {
		BaseResponse baseResponse = speechInfoService.deleteSpeechInfo(speechId);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * @author riaz_netizen
	 * @since 25-06-2020
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> speechInfoList(@RequestParam Long cmsId) {
		ItemResponse itemResponse = speechInfoService.showSpeechInfoList(cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	@GetMapping(value = "/welcome/speechs")
	public ResponseEntity<?> findWelcomeSpeech(@RequestParam Long cmsId) {
			
		return new ResponseEntity<>(speechInfoService.findWelcomeSpeech(cmsId), HttpStatus.OK);
	}

}
