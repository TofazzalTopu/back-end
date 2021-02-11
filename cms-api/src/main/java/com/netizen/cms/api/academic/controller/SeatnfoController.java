package com.netizen.cms.api.academic.controller;

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
import com.netizen.cms.api.academic.model.request.SeatInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.SeatInfoUpdateRequest;
import com.netizen.cms.api.academic.service.SeatInfoService;
import com.netizen.cms.api.common.response.BaseResponse;


@RestController
@RequestMapping("/seat-info")
public class  SeatnfoController {
	
	@Autowired
	private SeatInfoService seatInfoService;
	
	
	@PostMapping("/save")
	public ResponseEntity<BaseResponse> saveSeatInfo(@RequestBody @Valid SeatInfoSaveRequest seatInfoSaveRequest){
		
		BaseResponse response =  seatInfoService.saveSeatInfo(seatInfoSaveRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<BaseResponse> findSeatInfos(@RequestParam Long cmsId){
	
		BaseResponse response =  seatInfoService.findSeatInfos(cmsId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<BaseResponse> updatedressInfo(@RequestBody @Valid SeatInfoUpdateRequest seatInfoUpdateRequest){
		
		BaseResponse response =  seatInfoService.updateSeatInfo(seatInfoUpdateRequest);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<BaseResponse> deleteSeatInfo(@RequestParam Long seatId){
		
		BaseResponse response =  seatInfoService.deleteSeatInfo(seatId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}


}
