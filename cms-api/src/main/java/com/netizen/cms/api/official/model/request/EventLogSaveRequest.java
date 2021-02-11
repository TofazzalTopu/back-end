package com.netizen.cms.api.official.model.request;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventLogSaveRequest {

	private int eventSerial;

	@NotEmpty
	private String eventTitle;

	@NotEmpty
	private String eventDetails;

	@NotEmpty
	private String eventType;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date eventStartDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date eventEndDate;
	
	@NotNull
	private Long cmsId;
}
