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
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.official.model.request.EventLogSaveRequest;
import com.netizen.cms.api.official.model.request.EventLogUpdateRequest;
import com.netizen.cms.api.official.service.EventLogService;

@RestController
@RequestMapping(value = "/event/log")
public class EventLogController {
	
	@Autowired
	public EventLogService eventLogService;

	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveMemberInfo(@RequestBody @Valid EventLogSaveRequest request) {
		BaseResponse baseResponse = eventLogService.saveEventLog(request);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @param updateRequest
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateMemberInfo(@RequestBody @Valid EventLogUpdateRequest updateRequest) {
		BaseResponse baseResponse = eventLogService.updateEventLog(updateRequest);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}

	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @param eventId
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BaseResponse> deleteEvent(@RequestParam Long eventId) {
		BaseResponse baseResponse = eventLogService.deleteEventLog(eventId);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> eventLogList(@RequestParam Long cmsId){
	
		ItemResponse itemResponse=eventLogService.showEventLogList(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}

}
