package com.netizen.cms.api.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.academic.service.BookInfoService;
import com.netizen.cms.api.academic.service.ClassInfoService;
import com.netizen.cms.api.academic.service.DressInfoService;
import com.netizen.cms.api.academic.service.FeeInfoService;
import com.netizen.cms.api.academic.service.SeatInfoService;
import com.netizen.cms.api.academic.service.SyllabusInfoService;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;

@RestController
@RequestMapping("/public")
public class PublicAcademicController {

	@Autowired
	public DressInfoService dressInfoService;
	@Autowired
	private SyllabusInfoService syllabusInfoService;
	@Autowired
	private SeatInfoService seatInfoService;
	@Autowired
	private FeeInfoService feeInfoService;
	@Autowired
	public BookInfoService bookInfoService;
	@Autowired
	public ClassInfoService classInfoService;
	
	/**
	 * @author riaz_netizen
	 * @since 19-07-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/classes")
	public ResponseEntity<ItemResponse> classInfoList(@RequestParam Long cmsId){
		ItemResponse itemResponse=classInfoService.classInfoList(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}

	
	/**
	 * @author riaz_netizen
	 * @since 08-07-2020
	 * @param gender
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/genderWise/dress")
	public ResponseEntity<ItemResponse> getGenderWiseDressList(@RequestParam String gender,@RequestParam Long cmsId){
		ItemResponse itemResponse=dressInfoService.findGendeWiseDressListForClient(gender, cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 16-07-2020
	 * @param cmsId
	 * @return
	 */
	@GetMapping("/syllabus")
	public ResponseEntity<BaseResponse> findSyllabusInfos(@RequestParam Long cmsId){
		BaseResponse response =  syllabusInfoService.findSyllabusInfos(cmsId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 16-07-2020
	 * @param cmsId
	 * @return
	 */
	@GetMapping("/seats")
	public ResponseEntity<BaseResponse> findSeatInfos(@RequestParam Long cmsId){	
		BaseResponse response =  seatInfoService.findSeatInfos(cmsId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 16-07-2020
	 * @param cmsId
	 * @return
	 */
	@GetMapping("/classWise/fees")
	public ResponseEntity<BaseResponse> findFeeInfos(@RequestParam Long classId,@RequestParam Long cmsId){
		BaseResponse response =  feeInfoService.findClassWiseFeesInfo(classId,cmsId);		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @param classId
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/classWise/book")
	public ResponseEntity<ItemResponse> getBookInfoList(@RequestParam Long classId,@RequestParam Long cmsId){
		ItemResponse itemResponse=bookInfoService.searchClassWiseBookList(classId, cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
}
