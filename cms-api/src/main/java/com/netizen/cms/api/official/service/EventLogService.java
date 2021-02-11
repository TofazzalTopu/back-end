package com.netizen.cms.api.official.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.ApplicationUtils;
import com.netizen.cms.api.official.model.entity.EventLog;
import com.netizen.cms.api.official.model.request.EventLogSaveRequest;
import com.netizen.cms.api.official.model.request.EventLogUpdateRequest;
import com.netizen.cms.api.official.model.response.EventLogViewResponse;
import com.netizen.cms.api.official.repository.EventLogRepository;

@Service
public class EventLogService {
	@Autowired
	private EventLogRepository eventLogRepository;
	
	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse saveEventLog(EventLogSaveRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		EventLog eventLog = new EventLog();

		eventLog.setCmsInfo(cmsInfo);
		eventLog.setEventSerial(request.getEventSerial());
		eventLog.setEventTitle(request.getEventTitle());
		eventLog.setEventType(request.getEventType());
		eventLog.setEventDetails(request.getEventDetails());
		eventLog.setEventStartDate(request.getEventStartDate());
		eventLog.setEventEndDate(request.getEventEndDate());
		eventLog.setEventStatus(1);

		eventLogRepository.save(eventLog);

		baseResponse.setMessage("Event Log Successfully Saved.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updateEventLog(EventLogUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		EventLog eventLog = eventLogRepository.findByEventIdAndCmsInfo(request.getEventId(), cmsInfo);

		if (eventLog == null) {
			baseResponse.setMessage("No event found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		eventLog.setEventSerial(request.getEventSerial());
		eventLog.setEventTitle(request.getEventTitle());
		eventLog.setEventType(request.getEventType());
		eventLog.setEventDetails(request.getEventDetails());
		eventLog.setEventStartDate(request.getEventStartDate());
		eventLog.setEventEndDate(request.getEventEndDate());

		baseResponse.setMessage("Event Info Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @param eventId
	 * @return
	 */
	@Transactional
	public BaseResponse deleteEventLog(Long eventId) {

		BaseResponse baseResponse = new BaseResponse();

		EventLog eventLog = eventLogRepository.findById(eventId).get();

		if (eventLog == null) {
			baseResponse.setMessage("No event found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		eventLogRepository.delete(eventLog);

		baseResponse.setMessage("Event log Successfully deleted.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	
	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse showEventLogList(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<EventLog> eventLogs = eventLogRepository.findByCmsInfo_cmsIdOrderByEventSerialAsc(cmsId);

		List<EventLogViewResponse> responses = new ArrayList<>();

		for (EventLog log : eventLogs) {
			EventLogViewResponse response = new EventLogViewResponse();
			copyEventLogToEventLogViewResponse(log, response);
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}
	

	public void copyEventLogToEventLogViewResponse(EventLog eventLog, EventLogViewResponse eventLogViewResponse) {

		eventLogViewResponse.setEventId(eventLog.getEventId());
		eventLogViewResponse.setEventSerial(eventLog.getEventSerial());
		eventLogViewResponse.setEventTitle(eventLog.getEventTitle());
		eventLogViewResponse.setEventType(eventLog.getEventType());
		eventLogViewResponse.setEventDetails(eventLog.getEventDetails());
		
		if(eventLog.getEventStartDate() != null) {
			eventLogViewResponse.setEventStartDate(ApplicationUtils.LOCAL_DATETIME_FORMAT.format(eventLog.getEventStartDate()));  
		}
		
		if(eventLog.getEventEndDate() != null) {
			eventLogViewResponse.setEventEndDate(ApplicationUtils.LOCAL_DATETIME_FORMAT.format(eventLog.getEventEndDate()));  
		}

		eventLogViewResponse.setEventStatus(eventLog.getEventStatus());
		eventLogViewResponse.setTotalDay(ApplicationUtils.getDiffOfDays(eventLog.getEventStartDate(), eventLog.getEventEndDate()) +1);

	}

}
