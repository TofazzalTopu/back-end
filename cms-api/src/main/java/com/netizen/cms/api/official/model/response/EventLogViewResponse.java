package com.netizen.cms.api.official.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventLogViewResponse {
	
	private Long eventId;

	private int eventSerial;
	
	private String eventTitle;

	private String eventDetails;

	private String eventType;

	private String eventStartDate;

	private String eventEndDate;
	
	private int eventStatus;
	
	private long totalDay;

}
